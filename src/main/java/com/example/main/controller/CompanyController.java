package com.example.main.controller;

import com.example.main.model.Company;
import com.example.main.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @RequestMapping("/companyRegistered")
    @ResponseBody
    public Company companyRegistered(Company company){
Company company1=companyService.registered(company);
return company1;


        }


}