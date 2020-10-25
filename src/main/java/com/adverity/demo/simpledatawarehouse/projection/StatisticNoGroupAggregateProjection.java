package com.adverity.demo.simpledatawarehouse.projection;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "statisticNoGroupAggregate", types = Statistic.class)
public interface StatisticNoGroupAggregateProjection {

	Integer getClicks();

	Integer getImpressions();

	@Value("#{target.impressions > 0 ? (100.0 * target.clicks / target.impressions) : null}")
	Double getClickThroughRate();

}
