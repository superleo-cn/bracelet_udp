package com.qt.sql;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BraceletDataSource {

	private static final Logger LOGGER = Logger.getLogger(BraceletDataSource.class);

	private static HikariDataSource ds = null;
	static {
		LOGGER.info("==========Init DataSource==========");
		HikariConfig config = new HikariConfig("/database.properties");
		ds = new HikariDataSource(config);
	}

	private BraceletDataSource() {

	}

	public static DataSource getDataSoruce() {
		return ds;
	}

	public void close() {
		ds.close();
	}
}
