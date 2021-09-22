package com.codegym.service.customer;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IService<Customer> {
    Iterable<Customer> findAllByProvince(Province province);
    Page<Customer> findAllByNameContaining (String name, Pageable pageable);
    Page<Customer> findAll(Pageable pageable);

}
