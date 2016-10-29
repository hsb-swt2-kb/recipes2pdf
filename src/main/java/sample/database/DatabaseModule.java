package sample.database;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.inject.Singleton;

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
        //bind(SessionFactory.class).to(SessionProvider.class).in(Scopes.SINGLETON);

    }

    @Provides
    @Singleton
    SessionFactory provideSessionFactory() {
        // fix for Hibernate 5 http://stackoverflow.com/questions/32405031/hibernate-5-org-hibernate-mappingexception-unknown-entity
        //return new Configuration().configure().buildSessionFactory();

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml")
        .addAnnotatedClass(sample.model.Category.class)
        .addAnnotatedClass(sample.model.Cookbook.class)
        .addAnnotatedClass(sample.model.Recipe.class)
        .addAnnotatedClass(sample.model.CookbookRecipe.class)
        .addAnnotatedClass(sample.model.CookbookSortlevel.class)
        .addAnnotatedClass(sample.model.Course.class)
        .addAnnotatedClass(sample.model.Daytime.class)
        .addAnnotatedClass(sample.model.Ingredient.class)
        .addAnnotatedClass(sample.model.Nurture.class)
        .addAnnotatedClass(sample.model.Rating.class)
        .addAnnotatedClass(sample.model.RecipeIngredient.class)
        .addAnnotatedClass(sample.model.Region.class)
        .addAnnotatedClass(sample.model.Season.class)
        .addAnnotatedClass(sample.model.Sortlevel.class)
        .addAnnotatedClass(sample.model.Source.class)
        .addAnnotatedClass(sample.model.Unit.class)
        ;
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
        return sessionFactory;

        /*Configuration hibConfiguration = new Configuration()
                .configure();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(hibConfiguration.getProperties())
                .buildServiceRegistry();
        SessionFactory sessionFactory = hibConfiguration.buildSessionFactory(serviceRegistry);
        return sessionFactory;*/

        /*try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml")
                .addAnnotatedClass(sample.model.Cookbook.class)
            ;
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SessionFactory ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return ourSessionFactory;
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }*/
    }
}
