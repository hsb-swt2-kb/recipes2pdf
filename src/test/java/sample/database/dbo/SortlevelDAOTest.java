package sample.database.dbo;

import org.javalite.activejdbc.DBException;
import org.junit.Test;
import sample.database.dao.SortlevelDAO;
import sample.model.Sortlevel;

/**
 * Created by czoeller on 25.05.16.
 */
public class SortlevelDAOTest extends ADatabaseTest {

    @Test(expected=DBException.class)
    public void testSortlevelDuplicateThrowsException() {
        final SortlevelDAO sortlevelDAO = new SortlevelDAO();

        Sortlevel sortlevel1 = new Sortlevel();
        Sortlevel sortlevel2 = new Sortlevel();
        String sortlevel_name = "Category";
        sortlevel1.setName(sortlevel_name);
        sortlevel2.setName(sortlevel_name);
        sortlevelDAO.insert(sortlevel1);
        sortlevelDAO.insert(sortlevel2);
    }

}
