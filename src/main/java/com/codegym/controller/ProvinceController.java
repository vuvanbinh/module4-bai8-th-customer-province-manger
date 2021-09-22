package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/provinces")
public class ProvinceController {
    @Autowired
   private IProvinceService provinceService;
    @Autowired
   private ICustomerService customerService;


    @GetMapping("")
    public ModelAndView showListProvince(){
        Iterable<Province> provinces = provinceService.findAll();
        ModelAndView mav = new ModelAndView("province/list");
        mav.addObject("provinces",provinces);
        return mav;
    }

    @GetMapping("create")
    public ModelAndView showFormCreate(){
        ModelAndView mav = new ModelAndView("province/create");
        mav.addObject("province",new Province());
        return mav;
    }

//    @PostMapping("create")
//    public ModelAndView createProvince (@Valid @ModelAttribute("province") Province province,BindingResult bindingResult ){
//
//        if (bindingResult.hasErrors()){
//            return new ModelAndView("/province/create");
//        }
//
//        provinceService.save(province);
//        return new ModelAndView("redirect:/provinces");
//    }


    @PostMapping("create")
    public String createProvince(@Validated @ModelAttribute("province") Province province, RedirectAttributes redirect,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/province/create";
        }
        provinceService.save(province);
        redirect.addFlashAttribute("message","New province created successfully");
        return "redirect:/provinces";
    }

    @GetMapping("{id}/edit")
    public ModelAndView showFormEdit(@PathVariable("id") Optional<Province> province){
        if(province.isPresent()){
            ModelAndView mav = new ModelAndView("province/edit");
            mav.addObject("province",province.get());
        return mav;
        }else {
            ModelAndView mav = new ModelAndView("error.404");
            return mav;
        }
    }
    @PostMapping("edit")
    public String editProvince(@ModelAttribute Province province,RedirectAttributes redirect){
        provinceService.save(province);
        redirect.addFlashAttribute("message","Province edit successfully");
        return "redirect:/provinces";
    }

    @GetMapping("{id}/delete")
    public ModelAndView showFormDelete(@PathVariable("id") Optional<Province> province){
        if(province.isPresent()){
        ModelAndView mav = new ModelAndView("province/delete");
        mav.addObject("province",province.get());
        return mav;
        }else {
            ModelAndView mav = new ModelAndView("error.404");
            return mav;
        }
    }
    @PostMapping("delete")
    public String deleteProvince(@ModelAttribute Province province, RedirectAttributes redirect){
        provinceService.remove(province.getId());
        redirect.addFlashAttribute("message","Delete province successfully");
        return "redirect:/provinces";
    }

    @GetMapping("{id}/detail")
    public ModelAndView showProvinceDetail(@PathVariable("id") Optional<Province> province){
        if(province.isPresent()){
            ModelAndView mav = new ModelAndView("province/detail");
            Iterable<Customer> customers = customerService.findAllByProvince(province.get());

            mav.addObject("province",province.get());
            mav.addObject("customers",customers);
            return mav;
        }else {
            ModelAndView mav = new ModelAndView("error.404");
            return mav;
        }
    }

}
