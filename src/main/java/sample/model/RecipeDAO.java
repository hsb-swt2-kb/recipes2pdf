package sample.model;

import org.javalite.activejdbc.LazyList;
import sample.model.activejdbc.RecipeDBO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 28.04.16.
 */
public class RecipeDAO implements IDAO<Recipe> {
    /**
     * Returns all records of this entity. If you need to get a subset, look for variations of "find()".
     *
     * @return result list
     */
    @Override
    public List<Recipe> getAll() {
        List<Recipe> list = new ArrayList<>();
        final LazyList<RecipeDBO> all = RecipeDBO.findAll();
        for( RecipeDBO recipeDBO : all ) {
            list.add( mapToPOJO( recipeDBO ) );
        }
        return list;
    }

    /**
     * Returns an Optional record with given id.
     *
     * @param id
     * @return Optional<T> the optional record.
     */
    @Override
    public Optional<Recipe> findById(long id) {
        final Optional<RecipeDBO> recipeDBO = Optional.ofNullable( RecipeDBO.findById(id) );
        if( null == recipeDBO ) {
            return Optional.empty();
        }
        final Recipe recipePOJO = mapToPOJO( recipeDBO.get() );
        return Optional.ofNullable(recipePOJO);
    }

    /**
     * Find first occurrence of record that fulfills the query.
     *
     * @param subQuery this is a set of conditions that normally follow the "where" clause. Example:
     *                 <code>"department = ? and dob &gt ?"</code>.
     * @param params   list of parameters corresponding to the place holders in the subquery.
     * @return instance of <code>List</code> containing results.
     */
    @Override
    public Optional<Recipe> findFirst(String subQuery, Object... params) {
        final Optional<RecipeDBO> recipeDBO = Optional.ofNullable(RecipeDBO.findFirst(subQuery, params));
        if ( !recipeDBO.isPresent() ) {
            Optional.empty();
        }
        return Optional.of( mapToPOJO( recipeDBO.get() ) );
    }

    /**
     * ID of recipe has to be null for insert.
     *
     * @param recipe
     */
    @Override
    public void insert(Recipe recipe) {
        if( null != recipe.getID() ) {
            throw new IllegalStateException("A new Recipe has no id!");
        }
        final RecipeDBO recipeDBO = mapToDBO(recipe);
        recipeDBO.saveIt();
    }

    @Override
    public void update(Recipe recipe) {
        mapToDBO(recipe).saveIt();
    }

    @Override
    public void delete(Recipe recipe) {
        final Optional<RecipeDBO> recipeOptional = Optional.ofNullable(RecipeDBO.findById(recipe.getID()));
        recipeOptional.orElseThrow( () -> new IllegalStateException ("Recipe with id not found to delete.")).delete();
    }

    private RecipeDBO mapToDBO(Recipe recipe) {
        final RecipeDBO recipeDBO = new RecipeDBO();

        recipeDBO.setId( recipe.getID() );
        recipeDBO.setTitle(recipe.getTitle());
        return recipeDBO;
    }

    private Recipe mapToPOJO(RecipeDBO recipe) {
        final Recipe recipePOJO = new Recipe( recipe.getID() );
        recipePOJO.setTitle(recipe.getTitle());
        return recipePOJO;
    }
}
