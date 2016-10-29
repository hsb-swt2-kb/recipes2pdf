package sample;

import com.github.vbauer.herald.ext.guice.LogModule;
import com.google.inject.AbstractModule;
import sample.config.IConfig;
import sample.database.DatabaseConfig;
import sample.database.DatabaseModule;

/**
 * Created by noex_ on 26.10.2016.
 */
public class ApplicationConfig extends AbstractModule {

    @Override
    protected void configure() {
        install( new LogModule() );
        install(new DatabaseModule( new DatabaseConfig( IConfig.getInstance() ) ) );
    }

}
