package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticDatasourceDailyAggregate", types = Statistic.class)
public interface StatisticDatasourceDailyAggregateProjection extends StatisticNoGroupDailyAggregateProjection {

	String getDatasource();

}
