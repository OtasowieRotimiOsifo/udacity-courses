package com.udacity.jwdnd.course1.cloudstorage.controller;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WhiteLabelErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        String message = "Error occurred with code: " + statusCode.toString() +" with your request: ";
	        model.addAttribute("error", message);
	    }
	    return "error";
	}
}
