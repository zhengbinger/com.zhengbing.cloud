package com.zhengbing.cloud.starter.component.excel;

import com.zhengbing.cloud.starter.util.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 导出数据到Excel工具类
 *
 * @author: zhengbing_vendor
 * @date: 2019/8/2
 */
public class ExcelExport<T> {

    private Logger log = LoggerFactory.getLogger(ExcelExport.class);

    /**
     * 2007 版本以上 最大支持1048576行
     */
    public final static String EXCEL_FILE_2007 = "2007";
    /**
     * 2003 版本 最大支持65536 行
     */
    public final static String EXCEL_FILE_2003 = "2003";

    /**
     * 03版excel 扩展名
     */
    static final String EXCEL_FILE_EXTEND_NAME_03 = ".xls";

    /**
     * 07版excel 扩展名
     */
    public static final String EXCEL_FILE_EXTEND_NAME_07 = ".xlsx";

    /**
     * 时间日期格式
     */
    static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式
     */
    static final String PATTERN_DATE = "yyyy-MM-dd";

    /**
     * 正则匹配规则
     */
    static final String REG_PATTERN = "^//d+(//.//d+)?$";

    /**
     * 序列化中自动生成的字段，导出的时候需要排除此字段
     */
    private final String SERIALVERSIONUID = "serialVersionUID";

    /**
     * <p>
     * 导出无标题行，不指定sheet名称，默认sheet名称为sheet1 <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * 默认使用版本office 97-03
     * </p>
     *
     * @param dataset 数据集合
     * @param out     输出流
     */
    public void export(Collection<T> dataset, OutputStream out) {
        export("", dataset, out);
    }

    /**
     * <p>
     * 导出无头部标题行Excel <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * </p>
     *
     * @param sheetName sheet名称
     * @param dataset   数据集合
     * @param out       输出流
     */
    public void export(String sheetName, Collection<T> dataset, OutputStream out) {
        export(sheetName, null, dataset, out, PATTERN_DATE_TIME);
    }

    /**
     * <p>
     * 导出带标题行，不指定sheet名称，默认sheet名称为sheet1 <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * 默认使用版本 office 97-03
     * </p>
     *
     * @param headers 标题行信息
     * @param dataset 数据集合
     * @param out     输出流
     */
    public void export(String[] headers, Collection<T> dataset, OutputStream out) {
        export(null, headers, dataset, out);
    }

    /**
     * <p>
     * 导出带有头部标题行的Excel <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * </p>
     *
     * @param sheetName sheet名称
     * @param headers   头部标题集合
     * @param dataset   数据集合
     * @param out       输出流
     */
    public void export(String sheetName, String[] headers, Collection<T> dataset, OutputStream out) {
        export(sheetName, headers, dataset, out, PATTERN_DATE_TIME);
    }

    /**
     * 导出带有头部标题行的Excel 指定时间日期格式
     *
     * @param sheetName       sheet名称
     * @param headers         头部标题集合
     * @param dataset         数据集合
     * @param out             输出流
     * @param dateTimePattern 时间日期格式
     */
    public void export(String sheetName, String[] headers, Collection<T> dataset, OutputStream out, String dateTimePattern) {
        exportExcel(sheetName, headers, dataset, null, null, out, dateTimePattern);
    }

    /**
     * 导出数据集中包含指定字段的数据
     *
     * @param sheetName       sheet名称
     * @param headers         头部标题集合
     * @param includes        需要包含的字段集合
     * @param dataset         数据集合
     * @param out             输出流
     * @param dateTimePattern 时间日期格式
     */
    public void exportIncludes(String sheetName, String[] headers, String[] includes, Collection<T> dataset, OutputStream out, String dateTimePattern) {
        exportExcel(sheetName, headers, dataset, includes, null, out, dateTimePattern);
    }

    /**
     * 导出数据集中排除指定字段的数据
     *
     * @param sheetName       sheet名称
     * @param headers         头部标题集合
     * @param excludes        需要排除的字段集合
     * @param dataset         数据集合
     * @param out             输出流
     * @param dateTimePattern 时间日期格式
     */
    public void exportExcludes(String sheetName, String[] headers, String[] excludes, Collection<T> dataset, OutputStream out, String dateTimePattern) {
        exportExcel(sheetName, headers, dataset, null, excludes, out, dateTimePattern);
    }

