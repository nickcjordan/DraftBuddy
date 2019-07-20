package com.falifa.draftbuddy.ui.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.falifa.draftbuddy.ui.Log;

@RestController
@RequestMapping("/error")
public class ErrorHandlerController implements ErrorController {

	private final ErrorAttributes errorAttributes;

	@Autowired
	public ErrorHandlerController(ErrorAttributes errorAttributes) {
		Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
		this.errorAttributes = errorAttributes;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping
	public Map<String, Object> error(HttpServletRequest aRequest){
		Map<String, Object> body = getErrorAttributes(aRequest,getTraceParameter(aRequest));
		String unsplit = ((String) body.get("trace"));
		if (unsplit != null) {
			Log.err(unsplit);
			BaseController.errorMessage = "ERROR :: " + unsplit;
			String[] trace = unsplit.split("\n\t");
			if (trace != null) {
				body.put("trace", trace);
			} 
		}
		return body;
	}

	private boolean getTraceParameter(HttpServletRequest request) {
		if (request.getParameter("trace") == null) {
			return false;
		}
		return !"false".equals(request.getParameter("trace").toLowerCase());
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
		return errorAttributes.getErrorAttributes((WebRequest) new ServletRequestAttributes(aRequest), includeStackTrace);
	}

}
