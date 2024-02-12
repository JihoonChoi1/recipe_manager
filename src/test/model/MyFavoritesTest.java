package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the MyFavorites class
public class MyFavoritesTest {
    Recipe r1;
    Recipe r2;
    Recipe r3;
    Recipe r4;
    MyFavorites favCollection1;
    MyFavorites favCollection2;

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

        favCollection1 = new MyFavorites();
        favCollection2 = new MyFavorites();
    }

    @Test
    void testConstructor(){
        List<Recipe> list = favCollection1.getRecipes();
        assertEquals(0, list.size());
    }

    @Test
    void testAddBookmark(){
        favCollection1.addBookmark(r1);
        List<Recipe> list1 = favCollection1.getRecipes();
        assertEquals(1, list1.size());
        assertEquals(r1, list1.get(0));
        List<Recipe> list2 = favCollection1.getBookMarkedRecipes();
        assertEquals(1, list2.size());
        assertEquals(r1, list2.get(0));
        favCollection1.addBookmark(r1);
        List<Recipe> list3 = favCollection1.getRecipes();
        assertEquals(1, list3.size());
        assertEquals(r1, list3.get(0));
        List<Recipe> list4 = favCollection1.getBookMarkedRecipes();
        assertEquals(1, list4.size());
        assertEquals(r1, list4.get(0));

        favCollection1.addRecipe(r2);
        favCollection1.addRecipe(r3);
        favCollection1.addRecipe(r4);

        favCollection1.addBookmark(r2);
        List<Recipe> list5 = favCollection1.getRecipes();
        assertEquals(4, list5.size());
        assertEquals(r1, list5.get(0));
        assertEquals(r2, list5.get(1));
        assertEquals(r3, list5.get(2));
        assertEquals(r4, list5.get(3));

        List<Recipe> list6 = favCollection1.getBookMarkedRecipes();
        assertEquals(2, list6.size());
        assertEquals(r1, list6.get(0));
        assertEquals(r2, list6.get(1));
    }

    @Test
    void testRemoveRecipe(){
        favCollection1.removeRecipe(r1);
        List<Recipe> list = favCollection1.getRecipes();
        assertEquals(0, list.size());

        favCollection1.addRecipe(r1);
        favCollection1.addRecipe(r2);
        favCollection1.addRecipe(r3);
        favCollection1.addRecipe(r4);

        List<Recipe> list1 = favCollection1.getRecipes();
        assertEquals(4, list1.size());
        assertEquals(r1, list1.get(0));
        assertEquals(r2, list1.get(1));
        assertEquals(r3, list1.get(2));
        assertEquals(r4, list1.get(3));

        favCollection1.addBookmark(r1);
        favCollection1.removeRecipe(r1);

        List<Recipe> list2 = favCollection1.getRecipes();
        assertEquals(3, list2.size());
        assertEquals(r2, list2.get(0));

        List<Recipe> list3 = favCollection1.getBookMarkedRecipes();
        assertEquals(0, list3.size());

        favCollection1.removeRecipe(r2);
        List<Recipe> list4 = favCollection1.getRecipes();
        assertEquals(2, list4.size());
        assertEquals(r3, list4.get(0));
        assertEquals(r4, list4.get(1));
    }

    @Test
    void testSearchRecipes(){
        List<Recipe> list = favCollection1.searchRecipes("food1");
        assertEquals(0, list.size());

        favCollection1.addRecipe(r1);
        List<Recipe> list1 = favCollection1.searchRecipes("food1");
        assertEquals(1, list1.size());
        assertEquals(r1, list1.get(0));

        favCollection1.addRecipe(r2);
        favCollection1.addRecipe(r3);
        favCollection1.addRecipe(r4);

        favCollection1.addBookmark(r1);
        List<Recipe> list5 = favCollection1.searchRecipes("food1");
        assertEquals(1, list5.size());
        assertEquals(r1, list5.get(0));

        List<Recipe> list2 = favCollection1.searchRecipes("food3");
        assertEquals(2, list2.size());
        assertEquals(r2, list2.get(0));
        assertEquals(r3, list2.get(1));

        List<Recipe> list3 = favCollection1.searchRecipes("food");
        assertEquals(4, list3.size());
        assertEquals(r1, list3.get(0));
        assertEquals(r4, list3.get(3));

        List<Recipe> list4 = favCollection1.searchRecipes("Ramen");
        assertEquals(0, list4.size());

        favCollection1.addBookmark(r2);

        List<Recipe> list6 = favCollection1.searchRecipes("food");
        assertEquals(4, list6.size());
        assertEquals(r1, list6.get(0));
        assertEquals(r4, list6.get(3));

        favCollection1.addBookmark(r3);
        favCollection1.addBookmark(r4);

        List<Recipe> list7 = favCollection1.searchRecipes("food");
        assertEquals(4, list7.size());
        assertEquals(r1, list7.get(0));
        assertEquals(r4, list7.get(3));
    }

    @Test
    void testHasRecipe(){
        assertFalse(favCollection1.hasRecipe(r1));
        favCollection1.addRecipe(r1);
        assertTrue(favCollection1.hasRecipe(r1));
        favCollection1.addBookmark(r1);
        assertTrue(favCollection1.hasRecipe(r1));
        favCollection1.addRecipe(r2);
        favCollection1.addRecipe(r3);

        assertTrue(favCollection1.hasRecipe(r2));
        favCollection1.removeRecipe(r2);
        assertFalse(favCollection1.hasRecipe(r2));

        favCollection1.addRecipe(r2);
        favCollection1.addBookmark(r2);
        assertTrue(favCollection1.hasRecipe(r2));

        assertFalse(favCollection1.hasRecipe(r4));
        favCollection1.addRecipe(r4);
        assertTrue(favCollection1.hasRecipe(r4));
    }

    @Test
    void testSortByCookingTime() {
        favCollection1.sortByCookingTime();
        assertEquals(0, favCollection1.getRecipes().size());

        favCollection1.addRecipe(r1);
        favCollection1.sortByCookingTime();
        List<Recipe> list = favCollection1.getRecipes();
        assertEquals(1, list.size());
        assertEquals(r1, list.get(0));

        favCollection1.addRecipe(r2);
        favCollection1.addRecipe(r3);

        favCollection1.sortByCookingTime();
        List<Recipe> list1 = favCollection1.getRecipes();
        assertEquals(3, list1.size());
        assertEquals(r2, list1.get(0));
        assertEquals(r3, list1.get(1));
        assertEquals(r1, list1.get(2));

        favCollection1.addRecipe(r4);

        favCollection1.sortByCookingTime();
        List<Recipe> list2 = favCollection1.getRecipes();
        assertEquals(4, list2.size());
        assertEquals(r2, list2.get(0));
        assertEquals(r3, list2.get(1));
        assertEquals(r1, list2.get(2));
        assertEquals(r4, list2.get(3));

        favCollection2.addBookmark(r1);
        favCollection2.sortByCookingTime();
        List<Recipe> list3 = favCollection2.getBookMarkedRecipes();
        assertEquals(1, list3.size());
        assertEquals(r1, list3.get(0));
        favCollection2.addRecipe(r2);
        favCollection2.addRecipe(r3);
        favCollection2.addRecipe(r4);

        favCollection2.sortByCookingTime();
        List<Recipe> list4 = favCollection2.getBookMarkedRecipes();
        assertEquals(1, list4.size());
        assertEquals(r1, list4.get(0));

        List<Recipe> list5 = favCollection2.getRecipes();
        assertEquals(4, list5.size());
        assertEquals(r1, list5.get(0));
        assertEquals(r2, list5.get(1));
        assertEquals(r3, list5.get(2));
        assertEquals(r4, list5.get(3));

        favCollection2.addBookmark(r2);
        favCollection2.addBookmark(r4);

        favCollection2.sortByCookingTime();
        List<Recipe> list6 = favCollection2.getBookMarkedRecipes();
        assertEquals(3, list6.size());
        assertEquals(r2, list6.get(0));
        assertEquals(r1, list6.get(1));
        assertEquals(r4, list6.get(2));

        List<Recipe> list7 = favCollection2.getRecipes();
        assertEquals(4, list7.size());
        assertEquals(r2, list7.get(0));
        assertEquals(r1, list7.get(1));
        assertEquals(r4, list7.get(2));
        assertEquals(r3, list7.get(3));

        favCollection2.addBookmark(r3);
        favCollection2.sortByCookingTime();
        List<Recipe> list8 = favCollection2.getRecipes();
        assertEquals(4, list8.size());
        assertEquals(r2, list8.get(0));
        assertEquals(r3, list8.get(1));
        assertEquals(r1, list8.get(2));
        assertEquals(r4, list8.get(3));

        List<Recipe> list9 = favCollection2.getBookMarkedRecipes();
        assertEquals(4, list9.size());
        assertEquals(r2, list9.get(0));
        assertEquals(r3, list9.get(1));
        assertEquals(r1, list9.get(2));
        assertEquals(r4, list9.get(3));
    }
}
