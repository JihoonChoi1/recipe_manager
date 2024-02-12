package ui;

import model.*;
import persistence.CollectionJsonReader;
import persistence.FavoritesJsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//This class is a Recipe Application that runs a GUI, acting as a recipe manager.
public class RecipeAppGUI extends JFrame {

    private static final String JSON_COLLECTION = "./data/RecipeCollection.json";
    private static final String JSON_FAVORITES = "./data/MyFavorites.json";

    private static final ImageIcon addRecipeIcon = new ImageIcon("images/Add_Recipe_Icon.png");
    private static final ImageIcon exitIcon = new ImageIcon("images/exit_logo.png");
    private static final ImageIcon sortIcon = new ImageIcon("images/Sort_Button_logo.png");

    private JPanel collectionTab;
    private JPanel myFavoritesTab;

    private JPanel collectionTabLeft;
    private JPanel collectionTabCenter;
    private JLabel collectionRecipeDetailCenter;
    private JLabel collectionDetailTop;
    private JButton collectionCreateButton;
    private JButton collectionExitButton;
    private JButton collectionSortButton;

    private JPanel favoritesTabLeft;
    private JPanel favoritesTabCenter;
    private JLabel favoritesDetailCenter;
    private JLabel favoritesDetailTop;
    private JButton favoritesCreateButton;
    private JButton favoritesExitButton;
    private JButton favoritesSortButton;


    private RecipeCollection rc;
    private MyFavorites myFavorites;

