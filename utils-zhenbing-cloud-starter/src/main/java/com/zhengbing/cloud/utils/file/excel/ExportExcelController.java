package com.zhengbing.cloud.utils.file.excel;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: zhengbing_vendor
 * @date: 2019/8/2
 */
@Controller
@RequestMapping("/test")
public class ExportExcelController {

    @Autowired
    private ExportExcelUtil< T > exportExcelUtil;

    @GetMapping("/get/excel")
    public void getExcel( HttpServletRequest request, HttpServletResponse response,
                          @RequestParam String fileName,
                          @RequestParam String[] headers ) throws Exception {
            // 准备数据
          List<UserModel> list = new ArrayList<>();
          headers = new String[]{ "员工ID", "姓名", "年龄" };
          fileName = "excel1";
          ExportExcelWrapper<UserModel> util = new ExportExcelWrapper<UserModel>();
          util.exportExcel(fileName, fileName, headers, list, response, ExportExcelUtil.EXCEL_FILE_2003);
     }
}
