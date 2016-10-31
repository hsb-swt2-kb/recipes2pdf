package sample.database.service;

import sample.database.dao.*;
import sample.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;


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

        if( recipe.getCookbookRecipes().isEmpty() ) {
            Cookbook cookbook = new Cookbook();
            cookbook.setTitle("Generated Cookbook");
            cookbook = cookbookDAO.findOrCreate(cookbook, "title", cookbook.getTitle());

            final CookbookRecipe cookbookRecipe = new CookbookRecipe();
            cookbookRecipe.setCookbook(cookbook);
            cookbookRecipe.setRecipe(recipe);

            cookbook.getCookbookRecipes().add(cookbookRecipe);
            recipe.getCookbookRecipes().add(cookbookRecipe);
            //cookbookDAO.add(cookbook);
            //cookbookRecipeDAO.add(cookbookRecipe);
        }

        // Reuse Entities in database
        recipe.setCategory( categoryDAO.findOrCreate( recipe.getCategory(), "name", recipe.getCategory().getName()));
        recipe.setCourse( courseDAO.findOrCreate( recipe.getCourse(), "name", recipe.getCourse().getName()));
        recipe.setDaytime( daytimeDAO.findOrCreate( recipe.getDaytime(), "name", recipe.getDaytime().getName()));
        recipe.setNurture( nurtureDAO.findOrCreate( recipe.getNurture(), "name", recipe.getNurture().getName()));
        recipe.setRegion( regionDAO.findOrCreate( recipe.getRegion(), "name", recipe.getRegion().getName()));
        recipe.setSeason( seasonDAO.findOrCreate( recipe.getSeason(), "name", recipe.getSeason().getName()));
        recipe.setSource( sourceDAO.findOrCreate( recipe.getSource(), "name", recipe.getSource().getName()));

        for (RecipeIngredient recipeIngredient: recipe.getRecipeIngredients() ) {
            recipeIngredient.setIngredient( ingredientDAO.findOrCreate(recipeIngredient.getIngredient(), "name", recipeIngredient.getIngredient().getName().replace("\t", "").trim() ) );
            recipeIngredient.setUnit( unitDAO.findOrCreate(recipeIngredient.getUnit(), "name", recipeIngredient.getUnit().getName().replace("\t", "").trim() ) );
            recipeIngredient.setRecipe(recipe);
        }

        recipeDAO.saveOrUpdate(recipe);
    }
}
