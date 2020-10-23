package com.adverity.demo.simpledatawarehouse.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "statistic",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = { "datasource", "campaign", "daily" }, name = "uk_statistic")
		},
		indexes = {
				@Index(columnList = "datasource", name = "idx_statistic_datasource"),
				@Index(columnList = "campaign", name = "idx_statistic_campaign"),
				@Index(columnList = "daily", name = "idx_statistic_daily"),
		})
public class Statistic {

	@Id()
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 200)
	private String datasource;

	@Column(length = 200)
	private String campaign;

	private LocalDate daily;

	private Integer clicks;

	private Integer impressions;

	public Integer getId() {
		return id;
	}

	public Statistic setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getDatasource() {
		return datasource;
	}

	public Statistic setDatasource(String datasource) {
		this.datasource = datasource;
		return this;
	}

	public String getCampaign() {
		return campaign;
	}

	public Statistic setCampaign(String campaign) {
		this.campaign = campaign;
		return this;
	}

	public LocalDate getDaily() {
		return daily;
	}

	public Statistic setDaily(LocalDate daily) {
		this.daily = daily;
		return this;
	}

	public Integer getClicks() {
		return clicks;
	}

	public Statistic setClicks(Integer clicks) {
		this.clicks = clicks;
		return this;
	}

	public Integer getImpressions() {
		return impressions;
	}

	public Statistic setImpressions(Integer impressions) {
		this.impressions = impressions;
		return this;
	}
}
