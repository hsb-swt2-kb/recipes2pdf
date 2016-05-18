package sample.database.dao;

import sample.database.dbo.NurtureDBO;
import sample.model.Nurture;

/**
 * Created by czoeller on 02.05.2016.
 */
public class NurtureDAO extends ADAO<Nurture, NurtureDBO> {

    @Override
    Nurture toPOJO(NurtureDBO nurtureDBO) {
        final Nurture nurture = new Nurture();
        nurture.setID(nurtureDBO.getID());
        nurture.setName(nurtureDBO.getName());
        return nurture;
    }

    @Override
    NurtureDBO toDBO(Nurture pojo) {
        NurtureDBO nurtureDBO = new NurtureDBO();
        if (findById(pojo.getID()).isPresent()) {
            nurtureDBO.setID(pojo.getID());
        }

        nurtureDBO.setName(pojo.getName());
        return nurtureDBO;
    }
}

