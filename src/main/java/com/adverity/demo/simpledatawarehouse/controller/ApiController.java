package com.adverity.demo.simpledatawarehouse.controller;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;
import com.adverity.demo.simpledatawarehouse.projection.StatisticCampaignAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticCampaignDailyAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticDatasourceAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticDatasourceDailyAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticDefaultAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticDefaultDailyAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticNoGroupAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticNoGroupDailyAggregateProjection;
import com.adverity.demo.simpledatawarehouse.repository.StatisticRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiController.URL_STATISTICS)
public class ApiController {

	static final String URL_STATISTICS = "/api/statistics";
	static final String URL_AGGREGATES = "/aggregates";
	public static final String URL_STATISTIC_AGGREGATE = URL_STATISTICS + URL_AGGREGATES;

	@Autowired
	private ProjectionFactory factory;

	@Autowired
	StatisticRepository repository;

	// *** Aggregates for all campaigns
	// ********************************
	@GetMapping(value = URL_AGGREGATES + "/all/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatisticNoGroupAggregateProjection> getAggregateAll(@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		Optional<Statistic> optionalStatistic = repository.getAggregateOnInterval(start, end);
		if (optionalStatistic.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		StatisticNoGroupAggregateProjection projected = factory.createProjection(StatisticNoGroupAggregateProjection.class, optionalStatistic.get());

		return new ResponseEntity(projected, HttpStatus.OK);
	}

	@GetMapping(value= URL_AGGREGATES + "/daily/all/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StatisticNoGroupDailyAggregateProjection>> getDailyAggregateAll(
			@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		List<Statistic> entities = repository.getDailyAggregateOnInterval(start, end);
		List<StatisticNoGroupDailyAggregateProjection> projected = entities.stream()
				.map(item -> factory.createProjection(StatisticNoGroupDailyAggregateProjection.class, item))
				.collect(Collectors.toList());

		return new ResponseEntity(projected, HttpStatus.OK);
	}

	// *** Aggregates for one (data)source
	// **************************************************
	@GetMapping(value = URL_AGGREGATES + "/ds/{datasource}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatisticDatasourceAggregateProjection> getAggregateOfDatasource(@PathVariable(value = "datasource") String datasource,
			@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		Optional<Statistic> optionalStatistic = repository.getAggregateDatasourceOnInterval(datasource, start, end);
		if (optionalStatistic.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		StatisticDatasourceAggregateProjection projected = factory.createProjection(StatisticDatasourceAggregateProjection.class, optionalStatistic.get());

		return new ResponseEntity(projected, HttpStatus.OK);
	}

	@GetMapping(value = URL_AGGREGATES + "/daily/ds/{datasource}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StatisticDatasourceDailyAggregateProjection>> getDailyAggregateOfDatasource(@PathVariable(value = "datasource") String datasource,
			@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		List<Statistic> entities = repository.getDailyAggregateDatasourceOnInterval(datasource, start, end);
		List<StatisticDatasourceDailyAggregateProjection> projected = entities.stream()
				.map(item -> factory.createProjection(StatisticDatasourceDailyAggregateProjection.class, item))
				.collect(Collectors.toList());

		return new ResponseEntity(projected, HttpStatus.OK);
	}

	// *** Aggregates for a campaign
	// **************************************************
	@GetMapping(value = URL_AGGREGATES + "/cp/{campaign}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<StatisticCampaignAggregateProjection> getAggregateOfCampaign(@PathVariable(value = "campaign") String campaign,
			@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		Optional<Statistic> optionalStatistic = repository.getAggregateCampaignOnInterval(campaign, start, end);
		if (optionalStatistic.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		StatisticCampaignAggregateProjection projected = factory.createProjection(StatisticCampaignAggregateProjection.class, optionalStatistic.get());

		return new ResponseEntity(projected, HttpStatus.OK);
	}

	@GetMapping(value = URL_AGGREGATES + "/daily/cp/{campaign}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StatisticCampaignDailyAggregateProjection>> getDailyAggregateOfCampaign(@PathVariable(value = "campaign") String campaign,
			@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		List<Statistic> entities = repository.getDailyAggregateCampaignOnInterval(campaign, start, end);
		List<StatisticCampaignDailyAggregateProjection> projected = entities.stream()
				.map(item -> factory.createProjection(StatisticCampaignDailyAggregateProjection.class, item))
				.collect(Collectors.toList());

		return new ResponseEntity(projected, HttpStatus.OK);
	}

	// *** Aggregates for a campaign only on one source
	// ************************************************
	@GetMapping(value = URL_AGGREGATES + "/ds-cp/{datasource}/{campaign}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatisticDefaultAggregateProjection> getAggregateOfCampaignOnSource(@PathVariable(value = "datasource") String datasource,
			@PathVariable(value = "campaign") String campaign,
			@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		Optional<Statistic> optionalStatistic = repository.getAggregateCampaignOnInterval(campaign, start, end);
		if (optionalStatistic.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		StatisticDefaultAggregateProjection projected = factory.createProjection(StatisticDefaultAggregateProjection.class, optionalStatistic.get());

		return new ResponseEntity(projected, HttpStatus.OK);
	}

	@GetMapping(value = URL_AGGREGATES + "/daily/ds-cp/{datasource}/{campaign}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StatisticDefaultDailyAggregateProjection>> getDailyAggregateOfCampaignOnSource(@PathVariable(value = "datasource") String datasource,
			@PathVariable(value = "campaign") String campaign,
			@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		List<Statistic> entities = repository.getDailyAggregateDatasourceCampaignOnInterval(datasource, campaign, start, end);
		List<StatisticDefaultDailyAggregateProjection> projected = entities.stream()
				.map(item -> factory.createProjection(StatisticDefaultDailyAggregateProjection.class, item))
				.collect(Collectors.toList());

		return new ResponseEntity(projected, HttpStatus.OK);
	}
}
