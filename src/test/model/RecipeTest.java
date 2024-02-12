package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Recipe class
class RecipeTest {
    Recipe r1;
    Recipe r2;
    Recipe r3;
    Recipe r4;

    @BeforeEach
    void runBefore(){
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

        r1 = new Recipe("food1", i1, step1, 15);
        r2 = new Recipe("food2", i1, step1, 1);
        r3 = new Recipe("food3", i2, step1, 5);
        r4 = new Recipe("food4", i2, step2, 30);
    }

    @Test
    void testConstructor(){
        assertEquals("food1", r1.getFoodName());

        List<String> list1 = r2.getIngredients();
        assertEquals(3, list1.size());
        assertEquals("Ingredient1", list1.get(0));

        assertEquals(1, r2.getCookingTime());

        List<String> list2 = r4.getSteps();
        assertEquals(1, list2.size());
        assertEquals("Step1", list2.get(0));
    }

    @Test
    void testSetters(){
        List<String> i2 = new ArrayList<>();
        i2.add("Ingredient1");

        r1.setIngredients(i2);

        r1.setCookingTime(1);

        List<String> step2 = new ArrayList<>();
        step2.add("Step1");
        r1.setSteps(step2);

        r1.setFoodName("Ramen");

        assertEquals(i2, r1.getIngredients());
        assertEquals(step2, r1.getSteps());
        assertEquals(1, r1.getCookingTime());
        assertEquals("Ramen", r1.getFoodName());
    }

    @Test
    void testGiveRating(){
        r1.giveRating(0.1);
        assertEquals(0.1, r1.getAverageRating());

        r2.giveRating(4.9);
        assertEquals(4.9, r2.getAverageRating());

        r3.giveRating(1);
        assertEquals(1, r3.getAverageRating());
        r3.giveRating(5);
        assertEquals(3, r3.getAverageRating());
        r3.giveRating(3);
        assertEquals(3, r3.getAverageRating());
        r3.giveRating(0);
        assertEquals(2.25, r3.getAverageRating());
    }


}