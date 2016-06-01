package sample.database.dao;

import sample.database.dbo.NurtureDBO;
import sample.model.Nurture;

import java.util.Optional;

/**
 * Database Access Object for Nurture.
 * Does Mapping from POJO to DBO and vice versa.
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
        Optional<Nurture> nurture = findFirst("name=?", pojo.getName());

        // if nurture not present in DB then insert it.
        if(!nurture.isPresent()){
            nurtureDBO.setName(pojo.getName());
            nurtureDBO.saveIt();
        }
        else {  // else read data from existing nurture entry in DB
            nurtureDBO.setID(nurture.get().getID());
            nurtureDBO.setName(nurture.get().getName());
        }
        return nurtureDBO;
    }
}

