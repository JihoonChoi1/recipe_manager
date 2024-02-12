package persistence;

import model.RecipeCollection;
import model.Recipe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.List;
import org.json.*;

// "Code influenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
// Represents a reader that reads RecipeCollection from JSON data stored in file
public class CollectionJsonReader {
    protected String source;

    // EFFECTS: constructs reader to read from source file
    public CollectionJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads RecipeCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RecipeCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipeCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    protected String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses RecipeCollection from JSON object and returns it
    private RecipeCollection parseRecipeCollection(JSONObject jsonObject) {
        RecipeCollection rc = new RecipeCollection();
        addRecipes(rc, jsonObject);
        return rc;
    }

    // MODIFIES: rc
    // EFFECTS: parses Recipes from JSON object and adds them to RecipeCollection
    protected void addRecipes(RecipeCollection rc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipe(rc, nextRecipe);
        }
    }

    // MODIFIES: rc
    // EFFECTS: parses Recipe from JSON object and adds it to RecipeCollection
    private void addRecipe(RecipeCollection rc, JSONObject jsonObject) {
        String foodName = jsonObject.getString("foodName");
        List<String> ingredients = jsonArrayToStringList(jsonObject.getJSONArray("ingredients"));
        List<String> steps = jsonArrayToStringList(jsonObject.getJSONArray("steps"));
        int cookingTime = jsonObject.getInt("cookingTime");
        int numOfRatings = jsonObject.getInt("numOfRatings");
        double sumOfRatings = jsonObject.getDouble("sumOfRatings");
        double averageRating = jsonObject.getDouble("averageRating");
        Recipe recipe = new Recipe(foodName, ingredients, steps, cookingTime,
                                    numOfRatings, sumOfRatings, averageRating);
        rc.addRecipe(recipe);
    }

    // EFFECTS: Changes the given JSONArray to list and returns that list.
    protected List<String> jsonArrayToStringList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}