package model;

import java.util.Comparator;

// Comparator class of recipe to compare two recipes by the cooking time
public class RecipeComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe r1, Recipe r2) {
        return Integer.compare(r1.getCookingTime(), r2.getCookingTime());
    }
}
