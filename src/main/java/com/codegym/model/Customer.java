package com.codegym.model;

import javax.persistence.*;

@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    private String img;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    public Customer() {
    }

    public Customer(Long id, String name, String age, Province province,String img) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.province = province;
        this.img=img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getId() {
        return id;
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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
