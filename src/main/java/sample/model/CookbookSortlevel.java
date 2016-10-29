package sample.model;

import javax.persistence.*;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
@Table(name = "cookbook_sortlevel", schema = "", catalog = "")
public class CookbookSortlevel {
    private Integer id;
    private Sortlevel sortlevelBySortlevelId;
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

        CookbookSortlevel that = (CookbookSortlevel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "sortlevel_id", referencedColumnName = "id")
    public Sortlevel getSortlevelBySortlevelId() {
        return sortlevelBySortlevelId;
    }

    public void setSortlevelBySortlevelId(Sortlevel sortlevelBySortlevelId) {
        this.sortlevelBySortlevelId = sortlevelBySortlevelId;
    }

    @ManyToOne
    @JoinColumn(name = "cookbook_id", referencedColumnName = "id")
    public Cookbook getCookbookByCookbookId() {
        return cookbookByCookbookId;
    }

    public void setCookbookByCookbookId(Cookbook cookbookByCookbookId) {
        this.cookbookByCookbookId = cookbookByCookbookId;
    }
}
