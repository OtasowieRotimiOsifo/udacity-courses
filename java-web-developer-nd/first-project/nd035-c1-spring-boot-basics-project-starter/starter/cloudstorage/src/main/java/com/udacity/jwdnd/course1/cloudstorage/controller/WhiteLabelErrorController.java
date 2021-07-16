package com.udacity.jwdnd.course1.cloudstorage.controller;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WhiteLabelErrorController implements ErrorController {

	Logger logger = LoggerFactory.getLogger(WhiteLabelErrorController.class);
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		
		logger.info("request = {}", request.toString());
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        String message = "An Error with http error code: " + statusCode.toString() +" occurred while processing your request: ";
	        model.addAttribute("error", message);
	    }
	    return "error";
	}
}
