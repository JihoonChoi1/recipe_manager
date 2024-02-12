package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// MyFavorites is a class inherited from RecipeCollection class that has extra field of list
// for the bookmarked recipe.
// This class is to save the recipe from RecipeCollection to the favorite list
public class MyFavorites extends RecipeCollection {
    private List<Recipe> bookMarkedRecipes;

    // Constructs an object for storing favorite recipe list
    public MyFavorites() {
        super();
        bookMarkedRecipes = new ArrayList<>();
    }

    // REQUIRES: r is not null
    // MODIFIES: this
    // EFFECTS: adds r to bookmarked recipe list. If r is in the super list,
    //            deletes it and add it to bookmarked recipe list.
    public void addBookmark(Recipe r) {
        if (this.hasRecipe(r)) {
            this.removeRecipe(r);
        }
        EventLog.getInstance().logEvent(new Event("Bookmark added to recipe: " + r.getFoodName()));
        bookMarkedRecipes.add(r);
    }

    // REQUIRES: recipe is not null
    // MODIFIES: this
    // EFFECTS: removes the given recipe object from the bookmarked list or the super list
    @Override
    public void removeRecipe(Recipe recipe) {
        if (bookMarkedRecipes.contains(recipe)) {
            bookMarkedRecipes.remove(recipe);
        } else {
            super.removeRecipe(recipe);
        }
        EventLog.getInstance().logEvent(new Event("Recipe : " + recipe.getFoodName() + "Removed"));
    }

    // REQUIRES: foodName is neither an empty string nor null
    // EFFECTS: returns all the food's recipe that includes the given food name
    @Override
    public List<Recipe> searchRecipes(String foodName) {
        List<Recipe> toReturn = new ArrayList<>();
        for (Recipe r : bookMarkedRecipes) {
            if (r.getFoodName().contains(foodName)) {
                toReturn.add(r);
            }
        }
        toReturn.addAll(super.searchRecipes(foodName));
        return toReturn;
    }

    // REQUIRES: target is not null
    // EFFECTS: returns true if the given recipe is contained in the bookmarked list or super list.
    @Override
    public boolean hasRecipe(Recipe target) {
        for (Recipe r : bookMarkedRecipes) {
            if (r == target) {
                return true;
            }
        }
        return super.hasRecipe(target);
    }

    // EFFECTS: Returns a list with the bookmarked recipe at the front followed by super class list.
    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> toReturn = new ArrayList<>(bookMarkedRecipes);
        toReturn.addAll(super.getRecipes());
        return toReturn;
    }

    // MODIFIES: this
    // EFFECTS: sorts the bookmarked list and the super list by cooking time(in minutes) in ascending order
    @Override
    public void sortByCookingTime() {
        bookMarkedRecipes.sort(new RecipeComparator());
        super.sortByCookingTime();
        EventLog.getInstance().logEvent(new Event("Recipe list at favorites list sorted by cooking time"));
    }

    public List<Recipe> getBookMarkedRecipes() {
        return this.bookMarkedRecipes;
    }

    // EFFECTS: returns MyFavorites as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("recipes", recipesToJson(super.getRecipes()));
        json.put("bookMarkedRecipes", recipesToJson(this.bookMarkedRecipes));
        return json;
    }
}
