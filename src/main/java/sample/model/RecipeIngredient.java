package sample.model;

import javax.persistence.*;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
@Table(name = "recipe_ingredient", schema = "", catalog = "")
public class RecipeIngredient implements Comparable<RecipeIngredient> {
    private Integer id;
    private Double amount;
    private Unit unit;
    private Ingredient ingredient;
    private Recipe recipe;

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
        result = 31 * result + (getIngredient() != null ? getIngredient().hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public int compareTo(RecipeIngredient o) {
        if( o.getIngredient().getName().equals( this.getIngredient().getName())) return 0;
        else return -1;
    }
}
