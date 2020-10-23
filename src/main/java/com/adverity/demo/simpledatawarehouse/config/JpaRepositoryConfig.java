package com.adverity.demo.simpledatawarehouse.config;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;
import com.adverity.demo.simpledatawarehouse.repository.StatisticRepository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = Statistic.class)
@EnableJpaRepositories(basePackageClasses = { StatisticRepository.class})
public class JpaRepositoryConfig {

}
