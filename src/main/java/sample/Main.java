package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import sample.database.DatabaseModule;
import sample.database.dao.CookbookDAOImpl;
import sample.database.dao.ICookbookDAO;
import sample.ui.CLI;
import sample.ui.GUI;
import sample.ui.Shell;

/**
 * The Main starts the program.
 */
public class Main
{
    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new DatabaseModule() );
        final ICookbookDAO cookbookDAO = injector.getInstance(CookbookDAOImpl.class);
        //cookbookDAO.save(new Cookbook());

        // select UI
        if(args.length==0)
            new GUI().start();
        else if (args[0].equals("shell"))
            new Shell().start();
        else
            new CLI().start(args);
    }
}
