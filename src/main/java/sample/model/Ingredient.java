package sample.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
public class Ingredient {
    private Integer id;
    private String name;
    private Collection<RecipeIngredient> recipeIngredientsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "ingredientByIngredientId")
    public Collection<RecipeIngredient> getRecipeIngredientsById() {
        return recipeIngredientsById;
    }

    public void setRecipeIngredientsById(Collection<RecipeIngredient> recipeIngredientsById) {
        this.recipeIngredientsById = recipeIngredientsById;
    }
}
