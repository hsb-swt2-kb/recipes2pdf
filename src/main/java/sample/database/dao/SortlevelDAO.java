package sample.database.dao;

import sample.database.dbo.SortlevelDBO;
import sample.model.Sortlevel;

/**
 * Database Access Object for Sortlevel.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 02.05.2016.
 */
public class SortlevelDAO extends ADAO<Sortlevel, SortlevelDBO> {

    @Override
    Sortlevel toPOJO(SortlevelDBO sortlevelDBO) {
        final Sortlevel sortlevel = new Sortlevel();
        sortlevel.setID(sortlevelDBO.getID());
        sortlevel.setName(sortlevelDBO.getName());
        return sortlevel;
    }

    @Override
    SortlevelDBO toDBO(Sortlevel pojo) {
        SortlevelDBO sortlevelDBO = new SortlevelDBO();
        if (findById(pojo.getID()).isPresent()) {
            sortlevelDBO.setID(pojo.getID());
        }

        sortlevelDBO.setName(pojo.getName());
        return sortlevelDBO;
    }
}
