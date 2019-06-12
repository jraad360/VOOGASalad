# Design

## Introduction
This program will provide an intuitive authoring environment for tower-defense games. The scope of this authoring environment encompasses titles such as Bloons Tower Defense, Plants vs. Zombies, and Clash of Clans. The primary design goals of this project include the ability to make an authoring environment that is flexible enough to support multiple variations of a specific game genre. Thus, the game engine will need to be able to process a rather broad interpretation of a set of rules instead of exact and pre-defined notions/rules of specific tower defense games (Multi-Row waves of Plants vs Zombies compared to a single path game such as Bloons compared to a user vs enemy game in clash of clans). The player will simply display the results of the processing of the game engine without knowing any logic of how the game works or what anything means. User interactions will change state within the game engine without the front-end/player understanding what is necessarily going on. As such, this project will test our ability to create effective lines of "public" communication between our authoring environment, player, and game engine such that each portion of the project can exist detached from the other, and without heavy dependencies on each end. 

## Overview

### Game Authoring Environment
The Game Authoring Environment is designed to communicate extensively with the Game Engine using the Game Engine's external API calls. The Authoring Environment will be split up into separate panes. There will be no need for the Authoring Environment to provide an external API given that the player and data will rely on the external API of the game engine as well. In terms of internal design, the Authoring Environment will be split into separate panes that contain crucial buttons or lists of agents and their attributes. 

### Player
The Player module is designed to be primarily frontend with most of the necessary behavior routed to API calls to the game engine. With each step of the animation, Game Player will be in constant communication with the Game Engine. Different components of the Game Player will also implement Observables that have a Game Engine observer listening to them so the Game Engine can receive information to update the game state. When the game state is updated, multiple controllers (e.g. AgentController, StatsController) then make API calls, retrieve information, and update the user view.

### Game Engine
The Game Engine is designed to incrementally update the game one frame at a time. It will register any changes that occur in a frame and send these changes to the Player. This means the Game Engine is responsible for the physics of moving characters, projectiles, turning towers, deciding how attackers move, pathing, relationships between objects, and checking if objectives are met. The Game Engine will also develop two more sub-modules that hold the state of the game and the object hierarchy.

### Data
The data portion is built around being able to serialize and deserialze the game state as well as individual objects. This serves two primary points, one of saving and loading the game state for switching between player and editor or saving a gametype, and another for transmissing and communicating updates to the game state between the game engine and the players during an online match. 

## User Interface

### Player 

**Main Screen**
The main screen will be the game screen. The player's stats at any given time (lives, score, resources available) will be on screen in a corner. There will be  "store" containing the types of towers available to the player. The player will also get access to a settings button that when clicked will pause the game and open a menu that allows player to restart, keep playing, pause music (if there is music), switch games, etc.

**Play**
When the user plays, the towers will be constantly animating if they are shooting. When a user is deciding to play a tower, they will be able to see the range (if available) of the tower's attack. 

**Multiplayer**
In Multiplayer mode, the two games run in parallel so the game screen will look similar but with a small panel that shows the opponent's game state.


### Game Authoring Environment

The general layout of the game authoring environment as currently planned can be seen at [this link](https://i.imgur.com/BuuLCT9.png).

- **Components**: Users will interact with the authoring environment through a series of panels. These panels will contain visual elements (ex. images, buttons) with clear functions. Users will be able to click elements and dropdown lists to open new views, edit different fields, and view information about specific components.
- Plan to extend from JavaFX's Dialog class to create small dialog box popups as users select certain buttons. These will allow the user to populate necessary information in fields for creating game elements.
- The authoring environment offers users the ability to select rules and objectives for the game to follow. Since the game engine will be somewhat restricted in its ability to abstract rules and objectives, these will be somewhat restricted in terms of user selection (i.e. dropdowns, specific preset values to choose from).
- Other elements, such as tower characteristics, should be pretty flexible. Parts of these attributes will be manually typed in by users. In these cases, the engine will check for invalid data or empty data once submitted by the user. Feedback will be given through some sort of dialog to the user. 
- Placing game elements will also have error checking. If a user attempts, for example, to place a tower on top of another tower, the game engine will notify the authoring environment of an error, not allow the placement, and trigger the authoring environment to notify the user.

## Design Details

### Game Authoring Environment
Branching off the overview, the authoring environment makes use of the authoring_environment module, a module containing all classes necessary to define the initial game state and rules for a certain game mode. The essential design feature of the authoring environment is how it communicates with the game engine in terms of creating the initial inventory of placeable agent items. Essentially, the game authoring environment will have access to the game engine's external API for defining agents. When an agent is defined within the authoring environment, it will make a call to the engine's "defineAgent" method and pass in the values set by the authoring environment. Even more, the actual call to this defineAgent method is done using a controller. This way, we can encapsulate the authoring environment from the game engine and use a middle layer to actually make the method call. This also applies to placing agents on the map within the authoring environment--an API call from the game engine will be used which contains the proper logic for bounds checking and locaton checking for agents. In terms of designing the authoring environment internally, we will have different panes that have different roles. This includes an agent pane with buttons that allow for creating and deletion of agents, an attributes pane that allows for dynamic editing of an agent, a map pane that designs and sets the initial positions of agents for a game mode, etc. These different panels will be a part of their own modules and as such any communication between them would require an internal API. An example would be between the communication between the agents panel and the dynamic attributes panel. When selecting an agent, the attributes panel will need to obtain the agent definition state and display it within an editable field. When changes within the dynamic attributes panel are made, the agents will need to be able to receive those updates. As such, there is a lot of room for communication between different panes or panels within the authoring environment with a lack of too much dependency on outside modules due to the use of a middle layer controller. 

### Game Engine

The Game Engine will be responsible for three major areas:
1) Object Hierarchies
    * Action
        * movement
            * MoveAtRelativeAngle
            * MovementAction
            * MoveOnPointPath
            * MoveStraightToAgent
            * MoveToAgentPRM
        * spawn
            SpawnAgent
            SpawnAgentInitialDirection
        * ApplyAgentOperation
        * BaseAgentAction
        * PointToAgent
    * ActionDecision
    * Agent
    * AgentOperation
        * ChangeSpeed
        * DamageOperation
        * HealOperation
    * Condition
        * BaseAgentCondition
        * ClosestCondition
        * CollisionCondition
        * IntervalCondition
        * RangeCondition
      
    * Objective
        * StartToFinish
        * Time
        * Speed
        * LastOneStanding
        * TargetScore

    * Attributes
        * Lives
        * Money
        * Name
        * Level

