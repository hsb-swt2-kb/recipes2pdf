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

        addAssociations(pojo, cookbookDBO);
        removeUnusedAssociations(pojo, cookbookDBO);

        return cookbookDBO;
    }

    private void addAssociations(Cookbook pojo, CookbookDBO cookbookDBO) {

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
                    }
                }
            }
        }
    }

    private void removeUnusedAssociations(Cookbook pojo, CookbookDBO cookbookDBO) {

        /*
         * A list of the ids of the associated recipes.
         * */
        final List<Long> listOfAssociatedRecipeIDsInPojo = pojo.getRecipes()
            .stream()
            .filter(recipe -> recipe.getID() != null)
            .map(IIdentifiable::getID)
            .collect(Collectors.toList());

         /*
         * There might be pojos in this cookbook that are associated but not persisted yet.
         * Therefore their id is null, but other attributes are set. The name is used as identification for there pojos
         * */
        final List<String> listOfAssociatedRecipeNamessInPojoButNotPersistedYet = pojo.getRecipes()
            .stream()
            .filter(recipe -> recipe.getID() == null)
            .map(IRecipe::getTitle)
            .collect(Collectors.toList());

        /*
         * Fill a list with dbo that need to be unassociated.
         * This is the case if: there is a association in the database
         * but the passed pojo doesn't has this association any more.
         * But as there are recipe pojos that are not persisted yet but part of the cookbook
         * pojo they must be not in the list of spared recipe names we have to skip them.
         * */
        List<RecipeDBO>listOfUnAssociatedIDsInPojoAndDBO = cookbookDBO.getAll(RecipeDBO.class)
            .stream()
            .filter(iRecipeDBO ->
                !listOfAssociatedRecipeIDsInPojo.contains(iRecipeDBO.getID())
                && !listOfAssociatedRecipeNamessInPojoButNotPersistedYet.contains(iRecipeDBO.getTitle()))
            .collect(Collectors.toList());

        listOfUnAssociatedIDsInPojoAndDBO.stream().forEach(cookbookDBO::remove);
    }
}
