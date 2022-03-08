package com.example.demo.controller;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestController {
	private final Environment env;
	
	@GetMapping("/yml/{str}")
	@ResponseBody
	public String test1(@PathVariable String str) {
		log.debug("str is {}", str);
		return env.getProperty(str);
	}
}
