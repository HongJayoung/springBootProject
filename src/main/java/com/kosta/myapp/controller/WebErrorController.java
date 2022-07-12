package com.kosta.myapp.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebErrorController implements ErrorController {
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model,Exception ex) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE); 
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
         model.addAttribute("msg", "해당 페이지를 찾을 수 없습니다!!!");       
            } else {
         model.addAttribute("msg", "처리중 에러 발생!!!" + ex.getMessage());               
            }
        }
        return "error/error";
    }
}
