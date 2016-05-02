package sample.model.dao;

import sample.model.Cookbook;
import sample.model.activejdbc.CookbookDBO;

/**
 * Created by noex_ on 02.05.2016.
 */
public class CookbookDAO extends ADAO<Cookbook, CookbookDBO> {

    @Override
    Cookbook toPOJO(CookbookDBO cookbookDBO) {
        final Cookbook cookbook = new Cookbook();
        cookbook.setID(cookbookDBO.getID());
        cookbook.setTitle(cookbookDBO.getTitle());
        return cookbook;
    }

    @Override
    CookbookDBO toDBO(Cookbook pojo) {
        CookbookDBO cookbookDBO = new CookbookDBO();
        if (findById(pojo.getID()).isPresent()) {
            cookbookDBO.setID(pojo.getID());
        }

        cookbookDBO.setTitle(pojo.getTitle());
        return cookbookDBO;
    }
}
