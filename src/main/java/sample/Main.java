package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import sample.database.DatabaseModule;
import sample.database.dao.CookbookDAOImpl;
import sample.database.dao.ICookbookDAO;

/**
 * The Main starts the program.
 */
public class Main
{
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DatabaseModule() );
        final ICookbookDAO cookbookDAO = injector.getInstance(CookbookDAOImpl.class);
        //cookbookDAO.save(new Cookbook());
    }
}
