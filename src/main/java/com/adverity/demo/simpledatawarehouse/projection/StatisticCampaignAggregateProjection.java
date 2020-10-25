package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticCampaignAggregate", types = Statistic.class)
public interface StatisticCampaignAggregateProjection extends StatisticNoGroupAggregateProjection {

	String getCampaign();

}
