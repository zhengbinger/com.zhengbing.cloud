package com.zhengbing.cloud.utils.file.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 导出数据到Excel工具类
 * @author: zhengbing_vendor
 * @date: 2019/8/2
 */
@Component
public class ExportExcelUtil<T> {

    /**
     * 2007 版本以上 最大支持1048576行
     */
    public  final static String  EXCEL_FILE_2007 = "2007";
    /**
     * 2003 版本 最大支持65536 行
     */
    public  final static String  EXCEL_FILE_2003 = "2003";

    /**
     * 03版excel 扩展名
     */
    public static final String EXCEL_FILE_EXTEND_NAME_03 = ".xls";

    /**
     * 07版excel 扩展名
     */
    public static final String EXCEL_FILE_EXTEND_NAME_07 = ".xlsx";

    /**
     * 时间日期格式
     */
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式
     */
    public static final String PATTERN_DATE = "yyyy-MM-dd";

    /**
     * 正则匹配规则
     */
    public static final String REG_PATTERN = "^//d+(//.//d+)?$";

    /**
     * <p>
     * 导出无标题行，不指定sheet名称，默认sheet名称为sheet1 <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * 默认使用版本office 97-03
     * </p>
     *
     * @param dataset 数据集合
     * @param out 输出流
     */
    public void export( Collection<T> dataset, OutputStream out) {
        export( "",dataset, out);
    }

        /**
         * <p>
         * 导出无头部标题行Excel <br>
         * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
         * </p>
         *
         * @param sheetName sheet名称
         * @param dataset 数据集合
         * @param out 输出流
         */
        public void export( String sheetName, Collection<T> dataset, OutputStream out) {
            export(sheetName, null, dataset, out, PATTERN_DATE_TIME);
        }

    /**
     * <p>
     * 导出带标题行，不指定sheet名称，默认sheet名称为sheet1 <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * 默认使用版本office 97-03
     * </p>
     *
     * @param headers 标题行信息
     * @param dataset 数据集合
     * @param out 输出流
     */
    public void export(String[] headers,Collection<T> dataset, OutputStream out) {
        export(null, headers,dataset, out );
    }

    /**
     * <p>
     * 导出带有头部标题行的Excel <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * </p>
     *
     * @param headers 头部标题集合
     * @param dataset 数据集合
     * @param out 输出流
     */
    public void export(String sheetName,String[] headers, Collection<T> dataset, OutputStream out) {
        export( sheetName,headers, dataset, out,PATTERN_DATE_TIME );
    }


    public void export(String sheetName,String[] headers, Collection<T> dataset, OutputStream out,String dateTimePattern) {
        exportExcel(sheetName, headers, dataset, out, dateTimePattern);
    }

    /**
     * <p>
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此方法生成2003版本的excel,文件名后缀：xls <br>
     * </p>
     *
     * @param title
     *            表格标题名
     * @param headers
     *            表格头部标题集合
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *            JavaBean属性的数据类型有基本数据类型及String,Date
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet;
        if ( null == title ){
            sheet = workbook.createSheet("sheet1");
        }else {
            sheet = workbook.createSheet(title);
        }

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);

        // 填充表格标题
        fillHeader(workbook,sheet,headers);
        fillData( workbook,sheet,dataset,pattern );

        try {
            workbook.write(out);
        } catch ( IOException e) {
            e.printStackTrace();
        }finally {
            try{
                out.close();
            }catch ( IOException e ){
                e.printStackTrace();
            }

        }
    }

    /**
     * 填充标题
     *
     * @param workbook
     * @param headers
     */
    private void fillHeader(HSSFWorkbook workbook,HSSFSheet sheet,String[] headers){

        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor( IndexedColors.GREY_50_PERCENT.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setColor( IndexedColors.WHITE.index);
        font.setFontHeightInPoints((short) 11);
        headerStyle.setFont(font);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cellHeader;
        if ( null != headers && headers.length>0 ){
            for (int i = 0; i < headers.length; i++) {
                cellHeader = row.createCell(i);
                cellHeader.setCellStyle(headerStyle);
                cellHeader.setCellValue(new HSSFRichTextString(headers[i]));
            }
        }
    }

    /**
     * 填充数据
     * @param workbook
     * @param sheet
     * @param dataset
     * @param datePattern
     */
    private void fillData(HSSFWorkbook workbook,HSSFSheet sheet,Collection<T> dataset,String datePattern ){

        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        contentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment( VerticalAlignment.CENTER);
        HSSFFont contentFont = workbook.createFont();
        contentFont.setBold(false);
        contentStyle.setFont(contentFont);

        Iterator<T> datas = dataset.iterator();
        int index = 0;
        Field field;
        String fieldName;
        String getMethodName;
        Class clazz;
        Method getMethod;
        Object value;
        while (datas.hasNext()) {
            index++;
            HSSFRow row = sheet.createRow(index);
            T data = datas.next();
            // 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = data.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(contentStyle);
                field = fields[i];
                fieldName = field.getName();
                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    clazz = data.getClass();
                    getMethod = clazz.getMethod( getMethodName, new Class[]{} );
                    value = getMethod.invoke( data, new Object[]{} );
                    SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                    Pattern dataPattern = Pattern.compile( REG_PATTERN );
                    String textValue ="";
                    if ( value instanceof Integer ){
                        cell.setCellValue( ( Integer ) value );
                    } else if ( value instanceof Float ){
                        textValue = String.valueOf( ( Float ) value );
                        cell.setCellValue( textValue );
                    } else if ( value instanceof Double ){
                        textValue = String.valueOf( ( Double ) value );
                        cell.setCellValue( textValue );
                    } else if ( value instanceof Long ){
                        cell.setCellValue( ( Long ) value );
                    } else{
                        if ( value instanceof Boolean ){
                            textValue = "是";
                            if ( !( Boolean ) value ){
                                textValue = "否";
                            }
                        } else if ( value instanceof Date ){
                            textValue = sdf.format( ( Date ) value );

                        } else {
                            // 其它数据类型都当作字符串简单处理
                            if ( value != null ){
                                textValue = value.toString();
                            }
                        }
                        if ( textValue != null ){
                            Matcher matcher = dataPattern.matcher( textValue );
                            if ( matcher.matches() ){
                                // 是数字当作double处理
                                cell.setCellValue( Double.parseDouble( textValue ) );
                            } else {
                                HSSFRichTextString richString = new HSSFRichTextString( textValue );
                                cell.setCellValue( richString );
                            }
                        }
                    }
                }  catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch ( IllegalAccessException e ) {
                    e.printStackTrace();
                } catch ( InvocationTargetException e ) {
                    e.printStackTrace();
                }
            }
        }
    }
}