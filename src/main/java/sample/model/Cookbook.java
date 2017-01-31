package sample.model;

import sample.ui.adapter.GUIRepresentable;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
public class Cookbook implements GUIRepresentable {
    private Integer id;
    private String title;
    private Set<CookbookRecipe> cookbookRecipes = new CopyOnWriteArraySet<>();
    private Set<CookbookSortlevel> cookbookSortlevels = new CopyOnWriteArraySet<>();

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

    @OneToMany(mappedBy = "cookbook", cascade = CascadeType.ALL)
    public Set<CookbookRecipe> getCookbookRecipes() {
        return cookbookRecipes;
    }

    public void setCookbookRecipes(Set<CookbookRecipe> cookbookRecipes) {
        this.cookbookRecipes = cookbookRecipes;
    }

    @OneToMany(mappedBy = "cookbook", cascade = CascadeType.ALL)
    public Set<CookbookSortlevel> getCookbookSortlevels() {
        return cookbookSortlevels;
    }

    public void setCookbookSortlevels(Set<CookbookSortlevel> cookbookSortlevels) {
        this.cookbookSortlevels = cookbookSortlevels;
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
