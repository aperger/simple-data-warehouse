package com.adverity.demo.simpledatawarehouse.config;

import com.adverity.demo.simpledatawarehouse.entity.Statistic;
import com.adverity.demo.simpledatawarehouse.repository.StatisticRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitializeDataWarehouse {

	private static Logger logger = LoggerFactory.getLogger(InitializeDataWarehouse.class);

	private static boolean firstLine = true;

	@Autowired
	StatisticRepository repository;

	@Value("${data-warehouse.resource-name:statistics.csv}")
	String resourceName;

	@EventListener(ApplicationReadyEvent.class)
	public void doInit() {
		loadDataFromResource();
	}

	private void loadDataFromResource() {
		logger.info("Initialization of Warehouse is starting");
		loadResourceFile();
		logger.info("Warehouse is initialized");
	}

	@Transactional
	private void loadResourceFile() {
		InputStream is = getResourceFileAsInputStream(resourceName);
		if (is != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			repository.deleteAll();
			repository.flush();

			firstLine = true;
			reader.lines().forEach(line -> {
				// datasource,campaign,daily,clicks,impressions
				String cols[] = line.split(",");
				if (cols.length != 5) {
					logger.warn("Invalid file CSV line: {}", line);
					return;
				}

				if (firstLine) {
					firstLine = false;
					return;
				}

				String datasource = cols[0];
				String campaign = cols[1];

				// default, ISO_LOCAL_DATE
				LocalDate daily;
				try {
					daily = LocalDate.parse(cols[2]);
				} catch (DateTimeParseException exception) {
					logger.warn("Invalid date format: {}", cols[2]);
					return;
				}

				Integer clicks;
				Integer impressions;
				try {
					clicks = Integer.valueOf(cols[3]);
					impressions = Integer.valueOf(cols[4]);
				} catch ( NumberFormatException exception) {
					logger.warn("Invalid number format: {} or {}", cols[3], cols[4]);
					return;
				}

				Statistic statistic = new Statistic()
						.setDatasource(datasource)
						.setCampaign(campaign)
						.setDaily(daily)
						.setClicks(clicks)
						.setImpressions(impressions);

				repository.save(statistic);
			});
			repository.flush();


		} else {
			throw new RuntimeException("resource not found");
		}
	}

	public static InputStream getResourceFileAsInputStream(String fileName) {
		ClassLoader classLoader = InitializeDataWarehouse.class.getClassLoader();
		return classLoader.getResourceAsStream(fileName);
	}
}
