package sample.database.dao;

import sample.database.dbo.DaytimeDBO;
import sample.model.Daytime;

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
        if (findById(pojo.getID()).isPresent()) {
            daytimeDBO.setID(pojo.getID());
        }

        daytimeDBO.setName(pojo.getName());
        return daytimeDBO;
    }
}
