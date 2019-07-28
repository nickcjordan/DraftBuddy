package com.falifa.draftbuddy.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlerController implements ErrorController {

	private static final Logger log = LoggerFactory.getLogger(ErrorHandlerController.class);

	@RequestMapping("/error")
	@ResponseBody
	public String handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		log.error("ERROR CONTROLLER :: {} :: {}", statusCode, exception == null ? "N/A" : exception.getMessage());
		return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>" + "<div>Exception Message: <b>%s</b></div><body></html>", statusCode,
				exception == null ? "N/A" : exception.getMessage());
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
