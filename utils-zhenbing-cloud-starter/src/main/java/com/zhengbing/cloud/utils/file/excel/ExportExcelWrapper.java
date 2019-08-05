package com.zhengbing.cloud.utils.file.excel;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Collection;

/**
 * @Description:
 * @author: zhengbing_vendor
 * @date: 2019/8/2
 */
public class ExportExcelWrapper<T> extends ExportExcelUtil<T> {


    /**
     * <p>
     * 导出带有头部标题行的Excel <br> 提供下载，使用response返回给客户端
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * </p>
     *
     * @param sheetName sheet名称
     * @param headers 头部标题集合
     * @param dataset 数据集合
     * @paran response Http响应对象
     */
    public void exportExcel( String fileName, String sheetName, String[] headers, Collection<T> dataset, HttpServletResponse response) {
        try {
            String extendName = ExportExcelUtil.EXCEL_FILE_EXTEND_NAME_03;
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + extendName);
            export(sheetName, headers, dataset, response.getOutputStream(), ExportExcelUtil.PATTERN_DATE_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