2) State Object
    * List<Agent\> availableTowers;
    * List<Agent\> currentAgents;
    * List<Agent\> currentObjectives;
    * List<Agent\> curretAttributes;
    * Image Background

3) Game Physics/Logic
    * Ticking through game
        * Each Tick
            * Listen on each object, determine what needs to be changed for the state and as it needs to be changed, send the updated version of all changed objects to the Player. The player the sends that updates their copy of the state and displays the change.

This project will contain six modules:
1) GameEngine
2) Player
3) Author
4) Data
5) State - holds instances of all game state objects
6) GameStateObjects
    * Agent Hierarchy
    * Objective Hierarchy
    * Rule Hierarchy
    * Attribute Hierarchy (lives, money, etc.)

### Player

The player's main class Game will extend JavaFX's Application. This Application will hold a GameMap object, a SceneController, an AgentController, and AttributesController class. 

The GameMap object will be the front-end reflection of the map defined by authoring and is static throughout the game. The SceneController is responsible for switching between Scenes. The AgentController holds the AgentState objects for all of the agents on the map and the AttributesController holds the Attributes classes for the player(s).

The reflection of the progress of the game will be through observers. Changes to the AgentState (a model for agent properties existing mostly for frontend purposes) will notify their respective observers. These actions will then enter a queue for their corresponding author_states.frontend_objects.AgentView objects. Each step() of the main Application class (Game) will process actions from the queue for the appearance of a smooth game.

The author_states.frontend_objects.AgentView objects are stored in a Level class that extends Scene. This Level class is created by the Game Engine object with specific properties and identifiable by an id. If the active level, changes to the AgentState will be reflected in the AgentViews in that level.

On the converse, changes to the game made from the front-end (store purchase, tower movement, etc.) will be delivered to the AgentState in the game engine through an external handler.

### Data
For serialization/deserialization the Data client will use GSON. Should the limitations of JSON's smaller variety of data representations prove problematic the switch to XML can be done rather easily however. From here, once the game state can be serialized, this library will be used to implement networked implementations of the interfaces between the Game Engine and the player, where both sides do not need to know anything about the networking to operate. Updates to objects in the Game Engine will be passed along to the Player, and vice versa. This will be done by the API call being made on the client side, for instance, will be packaged along with the data payload on the internet wire, and using Reflections the server instance on the other end can determine which method to call on the real Game Engine instance. 
## Example games
- Bloons 
- Clash of Clans
- Plants vs. Zombies

## Design Considerations

* Authoring Subteam
    * A major design consideration was what the authoring environment would communicate with on creation of a game. Essentially, the debate was between whether or not the authoring environment would communicate directly with the game data or the game engine itself. 
        * Communicating with data: The game authoring environment would set values for fields and agents and save that into a data file from which the game engine would load
        * **Communicating with game engine directly**: This would be the easier route. ALthough the data provides a level of indirection, changing values within the authoring environment could change the actual state values within the game engine. In this case, we could provide a controller interface to communicate between the authoring environment and the game engine. This still allows us to define initial states within the game engine that allows it to run directly instead of loading a file everytime.
    * Another design consideration was how to make the authoring environment modular. We decided that we would have panes to hold specific items such that panes could be moved around for drag-and drop features. If we instead put individual buttons on the screen and hard-coded their positions without a wrapper like a pane, it would be very hard to make modifications on a group of buttons or implement a drag and drop feature

### Player Subteam
* Our biggest design consideration was how information was going to travel between Game Player and Game Engine. We decided against having an external API for the Player that the Engine would be constantly making calls to to retrieve information, and opted to make use of the Observer/Observable pattern. Game Engine will place observers on the Player modesl (e.g. AgentState). Whenever changes are made to the Player models their observers will be notified, and the Game Engine will process those changes. 
* Another design consideration we made was a UI design choice. When implementing multiplayer mode, we initially split the user interface view into two screens of equal size, one showing with the player's game state and the other showing his opponent's. We then reconsidered this and changed the view of the opponent's screen to be much smaller and towards the corner because we realized that the user would probably care more about interacting with his own game than simply viewing his opponent's.