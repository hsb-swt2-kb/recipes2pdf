package sample.database.dao;

import sample.database.dbo.CookbookDBO;
import sample.database.dbo.RecipeDBO;
import sample.database.dbo.SortlevelDBO;
import sample.model.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Database Access Object for Cookbook.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 02.05.2016.
 */
public class CookbookDAO extends ADAO<Cookbook, CookbookDBO> {

    @Override
    Cookbook toPOJO(CookbookDBO cookbookDBO) {
        final Cookbook cookbook = new Cookbook();
        cookbook.setID(cookbookDBO.getID());
        cookbook.setTitle(cookbookDBO.getTitle());

        List<ISortlevel> sortlevels = cookbookDBO.getSortlevel();
        if (null != sortlevels) {
            for (ISortlevel sortlevel : sortlevels) {
                cookbook.addSortlevel(new SortlevelDAO().toPOJO((SortlevelDBO) sortlevel));
            }
        }

        List<IRecipe> recipes = cookbookDBO.getRecipes();
        if (null != recipes) {
            for (IRecipe recipe : recipes) {
                cookbook.addRecipe(new RecipeDAO().toPOJO((RecipeDBO) recipe));
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
        if (null != sortlevels) {
            for (ISortlevel sortlevel : sortlevels) {
                final Optional<Sortlevel> byName = new SortlevelDAO().findFirst("name = ?", sortlevel.getName());
                if (byName.isPresent()) {
                    // If already persisted but not associated yet then associate existing dbo to cookbook
                    cookbookDBO.addSortlevel(new SortlevelDAO().toDBO(byName.get()));
                    LOG.debug("Associated already persisted Sortlevel = [" + byName.get() + "] to cookbook = [" + cookbookDBO + "].");
                } else if (!byName.isPresent()) {
                    // If not persisted yet then persist and associate to cookbook
                    final Sortlevel notInsertedSortlevel = (Sortlevel) sortlevel;
                    new SortlevelDAO().insert(notInsertedSortlevel);
                    SortlevelDBO sortlevelDBO = new SortlevelDAO().toDBO((Sortlevel) sortlevel);
                    cookbookDBO.addSortlevel(sortlevelDBO);
                    LOG.debug("Persisted new Sortlevel = [" + sortlevelDBO + "] and associated to cookbook = [" + cookbookDBO + "].");
                } else if (cookbookDBO.get(SortlevelDBO.class, "name = ?", sortlevel.getName()).isEmpty()) {
                    // If already persisted but not associated yet then associate new dbo to cookbook
                    cookbookDBO.addSortlevel(new SortlevelDAO().toDBO((Sortlevel) sortlevel));
                    LOG.debug("Associated already persisted Sortlevel = [" + byName.get() + "] to cookbook = [" + cookbookDBO + "].");
                }
            }
        }

        List<IRecipe> recipes = pojo.getRecipes();
        if (null != recipes) {
            for (IRecipe recipe : recipes) {
                final Optional<Recipe> recipeOpt = new RecipeDAO().findById(recipe.getID());
                if (!recipeOpt.isPresent()) {
                    final RecipeDBO recipeDBO = new RecipeDAO().toDBO((Recipe) recipe);
                    recipeDBO.saveIt();
                    cookbookDBO.addRecipe(recipeDBO);
                } else if (recipeOpt.isPresent()) {
                    // Add recipe
                    final RecipeDBO recipeDBO = new RecipeDAO().toDBO((Recipe) recipe);
                    if( cookbookDBO.get(RecipeDBO.class, "recipe.id = ?", recipe.getID()).isEmpty()) {
                        cookbookDBO.add(recipeDBO);
                    } else {
                        // Remove recipe
                        final List<Long> listOfAssociatedIDsInPojo = pojo.getRecipes()
                            .stream()
                            .map(IIdentifiable::getID)
                            .collect(Collectors.toList());

                        List<RecipeDBO>listOfAssociatedIDsInPojoAndDBO = cookbookDBO.getAll(RecipeDBO.class)
                            .stream()
                            .filter(iRecipeDBO -> listOfAssociatedIDsInPojo.contains(recipeDBO.getID()))
                            .collect(Collectors.toList());

                        boolean associated = listOfAssociatedIDsInPojoAndDBO.contains(recipeDBO.getID());

                        if( !associated ) {
                            cookbookDBO.remove(recipeDBO);
                        }
                    }
                }
            }
        }

        return cookbookDBO;
    }
}
