
# Recipe book

For this term, I will make an application that performs as a recipe search engine. 
Users can search for the food they want to make and get the recipe for it.
Any people who would like to cook will be using this application.
The reason I came up with an idea to make a recipe book is because I needed
a recipe application that has more features than a regular recipe book.

## User stories

- As a user, I want to **search** for a recipe that I want, and add to my favorites list.
- I want to add **bookmarks** to the recipe in my favorites list to view bookmarked recipe always at to top.
- As a user, I would like to **view** the recipe I saved in my favorites list in detail.
- As a user, I would like to **view** the recipe I saved in collections list in detail.
- I would like to **sort** the list of recipe by the least cooking time to the longest cooking time.
- As a user, I would like to add Recipe to the favorites list.
- As a user, I would like to save my favorites list and all collection list to the file.(if I choose)
- As a user, I would like to reload all the data that I have saved before.(if I choose)

## Instructions for Grader

- You can add recipe to the Collection list by clicking green plus button on 
  the right side of the Collection tab.
- You can add recipe to the myFavorites list by clicking green plus button on 
  the right side of the My Favorites tab.
- After adding the new recipe to either tab, you will be able to see the button 
  created with the new recipe name on the left side of the tab.
- You can see the details of the recipe at the center of the tab by clicking any 
  recipe button that you want to look at.
- You can sort the recipe by cooking time by clicking at the icon on the top left-side of the tab.
- You will be able to see the visual component by looking at the green plus button(right side of the tab) 
  that are used to add new recipe.
- You will be able to see the visual component by looking at the red exit button(bottom of the tab) 
  that are used to exit the program. 
- You will be able to see the visual component by looking at the sort button(top-left of the tab)
  that are used to sort the recipes in order by cooking time.
- You can see the pop-up window when the program starts, that asks the users if they want to 
  reload the data that was saved. By clicking yes, you will be able to load the data.
- You can save the state of the application by clicking on the red exit button that 
  is located below of the tab. There will be a pop-up window shown that asks if the user wants 
  to save the data. By clicking yes, you will be able to save the data to the JSON file.

## Phase 4: Task 3

If I had more time to work on the project, I would like to make a new abstract class with a field
with a list of recipes. The RecipeCollection class I currently have is meant to be the recipe list
that interacts with all the program users. However, the MyFavorites class intends to be the container 
of recipes the user liked from the RecipeCollection list. After implementing the code, I thought there 
wasn’t enough reason for the hierarchy between these two classes. Due to the current structure, adding 
functionalities that should only be used in the RecipeCollection class may be challenging. As such, 
I would like to add an abstract class that only works as a container of the recipes with only basic 
functionalities that I can have related to the recipe list and two classes extending the new abstract 
class so that both classes can have unique functionalities for their own.
