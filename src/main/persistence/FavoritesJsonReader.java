package persistence;

import model.MyFavorites;
import model.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

// "Code influenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
// Represents a reader that reads MyFavorites from JSON data stored in file
public class FavoritesJsonReader extends CollectionJsonReader {

    // EFFECTS: constructs reader to read from source file
    public FavoritesJsonReader(String source) {
        super(source);
    }

    // EFFECTS: reads MyFavorites from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MyFavorites read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMyFavorites(jsonObject);
    }


    // EFFECTS: parses MyFavorites from JSON object and returns it
    private MyFavorites parseMyFavorites(JSONObject jsonObject) {
        MyFavorites myFavorites = new MyFavorites();
        addRecipes(myFavorites, jsonObject);
        return myFavorites;
    }

    // MODIFIES: myFavorites
    // EFFECTS: parses recipes and bookmarked recipes from JSON object and adds them to MyFavorites
    private void addRecipes(MyFavorites myFavorites, JSONObject jsonObject) {
        JSONArray jsonBookmarked = jsonObject.getJSONArray("bookMarkedRecipes");
        for (Object json : jsonBookmarked) {
            JSONObject nextRecipe = (JSONObject) json;
            addBookMarkedRecipe(myFavorites, nextRecipe);
        }
        super.addRecipes(myFavorites, jsonObject);
    }

    // MODIFIES: myFavorites
    // EFFECTS: parses recipes from JSON object and adds them to MyFavorites bookMarkedRecipe
    private void addBookMarkedRecipe(MyFavorites myFavorites, JSONObject jsonObject) {
        String foodName = jsonObject.getString("foodName");
        List<String> ingredients = jsonArrayToStringList(jsonObject.getJSONArray("ingredients"));
        List<String> steps = jsonArrayToStringList(jsonObject.getJSONArray("steps"));
        int cookingTime = jsonObject.getInt("cookingTime");
        int numOfRatings = jsonObject.getInt("numOfRatings");
        double sumOfRatings = jsonObject.getDouble("sumOfRatings");
        double averageRating = jsonObject.getDouble("averageRating");
        Recipe recipe = new Recipe(foodName, ingredients, steps, cookingTime,
                numOfRatings, sumOfRatings, averageRating);
        myFavorites.addBookmark(recipe);
    }
}
