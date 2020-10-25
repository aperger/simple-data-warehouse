package com.adverity.demo.simpledatawarehouse.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = { IndexController.class })
@AutoConfigureMockMvc
class IndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void actionIndex() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}
}