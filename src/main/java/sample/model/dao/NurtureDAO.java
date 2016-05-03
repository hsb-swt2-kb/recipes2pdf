package sample.model.dao;

import sample.model.Nurture;
import sample.model.activejdbc.NurtureDBO;

/**
 * Created by noex_ on 02.05.2016.
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

