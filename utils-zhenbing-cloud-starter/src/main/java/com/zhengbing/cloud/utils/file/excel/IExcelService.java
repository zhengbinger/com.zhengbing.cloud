package com.zhengbing.cloud.utils.file.excel;

import org.apache.poi.ss.formula.functions.T;

import java.io.OutputStream;
import java.util.Collection;

/**
 * @Description: excel 服务接口
 * @author: zhengbing_vendor
 * @date: 2019/8/3
 */
public interface IExcelService {

    /**
     * 传入  标题列定义数组和需要导出的数据
     * @param data 导出的数据
     * @param out 输出流
     */
    void export ( Collection< Object > data, OutputStream out );

    /**
     * 传入  标题列定义数组和需要导出的数据
     * @param headers  标题列,如：["姓名"，"年龄"]
     * @param data 导出的数据
     * @param out 输出流
     */
    void export ( String[] headers, Collection< Object > data, OutputStream out );


    /**
     * 传入  标题列定义数组和需要导出的数据
     * @param sheetName sheet名称自定义
     * @param headers  标题列,如：["姓名"，"年龄"]
     * @param data 导出的数据
     * @param out 输出流
     */
    void export ( String sheetName, String[] headers, Collection< Object > data, OutputStream out );

    /**
     * <p>
     * 导出带有头部标题行的Excel <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * </p>
     *
     * @param sheetName 表格标题
     * @param headers 头部标题集合
     * @param dataset 数据集合
     * @param out 输出流
     * @param version 2003 或者 2007，不传时默认生成2003版本
     */
    void export ( String sheetName, String[] headers, Collection< T > dataset, OutputStream out, String version );
}
