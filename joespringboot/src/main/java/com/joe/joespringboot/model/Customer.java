package com.joe.joespringboot.model;

import java.io.Serializable;

public class Customer implements Serializable {
    private Integer sysno;

    private String username;

    private Integer age;

    private String sex;

    private String address;

    private byte[] registiterDate;

    private static final long serialVersionUID = 1L;

    public Integer getSysno() {
        return sysno;
    }

    public void setSysno(Integer sysno) {
        this.sysno = sysno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public byte[] getRegistiterDate() {
        return registiterDate;
    }

    public void setRegistiterDate(byte[] registiterDate) {
        this.registiterDate = registiterDate;
    }
}