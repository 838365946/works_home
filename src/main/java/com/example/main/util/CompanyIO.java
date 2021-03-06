package com.example.main.util;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
/**
 * Created by Administrator on 2018/12/10.
 */

public class CompanyIO {
    private String propath=System.getProperty("user.dir");
    //写入公司介绍
    public String WriteDes(String des,int c_id) throws IOException {

        File file=new File(propath+"\\src\\main\\resources\\static\\companydes");
        if(file.getParentFile().exists()){
            file.mkdir();
        }
        FileOutputStream fileOutputStream=new FileOutputStream(file.getPath()+"/"+c_id+".txt");
        OutputStreamWriter osw=new OutputStreamWriter(fileOutputStream);
        osw.flush();
        osw.write(des);
        osw.close();
        fileOutputStream.close();
        return propath+"/src/main/resources/static/companydes/"+c_id+".txt";
    }
    //读取公司介绍
    public StringBuffer ReadDes(String filepath){
        StringBuffer sb=new StringBuffer();
        String str=null;
        try {
            System.out.println(filepath+"看");
            Reader reader= new InputStreamReader(new FileInputStream(filepath));
            BufferedReader bf = new BufferedReader(reader);
            while ((str=bf.readLine())!=null){
                sb.append(str);
                System.out.println(str);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }
    //上传公司图片
    public String singleFileUpload(MultipartFile file,int id) {
        if (Objects.isNull(file) || file.isEmpty()) {
            return "文件为空!";
        }
        String allName = file.getOriginalFilename();
        System.out.println("上传的文件名： "+allName);
        boolean b=allName.substring(allName.lastIndexOf(".")).equals(".jpeg");
        boolean b1=allName.substring(allName.lastIndexOf(".")).equals(".jpg");
        boolean b2=allName.substring(allName.lastIndexOf(".")).equals(".gif");
        if(allName.substring(allName.lastIndexOf(".")).equals(".png")||b|b1|b2){
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(propath+"/src/main/resources/static/img/"+id+"/"+allName);  //    文件夹 +/+ 文件完整名称  a.txt
                //如果没有files文件夹，则创建
                if (!Files.isWritable(path)) {
                    Files.createDirectories(Paths.get(propath+"/src/main/resources/static/img/" + id));
                }
                //文件写入指定路径
                Files.write(path, bytes);

                return "/img/"+id+"/"+allName;
            } catch (IOException e) {
                return "文件上传失败";
            }
        }else {
            return "文件格式不正确";
        }

    }
    //上传公司图片
    public String UploadImg(MultipartFile[] files, int c_id) {
        String msg;
        String str="";
        int  count=0;

        for (int i=0;i<files.length;i++){
            count++;
            System.out.println("有文件"+i);
            msg=singleFileUpload(files[i],c_id);


            if((files.length-count)<=0){
                str+=msg;
            }else{
                str+=msg;
                str+=",";
            }

        }
        return str;
    }
    //公司logo上传
    public String LogoUpload(MultipartFile logo,int id) {
        if (Objects.isNull(logo) || logo.isEmpty()) {
            return "文件为空!";
        }
        String allName = logo.getOriginalFilename();
        System.out.println("上传的文件名： "+allName);
        boolean b=allName.substring(allName.lastIndexOf(".")).equals(".jpeg");
        boolean b1=allName.substring(allName.lastIndexOf(".")).equals(".jpg");
        boolean b2=allName.substring(allName.lastIndexOf(".")).equals(".gif");
        if(allName.substring(allName.lastIndexOf(".")).equals(".png")||b|b1|b2){
            try {
                byte[] bytes = logo.getBytes();
                Path path = Paths.get(propath+"/src/main/resources/static/logo/" + id+"/"+allName);  //    文件夹 +/+ 文件完整名称  a.txt
                //如果没有files文件夹，则创建
                if (!Files.isWritable(path)) {
                    Files.createDirectories(Paths.get(propath+"/src/main/resources/static/logo/" + id));
                }
                //文件写入指定路径
                Files.write(path, bytes);

                return "/logo/"+id+"/"+allName;
            } catch (IOException e) {
                return "文件上传失败";
            }
        }else {
            return "文件格式不正确";
        }

    }

    //写入职位介绍
    public String WritePositionDes(String des,int p_id) throws IOException {

        File file=new File(propath+"\\src\\main\\resources\\static\\positiondes");
        if(file.getParentFile().exists()){
            file.mkdir();
        }
        FileOutputStream fileOutputStream=new FileOutputStream(file.getPath()+"/"+p_id+".txt");
        OutputStreamWriter osw=new OutputStreamWriter(fileOutputStream);
        osw.flush();
        osw.write(des);
        osw.close();
        fileOutputStream.close();
        return propath+"/src/main/resources/static/positiondes/"+p_id+".txt";
    }

    //读取职位介绍
    public StringBuffer ReadPositionDes(String filepath){
        StringBuffer sb=new StringBuffer();
        String str=null;
        try {
            Reader reader= new InputStreamReader(new FileInputStream(filepath));
            BufferedReader bf = new BufferedReader(reader);
            while ((str=bf.readLine())!=null){
                sb.append(str);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    //写入工作经历简介
    public String WriteExDes(String des,int e_id) throws IOException {

        File file=new File(propath+"\\src\\main\\resources\\static\\experience\\");
        if(file.getParentFile().exists()){
            file.mkdir();
        }
        FileOutputStream fileOutputStream=new FileOutputStream(file.getPath()+"/"+e_id+".txt");
        OutputStreamWriter osw=new OutputStreamWriter(fileOutputStream);
        osw.flush();
        osw.write(des);
        osw.close();
        fileOutputStream.close();
        return propath+"/src/main/resources/static/experience/"+e_id+".txt";
    }

    //读取工作经验介绍
    public StringBuffer ReadExDes(String filepath){
        StringBuffer sb=new StringBuffer();
        String str=null;
        try {
            Reader reader= new InputStreamReader(new FileInputStream(filepath));
            BufferedReader bf = new BufferedReader(reader);
            while ((str=bf.readLine())!=null){
                sb.append(str);
            }System.out.println(sb);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }
    //用户头像上传
    public String Updatehead(MultipartFile head,int id) {
        if (Objects.isNull(head) || head.isEmpty()) {
            return "文件为空!";
        }
        String allName = head.getOriginalFilename();

        boolean b=allName.substring(allName.lastIndexOf(".")).equals(".jpeg");
        boolean b1=allName.substring(allName.lastIndexOf(".")).equals(".jpg");
        boolean b2=allName.substring(allName.lastIndexOf(".")).equals(".gif");
        if(allName.substring(allName.lastIndexOf(".")).equals(".png")||b|b1|b2){
            try {
                byte[] bytes = head.getBytes();
                Path path = Paths.get(propath+"/src/main/resources/static/userlogo/" + id+"/"+allName);  //    文件夹 +/+ 文件完整名称  a.txt
                //如果没有files文件夹，则创建
                if (!Files.isWritable(path)) {
                    Files.createDirectories(Paths.get(propath+"/src/main/resources/static/userlogo/" + id));
                }
                //文件写入指定路径
                Files.write(path, bytes);

                return "/userlogo/"+id+"/"+allName;
            } catch (IOException e) {
                return "文件上传失败";
            }
        }else {
            return "文件格式不正确";
        }

    }

    public static  boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    public  static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    }