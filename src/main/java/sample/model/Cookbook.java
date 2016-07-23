package sample.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
public class Cookbook {
    private Integer id;
    private String title;
    private List<CookbookRecipe> cookbookRecipes = new ArrayList<>();
    private List<CookbookSortlevel> cookbookSortlevels = new ArrayList<>();

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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cookbook cookbook = (Cookbook) o;

        if (id != null ? !id.equals(cookbook.id) : cookbook.id != null) return false;
        if (title != null ? !title.equals(cookbook.title) : cookbook.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cookbook")
    public List<CookbookRecipe> getCookbookRecipes() {
        return cookbookRecipes;
    }

    public void setCookbookRecipes(List<CookbookRecipe> cookbookRecipes) {
        this.cookbookRecipes = cookbookRecipes;
    }

    @OneToMany(mappedBy = "cookbook")
    public List<CookbookSortlevel> getCookbookSortlevels() {
        return cookbookSortlevels;
    }

    public void setCookbookSortlevels(List<CookbookSortlevel> cookbookSortlevels) {
        this.cookbookSortlevels = cookbookSortlevels;
    }

    public void addRecipe(Recipe recipe) {
        CookbookRecipe cookbookRecipe = new CookbookRecipe();
        cookbookRecipe.setCookbook(this);
        cookbookRecipe.setRecipe(recipe);
        this.getCookbookRecipes().add( cookbookRecipe );
    }

    @Transient
    public List<Recipe> getRecipes() {
        return this.getCookbookRecipes().stream().map(CookbookRecipe::getRecipe).collect(Collectors.toList());
    }

    public void setRecipes(List<Recipe> recipes) {
        //TODO: implement
        throw new IllegalStateException("Not implemented yet.");
    }

    @Transient
    public List<Sortlevel>getSortlevel() {
        //TODO: implement
        throw new IllegalStateException("Not implemented yet.");
    }

    public void setSortlevel(List<Sortlevel> sortlevel) {
        //TODO: implement
        throw new IllegalStateException("Not implemented yet.");
    }
}
