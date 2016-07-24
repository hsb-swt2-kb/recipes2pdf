package sample.database;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.persist.jpa.JpaPersistModule;
import sample.database.dao.*;

/**
 * Created by czoeller on 11.07.16.
 */
public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {

        /**
         * Without the Scopes.SINGLETON, each time when you call
         * HibernateConnection connection = injector.getInstance(HibernateConnection.class);
         * a new Instance of HibernateConnection (with the included HibernateUtil) will be created.
         */
        bind(IUnitDAO.class).to(UnitDAOImpl.class).in(Scopes.SINGLETON);
        bind(IIngredientDAO.class).to(IngredientDAOImpl.class).in(Scopes.SINGLETON);
        bind(ICategoryDAO.class).to(CategoryDAOImpl.class).in(Scopes.SINGLETON);
        install(new JpaPersistModule("recipes2pdf-db"));
        bind(JPAInitializer.class).asEagerSingleton();
    }

}
