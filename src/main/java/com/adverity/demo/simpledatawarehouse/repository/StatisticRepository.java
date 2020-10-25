package com.adverity.demo.simpledatawarehouse.repository;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

public interface StatisticRepository extends JpaRepository<Statistic, Integer>, JpaSpecificationExecutor<Statistic> {

	/*
	@RestResource(path = "/all")
	List<Statistic> findBy(Sort sort);
	*/

	@RestResource(path = "/by-ds")
	List<Statistic> findByDatasource(@Param("datasource") String datasource, Sort sort);

	@RestResource(path = "/by-cp")
	List<Statistic> findByCampaign(@Param("campaign") String campaign, Sort sort);

	@RestResource(path = "/by-ds-and-cp")
	List<Statistic> findByDatasourceAndCampaign(@Param("campaign") String campaign, @Param("datasource") String datasource, Sort sort);

	@RestResource(path = "/by-ds-in-interval")
	List<Statistic> findByDatasourceAndDailyBetween(@Param("datasource") String datasource,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
			Sort sort);

	@RestResource(path = "/by-cp-in-interval")
	List<Statistic> findByCampaignAndDailyBetween(@Param("campaign") String campaign,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
			Sort sort);

	@RestResource(path = "/by-ds-and-cp-in-interval")
	List<Statistic> findByDatasourceAndCampaignAndDailyBetween(@Param("campaign") String campaign,
			@Param("datasource") String datasource,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
			Sort sort);

	@RestResource(exported = false, path = "/aggregate-all")
	@Query(value = "SELECT MAX(id) as id, NULL as datasource, NULL as campaign, NULL AS daily, SUM(s.clicks) AS clicks, SUM(s.impressions) as impressions "
			+ "FROM statistic s WHERE s.daily BETWEEN :start AND :end", nativeQuery = true)
	Optional<Statistic> getAggregateOnInterval(@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);

	@RestResource(exported = false, path = "/aggregate-ds")
	@Query(value = "SELECT MAX(id) as id, s.datasource, NULL AS campaign, NULL AS daily, SUM(s.clicks) AS clicks, SUM(s.impressions) as impressions "
			+ "FROM statistic s WHERE s.daily BETWEEN :start AND :end "
			+ "AND s.datasource = :datasource " + "GROUP BY s.datasource", nativeQuery = true)
	Optional<Statistic> getAggregateDatasourceOnInterval(@Param("datasource") String datasource,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);

	@RestResource(exported = false, path = "/aggregate-ds-cp")
	@Query(value = "SELECT MAX(id) as id, s.datasource, s.campaign, NULL AS daily, SUM(s.clicks) AS clicks, SUM(s.impressions) as impressions "
			+ "FROM statistic s WHERE s.daily BETWEEN :start AND :end "
			+ "AND s.datasource = :datasource "
			+ "AND s.campaign = :campaign "
			+ "GROUP BY s.datasource, s.campaign", nativeQuery = true)
	Optional<Statistic> getAggregateDatasourceCampaignOnInterval(@Param("datasource") String datasource,
			@Param("campaign") String campaign,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);


	@RestResource(exported = false, path = "/aggregate-cp")
	@Query(value = "SELECT MAX(id) as id, NULL AS datasource, s.campaign, NULL AS daily, SUM(s.clicks) AS clicks, SUM(s.impressions) as impressions "
			+ "FROM statistic s WHERE s.daily BETWEEN :start AND :end "
			+ "AND s.campaign = :campaign "
			+ "GROUP BY s.campaign", nativeQuery = true)
	Optional<Statistic> getAggregateCampaignOnInterval(@Param("campaign") String campaign,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);


	@RestResource(exported = false, path = "/daily-aggregate-all")
	@Query(value = "SELECT MAX(id) as id, NULL as datasource, NULL as campaign, s.daily, SUM(s.clicks) AS clicks, SUM(s.impressions) as impressions "
			+ "FROM statistic s WHERE s.daily BETWEEN :start AND :end " + "GROUP BY s.daily " + "ORDER BY s.daily", nativeQuery = true)
	List<Statistic> getDailyAggregateOnInterval(@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);

	@RestResource(exported = false, path = "/daily-aggregate-ds")
	@Query(value = "SELECT MAX(id) as id, s.datasource, NULL as campaign, s.daily, SUM(s.clicks) AS clicks, SUM(s.impressions) as impressions "
			+ "FROM statistic s WHERE s.daily BETWEEN :start AND :end "
			+ "AND s.datasource = :datasource "
			+ "GROUP BY s.daily, s.datasource", nativeQuery = true)
	List<Statistic> getDailyAggregateDatasourceOnInterval(@Param("datasource") String datasource,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);

	@RestResource(exported = false, path = "/daily-aggregate-cp")
	@Query(value = "SELECT MAX(id) as id, NULL AS datasource, s.campaign, s.daily, SUM(s.clicks) AS clicks, SUM(s.impressions) as impressions "
			+ "FROM statistic s WHERE s.daily BETWEEN :start AND :end "
			+ "AND s.campaign = :campaign "
			+ "GROUP BY s.daily, s.campaign", nativeQuery = true)
	List<Statistic> getDailyAggregateCampaignOnInterval(@Param("campaign") String campaign,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);

	@RestResource(exported = false, path = "/daily-aggregate-ds-cp")
	@Query(value = "SELECT MAX(id) as id, s.datasource, s.campaign, s.daily, SUM(s.clicks) AS clicks, SUM(s.impressions) as impressions "
			+ "FROM statistic s WHERE s.daily BETWEEN :start AND :end "
			+ "AND s.datasource = :datasource "
			+ "AND s.campaign = :campaign "
			+ "GROUP BY s.daily, s.datasource, s.campaign", nativeQuery = true)
	List<Statistic> getDailyAggregateDatasourceCampaignOnInterval(@Param("datasource") String datasource,
			@Param("campaign") String campaign,
			@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end);
}
