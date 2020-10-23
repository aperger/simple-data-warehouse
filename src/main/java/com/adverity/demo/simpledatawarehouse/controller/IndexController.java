package com.adverity.demo.simpledatawarehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping(value = "/")
	public String actionIndex() {
		return "redirect:/api/rest/explorer/index.html#uri=/api/rest/statistics/search";
	}

}
