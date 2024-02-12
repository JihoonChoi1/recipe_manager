package persistence;

import model.Recipe;
import model.RecipeCollection;
import model.MyFavorites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// "Code influenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
// unit tests for JsonWriter class
public class JsonWriterTest extends JsonTest {

    private List<String> ingredients1, ingredients2, ingredients3, ingredients4;
    private List<String> steps1, steps2, steps3, steps4;

    @BeforeEach
    void runBefore() {
        ingredients1 = new ArrayList<>();
        ingredients1.add("i1");
        ingredients1.add("i2");
        ingredients1.add("i3");
        ingredients2 = new ArrayList<>();
        ingredients2.add("i4");
        ingredients2.add("i5");
        ingredients2.add("i6");
        ingredients3 = new ArrayList<>();
        ingredients3.add("i7");
        ingredients3.add("i8");
        ingredients3.add("i9");
        ingredients4 = new ArrayList<>();
        ingredients4.add("i10");
        ingredients4.add("i11");
        ingredients4.add("i12");

        steps1 = new ArrayList<>();
        steps1.add("s1");
        steps1.add("s2");
        steps1.add("s3");
        steps2 = new ArrayList<>();
        steps2.add("s4");
        steps2.add("s5");
        steps2.add("s6");
        steps3 = new ArrayList<>();
        steps3.add("s7");
        steps3.add("s8");
        steps3.add("s9");
        steps4 = new ArrayList<>();
        steps4.add("s10");
        steps4.add("s11");
        steps4.add("s12");
    }


    @Test
    void testWriterInvalidFile() {
        try {
            RecipeCollection wr = new RecipeCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRecipeCollection() {
        try {
            RecipeCollection rc = new RecipeCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecipeCollection.json");
            writer.open();
            writer.write(rc);
            writer.close();

            CollectionJsonReader reader = new CollectionJsonReader(
                    "./data/testWriterEmptyRecipeCollection.json");
            rc = reader.read();
            List<Recipe> list1 = rc.getRecipes();
            assertEquals(0, list1.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRecipeCollection() {
        try {
            RecipeCollection rc = new RecipeCollection();
            rc.addRecipe(new Recipe("food1", ingredients1, steps1, 5));
            rc.addRecipe(new Recipe("food2", ingredients2, steps2, 10));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecipeCollection.json");
            writer.open();
            writer.write(rc);
            writer.close();

            CollectionJsonReader reader = new CollectionJsonReader(
                    "./data/testWriterGeneralRecipeCollection.json");
            rc = reader.read();
            List<Recipe> list1 = rc.getRecipes();
            assertEquals(2, list1.size());
            checkRecipe("food1", ingredients1, steps1, 5,0,
                    0,0, list1.get(0));
            checkRecipe("food2", ingredients2, steps2, 10, 0,
                    0, 0, list1.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyFavorites() {
        try {
            MyFavorites myFavorites = new MyFavorites();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFavorites.json");
            writer.open();
            writer.write(myFavorites);
            writer.close();

            FavoritesJsonReader reader = new FavoritesJsonReader(
                    "./data/testWriterEmptyFavorites.json");
            myFavorites = reader.read();
            List<Recipe> list1 = myFavorites.getRecipes();
            List<Recipe> list2 = myFavorites.getBookMarkedRecipes();
            assertEquals(0, list1.size());
            assertEquals(0, list2.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFavorites() {
        try {
            MyFavorites myFavorites = new MyFavorites();
            myFavorites.addRecipe(new Recipe("food1", ingredients1, steps1, 5));
            myFavorites.addRecipe(new Recipe("food2", ingredients2, steps2, 10));
            myFavorites.addBookmark(new Recipe("food3", ingredients3, steps3, 15));
            myFavorites.addBookmark(new Recipe("food4", ingredients4, steps4, 20));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFavorites.json");
            writer.open();
            writer.write(myFavorites);
            writer.close();

            FavoritesJsonReader reader = new FavoritesJsonReader("./data/testWriterGeneralFavorites.json");
            myFavorites = reader.read();

            List<Recipe> list1 = myFavorites.getRecipes();
            assertEquals(4, list1.size());
            checkRecipe("food3", ingredients3, steps3, 15,0,
                    0,0, list1.get(0));
            checkRecipe("food4", ingredients4, steps4, 20, 0,
                    0, 0, list1.get(1));
            checkRecipe("food1", ingredients1, steps1, 5,0,
                    0,0, list1.get(2));
            checkRecipe("food2", ingredients2, steps2, 10, 0,
                    0, 0, list1.get(3));

            List<Recipe> list2 = myFavorites.getBookMarkedRecipes();
            assertEquals(2, list2.size());
            checkRecipe("food3", ingredients3, steps3, 15,0,
                    0,0, list2.get(0));
            checkRecipe("food4", ingredients4, steps4, 20, 0,
                    0, 0, list2.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
