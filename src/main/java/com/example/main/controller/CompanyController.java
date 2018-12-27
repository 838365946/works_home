package com.example.main.controller;

import com.example.main.model.Company;
import com.example.main.model.Delivery;
import com.example.main.model.Message;
import com.example.main.model.Resume;
import com.example.main.service.CompanyService;
import com.example.main.util.CompanyIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    private CompanyIO companyIO = new CompanyIO();

    private Message message = new Message();

    @RequestMapping(value = "/addcompanydata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Message companyRegistered(@Param("files") MultipartFile[] files, @Param("logo") MultipartFile logo, Company company,HttpServletRequest request) {
        String des = company.getC_des();
        company.setC_des("上传中");
        Company company1 = companyService.registered(company);
        String imgpath = companyIO.UploadImg(files, company1.getC_id());
        String logopath = companyIO.LogoUpload(logo, company1.getC_id());
        try {
            String despath = companyIO.WriteDes(des, company1.getC_id());
            company1.setC_des(despath);
            company1.setC_img(imgpath);
            company1.setLogopath(logopath);
            company1.setC_check_status("未审核");
            companyService.registered(company1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            companyIO.WriteDes(des, company1.getC_id());
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.setB(true);
        message.setDes("完善资料成功，请等待审核");
        return message;
    }

    @RequestMapping(value = "/updatecompanydata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Message Updatecompanydata(@Param("files") MultipartFile[] files, @Param("logo") MultipartFile logo, Company company,HttpServletRequest request) throws IOException {
        String des=company.getC_des();
        company.setC_des("修改中");
        Company company1= (Company) request.getSession().getAttribute("company");
        String imgpath,despath,logopath;
        if (files!=null){
            File file=new File(System.getProperty("user.dir")+"/src/main/resources/static/img/" + company1.getC_id());
            if (file.exists()){
                if (file.isDirectory()){
                    file.delete();
                }
            }
            imgpath = companyIO.UploadImg(files, company1.getC_id());
company1.setC_img(imgpath);
        }
        try {
            despath=companyIO.WriteDes(des,company1.getC_id());
            company1.setC_des(despath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!logo.isEmpty()){
            File file=new File(System.getProperty("user.dir")+"/src/main/resources/static/logo/" + company1.getC_id());
            if (file.exists()){
                if (file.isDirectory()){
                    file.delete();
                }
            }
            logopath=companyIO.LogoUpload(logo,company1.getC_id());
            company1.setLogopath(logopath);
        }
        company1.setC_addr(company.getC_addr());
        company1.setC_name(company.getC_name());
        company1.setC_welfare(company.getC_welfare());
        company1.setC_industry(company.getC_industry());
        company1.setC_scale(company.getC_scale());
        company1.setC_check_status("未审核");
        Company company2=companyService.addcompany(company1);
        if (company2==null){
        message.setB(false);
        message.setDes("修改失败");

        }else {
            message.setB(true);
            message.setDes("修改成功");
            message.setData(company2);
        }
        return message;
    }

    @RequestMapping(value = "/pushViedoListToWeb", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    Map<String, Object> pushViedoListToWeb(@RequestBody Map<String, Object> param) {

        Map<String, Object> result = new HashMap<String, Object>();

        WebSocketServer.sendInfo("新客户呼入" + param);
        result.put("operationResult", true);
        return result;
    }

    @RequestMapping("/Audit_company")
    public Message audit(Company company) {
        Message message = new Message();
        List<Company> companies=companyService.CheckCompany("未审核");
        if (companies.size()>0){
            message.setB(true);
            message.setDes("获取未审核公司成功");
            message.setData(companies);
        }else {
            message.setB(false);
            message.setDes("获取未审核公司失败");
        }
        return message;
    }

    @RequestMapping("/Audit_record")
    public List<Company> record(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Company> companies = companyService.findall(pageRequest);
        List<Company> companies1 = null;
        if (companies.getContent() != null) {
            companies1 = companies.getContent();
        }
        return companies1;
    }


    @RequestMapping("/showcompmess")
    @ResponseBody
    public Message show(int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        Page<Company> companies = companyService.findall(pageRequest);
        List<Company> companies1 = null;
        if (companies.getContent() != null) {
            companies1 = companies.getContent();
        }
        if(companies1.size()>0){
            message.setB(true);
            message.setDes("获取成功");
            message.setData(companies1);
        }else {
            message.setB(false);
            message.setDes("获取失败");
        }
        return message;
    }

    @RequestMapping("/clogin")
    public ModelAndView CompanyLogin(Company company, HttpServletRequest request){

        System.out.println("公司进入了");
        ModelAndView modelAndView = new ModelAndView();
        Company company1 = companyService.CLogin(company);
        System.out.println(company1.toString());
      if(company1!=null){
          request.getSession().setAttribute("company",company1);
          modelAndView.addObject("name",company1.getC_name());
          modelAndView.setViewName("main");
      }else {
          modelAndView.setViewName("error");
      }
        return modelAndView;
    }
    @RequestMapping("/creg")
    public ModelAndView Reg(Company company){
        ModelAndView modelAndView=new ModelAndView();
        Company company1=companyService.addcompany(company);
        if (company1!=null){
            modelAndView.setViewName("login");
        }else {
            modelAndView.setViewName("error");
        }return modelAndView;
    }

    @RequestMapping("/readresume")
    @ResponseBody
    public Message test(HttpServletRequest request){

        Company company= (Company) request.getSession().getAttribute("company");
        List<Resume> resumes=new ArrayList<Resume>();
        for (Delivery d:company.getDeliveries()){
            for (Resume r:d.getUser().getResumes()){
                System.out.println(r.getR_id());
                resumes.add(r);
            }
        }message.setB(true);
        message.setDes("获取成功");
        message.setData(resumes);
        return message;
    }
}







