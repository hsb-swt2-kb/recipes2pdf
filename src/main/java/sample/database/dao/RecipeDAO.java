package sample.database.dao;

import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import sample.database.dbo.*;
import sample.model.*;

import java.util.List;

/**
 * Created by czoeller on 28.04.16.
 */
public class RecipeDAO extends ADAO<Recipe, RecipeDBO> {

    @Override
    Recipe toPOJO(RecipeDBO recipeDBO) {
        final Recipe recipe = new Recipe();
        recipe.setID(recipeDBO.getID());
        recipe.setTitle(recipeDBO.getTitle());
        recipe.setText(recipeDBO.getText());
        recipe.setPortions(recipeDBO.getPortions());
        recipe.setDuration(recipeDBO.getDuration());
        recipe.setImage(recipeDBO.getImage());
        recipe.setCalories(recipeDBO.getCalories());

        final CategoryDBO categoryDBO = (CategoryDBO) recipeDBO.getCategory();
        if( null != categoryDBO ) {
            recipe.setCategory( new CategoryDAO().toPOJO( categoryDBO ) );
        }

        final CourseDBO courseDBO = (CourseDBO) recipeDBO.getCourse();
        if( null != courseDBO ) {
            recipe.setCourse( new CourseDAO().toPOJO( courseDBO ) );
        }

        final RegionDBO regionDBO = (RegionDBO) recipeDBO.getRegion();
        if( null != regionDBO ) {
            recipe.setRegion( new RegionDAO().toPOJO( regionDBO ) );
        }

        final DaytimeDBO daytimeDBO = (DaytimeDBO) recipeDBO.getDaytime();
        if( null != daytimeDBO ) {
            recipe.setDaytime( new DaytimeDAO().toPOJO( daytimeDBO ) );
        }

        final SeasonDBO seasonDBO = (SeasonDBO) recipeDBO.getSeason();
        if( null != seasonDBO ) {
            recipe.setSeason( new SeasonDAO().toPOJO( seasonDBO ) );
        }

        final NurtureDBO nurtureDBO = (NurtureDBO) recipeDBO.getNurture();
        if( null != nurtureDBO ) {
            recipe.setNurture( new NurtureDAO().toPOJO( nurtureDBO ) );
        }

        final List<Triple<IIngredient, Integer, IUnit>> ingredients = recipeDBO.getIngredients();
        for ( Triple<IIngredient, Integer, IUnit> triple : ingredients) {
            Ingredient ingredient = new IngredientDAO().toPOJO( (IngredientDBO) triple.getLeft() );
            Unit unit = new UnitDAO().toPOJO( (UnitDBO) triple.getRight() );
            Integer amout = triple.getMiddle();

            Triple<IIngredient, Integer, IUnit> row = new MutableTriple<>(ingredient, amout, unit);
            recipe.add(row);
        }

        return recipe;
    }

    @Override
    RecipeDBO toDBO(Recipe recipe) {
        final RecipeDBO recipeDBO = new RecipeDBO();
        recipeDBO.setID(recipe.getID());
        recipeDBO.setTitle(recipe.getTitle());
        recipeDBO.setText(recipe.getText());
        recipeDBO.setPortions(recipe.getPortions());
        recipeDBO.setDuration(recipe.getDuration());
        recipeDBO.setImage(recipe.getImage());
        recipeDBO.setCalories(recipe.getCalories());
        recipeDBO.saveIt();

        final Category category = (Category) recipe.getCategory();
        if (category != null) {
            final CategoryDBO categoryDBO = new CategoryDAO().toDBO(category);
            categoryDBO.saveIt();
            recipeDBO.setCategory(categoryDBO);
        }

        final Course course = (Course) recipe.getCourse();
        if( null != course ) {
            final CourseDBO courseDBO = new CourseDAO().toDBO(course);
            courseDBO.saveIt();
            recipeDBO.setCourse( courseDBO );
        }

        final Region region = (Region) recipe.getRegion();
        if( null != region ) {
            final RegionDBO regionDBO = new RegionDAO().toDBO(region);
            regionDBO.saveIt();
            recipeDBO.setRegion( regionDBO );
        }

        final Daytime daytime = (Daytime) recipe.getDaytime();
        if( null != daytime ) {
            final DaytimeDBO daytimeDBO = new DaytimeDAO().toDBO(daytime);
            daytimeDBO.saveIt();
            recipeDBO.setDaytime( daytimeDBO );
        }

        final Season season = (Season) recipe.getSeason();
        if( null != season ) {
            final SeasonDBO seasonDBO = new SeasonDAO().toDBO(season);
            seasonDBO.saveIt();
            recipeDBO.setSeason( seasonDBO );
        }

        final Nurture nurture = (Nurture) recipe.getNurture();
        if( null != nurture ) {
            final NurtureDBO nurtureDBO = new NurtureDAO().toDBO(nurture);
            nurtureDBO.saveIt();
            recipeDBO.setNurture( nurtureDBO );
        }

        final List<Triple<IIngredient, Integer, IUnit>> ingredients = recipe.getIngredients();
        for ( Triple<IIngredient, Integer, IUnit> triple : ingredients) {
            recipeDBO.add(triple.getLeft().getName(), triple.getMiddle(), triple.getRight().getName());
        }
        return recipeDBO;
    }
}
