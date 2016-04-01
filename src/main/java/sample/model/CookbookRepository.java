package sample.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 30.03.16.
 */
public class CookbookRepository implements ICookbookRepository {

    @Override
    public List<ICookbook> getCookbooks() {
        List<ICookbook> list= new ArrayList<>();
        list.addAll(Cookbook.findAll());
        return list;
    }

    @Override
    public Optional<ICookbook> getCookbookById( long id ) {
        ICookbook result = Cookbook.findById( id );
        return Optional.ofNullable(result);
    }

}
