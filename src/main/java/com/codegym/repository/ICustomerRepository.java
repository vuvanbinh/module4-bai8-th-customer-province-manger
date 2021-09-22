package com.codegym.repository;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ICustomerRepository extends PagingAndSortingRepository<Customer,Long> {
    Iterable<Customer> findAllByProvince(Province province);
    Page<Customer> findAllByNameContaining (String name, Pageable pageable);
    Page<Customer> findAll(Pageable pageable);
}
