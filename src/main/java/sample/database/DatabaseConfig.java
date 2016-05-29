package sample.database;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.config.IConfig;

import java.io.File;
import java.io.IOException;

/**
 * Created by czoeller on 29.05.2016.
 */
public class DatabaseConfig {

    final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);
    private final IConfig baseConfig;

    DatabaseConfig(IConfig config) {
       this.baseConfig = config;
       init();
    }

    private void init() {
        try {
            FileUtils.forceMkdir( new File( getDatabaseDir() ) );
        } catch (IOException e) {
            LOG.error("Failed to create database dir.", e);
        }
    }

    String getDatabaseDir() {
        return System.getProperty("user.home") + File.separator + ".recipes2pdf" + File.separator + baseConfig.getProperty("DATABASE_DIR");
    }

    String getDatabasePath() {
        return getDatabaseDir() + File.separator + "database.db";
    }
}
