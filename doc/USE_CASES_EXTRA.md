50 More Use Cases
===

### Authoring
1. A user clicks the + button near the agent pane and the attributes pane becomes a form to create a new agent. The use cases in the previous document that address the creation of an agent still stand, just not in a popup window anymore.
2. When creating a new agent, the user can upload an image for the agent.
3. When creating a new agent the user can define a width and height for the agent that will then be used to actually represent that agent in the map.
4. A user can grab the handle of any pane and drag the entire pane out of it's default docked position. If the user drops it in middair, it forms it's own little window. If the user drops it into another dock, it docks in that new place.
5. A user can add a new action decision when creating/editing an agent in the attributes pane, and select the action, condition, and parameters associated with it.
6. A user can load a defined set of pre-made agents as examples to build from, or they can define a custom agent from scratch.
7. A user can set the style, location, and background image for the money, lives, and other HUD items.
8. A user can set the numerical bounds of certain common game values, such as money and lives.
9. A user clicks on File, Upload Image to Background, selects an image file, and it is set to fill the map.
10. A user double clicks a previously defined agent in the agent pane. The agent appears in the map, followed by a message display in the console.
11. A user clicks the lasso icon in the toolbar. The user clicks an area over the map, then drags and creates a circle. On mouse release, any agents intersecting this circle are selected.
12. Given a group of selected agents, a user clicks one of the agents in the group and drags it across the map. The other selected agents will move as well.
13. Given a given of selected agents, a user clicks on a part of the screen other than one of the selected agents. Then, all of the previously selected agents are unselected.
14. A user clicks file and load to load a pre-defined game with everything including inventory
16. A user clicks edit and undo or redo to go back to the previous state in terms of number of agents or movement in the map
17. A user is able to define regions in a map where agents can be placed
18. A user is able to define regions in a map where an agent is able to traverse through. It can define regions where agents can be placed. 
19. On close of the program, the authoring environment should prompt to save or discard the current authoring environment 

### Player
1. New multiplayer mode shows a javafx screen that allows player to enter an IP address and port
2. Player can see floatable objects like money and energy
3. Player can click on floatable objects and have them affect their stats
4. Agents can shoot different types of projectiles
5. Agents can start shooting and stop shooting projectiles
6. Player can click settings button
7. Player can click on Agent to look at stats
8. Player can stop game and load different game
9. Player can select to enter an IP address and port to join another person's game
10. Agents will look like they are facing their enemies

### Game Engine
1. An agent moves faster after getting damaged.
2. An agent fires projectiles while flying in a figure-8 motion.
3. A wall agent does not allow other agents to go through it.
4. An agent has the ability to convert other agents to the opposite team.
5. Enemies come into the screen in waves.
6. An enemy runs into a teleporter and its position is set to the other side of the teleporter
7. An agent's position is updated based on its velocity
8. An archer's arrow hits a wall and does damage
9. A tower's projectile goes over a friendly wall and continues forward toward an enemy
10. An agent dies, and is removed from this list of agents
11. An agent stops to attack then once the enemy is dead, continues on his way.
12. A new Agent is added to the screen.
13. The game is paused
14. An item is unlocked from the store
15. Authoring sends us a State and we need to send it to Data.
16. An archer's arrow hits an agent of the opposite team and its health decreases.
17. An agent fired projectiles that will always be targeted to the nearest enemy.
18. A house-like agent spawns enemies that always move toward a specific team.
19. An agent collides with a wall and continues to move against the wall.
20. An agent spawns a health package that increases the health of other agents on collision.

### Data
1. 