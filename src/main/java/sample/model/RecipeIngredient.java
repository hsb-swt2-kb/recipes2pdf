package sample.model;

import javax.persistence.*;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
@Table(name = "recipe_ingredient", schema = "", catalog = "")
public class RecipeIngredient {
    private Integer id;
    private Double amount;
    private Unit unitByUnitId;
    private Ingredient ingredientByIngredientId;
    private Recipe recipeByRecipeId;

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
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeIngredient that = (RecipeIngredient) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    public Unit getUnitByUnitId() {
        return unitByUnitId;
    }

    public void setUnitByUnitId(Unit unitByUnitId) {
        this.unitByUnitId = unitByUnitId;
    }

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    public Ingredient getIngredientByIngredientId() {
        return ingredientByIngredientId;
    }

    public void setIngredientByIngredientId(Ingredient ingredientByIngredientId) {
        this.ingredientByIngredientId = ingredientByIngredientId;
    }

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    public Recipe getRecipeByRecipeId() {
        return recipeByRecipeId;
    }

    public void setRecipeByRecipeId(Recipe recipeByRecipeId) {
        this.recipeByRecipeId = recipeByRecipeId;
    }
}
