package sample.database.dao;

import sample.database.dbo.CookbookDBO;
import sample.database.dbo.RecipeDBO;
import sample.database.dbo.SortlevelDBO;
import sample.model.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 02.05.2016.
 */
public class CookbookDAO extends ADAO<Cookbook, CookbookDBO> {

    @Override
    Cookbook toPOJO(CookbookDBO cookbookDBO) {
        final Cookbook cookbook = new Cookbook();
        cookbook.setID(cookbookDBO.getID());
        cookbook.setTitle(cookbookDBO.getTitle());

        List<ISortlevel> sortlevels = cookbookDBO.getSortlevel();
        if(null != sortlevels) {
            for(ISortlevel sortlevel : sortlevels) {
                cookbook.addSortlevel( new SortlevelDAO().toPOJO((SortlevelDBO) sortlevel) );
            }
        }

        List<IRecipe> recipes = cookbookDBO.getRecipes();
        if(null != recipes) {
            for(IRecipe recipe : recipes) {
                cookbook.addRecipe( new RecipeDAO().toPOJO((RecipeDBO) recipe) );
            }
        }

        return cookbook;
    }

    @Override
    CookbookDBO toDBO(Cookbook pojo) {
        CookbookDBO cookbookDBO = new CookbookDBO();
        if (findById(pojo.getID()).isPresent()) {
            cookbookDBO.setID(pojo.getID());
        }
        cookbookDBO.setTitle(pojo.getTitle());
        cookbookDBO.saveIt();

        List<ISortlevel> sortlevels = pojo.getSortlevel();
        if(null != sortlevels) {
            for(ISortlevel sortlevel : sortlevels) {
                final Optional<Sortlevel> byName = new SortlevelDAO().findFirst("name = ?", sortlevel.getName());
                if( byName.isPresent() ) {
                    // If already persisted but not associated yet then associate existing dbo to cookbook
                    cookbookDBO.addSortlevel(new SortlevelDAO().toDBO(byName.get()));
                } else if( !byName.isPresent() ) {
                    // If not persisted yet then persist and associate to cookbook
                    final Sortlevel notInsertedSortlevel = (Sortlevel) sortlevel;
                    new SortlevelDAO().insert(notInsertedSortlevel);
                    SortlevelDBO sortlevelDBO = new SortlevelDAO().toDBO((Sortlevel) sortlevel);
                    cookbookDBO.addSortlevel(sortlevelDBO);
                } else if( cookbookDBO.get(SortlevelDBO.class, "name = ?", sortlevel.getName() ).isEmpty() ) {
                    // If already persisted but not associated yet then associate new dbo to cookbook
                    cookbookDBO.addSortlevel(new SortlevelDAO().toDBO((Sortlevel) sortlevel));
                }
            }
        }

        List<IRecipe> recipes = pojo.getRecipes();
        if(null != recipes) {
            for(IRecipe recipe : recipes) {
                if( !new RecipeDAO().findById( recipe.getID() ).isPresent() ) {
                    cookbookDBO.addRecipe( new RecipeDAO().toDBO( (Recipe) recipe) );
                }
            }
        }

        return cookbookDBO;
    }
}
