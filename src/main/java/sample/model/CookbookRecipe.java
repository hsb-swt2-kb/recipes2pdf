package sample.model;

import javax.persistence.*;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
@Table(name = "cookbook_recipe", schema = "", catalog = "")
public class CookbookRecipe {
    private Integer id;
    private Recipe recipeByRecipeId;
    private Cookbook cookbookByCookbookId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CookbookRecipe that = (CookbookRecipe) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    public Recipe getRecipeByRecipeId() {
        return recipeByRecipeId;
    }

    public void setRecipeByRecipeId(Recipe recipeByRecipeId) {
        this.recipeByRecipeId = recipeByRecipeId;
    }

    @ManyToOne
    @JoinColumn(name = "cookbook_id", referencedColumnName = "id", nullable=false,insertable=false,updatable=false )
    public Cookbook getCookbookByCookbookId() {
        return cookbookByCookbookId;
    }

    public void setCookbookByCookbookId(Cookbook cookbookByCookbookId) {
        this.cookbookByCookbookId = cookbookByCookbookId;
    }
}
