package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import java.time.LocalDate;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticNoGroupDailyAggregate", types = Statistic.class)
public interface StatisticNoGroupDailyAggregateProjection extends StatisticNoGroupAggregateProjection {

	LocalDate getDaily();

}
