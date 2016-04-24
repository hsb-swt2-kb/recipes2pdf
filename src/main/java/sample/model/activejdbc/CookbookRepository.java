package sample.model.activejdbc;

import sample.model.ICookbook;
import sample.model.ICookbookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 30.03.16.
 */
class CookbookRepository implements ICookbookRepository<ICookbook> {
    @Override
    public List<ICookbook> getAll() {
        List<ICookbook> list = new ArrayList<>();
        list.addAll( Cookbook.findAll() );
        return list;
    }

    @Override
    public Optional<ICookbook> findById( long id ) {
        return Optional.ofNullable( Cookbook.findById( id ) );
    }

    @Override
    public Optional<ICookbook> findFirst(String subQuery, Object... params) {
        return Optional.ofNullable( Cookbook.findFirst(subQuery, params) );
    }
}
