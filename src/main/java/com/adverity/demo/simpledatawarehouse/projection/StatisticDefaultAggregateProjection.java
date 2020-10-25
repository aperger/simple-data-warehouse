package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticDefaultAggregate", types = Statistic.class)
public interface StatisticDefaultAggregateProjection extends StatisticNoGroupAggregateProjection {

	String getDatasource();

	String getCampaign();

}
