package sample.database.dao;

import sample.database.dbo.UnitDBO;
import sample.model.Unit;

import java.util.Optional;

/**
 * Database Access Object for Unit.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 02.05.2016.
 */
public class UnitDAO extends ADAO<Unit, UnitDBO> {
    @Override
    public Unit toPOJO(UnitDBO unitDBO) {
        final Unit unit = new Unit();
        unit.setID(unitDBO.getID());
        unit.setName(unitDBO.getName());
        return unit;
    }

    @Override
    public UnitDBO toDBO(Unit pojo) {
        UnitDBO unitDBO = new UnitDBO();
        Optional<Unit> unit = findFirst("name=?", pojo.getName());

        // if unit not present in DB then insert it.
        if(!unit.isPresent()){
            unitDBO.setName(pojo.getName());
            unitDBO.saveIt();
        }
        else {  // else read data from existing unit entry in DB
            unitDBO.setID(unit.get().getID());
            unitDBO.setName(unit.get().getName());
        }
        return unitDBO;
    }
}
