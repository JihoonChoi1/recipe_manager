package model;

import org.json.JSONObject;
import persistence.Writable;
import java.util.List;

// Represents a recipe to cook a specific food.
public class Recipe implements Writable {
    private static final int MAX_RATING = 5;
    private static final int MIN_RATING = 0;

    private String foodName;
    private List<String> ingredients;
    private List<String> steps;
    private int cookingTime;
    private int numOfRatings;
    private double sumOfRatings;
    private double averageRating;


    // REQUIRES: ingredients.size() > 0, steps.size() > 0, and cookingTime > 0
    // EFFECTS: constructs a recipe object with food name, ingredients, steps, and cooking time(in minutes) given
    public Recipe(String foodName, List<String> ingredients, List<String> steps, int cookingTime) {
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cookingTime = cookingTime;
        this.numOfRatings = 0;
        this.averageRating = 0;
    }

    public Recipe(String foodName, List<String> ingredients, List<String> steps, int cookingTime,
                  int numOfRatings, double sumOfRatings, double averageRating) {
        this(foodName, ingredients, steps, cookingTime);
        this.numOfRatings = numOfRatings;
        this.sumOfRatings = sumOfRatings;
        this.averageRating = averageRating;
    }

    // REQUIRES: foodName is not null and empty string
    // MODIFIES: this
    // EFFECTS: sets the food name to foodName
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    // REQUIRES: ingredients list is not null or empty
    // MODIFIES: this
    // EFFECTS: sets the list of ingredients to the given list
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    // REQUIRES: steps list is not null or empty
    // MODIFIES: this
    // EFFECTS: sets the list steps to the given list
    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<String> getSteps() {
        return steps;
    }

    // REQUIRES: cookingTime > 0
    // MODIFIES: this
    // EFFECTS: sets the cooking time to the given parameter cookingTime
    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    // REQUIRES: rating >= MIN_RATING, rating <= MAX_RATING
    // MODIFIES: this
    // EFFECTS: updates the average rating with the given rating
    public void giveRating(double rating) {
        this.numOfRatings++;
        sumOfRatings += rating;
        this.averageRating = this.sumOfRatings / this.numOfRatings;
    }

    public int getNumOfRatings() {
        return this.numOfRatings;
    }

    public double getSumOfRatings() {
        return this.sumOfRatings;
    }

    public double getAverageRating() {
        return this.averageRating;
    }

    // EFFECTS: returns Recipe as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("foodName", foodName);
        json.put("ingredients", ingredients);
        json.put("steps", steps);
        json.put("cookingTime", cookingTime);
        json.put("numOfRatings", numOfRatings);
        json.put("sumOfRatings", sumOfRatings);
        json.put("averageRating", averageRating);
        return json;
    }
}
