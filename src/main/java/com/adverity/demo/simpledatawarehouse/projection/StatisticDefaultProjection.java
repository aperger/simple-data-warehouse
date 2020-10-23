package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticDefault", types = Statistic.class)
public class StatisticDefaultProjection {
}
