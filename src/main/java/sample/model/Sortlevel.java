package sample.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
public class Sortlevel {
    private Integer id;
    private String name;
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

        Sortlevel sortlevel = (Sortlevel) o;

        if (id != null ? !id.equals(sortlevel.id) : sortlevel.id != null) return false;
        if (name != null ? !name.equals(sortlevel.name) : sortlevel.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "sortlevelBySortlevelId")
    public Collection<CookbookSortlevel> getCookbookSortlevelsById() {
        return cookbookSortlevelsById;
    }

    public void setCookbookSortlevelsById(Collection<CookbookSortlevel> cookbookSortlevelsById) {
        this.cookbookSortlevelsById = cookbookSortlevelsById;
    }
}
