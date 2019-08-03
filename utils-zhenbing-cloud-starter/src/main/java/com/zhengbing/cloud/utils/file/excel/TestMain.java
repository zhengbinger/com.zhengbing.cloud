package com.zhengbing.cloud.utils.file.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: zhengbing_vendor
 * @date: 2019/8/2
 */
public class TestMain {
        public static void main(String[] args) throws Exception{
            ExportExcelUtil<UserModel> util = new ExportExcelUtil<UserModel>();
            // 准备数据
            List<UserModel> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new UserModel("china","张三asdf",12));
                list.add(new UserModel("sense","李四asd",22));
                list.add(new UserModel("time","王五",44));
            }
            String[] columnNames = { "员工ID", "姓名", "年龄" };
//            util.export(  list, new FileOutputStream("D:/test.xls"));
            util.exportExcel("sheet", columnNames, list, new FileOutputStream( "D:/test.xls"), ExportExcelUtil.PATTERN_DATE_TIME);
        }
    }
