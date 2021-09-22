package com.codegym.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 10,min = 2)
    private String name;

    @OneToMany(targetEntity = Customer.class)
    private List<Customer> customers;

    public Province() {
    }

    public Province(Long id,@NotEmpty @Size(max = 10,min = 2) String name, List<Customer> customers) {
        this.id = id;
        this.name = name;
        this.customers = customers;
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

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
