//package com.zhengbing.cloud.starter.component.excel;
//
//import com.zhengbing.cloud.starter.util.GsonUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.Collection;
//
///**
// * 导出excel适配器
// *
// * @author: zhengbing_vendor
// * @date: 2019/8/2
// */
//public class ExcelExportWrapper<T> extends ExcelExport<T> {
//
//    private Logger log = LoggerFactory.getLogger(ExcelExportWrapper.class);
//
//    /**
//     * excel content-type
//     */
//    private static final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
//    /**
//     * excel content-header
//     */
//    private static final String EXCEL_CONTENT_HEADER = "Content-Disposition";
//    /**
//     * excel file format
//     */
//    private static final String EXCEL_CONTENT_NAME_FORMAT = "attachment;filename=";
//
//    /**
//     * 导出报错格式
//     */
//    private static final String EXPORT_ERROR_MSG_FORMAT = "导出失败：{}";
//
//    /**
//     * <p>
//     * 导出带有头部标题行的Excel <br>
//     * 会获取T的所有属性,serialVersionUID默认排除<br>
//     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
//     * </p>
//     *
//     * @param sheetName sheet名称
//     * @param headers   头部标题集合
//     * @param dataset   数据集合
//     * @param response  Http响应对象
//     */
//    public void exportExcelRequest(String fileName, String sheetName, String[] headers, Collection<T> dataset, HttpServletResponse response) {
//        try {
//            response.setContentType(EXCEL_CONTENT_TYPE);
//            response.addHeader(EXCEL_CONTENT_HEADER, EXCEL_CONTENT_NAME_FORMAT + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()) + ExcelExport.EXCEL_FILE_EXTEND_NAME_03);
//            export(sheetName, headers, dataset, response.getOutputStream(), ExcelExport.PATTERN_DATE_TIME);
//        } catch (Exception e) {
//            log.error(EXPORT_ERROR_MSG_FORMAT, GsonUtils.toJsonString(e.getStackTrace()));
//        }
//    }
//
//
//    /**
//     * <p>
//     * 导出带有头部标题行的Excel，指定包含的对象属性 <br>
//     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
//     * </p>
//     *
//     * @param sheetName sheet名称
//     * @param headers   头部标题集合
//     * @param includes  包含属性集合
//     * @param dataset   数据集合
//     * @param response  Http响应对象
//     */
//    public void exportExcelRequestIncludes(String fileName, String sheetName, String[] headers, String[] includes, Collection<T> dataset, HttpServletResponse response) {
//        try {
//            response.setContentType(EXCEL_CONTENT_TYPE);
//            response.addHeader(EXCEL_CONTENT_HEADER, EXCEL_CONTENT_NAME_FORMAT + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()) + ExcelExport.EXCEL_FILE_EXTEND_NAME_03);
//            exportIncludes(sheetName, headers, includes, dataset, response.getOutputStream(), ExcelExport.PATTERN_DATE_TIME);
//        } catch (Exception e) {
//            log.error(EXPORT_ERROR_MSG_FORMAT, GsonUtils.toJsonString(e.getStackTrace()));
//        }
//    }
//
//
//    /**
//     * <p>
//     * 导出带有头部标题行的Excel，指定排除的对象属性 <br>
//     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
//     * </p>
//     *
//     * @param sheetName sheet名称
//     * @param headers   头部标题集合
//     * @param excludes  排除属性集合
//     * @param dataset   数据集合
//     * @param response  Http响应对象
//     */
//    public void exportExcelRequestExcludes(String fileName, String sheetName, String[] headers, String[] excludes, Collection<T> dataset, HttpServletResponse response) {
//        try {
//            response.setContentType(EXCEL_CONTENT_TYPE);
//            response.addHeader(EXCEL_CONTENT_HEADER, EXCEL_CONTENT_NAME_FORMAT + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()) + ExcelExport.EXCEL_FILE_EXTEND_NAME_03);
//            exportExcludes(sheetName, headers, excludes, dataset, response.getOutputStream(), ExcelExport.PATTERN_DATE_TIME);
//        } catch (Exception e) {
//            log.error(EXPORT_ERROR_MSG_FORMAT, GsonUtils.toJsonString(e.getStackTrace()));
//        }
//    }
//
//}
