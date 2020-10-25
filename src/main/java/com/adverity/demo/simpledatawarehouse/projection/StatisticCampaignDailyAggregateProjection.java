package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import java.time.LocalDate;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticCampaignDailyAggregate", types = Statistic.class)
public interface StatisticCampaignDailyAggregateProjection extends StatisticNoGroupDailyAggregateProjection {

	String getCampaign();

}
