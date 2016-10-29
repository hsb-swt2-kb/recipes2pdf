package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The Main starts the program.
 */
public class Main {
    public static void main(final String[] args) {
        //new Database( DatabaseConnection.getDatabaseConnection() );
        Injector injector = Guice.createInjector(new ApplicationConfig());
        Application app = injector.getInstance(Application.class);
        try {
            app.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
