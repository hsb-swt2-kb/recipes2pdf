package sample.model.dao;

import sample.model.Daytime;
import sample.model.activejdbc.DaytimeDBO;

/**
 * Created by noex_ on 02.05.2016.
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
