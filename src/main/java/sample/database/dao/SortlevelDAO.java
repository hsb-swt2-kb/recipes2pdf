package sample.database.dao;

import sample.database.dbo.SortlevelDBO;
import sample.model.Sortlevel;

import java.util.Optional;

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
        Optional<Sortlevel> sortlevel = findFirst("name=?", pojo.getName());

        // if sortlevel not present in DB then insert it.
        if(!sortlevel.isPresent()){
            sortlevelDBO.setName(pojo.getName());
            sortlevelDBO.saveIt();
        }
        else {  // else read data from existing sortlevel entry in DB
            sortlevelDBO.setID(sortlevel.get().getID());
            sortlevelDBO.setName(sortlevel.get().getName());
        }
        return sortlevelDBO;
    }
}
