package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

public class CustomerForm {
    private Long id;
    private String name;
    private String age;
    private MultipartFile img;
    private Province province;

    public CustomerForm() {
    }

    public CustomerForm(Long id, String name, String age, MultipartFile img, Province province) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.img = img;
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
