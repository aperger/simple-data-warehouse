package com.adverity.demo.simpledatawarehouse.controller;

import com.adverity.demo.simpledatawarehouse.repository.StatisticRepository;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({ "classpath:/application-test.properties" })
class ApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StatisticRepository repository;

	@Test
	void testGetAggregateAll() throws Exception {
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/all/2010-12-01/2016-01-01")).andExpect(status().isNotFound());
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/all/2019-01-01/2019-12-31")).andExpect(status().isOk());
	}

	@Test
	void testGetDailyAggregateAll() throws Exception {
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/daily/all/2010-12-01/2016-01-01"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/daily/all/2019-11-12/2019-11-18"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(7)));
	}

	@Test
	void testGetAggregateOfDatasource() throws Exception {
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/ds/#INVALID#/2019-12-01/2019-12-31")).andExpect(status().isNotFound());
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/ds/Google Ads/2019-12-01/2019-12-31")).andExpect(status().isNotFound());
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/ds/Google Ads/2019-01-01/2019-11-18"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.clicks", Matchers.is(800)))
				.andExpect(jsonPath("$.impressions", Matchers.is(558973)))
				.andExpect(jsonPath("$.clickThroughRate", Matchers.closeTo(BigDecimal.valueOf(100.0 * 800.0 / 558973.0), BigDecimal.valueOf(0.0001))));
	}

	@Test
	void testGetDailyAggregateOfDatasource() throws Exception {
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/daily/ds/Twitter Ads/2019-12-01/2019-12-31")).andExpect(status().isOk());
	}

	@Test
	void testGetAggregateOfCampaign() throws Exception {
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/cp/#INVALID#/2019-12-01/2019-12-31")).andExpect(status().isNotFound());
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/cp/GDN_Retargeting/2019-01-05/2019-01-08"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.clicks", Matchers.is(117)))
				.andExpect(jsonPath("$.impressions", Matchers.is(110914)))
				.andExpect(jsonPath("$.clickThroughRate", Matchers.closeTo(BigDecimal.valueOf(100.0 * 117.0 / 110914.0), BigDecimal.valueOf(0.0001))));
	}

	@Test
	void testGetDailyAggregateOfCampaign() throws Exception {
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/daily/cp/GDN_Retargeting/2019-01-05/2019-01-08"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(4)))
				.andExpect(jsonPath("$[3].clicks", Matchers.is(37)))
				.andExpect(jsonPath("$[3].impressions", Matchers.is(35228)))
				.andExpect(jsonPath("$[3].clickThroughRate", Matchers.closeTo(BigDecimal.valueOf(100.0 * 37.0 / 35228.0), BigDecimal.valueOf(0.0001))));
	}

	@Test
	void testGetAggregateOfCampaignOnSource() throws Exception {
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/ds-cp/#INVALID#/#INVALID#/2019-12-01/2019-12-31"))
				.andExpect(status()
				.isNotFound());

		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/ds-cp/Twitter Ads/SN_Wettbewerber/2019-01-01/2019-12-31"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.clicks", Matchers.is(19)))
				.andExpect(jsonPath("$.impressions", Matchers.is(650)))
				.andExpect(jsonPath("$.clickThroughRate", Matchers.closeTo(Double.valueOf(100.0 * 19.0 / 650.0), Double.valueOf(0.0001))));
	}

	@Test
	void testGetDailyAggregateOfCampaignOnSource() throws Exception {
		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/daily/ds-cp/#INVALID#/#INVALID#/2019-12-01/2019-12-31"))
				.andExpect(status().isNotFound());

		mockMvc.perform(get(ApiController.URL_STATISTIC_AGGREGATE + "/daily/ds-cp/Google Ads/Mitgliedschaft KiMi/2019-05-13/2019-05-21"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(9)));
	}
}