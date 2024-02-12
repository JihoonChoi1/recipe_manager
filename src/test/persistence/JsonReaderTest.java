package persistence;

import model.MyFavorites;
import model.Recipe;
import model.RecipeCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

// "Code influenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo"
// unit tests for CollectionJsonReader and FavoritesJsonReader class
public class JsonReaderTest extends JsonTest{
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
    void testReaderNonExistentFile() {
        CollectionJsonReader reader = new CollectionJsonReader("./data/noSuchFile.json");
        try {
            RecipeCollection rc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCollection() {
        CollectionJsonReader reader = new CollectionJsonReader("./data/testReaderEmptyCollection.json");
        try {
            RecipeCollection rc = reader.read();
            List<Recipe> list1 = rc.getRecipes();
            assertEquals(0, list1.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCollection() {
        CollectionJsonReader reader = new CollectionJsonReader("./data/testReaderGeneralCollection.json");
        try {
            RecipeCollection rc = reader.read();
            List<Recipe> list1 = rc.getRecipes();
            assertEquals(2, list1.size());
            checkRecipe("food1", ingredients1, steps1, 15,0,
                    0,0, list1.get(0));
            checkRecipe("food2", ingredients2, steps2, 20, 0,
                    0, 0, list1.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyFavorites() {
        FavoritesJsonReader reader = new FavoritesJsonReader("./data/testReaderEmptyFavorites.json");
        try {
            MyFavorites myFavorites = reader.read();
            List<Recipe> list1 = myFavorites.getRecipes();
            List<Recipe> list2 = myFavorites.getBookMarkedRecipes();
            assertEquals(0, list1.size());
            assertEquals(0, list2.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFavorites() {
        FavoritesJsonReader reader = new FavoritesJsonReader("./data/testReaderGeneralFavorites.json");
        try {
            MyFavorites myFavorites = reader.read();
            List<Recipe> list1 = myFavorites.getRecipes();
            assertEquals(4, list1.size());
            checkRecipe("food3", ingredients3, steps3, 5,0,
                    0,0, list1.get(0));
            checkRecipe("food4", ingredients4, steps4, 10, 0,
                    0, 0, list1.get(1));
            checkRecipe("food1", ingredients1, steps1, 15,0,
                    0,0, list1.get(2));
            checkRecipe("food2", ingredients2, steps2, 20, 0,
                    0, 0, list1.get(3));
            List<Recipe> list2 = myFavorites.getBookMarkedRecipes();
            assertEquals(2, list2.size());
            checkRecipe("food3", ingredients3, steps3, 5,0,
                    0,0, list2.get(0));
            checkRecipe("food4", ingredients4, steps4, 10, 0,
                    0, 0, list2.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
