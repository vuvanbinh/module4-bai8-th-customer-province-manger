package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.CustomerForm;
import com.codegym.model.Province;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("customers")
@PropertySource("classpath:imageFile.properties")
public class CustomerController {
    @Value("${file-image}")
    private String imageFile;

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("")
    public ModelAndView showCustomerList(@RequestParam("search")Optional<String> search,@PageableDefault(sort = "name",size = 3) Pageable pageable){
        Page<Customer> customers;
        ModelAndView mav = new ModelAndView("customer/list");
        if(search.isPresent()){
            customers = customerService.findAllByNameContaining(search.get(),pageable);
            mav.addObject("back","Back to customer list");
        }else {
            customers = customerService.findAll(pageable);
        }
        mav.addObject("customers",customers);
        return mav;
    }
    @GetMapping("create")
    public ModelAndView showFormCreate(){
        ModelAndView mav = new ModelAndView("customer/create");
        mav.addObject("customer",new CustomerForm());
        return mav;
    }

    @PostMapping("create")
    public String createCustomer(@ModelAttribute CustomerForm customerForm, RedirectAttributes redirect){
        MultipartFile multipartFileImg = customerForm.getImg();
        String fileName = multipartFileImg.getOriginalFilename();

        try {
            FileCopyUtils.copy(multipartFileImg.getBytes(),new File(imageFile+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Customer customer = new Customer();
        customer.setName(customerForm.getName());
        customer.setAge(customerForm.getAge());
        customer.setImg(fileName);
        customer.setProvince(customerForm.getProvince());

        customerService.save(customer);
        redirect.addFlashAttribute("massage","Create new customer successfully!");
        return "redirect:/customers";
    }


    @GetMapping("{id}/edit")
    public ModelAndView showFormUpdate(@PathVariable("id") Optional<Customer> customer){
        ModelAndView mav = new ModelAndView("/customer/edit");
        mav.addObject("customer",customer.get());
        return mav;
    }
    @PostMapping("edit")
    public String UpdateCustomer(@ModelAttribute Customer customer, MultipartFile newImgFile, RedirectAttributes redirect){
        String imgFileName = newImgFile.getOriginalFilename();

        try {
            FileCopyUtils.copy(newImgFile.getBytes(),new File(imageFile+imgFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (imgFileName!=""){
            customer.setImg(imgFileName);
        }
        customerService.save(customer);
        redirect.addFlashAttribute("message","Update customer successfully!");
        return "redirect:/customers";
    }

    @GetMapping("{id}/delete")
    public ModelAndView showDeleteForm(@PathVariable("id") Optional<Customer> customer){
        ModelAndView mav = new ModelAndView("customer/delete");
        mav.addObject("customer",customer.get());
        return mav;
    }
    @PostMapping("delete")
    public String deleteCustomer(@ModelAttribute Customer customer,RedirectAttributes redirect){
        customerService.remove(customer.getId());
        redirect.addFlashAttribute("message","Delete customer successfully!");
        return "redirect:/customers";
    }

    @GetMapping("{id}/detail")
    public ModelAndView showDetailCustomer(@PathVariable("id") Optional<Customer> customer){
        ModelAndView mav = new ModelAndView("customer/detail");
        mav.addObject("customer",customer.get());
        return mav;
    }
}
