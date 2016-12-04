package sample.database;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.persist.jpa.JpaPersistModule;
import sample.database.dao.GenericDAOImpl;
import sample.database.dao.IGenericDAO;
import sample.model.*;

import java.util.Properties;

/**
 * Created by czoeller on 11.07.16.
 */
public class DatabaseModule extends AbstractModule {

    private final DatabaseConfig databaseConfig;

    public DatabaseModule(final DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    protected void configure() {

        /**
         * Without the Scopes.SINGLETON, each time when you call
         * HibernateConnection connection = injector.getInstance(HibernateConnection.class);
         * a new Instance of HibernateConnection (with the included HibernateUtil) will be created.
         */
        bind(new TypeLiteral<IGenericDAO<CookbookRecipe, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<CookbookRecipe, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Cookbook, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Cookbook, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Recipe, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Recipe, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Ingredient, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Ingredient, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<RecipeIngredient, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<RecipeIngredient, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Category, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Category, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Unit, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Unit, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Course, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Course, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Season, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Season, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Daytime, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Daytime, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Nurture, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Nurture, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Region, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Region, Integer>>(){}).in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<Source, Integer>>(){}).to(new TypeLiteral<GenericDAOImpl<Source, Integer>>(){}).in(Scopes.SINGLETON);

        install( createJpaPersistModule(databaseConfig) );
        bind(JPAInitializer.class).asEagerSingleton();
    }

    private JpaPersistModule createJpaPersistModule(DatabaseConfig databaseConfig) {
        Properties props = new Properties();
        props.put("javax.persistence.jdbc.url", databaseConfig.getDatabaseURL() + ";DB_CLOSE_DELAY=-1");
        System.out.println("Database at: " + databaseConfig.getDatabaseURL() );
        JpaPersistModule jpaModule = new JpaPersistModule("recipes2pdf-db");
        jpaModule.properties(props);
        return jpaModule;
    }
}
