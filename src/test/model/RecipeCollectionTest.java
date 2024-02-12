package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the RecipeCollection class
public class RecipeCollectionTest {
    Recipe r1;
    Recipe r2;
    Recipe r3;
    Recipe r4;
    RecipeCollection rc1;

    @BeforeEach
    void runBefore() {
        List<String> i1 = new ArrayList<>();
        i1.add("Ingredient1");
        i1.add("Ingredient2");
        i1.add("Ingredient3");

        List<String> i2 = new ArrayList<>();
        i2.add("Ingredient1");

        List<String> step1 = new ArrayList<>();
        step1.add("Step1");
        step1.add("Step2");
        step1.add("Step3");
        step1.add("Step4");

        List<String> step2 = new ArrayList<>();
        step2.add("Step1");

        r1 = new Recipe("food12", i1, step1, 15);
        r2 = new Recipe("food32", i1, step1, 1);
        r3 = new Recipe("food3", i2, step1, 5);
        r4 = new Recipe("food4", i2, step2, 30);

        rc1 = new RecipeCollection();
    }

    @Test
    void testConstructor(){
        List<Recipe> list = rc1.getRecipes();
        assertEquals(0, list.size());
    }

    @Test
    void testAddRecipe(){
        rc1.addRecipe(r1);
        List<Recipe> list = rc1.getRecipes();
        assertEquals(1, list.size());
        assertEquals(r1, list.get(0));

        rc1.addRecipe(r1);
        List<Recipe> list1 = rc1.getRecipes();
        assertEquals(1, list1.size());
        assertEquals(r1, list1.get(0));

        rc1.addRecipe(r2);
        List<Recipe> list2 = rc1.getRecipes();
        assertEquals(2, list2.size());
        assertEquals(r1, list2.get(0));
        assertEquals(r2, list2.get(1));
    }

    @Test
    void testRemoveRecipe(){
        rc1.removeRecipe(r1);
        List<Recipe> list = rc1.getRecipes();
        assertEquals(0, list.size());

        rc1.addRecipe(r1);
        rc1.addRecipe(r2);
        rc1.addRecipe(r3);
        rc1.addRecipe(r4);

        List<Recipe> list1 = rc1.getRecipes();
        assertEquals(4, list1.size());
        assertEquals(r1, list1.get(0));
        assertEquals(r2, list1.get(1));
        assertEquals(r3, list1.get(2));
        assertEquals(r4, list1.get(3));

        rc1.removeRecipe(r1);
        List<Recipe> list2 = rc1.getRecipes();
        assertEquals(3, list2.size());
        assertEquals(r2, list2.get(0));

        rc1.removeRecipe(r1);
        List<Recipe> list3 = rc1.getRecipes();
        assertEquals(3, list3.size());
        assertEquals(r2, list3.get(0));
        assertEquals(r4, list3.get(2));
    }

    @Test
    void testSearchRecipes(){
        List<Recipe> list = rc1.searchRecipes("food1");
        assertEquals(0, list.size());

        rc1.addRecipe(r1);
        List<Recipe> list1 = rc1.searchRecipes("food1");
        assertEquals(1, list1.size());
        assertEquals(r1, list1.get(0));

        rc1.addRecipe(r2);
        rc1.addRecipe(r3);
        rc1.addRecipe(r4);

        List<Recipe> list2 = rc1.searchRecipes("food3");
        assertEquals(2, list2.size());
        assertEquals(r2, list2.get(0));
        assertEquals(r3, list2.get(1));

        List<Recipe> list3 = rc1.searchRecipes("food");
        assertEquals(4, list3.size());
        assertEquals(r1, list3.get(0));
        assertEquals(r4, list3.get(3));

        List<Recipe> list4 = rc1.searchRecipes("Ramen");
        assertEquals(0, list4.size());
    }

    @Test
    void testHasRecipe(){
        assertFalse(rc1.hasRecipe(r1));
        rc1.addRecipe(r1);
        assertTrue(rc1.hasRecipe(r1));
        rc1.addRecipe(r2);
        rc1.addRecipe(r3);

        assertTrue(rc1.hasRecipe(r2));
        rc1.removeRecipe(r2);
        assertFalse(rc1.hasRecipe(r2));

        assertFalse(rc1.hasRecipe(r4));
        rc1.addRecipe(r4);
        assertTrue(rc1.hasRecipe(r4));
    }

    @Test
    void testSortByCookingTime(){
        rc1.sortByCookingTime();
        assertEquals(0, rc1.getRecipes().size());

        rc1.addRecipe(r1);
        rc1.sortByCookingTime();
        List<Recipe> list = rc1.getRecipes();
        assertEquals(1, list.size());
        assertEquals(r1, list.get(0));

        rc1.addRecipe(r2);
        rc1.addRecipe(r3);

        rc1.sortByCookingTime();
        List<Recipe> list1 = rc1.getRecipes();
        assertEquals(3, list1.size());
        assertEquals(r2, list1.get(0));
        assertEquals(r3, list1.get(1));
        assertEquals(r1, list1.get(2));

        rc1.addRecipe(r4);

        rc1.sortByCookingTime();
        List<Recipe> list2 = rc1.getRecipes();
        assertEquals(4, list2.size());
        assertEquals(r2, list2.get(0));
        assertEquals(r3, list2.get(1));
        assertEquals(r1, list2.get(2));
        assertEquals(r4, list2.get(3));
    }
}
