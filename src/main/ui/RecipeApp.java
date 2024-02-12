package ui;

import model.MyFavorites;
import model.Recipe;
import model.RecipeCollection;
import model.RecipeComparator;
import persistence.CollectionJsonReader;
import persistence.FavoritesJsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// This class is a Recipe Application that runs a program, acts as a recipe manager.
public class RecipeApp {
    private static final String JSON_COLLECTION = "./data/RecipeCollection.json";
    private static final String JSON_FAVORITES = "./data/MyFavorites.json";
    private Scanner scanner;
    private RecipeCollection rc;
    private MyFavorites myFavorites;
    boolean inFavoritesView;

    private JsonWriter collectionJsonWriter;
    private JsonWriter favoritesJsonWriter;
    private CollectionJsonReader collectionJsonReader;
    private FavoritesJsonReader favoritesJsonReader;

    // EFFECTS: Runs the recipe application.
    public RecipeApp() {
        scanner = new Scanner(System.in);
        collectionJsonWriter = new JsonWriter(JSON_COLLECTION);
        favoritesJsonWriter = new JsonWriter(JSON_FAVORITES);
        collectionJsonReader = new CollectionJsonReader(JSON_COLLECTION);
        favoritesJsonReader = new FavoritesJsonReader(JSON_FAVORITES);
        runRecipeApp();
    }

