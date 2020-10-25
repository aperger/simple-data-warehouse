package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticDatasourceAggregate", types = Statistic.class)
public interface StatisticDatasourceAggregateProjection extends StatisticNoGroupAggregateProjection {

	String getDatasource();

}
