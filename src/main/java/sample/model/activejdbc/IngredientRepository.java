package sample.model.activejdbc;

import sample.model.IIngredient;
import sample.model.IIngredientRepository;
import sample.model.activejdbc.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 03.04.16.
 */
public class IngredientRepository implements IIngredientRepository {
    @Override
    public List<IIngredient> getAll() {
        List<IIngredient> list = new ArrayList<>();
        list.addAll(Ingredient.findAll());
        return list;
    }

    @Override
    public Optional<IIngredient> findById(long id) {
        return Optional.ofNullable(Ingredient.findById(id));
    }

    @Override
    public Optional<IIngredient> findFirst(String subQuery, Object... params) {
        return Optional.ofNullable(Ingredient.findFirst(subQuery, params));
    }
}
