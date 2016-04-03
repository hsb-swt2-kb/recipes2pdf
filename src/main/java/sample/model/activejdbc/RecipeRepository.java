package sample.model.activejdbc;

import sample.model.IRecipe;
import sample.model.IRecipeRepository;
import sample.model.activejdbc.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 03.04.16.
 */
public class RecipeRepository implements IRecipeRepository {
    @Override
    public List<IRecipe> getAll() {
        List<IRecipe> list = new ArrayList<>();
        list.addAll(Recipe.findAll());
        return list;
    }

    @Override
    public Optional<IRecipe> findById(long id) {
        return Optional.ofNullable(Recipe.findById(id));
    }

    @Override
    public Optional<IRecipe> findFirst(String subQuery, Object... params) {
        return Optional.ofNullable(Recipe.findFirst(subQuery, params));
    }
}
