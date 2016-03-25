package sample.model;

import org.javalite.activejdbc.Base;

import java.util.List;

/**
 * Created by czoeller on 25.03.16.
 */
public class TestORM {

    public static void main( String[] args ) {
        Base.open( "org.sqlite.JDBC", "jdbc:sqlite://database.sql", null, null );
        List<Recipe> recipeList = Recipe.where("name = 'John'");

    }
}
