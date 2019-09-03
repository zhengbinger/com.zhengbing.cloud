package com.zhengbing.cloud.utils.file.excel;

/**
 * @description:
 * @author: zhengbing_vendor
 * @date: 2019/8/2
 */
public class UserModel {

    private String employeeId;
    private String name;
    private int  age;

    public UserModel(String employeeId,String name ,int age){
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
    }

    public String getEmployeeId () {
        return employeeId;
    }

    public void setEmployeeId ( String employeeId ) {
        this.employeeId = employeeId;
    }

    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public int getAge () {
        return age;
    }

    public void setAge ( int age ) {
        this.age = age;
    }
}
