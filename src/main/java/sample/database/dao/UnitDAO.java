package sample.database.dao;

import sample.database.dbo.UnitDBO;
import sample.model.Unit;

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
        if (findById(pojo.getID()).isPresent()) {
            unitDBO.setID(pojo.getID());
        }

        unitDBO.setName(pojo.getName());
        return unitDBO;
    }
}
