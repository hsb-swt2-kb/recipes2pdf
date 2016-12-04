package sample.database.service;

import sample.database.dao.IGenericDAO;
import sample.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;


/**
 * Created by noex_ on 26.10.2016.
 */
@Singleton
public class RecipeService {

    @Inject
    private IGenericDAO<CookbookRecipe, Integer> cookbookRecipeDAO;
    @Inject
    private IGenericDAO<Cookbook, Integer> cookbookDAO;
    @Inject
    private IGenericDAO<Recipe, Integer> recipeDAO;
    @Inject
    private IGenericDAO<Ingredient, Integer> ingredientDAO;
    @Inject
    private IGenericDAO<RecipeIngredient, Integer> recipeIngredientDAO;
    @Inject
    private IGenericDAO<Unit, Integer> unitDAO;
    @Inject
    private IGenericDAO<Category, Integer> categoryDAO;
    @Inject
    private IGenericDAO<Course, Integer> courseDAO;
    @Inject
    private IGenericDAO<Daytime, Integer> daytimeDAO;
    @Inject
    private IGenericDAO<Nurture, Integer> nurtureDAO;
    @Inject
    private IGenericDAO<Region, Integer> regionDAO;
    @Inject
    private IGenericDAO<Season, Integer> seasonDAO;
    @Inject
    private IGenericDAO<Source, Integer> sourceDAO;

    public void create(Recipe recipe) {

        if (recipe.getCookbookRecipes()
                  .isEmpty()) {
            Cookbook cookbook = new Cookbook();
            cookbook.setTitle("Generated Cookbook");
            cookbook = cookbookDAO.findOrNew(cookbook, "title", cookbook.getTitle());

            final CookbookRecipe cookbookRecipe = new CookbookRecipe();
            cookbookRecipe.setCookbook(cookbook);
            cookbookRecipe.setRecipe(recipe);

            cookbook.getCookbookRecipes()
                    .add(cookbookRecipe);
            recipe.getCookbookRecipes()
                  .add(cookbookRecipe);
            //cookbookDAO.add(cookbook);
            //cookbookRecipeDAO.add(cookbookRecipe);
        }

        // Reuse Entities in database
        recipe.setCategory(categoryDAO.findOrNew(recipe.getCategory(), "name", recipe.getCategory()
                                                                                     .getName()));
        recipe.setCourse(courseDAO.findOrNew(recipe.getCourse(), "name", recipe.getCourse()
                                                                               .getName()));
        recipe.setDaytime(daytimeDAO.findOrNew(recipe.getDaytime(), "name", recipe.getDaytime()
                                                                                  .getName()));
        recipe.setNurture(nurtureDAO.findOrNew(recipe.getNurture(), "name", recipe.getNurture()
                                                                                  .getName()));
        recipe.setRegion(regionDAO.findOrNew(recipe.getRegion(), "name", recipe.getRegion()
                                                                               .getName()));
        recipe.setSeason(seasonDAO.findOrNew(recipe.getSeason(), "name", recipe.getSeason()
                                                                               .getName()));
        recipe.setSource(sourceDAO.findOrNew(recipe.getSource(), "name", recipe.getSource()
                                                                               .getName()));

        for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
            // Fix name
            recipeIngredient.getIngredient()
                            .setName(recipeIngredient.getIngredient()
                                                     .getName()
                                                     .replace("\t", "")
                                                     .trim());
            recipeIngredient.getUnit()
                            .setName(recipeIngredient.getUnit()
                                                     .getName()
                                                     .replace("\t", "")
                                                     .trim());
            recipeIngredient.setRecipe(recipe);

            /*
            Ingredient ingredient = ingredientDAO.findOrNew(recipeIngredient.getIngredient(), "name", recipeIngredient.getIngredient().getName() );
            Unit unit = unitDAO.findOrNew(recipeIngredient.getUnit(), "name", recipeIngredient.getUnit().getName());

            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setUnit(unit);
            recipeIngredient.setRecipe(recipe);
            unitDAO.saveOrUpdate(unit);
            ingredientDAO.saveOrUpdate(ingredient);
            ingredientDAO.saveOrUpdate(ingredient);*/
        }

        recipe = recipeDAO.saveOrUpdate(recipe);

        for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
            final Optional<Ingredient> ingredient = ingredientDAO.getAll()
                                                                 .stream()
                                                                 .filter(ingredient1 -> ingredient1.getName()
                                                                                                   .equals(recipeIngredient.getIngredient()
                                                                                                                           .getName()))
                                                                 .findFirst();
            final Optional<Unit> unit = unitDAO.getAll()
                                               .stream()
                                               .filter(unit1 -> unit1.getName()
                                                                     .equals(recipeIngredient.getUnit()
                                                                                             .getName()))
                                               .findFirst();
            if (ingredient.isPresent()) {
                recipeIngredient.setIngredient(ingredient.get());
            }
            if (unit.isPresent()) {
                recipeIngredient.setUnit(unit.get());
            }
            recipeIngredientDAO.saveOrUpdate(recipeIngredient);
        }

    }
}
