package sample.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
public class Cookbook {
    private Integer id;
    private String title;
    private Collection<CookbookRecipe> cookbookRecipes;
    private Collection<CookbookSortlevel> cookbookSortlevels;

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

    @OneToMany(mappedBy = "cookbook", fetch = FetchType.EAGER)
    public Collection<CookbookRecipe> getCookbookRecipes() {
        return cookbookRecipes;
    }

    public void setCookbookRecipes(Collection<CookbookRecipe> cookbookRecipes) {
        this.cookbookRecipes = cookbookRecipes;
    }

    @OneToMany(mappedBy = "cookbook")
    public Collection<CookbookSortlevel> getCookbookSortlevels() {
        return cookbookSortlevels;
    }

    public void setCookbookSortlevels(Collection<CookbookSortlevel> cookbookSortlevels) {
        this.cookbookSortlevels = cookbookSortlevels;
    }
}
