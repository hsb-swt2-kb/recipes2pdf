package sample.model;

import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by czoeller on 11.07.16.
 */
@Entity
public class Recipe {
    private Integer id;
    private String title;
    private String text;
    private Integer portions;
    private Integer duration;
    private byte[] image;
    private Integer calories;
    private List<CookbookRecipe> cookbookRecipes = new ArrayList<>();
    private Source source;
    private Nurture nurture;
    private Season season;
    private Daytime daytime;
    private Region region;
    private Course course;
    private Category category;
    private Set<RecipeIngredient> recipeIngredients = new TreeSet<>();

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

    @Basic
    @Column(name = "text", length = 424242)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "portions")
    public Integer getPortions() {
        return portions;
    }

    public void setPortions(Integer portions) {
        this.portions = portions;
    }

    @Basic
    @Column(name = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    //TODO: fix dialect support for lob. @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Basic
    @Column(name = "calories")
    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != null ? !id.equals(recipe.id) : recipe.id != null) return false;
        if (title != null ? !title.equals(recipe.title) : recipe.title != null) return false;
        if (text != null ? !text.equals(recipe.text) : recipe.text != null) return false;
        if (portions != null ? !portions.equals(recipe.portions) : recipe.portions != null) return false;
        if (duration != null ? !duration.equals(recipe.duration) : recipe.duration != null) return false;
        if (image != null ? !image.equals(recipe.image) : recipe.image != null) return false;
        if (calories != null ? !calories.equals(recipe.calories) : recipe.calories != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (portions != null ? portions.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (calories != null ? calories.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    public List<CookbookRecipe> getCookbookRecipes() {
        return cookbookRecipes;
    }

    public void setCookbookRecipes(List<CookbookRecipe> cookbookRecipes) {
        this.cookbookRecipes = cookbookRecipes;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nurture_id", referencedColumnName = "id")
    public Nurture getNurture() {
        return nurture;
    }

    public void setNurture(Nurture nurture) {
        this.nurture = nurture;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "daytime_id", referencedColumnName = "id")
    public Daytime getDaytime() {
        return daytime;
    }

    public void setDaytime(Daytime daytime) {
        this.daytime = daytime;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    @Transient
    public List<Ingredient> getIngredients() {
        return this.getRecipeIngredients().stream().map(RecipeIngredient::getIngredient).collect(Collectors.toList());
    }

    public void setIngredients(List<Ingredient> ingredients) {
        //TODO: implement
        throw new NotImplementedException("setIngredients is not implemented yet");
    }

    @Deprecated
    public void add(String s, double amount, String s1) {
        throw new IllegalStateException("Moved to DAO.");
    }
}
