package com.anuragntl.pictolabels.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import com.anuragntl.pictolabels.components.FileManager;
import org.springframework.beans.factory.annotation.Autowired;

@ControllerAdvice
@RestController
public class IDExtractor {
    @Autowired
    FileManager fileManager;
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Map<String,Object>> handleAll(Throwable t,WebRequest request)
	{
		Map<String,Object> response=new HashMap<String,Object>()
				{{
					put("error",true);
					put("description",t.getMessage());
				}};
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
};


