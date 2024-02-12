package persistence;

import model.Recipe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// "Code influenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
// JsonTest class is to check if the Recipe is valid.
public class JsonTest {

    // EFFECTS: checks if Recipe r matches the parameter given.
    protected void checkRecipe(String foodName, List<String> ingredients, List<String> steps,int cookingTime,
                                  int numOfRatings, double sumOfRatings, double averageRating, Recipe r) {
        assertTrue(foodName.equals(r.getFoodName()));
        List<String> i = r.getIngredients();
        assertEquals(ingredients.size(), i.size());
        for(int j = 0; j < ingredients.size(); j++) {
            assertEquals(ingredients.get(j), i.get(j));
        }
        List<String> s = r.getSteps();
        assertEquals(steps.size(), s.size());
        for(int k = 0; k < steps.size(); k++) {
            assertEquals(steps.get(k), s.get(k));
        }
        assertEquals(cookingTime, r.getCookingTime());
        assertEquals(numOfRatings, r.getNumOfRatings());
        assertEquals(sumOfRatings, r.getSumOfRatings());
        assertEquals(averageRating, r.getAverageRating());
    }
}
