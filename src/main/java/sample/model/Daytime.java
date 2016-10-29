package sample.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
public class Daytime {
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

        Daytime daytime = (Daytime) o;

        if (id != null ? !id.equals(daytime.id) : daytime.id != null) return false;
        if (name != null ? !name.equals(daytime.name) : daytime.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "daytime")
    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
