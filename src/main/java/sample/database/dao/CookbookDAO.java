package sample.database.dao;

import sample.database.dbo.CookbookDBO;
import sample.model.Cookbook;

/**
 * Created by czoeller on 02.05.2016.
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
