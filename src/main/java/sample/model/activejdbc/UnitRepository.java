package sample.model.activejdbc;

import sample.model.IUnit;
import sample.model.IUnitRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 02.04.16.
 */
public class UnitRepository implements IUnitRepository {
    @Override
    public List<IUnit> getAll() {
        List<IUnit> list = new ArrayList<>();
        list.addAll(Unit.findAll());
        return list;
    }

    @Override
    public Optional<IUnit> findById(long id) {
        return Optional.ofNullable(Unit.findById(id));
    }

    @Override
    public Optional<IUnit> findFirst(String subQuery, Object... params) {
        return Optional.ofNullable(Unit.findFirst(subQuery, params));
    }
}
