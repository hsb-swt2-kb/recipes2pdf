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
    private Collection<CookbookRecipe> cookbookRecipesById;
    private Collection<CookbookSortlevel> cookbookSortlevelsById;

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

    @OneToMany(mappedBy = "cookbookByCookbookId", fetch = FetchType.EAGER)
    public Collection<CookbookRecipe> getCookbookRecipesById() {
        return cookbookRecipesById;
    }

    public void setCookbookRecipesById(Collection<CookbookRecipe> cookbookRecipesById) {
        this.cookbookRecipesById = cookbookRecipesById;
    }

    @OneToMany(mappedBy = "cookbookByCookbookId")
    public Collection<CookbookSortlevel> getCookbookSortlevelsById() {
        return cookbookSortlevelsById;
    }

    public void setCookbookSortlevelsById(Collection<CookbookSortlevel> cookbookSortlevelsById) {
        this.cookbookSortlevelsById = cookbookSortlevelsById;
    }
}
