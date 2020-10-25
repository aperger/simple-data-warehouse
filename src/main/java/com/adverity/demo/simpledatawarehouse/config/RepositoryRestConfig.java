package com.adverity.demo.simpledatawarehouse.config;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;
import com.adverity.demo.simpledatawarehouse.projection.StatisticCampaignAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticDatasourceAggregateProjection;
import com.adverity.demo.simpledatawarehouse.projection.StatisticDefaultAggregateProjection;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryRestConfig implements RepositoryRestConfigurer {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		// @formatter:off
		config.exposeIdsFor(
			Statistic.class);
		// @formatter:on

		config.setReturnBodyForPutAndPost(true);

		// @formatter:off
		config.getProjectionConfiguration()
			.addProjection(StatisticDefaultAggregateProjection.class,
					StatisticDatasourceAggregateProjection.class,
					StatisticCampaignAggregateProjection.class);
		// @formatter:on
	}

}
