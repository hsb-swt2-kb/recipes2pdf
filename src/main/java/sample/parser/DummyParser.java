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
        Source source = new Source();
        source.setName("Testquelle");
        recipe.setSource(source);
        Season season = new Season();
        season.setName("Testsaison");
        recipe.setSeason(season);
        Category category = new Category();
        category.setName("Testkategorie");
        recipe.setCategory(category);
        Course course = new Course();
        course.setName("TestGerichtart");
        recipe.setCourse(course);
        Daytime daytime = new Daytime();
        daytime.setName("Testtageszeit");
        recipe.setDaytime(daytime);
        Nurture nurture = new Nurture();
        nurture.setName("TestErnährungsart");
        recipe.setNurture(nurture);
        Region region = new Region();
        region.setName("Testregion");
        recipe.setRegion(region);

        return recipe;
    }

    @Override
    public boolean accepts(List<String> text) {
        return true;
    }
}
