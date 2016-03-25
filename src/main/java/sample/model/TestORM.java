package sample.model;

import org.javalite.activejdbc.Base;

import java.util.List;

/**
 * Created by czoeller on 25.03.16.
 */
public class TestORM {

    public static void main( String[] args ) {

        TestORM t = new TestORM();
        final String path = t.getClass().getClassLoader().getResource("database/database.db").getPath();
        Base.open( "org.sqlite.JDBC", "jdbc:sqlite://" + path, null, null );

        new Recipe().set("title", "First Recipe").saveIt();

        List<Recipe> recipeList = Recipe.findAll();
        recipeList.forEach(System.out::println);

    }
}
