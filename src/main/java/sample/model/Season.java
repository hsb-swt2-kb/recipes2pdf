package sample.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
public class Season {
    private Integer id;
    private String name;
    private List<Recipe> recipes;

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

        Season season = (Season) o;

        if (id != null ? !id.equals(season.id) : season.id != null) return false;
        if (name != null ? !name.equals(season.name) : season.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "season")
    public List<Recipe> getRecipesById() {
        return recipes;
    }

    public void setRecipesById(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
