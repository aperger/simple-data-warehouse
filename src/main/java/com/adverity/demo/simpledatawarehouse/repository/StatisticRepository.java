package com.adverity.demo.simpledatawarehouse.repository;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

public interface StatisticRepository extends JpaRepository<Statistic, Integer>, JpaSpecificationExecutor<Statistic> {

	@RestResource(path = "/all")
	List<Statistic> findBy(Sort sort);

	@RestResource(path = "/ds")
	List<Statistic> findByDatasource(@Param("datasource") String datasource, Sort sort);

	@RestResource(path = "/cp")
	List<Statistic> findByCampaign(@Param("campaign") String campaign, Sort sort);

	@RestResource(path = "/ds-cp")
	List<Statistic> findByDatasourceAndCampaign(@Param("campaign") String campaign, @Param("datasource") String datasource, Sort sort);

	@RestResource(path = "/ds-interval")
	List<Statistic> findByDatasourceAndDailyBetween(@Param("datasource") String datasource,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
			Sort sort);

	@RestResource(path = "/cp-interval")
	List<Statistic> findByCampaignAndDailyBetween(@Param("campaign") String campaign,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
			Sort sort);

	@RestResource(path = "/ds-cp-interval")
	List<Statistic> findByDatasourceAndCampaignAndDailyBetween(@Param("campaign") String campaign,
			@Param("datasource") String datasource,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
			Sort sort);
}