    private JsonWriter collectionJsonWriter;
    private JsonWriter favoritesJsonWriter;
    private CollectionJsonReader collectionJsonReader;
    private FavoritesJsonReader favoritesJsonReader;


    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    // MODIFIES: this
    // EFFECTS: starts the GUI application
    public RecipeAppGUI() {
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: initializes all the visual components
    public void initialize() {
        rc = new RecipeCollection();
        myFavorites = new MyFavorites();
        collectionJsonWriter = new JsonWriter(JSON_COLLECTION);
        favoritesJsonWriter = new JsonWriter(JSON_FAVORITES);
        collectionJsonReader = new CollectionJsonReader(JSON_COLLECTION);
        favoritesJsonReader = new FavoritesJsonReader(JSON_FAVORITES);

        showLoadDialog();
        addTabs();
        setTitle("Recipe APP");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: Shows the pop-up dialog if the user wants to load the saved data
    public void showLoadDialog() {
        int ans = JOptionPane.showConfirmDialog(null,
                "Do you want to load your previous data?",
                "Load message", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            loadRecipeCollection();
        }
    }

    // MODIFIES: this
    // EFFECTS: assign recipe collection and myFavorites with the saved data
    //          if failed, doesn't load any data
    public void loadRecipeCollection() {
        try {
            rc = collectionJsonReader.read();
            JOptionPane.showMessageDialog(null,
                    "Successfully loaded last saved recipe collection.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to fetch the last saved recipe collection.");
        }
        try {
            myFavorites = favoritesJsonReader.read();
            JOptionPane.showMessageDialog(null,
                    "Successfully loaded your last saved favorites list");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to fetch your last saved favorites list.");
        }
    }

    // EFFECTS: Shows the pop-up window to the user, whether if the user wants to save
    //          the data or not
    public void showExitDialog() {
        int ans = JOptionPane.showConfirmDialog(null,
                "Do you want to save your current data?",
                "Save data", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            saveRecipeCollection();
        }
        new ConsolePrinter().printLog(EventLog.getInstance());
        System.exit(0);
    }

    // EFFECTS: Saves the collection list and the favorites list to the JSON file
    //          whichever fails to be saved won't be saved
    public void saveRecipeCollection() {
        try {
            collectionJsonWriter.open();
            collectionJsonWriter.write(rc);
            collectionJsonWriter.close();
            JOptionPane.showMessageDialog(null,
                        "Successfully saved recipe collection.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save the recipe collection.");
        }
        try {
            favoritesJsonWriter.open();
            favoritesJsonWriter.write(myFavorites);
            favoritesJsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Successfully saved your favorites list.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save the favorites list.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the tab and add it to the tabbedPane
    public void addTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        setViewRecipeTab();
        tabbedPane.addTab("MyCollection", collectionTab);
        tabbedPane.addTab("My Favorites", myFavoritesTab);

        getContentPane().add(tabbedPane);
    }



    // MODIFIES: this
    // EFFECTS: initializes the fields that are related to the collection and favorites list
    public void initializeFields() {
        initializeCollection();
        initializeFavorites();
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields that are needed for creating collection tab
    public void initializeCollection() {
        collectionTab = new JPanel(new BorderLayout());
        collectionTabLeft = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        collectionTabCenter = new JPanel(new BorderLayout());
        collectionRecipeDetailCenter = new JLabel();
        collectionDetailTop = new JLabel();
        collectionDetailTop.setFont(new Font("Courier", Font.BOLD, 20));
        collectionExitButton = new JButton(exitIcon);
        collectionExitButton.addActionListener(e -> showExitDialog());
        collectionCreateButton = new JButton(addRecipeIcon);
        collectionCreateButton.addActionListener(e -> createButtonPressed(rc, collectionTabLeft,
                collectionRecipeDetailCenter, collectionDetailTop));
        collectionSortButton = new JButton(sortIcon);
        collectionSortButton.addActionListener(e -> sortButtonPressed(rc, collectionTabLeft,
                collectionRecipeDetailCenter, collectionDetailTop, collectionSortButton));
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields that are needed for creating favorites tab
    public void initializeFavorites() {
        myFavoritesTab = new JPanel(new BorderLayout());
        favoritesTabLeft = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        favoritesTabCenter = new JPanel(new BorderLayout());
        favoritesDetailCenter = new JLabel();
        favoritesDetailTop = new JLabel();
        favoritesDetailTop.setFont(new Font("Courier", Font.BOLD, 20));
        favoritesExitButton = new JButton(exitIcon);
        favoritesExitButton.addActionListener(e -> showExitDialog());
        favoritesCreateButton = new JButton(addRecipeIcon);
        favoritesCreateButton.addActionListener(e -> createButtonPressed(myFavorites, favoritesTabLeft,
                favoritesDetailCenter, favoritesDetailTop));
        favoritesSortButton = new JButton(sortIcon);
        favoritesSortButton.addActionListener(e -> sortButtonPressed(myFavorites, favoritesTabLeft,
                favoritesDetailCenter, favoritesDetailTop, favoritesSortButton));
    }

    // MODIFIES: this
    // EFFECTS: Initializes fields and sets the component inside the collection tab and favorites tab
    public void setViewRecipeTab() {
        initializeFields();

        setViewRecipeTabLeft(rc.getRecipes(), collectionTab, collectionTabLeft, collectionRecipeDetailCenter,
                collectionDetailTop, collectionSortButton);
        setViewRecipeTabCenter(collectionTab, collectionTabCenter,
                collectionDetailTop, collectionRecipeDetailCenter);
        collectionTab.add(collectionCreateButton, BorderLayout.EAST);
        collectionTab.add(collectionExitButton, BorderLayout.SOUTH);

        setViewRecipeTabLeft(myFavorites.getRecipes(), myFavoritesTab, favoritesTabLeft, favoritesDetailCenter,
                favoritesDetailTop, favoritesSortButton);
        setViewRecipeTabCenter(myFavoritesTab, favoritesTabCenter,
                favoritesDetailTop, favoritesDetailCenter);
        myFavoritesTab.add(favoritesCreateButton, BorderLayout.EAST);
        myFavoritesTab.add(favoritesExitButton, BorderLayout.SOUTH);
    }

    // REQUIRES: tab != null, tabCenter != null, top != null, center != null
    // MODIFIES: this
    // EFFECTS: sets the center part of the recipe view tab
    public void setViewRecipeTabCenter(JPanel tab, JPanel tabCenter, JLabel top, JLabel center) {
        tabCenter.add(top, BorderLayout.NORTH);
        tabCenter.add(center, BorderLayout.CENTER);
        tabCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tab.add(tabCenter, BorderLayout.CENTER);
    }

    // REQUIRES: rc != null, tab != null, left != null, detailCenter != null, detailTop != null
    // MODIFIES: this
    // EFFECTS: sets the left part of the recipe view tab
    public void setViewRecipeTabLeft(List<Recipe> recipes, JPanel tab, JPanel left, JLabel detailCenter,
                                     JLabel detailTop, JButton sortButton) {
        left.add(sortButton);
        JLabel title = new JLabel("Recipes");
        left.add(title);
        left.setPreferredSize(new Dimension(120, 700));
        for (Recipe r : recipes) {
            JButton button = new JButton(r.getFoodName());
            button.addActionListener(e -> recipeButtonPressed(r, detailCenter, detailTop));
            left.add(button);
        }
        left.setBorder(new LineBorder(Color.BLACK, 3));
        tab.add(left, BorderLayout.WEST);
    }

    // REQUIRES: recipes != null, recipeListTab != null, detailCenter != null,
    //           detailTop != null, sortButton != null
    // MODIFIES: this
    // EFFECTS: rearranges the recipeListTab from the least cooking time to the longest cooking time
    public void sortButtonPressed(RecipeCollection rc, JPanel recipeListTab,
                                  JLabel detailCenter, JLabel detailTop, JButton sortButton) {
        rc.sortByCookingTime();
        recipeListTab.removeAll();
        recipeListTab.add(sortButton);
        JLabel title = new JLabel("Recipes");
        recipeListTab.add(title);
        for (Recipe r : rc.getRecipes()) {
            JButton button = new JButton(r.getFoodName());
            button.addActionListener(e -> recipeButtonPressed(r, detailCenter, detailTop));
            recipeListTab.add(button);
        }
        recipeListTab.validate();
    }

    // REQUIRES: collection != null, recipeListTab != null, detailCenter != null, detailTop != null
    // MODIFIES: this
    // EFFECT: prompts the user for the action needed for creating the recipe
    //         triggered when create recipe button is pressed
    public void createButtonPressed(RecipeCollection collection, JPanel recipeListTab, JLabel detailCenter,
                                    JLabel detailTop) {
        String foodName = promptString("food name");
        if (foodName == null) {
            return;
        }
        String cookingTime = promptString("cooking time");
        if (cookingTime == null) {
            return;
        }
        List<String> ingredients = promptList("ingredients");
        if (ingredients == null) {
            return;
        }
        List<String> steps = promptList("steps");
        if (steps == null) {
            return;
        }
        Recipe r = new Recipe(foodName, ingredients, steps, Integer.parseInt(cookingTime));
        collection.addRecipe(r);
        Button button = new Button(r.getFoodName());
        button.addActionListener(e -> recipeButtonPressed(r, detailCenter, detailTop));
        recipeListTab.add(button);
        recipeListTab.validate();
    }

    // REQUIRES: str != null
    // EFFECTS: Shows the pop-up window that asks the user to enter the string
    public String promptString(String str) {
        JTextField textField = new JTextField(20);
        int result = JOptionPane.showConfirmDialog(null,
                getStringInputPanel(textField, str),
                "Enter " + str, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return textField.getText();
        } else {
            return null;
        }
    }

    // REQUIRES: str != null
    // EFFECTS: Shows the pop-up window that asks the user to enter the strings separated by commas
    public List<String> promptList(String str) {
        List<String> list;
        String toReturn;
        JTextField textField = new JTextField(20);
        int result = JOptionPane.showConfirmDialog(null,
                getLongInputPanel(textField, str),
                "Enter " + str, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            toReturn = textField.getText();
            list = splitString(toReturn);
            return list;
        } else {
            return null;
        }
    }

    // REQUIRES: str != null
    // EFFECTS: splits the str given which is separated by commas and add each element to the list to return.
    public List<String> splitString(String str) {
        List<String> list = new ArrayList<>();
        int i = str.indexOf(",");
        while (i != -1) {
            list.add(str.substring(0, i));
            str = str.substring(i + 1);
            i = str.indexOf(",");
        }
        list.add(str);
        return list;
    }

    // REQUIRES: textField != null, str != null
    // EFFECTS: Creates the panel that is used to ask the user for the string input
    public JPanel getStringInputPanel(JTextField textField, String str) {
        JPanel stringInputPanel = new JPanel();
        JLabel askForString = new JLabel("Enter " + str);
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(askForString);
        verticalBox.add(textField);
        stringInputPanel.add(verticalBox);
        return stringInputPanel;
    }

    // REQUIRES: textField != null, str != null
    // EFFECTS: Creates the panel that is used to ask the user for the string input separated by commas
    public JPanel getLongInputPanel(JTextField textField, String str) {
        JPanel longInputPanel = new JPanel();
        JLabel howToType = new JLabel("Separate each " + str + " by commas");
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(howToType);
        verticalBox.add(textField);
        longInputPanel.add(verticalBox);
        return longInputPanel;
    }

    // REQUIRES: r != null, detailCenter != null, detailTop != null
    // MODIFIES: this
    // EFFECTS: sets the center of the recipe view tab with the given recipe
    public void recipeButtonPressed(Recipe r, JLabel detailCenter, JLabel detailTop) {
        detailTop.setText("<html>" + r.getFoodName() + "<br/> Estimated cooking time: "
                + r.getCookingTime() + "</html>");
        String procedure = "<html><pre>Ingredients:<br/>";
        for (String ingredient : r.getIngredients()) {
            procedure += "    " + ingredient + "<br/>";
        }
        procedure += "Steps: <br/>";
        for (String step : r.getSteps()) {
            procedure += "    " + step + "<br/>";
        }
        procedure += "</pre></html>";
        detailCenter.setText(procedure);
    }

    // EFFECTS: Runs the Recipe Application GUI
    public static void main(String[] args) {
        new RecipeAppGUI();
    }
}