    // MODIFIES: this
    // EFFECTS: Interacts with the user to enter the command until the user quits.
    private void runRecipeApp() {
        init();
        System.out.println("Welcome to the Recipe App!");
        boolean hasQuit = false;

        while (!hasQuit) {
            displayMenu();
            String command = scanner.nextLine();
            if (command.equals("7")) {
                hasQuit = true;
            } else {
                runCommand(command);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: Initializes all we need before starting interaction with the user.
    private void init() {
        List<String> i1 = new ArrayList<>();
        i1.add("Ingredient1");
        i1.add("Ingredient2");
        i1.add("Ingredient3");
        List<String> step1 = new ArrayList<>();
        step1.add("Step1");
        step1.add("Step2");
        step1.add("Step3");
        step1.add("Step4");
        Recipe r1 = new Recipe("food1", i1, step1, 15);
        Recipe r2 = new Recipe("food2", i1, step1, 10);
        Recipe r3 = new Recipe("food3", i1, step1, 20);
        rc = new RecipeCollection();
        rc.addRecipe(r1);
        rc.addRecipe(r2);
        rc.addRecipe(r3);
        inFavoritesView = false;
        myFavorites =  new MyFavorites();
    }

    // EFFECTS: displays the options that the user can choose at the main UI of the program.
    private void displayMenu() {
        System.out.println("**Hit the number from the options below and press enter**");
        System.out.println("1. Show all recipe");
        System.out.println("2. Go to my favorites list");
        System.out.println("3. Search for the specific food recipe");
        System.out.println("4. Add new recipe");
        System.out.println("5. Save the collection and my favorites recipe to file");
        System.out.println("6. Load the saved collection and my favorites recipe from file");
        System.out.println("7. quit");
    }

    // MODIFIES: this
    // EFFECTS: takes the command chosen by the user and performs in a specific way
    //          assigned by the command.
    private void runCommand(String command) {
        List<Recipe> recipes;
        if (command.equals("1")) {
            inFavoritesView = false;
            recipes = rc.getRecipes();
        } else if (command.equals("2")) {
            inFavoritesView = true;
            recipes = myFavorites.getRecipes();
        } else if (command.equals("3")) {
            inFavoritesView = false;
            recipes = searchForRecipe();
        } else {
            if (command.equals("4")) {
                createRecipe();
            } else if (command.equals("5")) {
                saveRecipeCollection();
            } else if (command.equals("6")) {
                loadRecipeCollection();
            } else if (!command.equals("7")) {
                invalidStatement();
            }
            return;
        }
        showRecipeMenu(recipes);
    }

    // EFFECTS: Prompts the user to enter the food name to search, and returns the list of recipe from
    //          recipe collection that is related to the food typed by the user.
    private List<Recipe> searchForRecipe() {
        System.out.println("\n" + "Type the name of the food you want to search for and press enter.");
        String toSearch = scanner.nextLine();
        return rc.searchRecipes(toSearch);
    }

    // MODIFIES: this
    // EFFECTS: Asks the user for the input if the user wants to view the sorted recipe list,
    //          and if user input is "y", the method sorts the given list.
    private void askWantSorted(List<Recipe> list) {
        System.out.println("\n" + "Do you want to view the menu sorted by the cooking time? Hit y/n and press enter");
        String command = scanner.nextLine();
        if (command.equals("y")) {
            list.sort(new RecipeComparator());
        } else if (!command.equals("n")) {
            System.out.println("Invalid input! Proceeding to unsorted recipes view by default");
        }
        System.out.println();
    }

    // MODIFIES: this
    // EFFECTS: shows all the recipe from the given list, and asks the user to choose the recipe
    //          that they want to see in detail. If, there are no recipes to view, it gets back to main UI.
    private void showRecipeMenu(List<Recipe> recipes) {
        askWantSorted(recipes);
        if (recipes.size() == 0) {
            System.out.println("There are no recipes to view! Getting back to main UI.. " + "\n");
        } else {
            System.out.println("Hit the number of the food to view in detail and press enter.");
            int count = 1;
            for (Recipe r : recipes) {
                System.out.println(count + ". " + r.getFoodName());
                count++;
            }
            int chosen = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            if (chosen >= 0 && chosen <= recipes.size()) {
                showRecipe(recipes.get(chosen - 1));
            } else {
                invalidStatement();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Shows the given recipe in a detail, with the options they can choose at that phase.
    private void showRecipe(Recipe r) {
        System.out.println("==================Recipe for " + r.getFoodName() + "==================");
        System.out.println("*******Ingredients Needed*******");
        printList(r.getIngredients());
        System.out.println("*******Steps*******");
        printList(r.getSteps());
        displayRecipeOption();
        String selected = scanner.nextLine();
        if (selected.equals("1") && !inFavoritesView) {
            tryAddFavorite(r);
        } else if (selected.equals("1")) {
            tryAddBookMark(r);
        } else if (selected.equals("2")) {
            System.out.println("Getting back to main UI..." + "\n");
        } else {
            invalidStatement();
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds the given recipe inside the favorites list only if the given recipe is not in
    //          the favorites list
    public void tryAddFavorite(Recipe r) {
        if (myFavorites.getRecipes().contains(r)) {
            System.out.println("Recipe is already in your favorites list!");
        } else {
            myFavorites.addRecipe(r);
            System.out.println("Successfully added to your favorites list.");
        }
        System.out.println();
    }

    // MODIFIES: this
    // EFFECTS: Adds the bookmark to the given recipe only if the given recipe is not bookmarked
    public void tryAddBookMark(Recipe r) {
        if (myFavorites.getBookMarkedRecipes().contains(r)) {
            System.out.println("Recipe is already bookmarked!");
        } else {
            myFavorites.addBookmark(r);
            System.out.println("Successfully added bookmark.");
        }
        System.out.println();
    }

    // EFFECTS: Prints out the appropriate options when viewing the recipe in detail.
    public void displayRecipeOption() {
        System.out.println("**Hit the number from the options below and press enter**");
        if (inFavoritesView) {
            System.out.println("1. Add bookmarks to the recipe above.");
        } else {
            System.out.println("1. Add the recipe above to the favorite list.");
        }
        System.out.println("2. Get back to main UI");
    }

    // EFFECTS: prints out each element of the given list.
    private void printList(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println();
    }

    // MODIFIES: this
    // EFFECTS: Creates a new recipe object and add it to the list where the user chose to add.
    private void createRecipe() {
        Recipe r = new Recipe(scanFoodName(), scanIngredients(), scanSteps(), scanCookingTime());

        System.out.println("\n" + "**Hit the number from the options below and press enter**");
        System.out.println("1. Add only to your favorites list.");
        System.out.println("2. Add to your favorites list and Collection list");
        String command = scanner.nextLine();

        if (command.equals("1")) {
            myFavorites.addRecipe(r);
        } else if (command.equals("2")) {
            myFavorites.addRecipe(r);
            rc.addRecipe(r);
        } else {
            invalidStatement();
        }
        System.out.println();
    }

    // EFFECTS: prompts the user to enter the name of the recipe that the user is creating
    //          and returns the entered String
    private String scanFoodName() {
        System.out.println("\n" + "Enter the food name and press enter.");
        String toReturn = scanner.nextLine();
        while (toReturn.equals("")) {
            System.out.println("The name of the food can't be empty. Enter the food name again.");
            toReturn = scanner.nextLine();
        }
        return toReturn;
    }

    // EFFECTS: prompts the user to enter the ingredients of the recipe that the user is creating
    //          and returns the entered list.
    private List<String> scanIngredients() {
        List<String> toReturn = new ArrayList<>();
        System.out.println("\n" + "Enter the ingredients. Leave the last line blank if you are done.");
        String entered = scanner.nextLine();
        while (entered.length() > 0) {
            toReturn.add(entered);
            entered = scanner.nextLine();
        }
        return toReturn;
    }

    // EFFECTS: prompts the user to enter the steps of the recipe that the user is creating
    //          and returns the entered list.
    private List<String> scanSteps() {
        List<String> toReturn = new ArrayList<>();
        System.out.println("Enter the steps. Leave the last line blank if you are done.");
        int count = 1;
        System.out.print(count + ". ");
        String entered = scanner.nextLine();
        while (entered.length() > 0) {
            toReturn.add(entered);
            count++;
            System.out.print(count + ". ");
            entered = scanner.nextLine();
        }
        return toReturn;
    }

    // EFFECTS: prompts the user to enter the cooking time of the recipe that the user is creating
    //          and returns the entered integer.
    private int scanCookingTime() {
        System.out.println("\n" + "Enter the estimated cooking time.");
        int toReturn = scanner.nextInt();
        scanner.nextLine();
        while (toReturn <= 0) {
            System.out.println("Should enter positive number. Enter again.");
            toReturn = scanner.nextInt();
        }
        return toReturn;
    }

    // EFFECTS: saves the recipe collection and my favorites list to file
    private void saveRecipeCollection() {
        try {
            collectionJsonWriter.open();
            collectionJsonWriter.write(rc);
            collectionJsonWriter.close();
            System.out.println("Successfully saved the recipe collection.");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to save the recipe collection.");
        }
        try {
            favoritesJsonWriter.open();
            favoritesJsonWriter.write(myFavorites);
            favoritesJsonWriter.close();
            System.out.println("Successfully saved your favorites list.");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to save your favorites list.");
        }
        System.out.println();
    }

    // EFFECTS: loads the last saved recipe collection and my favorites list from file
    private void loadRecipeCollection() {
        try {
            rc = collectionJsonReader.read();
            System.out.println("Successfully loaded last saved recipe collection.");
        } catch (IOException e) {
            System.out.println("Failed to fetch the last saved recipe collection.");
        }
        try {
            myFavorites = favoritesJsonReader.read();
            System.out.println("Successfully loaded your last saved favorites list");
        } catch (IOException e) {
            System.out.println("Failed to fetch your last saved favorites list.");
        }
        System.out.println();
    }

    // EFFECTS: prints out the invalid statement.
    public void invalidStatement() {
        System.out.println("Invalid input! Returning to main UI" + "\n");
    }
}
