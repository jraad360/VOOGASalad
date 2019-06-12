# 50 Use Cases

### Authoring
1. A user clicks the + button near the agent pane and a small window pops up to assist in that creation.
2. After clicking the + button and correctly filling the data fields within the popup window, a user clicks the "Cancel" button in the button right, causing the window to close without adding a new agent.
3. After clicking the + button and correctly filling the data fields within the popup window, a user clicks the "Create" button in the button right, the window closes, a new agent is added to the agent pane.
4. After clicking the + button and incorrectly filling a data field in the popup window with negative data, a user clicks the "Create" button in the button right, causing the game engine to throw an exception and the authoring environment to display an error message to the user stating "Invalid data".
5. After clicking the + button and incorrectly filling a data field in the popup window with empty data, a user clicks the "Create" button in the button right, causing the game engine to throw an exception and the authoring environment to display an error message to the user stating "Empty data".
6. After clicking the + button and incorrectly filling any of the data fields within the popup window, a user clicks the "Cancel" button in the button right, causing the window to close without adding a new agent or throwing an exception.
7. A user selects a previously customized agent in the agent pane on the right side of the screen. This causes the attribute pane on the left side of the screen to display information regarding that agent, which the user can edit.
8. The result of an operation that does not require immediate user action will be printed to the console, in a color on the grayscale if something happened normally, in green if it was especially good (might be unecessary), in orange if it was a warning, or in red if it was an error.
9. A user drags an agent from the agent pane onto the map and releases. Notify the game engine of the placement. If it is a valid placement, accept it. If not, display the error from the game engine.
10. A user clicks on an agent placed in the map. They can drag that agent around to change its position, or use the dynamically-filled attributes pane to set its position precisely or change other attributes.
11. A user clicks the select button (rectangle or lasso/freeform) in the toolbar. They are then able to select a group of agents by clicking and dragging through the map. The attributes window will show attributes all of the agents share.
12. Upon using the selection tool to select a group of agents in the map, the user can drag all of the agents around as one.
13. Multi-agent selection can be done by holding Shift or Ctrl, as is done in many other programs and has now become a standard.
14. Using the toolbar or menu, the background of the map can be chosen by loading an image file via a file chooser.
15. The toolbar or menu will have a grid vs. freeform map mode. Switching this will cause agents to snap to a grid or allow free placement of them. *Note: depending on how the GameEngine chooses to implement this, this may be a decision that has to be made when a new game is created.*
16. A user clicks on file, and load, to load an already initialized game state that was saved earlier. 
17. A user clicks on file, and save, to save the current game state into a data file. 
18. A user selects a created agent and the attributes panel that contains its information updates to that specific agent. A user inputs an incorrect field change such as a negative value and an error of "Invalid Data" pops up. 

### Player
1. The player opens up the program and is greeted with a splash screen that has four games available for the player to click on
2. The player clicks on his game and is then taken to a screen that asks "Single Player" or "Multiplayer"
3. The player clicks "Single Player" and is taken to the game screen.
4. The player clicks "Multiplayer" and is taken to a screen asking "Join a Game" or "Start a new Game"
5. The player clicks "Join a Game" and is taken to a game lobby with a list of other games that are waiting for someone to join.
6. The player clicks the settings icon in the top right corner, the game is paused, and the options to restart the level, return home, or resume playing are presented.
7. The player clicks on the cart icon in the top right corner and the game is paused and the game store opens.
8. The player clicks on a Floatable currency object and increments their currency progress bar in the top left.
9. The player clicks a tower from the store and attempts to place it in an invalid location and it is highlighted red and returned to store.
10. The player clicks on a tower from the store and can't afford it and receives a notification explaining so.

### Game Engine

1. User chooses to "save game" and the current state of the game calls Data's external API to save it to a file.
2. The authoring environment finishes creating the state and needs to save it. It calls Game Engine which then communicates with Data to save the state to a file.
3. Two objects collide. Within the game loop, Game Engine checks whether any objects have collided and executes the corresponding action.
4. Player uses currency to upgrade a tower.
5. Player chooses to load a game and Game Engine calls Data's external API to load from an XML file.
6. Player loses a life.
7. A bomb explodes and damages all units on the opposite team in its range
8. An attacker destroys a tower and starts moving to the nearest next tower
9. A balloon reaches the end of a track and the player looses one life
10. A character runs into a wall and cannot move in that direction anymore
11. A character decides the fastest way to get to a particular place
12. The user taps a sun falling and gains sun money
13. The big red dragon ate all of the mashed potatos
14. Authoring Environment chooses to implement "Robert Duvall" attacker.
15. Player defines a rule which must, in turn, be implemented in the flow of the game within the game engine.
16. The territory of the player is destroyed and the player has no lives left.
17. Both players lose all lives but someone must win.
18. A player obtains an object of monetary value which increases their ability to place objects to deter enemies.
19. Player drops special one-time-use "nuke" object that clears all enemies from screen (high price).
20. A multiplayer game is started, connecting another player instance to the same game engine.

### Data
1. The network interface uses the serialize and deseralize methods to translate Java objects over a connection
2. The game engine interprets a saved authoring environment for playing by loeading the saved file via the Data interface