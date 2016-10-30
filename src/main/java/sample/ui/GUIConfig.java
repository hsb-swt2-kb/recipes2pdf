package sample.ui;

import com.google.inject.AbstractModule;
import sample.ApplicationConfig;

/**
 * Created by noex_ on 29.10.2016.
 */
public class GUIConfig extends AbstractModule {

    @Override
    protected void configure() {
        install(new ApplicationConfig());
    }
}
