package sample.model.comparator;

import sample.model.Recipe;

import java.util.Comparator;

/**
 * Created by kai on 07.06.16.
 */
public class CourseComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.getCourse().getName().compareToIgnoreCase(recipe2.getCourse().getName());
    }
}
