package sample.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
@Table(name = "cookbook_recipe", schema = "", catalog = "", uniqueConstraints = {@UniqueConstraint(columnNames = {"recipe_id" , "cookbook_id"})})
public class CookbookRecipe {
    private Integer id;
    private Recipe recipe;
    private Cookbook cookbook;

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
        return Objects.equals(recipe.getId(), that.recipe.getId()) && Objects.equals(cookbook.getId(), that.cookbook.getId());
    }

    @Override
    public int hashCode() {
        int result = recipe != null && recipe.getId() != null ? recipe.getId().hashCode() : 0;
        result = 31 * result + (cookbook != null && cookbook.getId() != null ? cookbook.getId().hashCode() : 0);
        return result;
    }

    // Cascade all but REMOVE: A recipe should not be deleted when removing the association
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    // Cascade all but REMOVE: A cookbook should not be deleted when removing the association
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "cookbook_id", referencedColumnName = "id")
    public Cookbook getCookbook() {
        return cookbook;
    }

    public void setCookbook(Cookbook cookbook) {
        this.cookbook = cookbook;
    }
}
