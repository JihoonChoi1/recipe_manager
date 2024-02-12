package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;
import java.util.List;

// RecipeCollection is a class that has a list of recipes and has functionalities to interact with it.
public class RecipeCollection implements Writable {
    private List<Recipe> recipes;

    // EFFECTS: Constructs an object with an empty list for the recipes
    public RecipeCollection() {
        recipes = new ArrayList<>();
    }

    // REQUIRES: recipe is not null
    // MODIFIES: this
    // EFFECTS: adds the given recipe to the list if it is not already contained.
    public void addRecipe(Recipe recipe) {
        if (!recipes.contains(recipe)) {
            recipes.add(recipe);
        }
        if (this instanceof MyFavorites) {
            EventLog.getInstance().logEvent(new Event("New recipe " + recipe.getFoodName()
                    + " added to favorites list"));
        } else {
            EventLog.getInstance().logEvent(new Event("New recipe " + recipe.getFoodName()
                    + " added to Collection list"));
        }
    }

    // REQUIRES: recipe is not null.
    // MODIFIES: this
    // EFFECTS: removes the given recipe if it is contained in the list
    public void removeRecipe(Recipe recipe) {
        if (recipes.contains(recipe)) {
            recipes.remove(recipe);
        }
        EventLog.getInstance().logEvent(new Event("Recipe " + recipe.getFoodName()
                + " removed from " + this.getClass()));
    }

    // REQUIRES: foodName is neither an empty string nor null
    // EFFECTS: returns all the food's recipe that includes the given food name
    public List<Recipe> searchRecipes(String foodName) {
        List<Recipe> toReturn = new ArrayList<>();
        for (Recipe r : recipes) {
            if (r.getFoodName().contains(foodName)) {
                toReturn.add(r);
            }
        }
        return toReturn;
    }

    // REQUIRES: target is not null
    // EFFECTS: returns true of the list contains the given recipe. Otherwise, return false
    public boolean hasRecipe(Recipe target) {
        for (Recipe r : recipes) {
            if (r == target) {
                return true;
            }
        }
        return false;
    }

    public List<Recipe> getRecipes() {
        return new ArrayList<>(this.recipes);
    }

    // MODIFIES: this
    // EFFECTS: sorts the list by cooking time(in minutes) in ascending order
    public void sortByCookingTime() {
        recipes.sort(new RecipeComparator());
        EventLog.getInstance().logEvent(new Event("Recipe list at collection list sorted by cooking time"));
    }

    // EFFECTS: returns RecipeCollection as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("recipes", recipesToJson(this.recipes));
        return json;
    }

    // EFFECTS: returns the JSONArray made with parameter list
    protected JSONArray recipesToJson(List<Recipe> list) {
        JSONArray jsonArray = new JSONArray();
        for (Recipe r : list) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}
