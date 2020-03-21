package com.zhengbing.cloud.configurecenter;

import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhengbing
 * @descript
 * @date 2019-08-17
 * @email mydreambing@126.com
 * @since version 1.0
 */
public class Test {

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2019, 07, 29);

        System.out.println(DateUtils.addDays(calendar.getTime(), 3));
        System.out.println(DateUtils.addDays(calendar.getTime(), -3));
        System.out.println(1 % 30);
        System.out.println(calendar.hashCode());
    }
}
