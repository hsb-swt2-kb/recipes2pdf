package sample.database;

import com.github.vbauer.herald.annotation.Log;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import sample.config.IConfig;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

/**
 * Database configuration.
 * Created by czoeller on 29.05.2016.
 */
public class DatabaseConfig {

    @Log
    private Logger LOG;

    /**
     * Instance of base configuration
     */
    private final IConfig baseConfig;

    @Inject
    public DatabaseConfig(IConfig config) {
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
        return getDatabaseDir() + File.separator + "database";
    }

    public String getDatabaseURL() {
        return "jdbc:log4jdbc:h2:file:" + getDatabasePath().replaceAll("\\\\", "/");
    }
}
