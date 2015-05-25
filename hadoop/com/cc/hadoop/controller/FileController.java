package com.cc.hadoop.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.utils.StringUtils;
import com.cc.hadoop.logic.FileUploadLogic;

/**
 * @author HyNo
 * 
 */
@Controller
public class FileController {

    @Autowired
    private FileUploadLogic fileUploadLogic;

    @RequestMapping
   // @RequestMapping("fileUpload")
    public void fileUpload(HttpServletRequest request, HttpServletResponse response, Model model) {

        String img = fileUploadLogic.uploadFile(request,"");
        if(StringUtils.isNotBlank(img)){
        	send(response, img.trim());
        }else{
        	send(response, "");        
        }
    }

    public void send(HttpServletResponse response, String img) {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(img);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
    }

}
