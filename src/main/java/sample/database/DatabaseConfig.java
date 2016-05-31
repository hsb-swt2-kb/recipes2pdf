package sample.database;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.config.IConfig;

import java.io.File;
import java.io.IOException;

/**
 * Database configuration.
 * Created by czoeller on 29.05.2016.
 */
public class DatabaseConfig {
    final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);
    /**
     * Instance of base configuration
     */
    private final IConfig baseConfig;

    DatabaseConfig(IConfig config) {
        this.baseConfig = config;
        makeDirs();
    }

    /**
     * Create dirs for database.
     */
    private void makeDirs() {
        try {
            FileUtils.forceMkdir(new File(getDatabaseDir()));
        } catch (IOException e) {
            LOG.error("Failed to create database dir.", e);
        }
    }

    /**
     * Dir where the file based database is stored.
     *
     * @return the dir where the file based database is stored.
     */
    String getDatabaseDir() {
        return System.getProperty("user.home") + File.separator + ".recipes2pdf" + File.separator + baseConfig.getProperty("DATABASE_DIR");
    }

    /**
     * Path of the path of the file based database.
     *
     * @return the path of the file based database.
     */
    String getDatabasePath() {
        return getDatabaseDir() + File.separator + "database.db";
    }
}
