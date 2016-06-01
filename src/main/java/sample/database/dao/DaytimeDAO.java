package sample.database.dao;

import sample.database.dbo.DaytimeDBO;
import sample.model.Daytime;

import java.util.Optional;

/**
 * Database Access Object for Daytime.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 02.05.2016.
 */
public class DaytimeDAO extends ADAO<Daytime, DaytimeDBO> {

    @Override
    Daytime toPOJO(DaytimeDBO daytimeDBO) {
        final Daytime daytime = new Daytime();
        daytime.setID(daytimeDBO.getID());
        daytime.setName(daytimeDBO.getName());
        return daytime;
    }

    @Override
    DaytimeDBO toDBO(Daytime pojo) {
        DaytimeDBO daytimeDBO = new DaytimeDBO();
        Optional<Daytime> daytime = findFirst("name=?", pojo.getName());

        // if daytime not present in DB then insert it.
        if(!daytime.isPresent()){
            daytimeDBO.setName(pojo.getName());
            daytimeDBO.saveIt();
        }
        else {  // else read data from existing daytime entry in DB
            daytimeDBO.setID(daytime.get().getID());
            daytimeDBO.setName(daytime.get().getName());
        }
        return daytimeDBO;
    }
}