    /**
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此方法生成2003版本的excel,文件名后缀：xls <br>
     * </p>
     *
     * @param title    表格标题名
     * @param headers  表格头部标题集合
     * @param dataset  需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的JavaBean属性的数据类型有基本数据类型及String,Date
     * @param includes 包含的属性数组
     * @param excludes 排除的属性数组
     * @param out      与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern  如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    public void exportExcel(String title, String[] headers, Collection<T> dataset, String[] includes, String[] excludes, OutputStream out, String pattern) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet;
        // 传入的sheet名称为空时，默认给一个sheet1
        if (null == title) {
            sheet = workbook.createSheet("sheet1");
        } else {
            sheet = workbook.createSheet(title);
        }
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth(20);

        // 填充标题到sheet
        if (null != headers && headers.length > 0) {
            fillHeader(workbook, sheet, headers);
        }
        // 填充数据到sheet
        if (null != includes) {
            fillData(workbook, sheet, includes, null, dataset, pattern);
        } else if (null != excludes) {
            fillData(workbook, sheet, null, excludes, dataset, pattern);
        } else {
            fillData(workbook, sheet, null, null, dataset, pattern);
        }

        try {
            // 将excel文档对象写入到输出流
            workbook.write(out);
        } catch (IOException e) {
            log.error("io message :{},{}", e.getStackTrace(), GsonUtils.toJsonString(e.getStackTrace()));
        } finally {
            try {
                out.close();
                workbook.close();
            } catch (IOException e) {
                log.error("io message :{},{}", e.getStackTrace(), GsonUtils.toJsonString(e.getStackTrace()));
            }
        }
    }

    /**
     * 填充标题
     *
     * @param workbook HSSFWorkbook
     * @param sheet    HSSFSheet
     * @param headers  String[]
     */
    private void fillHeader(HSSFWorkbook workbook, HSSFSheet sheet, String[] headers) {
        // 设置标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setColor(IndexedColors.WHITE.index);
        font.setFontHeightInPoints((short) 11);
        headerStyle.setFont(font);

        // 创建表格标题行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cellHeader;
        if (null != headers && headers.length > 0) {
            // 填充标题栏样式和内容到标题行
            for (int i = 0; i < headers.length; i++) {
                cellHeader = row.createCell(i);
                cellHeader.setCellStyle(headerStyle);
                cellHeader.setCellValue(new HSSFRichTextString(headers[i]));
            }
        }
    }

    /**
     * 跟据包含或排除的属性填充导出数据
     *
     * @param workbook    HSSFWorkbook
     * @param sheet       HSSFSheet
     * @param includes    包含的属性
     * @param excludes    排除的属性
     * @param dataset     数据集
     * @param datePattern 时间格式
     */
    private void fillData(HSSFWorkbook workbook, HSSFSheet sheet, String[] includes, String[] excludes, Collection<T> dataset, String datePattern) {

        // 设置填充内容单元格样式
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        contentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont contentFont = workbook.createFont();
        contentFont.setBold(false);
        contentStyle.setFont(contentFont);


        Iterator<T> datas = dataset.iterator();
        int index = 0;
        while (datas.hasNext()) {

            index++;
            HSSFRow row = sheet.createRow(index);
            T data = datas.next();
            // 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = data.getClass().getDeclaredFields();
            // 排除序列化自动生成的 serialVersionUID 属性字段
            List<Field> fieldList = Arrays.stream(fields).filter(field -> !SERIALVERSIONUID.equals(field.getName())).collect(Collectors.toList());
            // 默认设置剩下的所有属性为 需要导出的属性
            List<String> includesList = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
            // 如果采用的是传入包含字段的形式，设置参数includes中含有的所有字段为导出字段
            if (null != includes) {
                includesList = Arrays.asList(includes);
            } else if (null != excludes) {
                // 如果是排除字段的方式，则在Class对象全量字段的基础上删除，excludes中的字段，
                includesList.removeAll(Arrays.asList(excludes));
            }
            List<String> finalIncludesList = includesList;
//            取includeList 中 在Class对象中存在的字段
            fieldList = fieldList.stream().filter(field -> finalIncludesList.contains(field.getName())).collect(Collectors.toList());
            for (int i = 0; i < includesList.size(); i++) {
                if (includesList.contains(fieldList.get(i).getName())) {
                    int fieldIndex = includesList.indexOf(fieldList.get(i).getName());
                    // 填充内容到单元格，如include方式，则按传入的include字段顺序写入excel
                    fillCell(row, contentStyle, fieldList.get(i), data, datePattern, fieldIndex);
                }
            }
        }
    }

    /**
     * 填充单元格，指定单元格填充索引索引位置
     *
     * @param row         HSSFRow行对象
     * @param cellStyle   单元格样式
     * @param field       属性对象
     * @param data        数据对象
     * @param datePattern 时间日期格式
     * @param index       int(单元格索引)
     */
    private void fillCell(HSSFRow row, CellStyle cellStyle, Field field, T data, String datePattern, int index) {
        String fieldName = field.getName();
        // 创建指定位置的单元格
        HSSFCell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        // 根据属性名称，得到对应的getXxx方法名
        String getMethodName = "get" + StringUtils.capitalize(fieldName);
        try {
            Class<?> clazz = data.getClass();
            // 通过反射得到getXxx 方法
            Method getMethod = clazz.getMethod(getMethodName);
            // 调用方法，得到方法返回值（字段值）
            Object value = getMethod.invoke(data);

            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Pattern dataPattern = Pattern.compile(REG_PATTERN);
            String textValue = null;
            // 对属性值按不同数据类型进行处理转化
            if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Float) {
                textValue = String.valueOf(value);
                cell.setCellValue(textValue);
            } else if (value instanceof Double) {
                textValue = String.valueOf(value);
                cell.setCellValue(textValue);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else {
                if (value instanceof Date) {
                    textValue = sdf.format((Date) value);
                } else if (value != null) {
                    textValue = value.toString();
                }
                if (textValue != null) {
                    Matcher matcher = dataPattern.matcher(textValue);
                    if (matcher.matches()) {
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        cell.setCellValue(richString);
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("填充Excel单元格数据出错{}", GsonUtils.toJsonString(e.getStackTrace()));
        }
    }
}