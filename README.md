# voogasalad
* Check ANALYSIS.md for an in-depth analysis by Jorge Raad

## Names of all people who worked on the project
* Jorge Raad
* Jake Mullett
* Luke Truitt
* David Miron
* Jamie Palka
* Joanna Li
* Mary Gooneratne
* Samuel Rabinowitz
* Mary Stuart Elder
* Eric Lin

## Date you started, date you finished, and an estimate of the number of hours worked on the project
### Start date - 03/19/2019
### End date - 05/03/2019
### Hours - 600

## Each person's role in developing the project
* Jorge Raad
    * Game Development
    * Agent
    * Commmunication between Data and Game Engine
    * Pathing on Game Engine side
    * Game physics
    * Game loop
    * Action Decisions
* Jake Mullett
    * Data Serialization
    * Data De-Serialization
    * Networking/Multiplayer
* Luke Truitt
    * Communication between Player and Game Engine
    * Properties
    * Attributes
    * LevelState/State
    * Game Physics
    * Exception Handling
    * Game Development
* David Miron
    * Action Decisions
    * Conditions
    * Communication between Author and Game Engine
    * Game Factory
    * Exception Handling
    * Level/State/Game
* Jamie Palka
    * Objectives
    * Action Decisions
    * Conditions
    * Agent
    * Game Loop
    * Level/State/Game
* Joanna Li
    * Splash Screen
    * Attributes
    * Objectives
    * Styling
* Mary Gooneratne
    * Communication between Player and Game Engine
    * Store
    * Styling
* Samuel Rabinowitz
    * Communication between Author and Game Engine
    * Agent Creation
    * Authoring Pane abstraction
    * Agent Pane
    * Attribute Pane and forms
    * Objective Creation
    * Error directing
* Mary Stuart Elder
    * Pathing backend on Authoring Side
    * Tools for pathing
    * Levelling
    * Styling
* Eric Lin
    * Mapping
    * Drag and Drop Containers
    * Placement and Lasso Tools
    * Error Checking
    * Styling

## Any books, papers, online, or human resources that you used in developing the project
### Stack Overflow
### Java Documentation

## Files used to start the project (the class(es) containing main)
##### To start Authoring, set up a .jar for it with Main as AuthoringEnvironment
##### To start Player, set up a .jar file for it with Main as GameApplication

## Files used to test the project and errors you expect your program to handle without crashing
##### Files used in this can be found in the directory doc/games. 

## Any data or resource files required by the project (including format of non-standard files)
##### You must run the entire folder, not just the XML.

## Any information about using the program (i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs)
##### The flow should be relatively easy to understand, the documentation for how to create Action Decisions and Conditions can be found in doc/GAME_CREATION.md

## Any decisions, assumptions, or simplifications you made to handle vague, ambiguous, or conflicting requirements
##### The only assumptions we made were in regard to how the game will be created. 
1) We assumed relative competency in understanding the logic that goes into creating Action Decisions
2) We assumed people would not want to compare separate properties
##### Other than this, we didn't assume anything major

## Any known bugs, crashes, or problems with the project's functionality
##### There is a bug with saving and reloading a game that duplicates all agents in the store and does not remove agents from the screen on reload.

## Any extra features included in the project
##### Networking
1) Shared Viewing, the game can be played on one computer and view on a separate computer
2) Multiplayer, we were close to finishing this, with a few more hours to strictly focus on this it would have been accomplished.

## Your impressions of the assignment to help improve it in the future
##### Very open ended but we feel like that made the project much more enjoyable to create and learn from.