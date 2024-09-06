# To Do List Spring Backend

{add test badges here, all projects you build from here on out will have tests, therefore you should have github workflow badges at the top of your repositories: [Github Workflow Badges](https://docs.github.com/en/actions/monitoring-and-troubleshooting-workflows/adding-a-workflow-status-badge)}

## Demo & Snippets

-   Include hosted link
-   Include images of app if CLI or Client App

---

## Requirements / Purpose

### Overview

Create an API to be integrated with your To Do UI project, that allows you to store and retrieve tasks from a database.

### MVP

- Deleting a task should set an `isArchived` flag in the database instead of deleting the task from the database
- Add a filter to the frontend application that allows you to filter tasks by category
- Categories and Todos should be stored in separate tables

### Endpoints

- `GET /categories`
- `POST /categories`
- `PUT /categories/:id`
- `DELETE /categories/:id`

- `GET /todos`
- `GET /todos?category={}`
- `POST /todos`
- `PUT/PATCH /todos/:id`
- `DELETE /todos/:id`

---

## Build Steps
 May not need this as should already be hosted hopefully (should have instructions for local hosting)
-   how to build / run project
-   use proper code snippets if there are any commands to run

---

## Design Goals / Approach

-   Design goals
-   why did you implement this the way you did?

---

## Features

-   What features does the project have?
-   list them...

---

## Known issues



---

## Future Goals

-   updating and deleting of Categories
-   Adding due dates
-   Implementing a calendar view with due dates

---

## Change logs

-   Write a paragraph labelled with the date every day you work on the project to discuss what you've done for the day. Be specific about the changes that have happened for that day.

### 28/8/2024 - Category End Points

-   Created the Category and ToDos service, repo, controller and entities
-   Implemented the Cateogry Post and Get
-   Added the webconfig to allow Postman and the Front end to work with the API


### 29/8/2024 - To Do End Points

-   Implemented the Post, Get, Delete and Update mappings inside the ToDoService and ToDoController
-   Checked all working with PostMan


### 30/8/2024 - Error Handling

-   Added Validation, Bad Request and Not Found Exceptions
-   Implemented a custom Global Exception Handler to handle all the Exceptions and provide a more meaningful error message back to the user
-   Added dependecies for End to End and unit testing
-   Created 2 End to End tests for the Category
-   Adds End to End test for Post-success
-   Adds broken test for End to End Post-failure


### 2/9/2024 - Testing

-   Fixes broken E2E test for Post-failure due to error not being name but category and field instead of name in the schema testing.
-   Added the structure for the ToDo testing

### 3/9/2024 - Testing Category and ToDo Continued

-   Adds the Github Actions for a basic continuous intergration testing on Push and Pull Requests.
-   Implements the rest of the tests for the ToDoService Unit Tests
-   Implemented the rest of the tests for the CategoryService Unit Test

### 4/9/2024 - Testing ToDo Continued

-   Implemented the test for Delete and Update for the ToDo End to End tests

### 5/9/2024 - Refactor

-   Changed isCompleted to be a boolean everywhere instead of being a integar for the DTOs of ToDo
-   Fixed issue with isCompleted being null due to slight variable name on front end

### 6/9/2024

-   Implements deleteById on the Category service and controller
-   Adds Unit and End to End tests for the deleteCategory success and fail

---

## What did you struggle with?

-   Implementing end to end testing for the todos. Understanding the syntax and ensuring it covered all the edge cases as well
-   Fixing memory related issues that arose with the unit testing as the service itself instancated a variable and so couldn't check that variable exactly

---

## Licensing Details

-   What type of license are you releasing this under?

---

## Further details, related projects, reimplementations

-   ToDo Frontend - [Frontend](https://github.com/Avocado955/todolist-frontend)
