package sample.parser;

import sample.exceptions.CouldNotParseException;
import sample.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * implemented by on 31.05.16 by markus
 */
public class DummyParser extends AConcreteParser {
    @Override
    public Recipe parse(List<String> text) throws CouldNotParseException {
        Recipe recipe = new Recipe();
        recipe.setTitle("Testrezept");
        recipe.setText("Testzubereitungstext");
        recipe.add("Milch",2,"Liter");
        recipe.setPortions(1);
        recipe.setCalories(5);
        recipe.setDuration(5);
        ISource source = new Source();
        source.setName("Testquelle");
        recipe.setSource(source);
        ISeason season = new Season();
        season.setName("Testsaison");
        recipe.setSeason(season);
        ICategory category = new Category();
        category.setName("Testkategorie");
        recipe.setCategory(category);
        ICourse course = new Course();
        course.setName("TestGerichtart");
        recipe.setCourse(course);
        IDaytime daytime = new Daytime();
        daytime.setName("Testtageszeit");
        recipe.setDaytime(daytime);
        INurture nurture = new Nurture();
        nurture.setName("TestErnährungsart");
        recipe.setNurture(nurture);
        IRegion region = new Region();
        region.setName("Testregion");
        recipe.setRegion(region);

        return recipe;
    }

    @Override
    public boolean accepts(List<String> text) {
        return true;
    }
}
