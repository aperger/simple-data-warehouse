package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import java.time.LocalDate;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticDefaultDailyAggregate", types = Statistic.class)
public interface StatisticDefaultDailyAggregateProjection extends StatisticNoGroupDailyAggregateProjection {

	String getDatasource();

	String getCampaign();

}
