# Project Plan

## High Level Project Timeline
4/2/19: Establish inter-module communication structures, essential classes created
4/6/19: Basic UI created (some panes/buttons), UI has noticeable communication with backend
4/9/19: Basic implementation complete, aim to create basic functionality for building a game in author, loading it from data into player/game engine, and playing a basic game
4/13/19: Added multiplayer split screen (two players, but still on same machine), data has progressed on implementing networking
4/18/19: Improvements to game physics and, flexibilty, design. Work on networking
4/23/19: Networking implemented, author and player have most desired elements
4/28/19: Complete implementation, at this point extra features added and final UI polishes completed

## Individual Responsibilities
### David
#### Primary responsibilities
- Game Physics
#### Secondary responsibilities
- Path Planning

### Jamie
#### Primary responsibilities
- Object hierarchy
#### Secondary responsibilities
- Supporting Listener functionality

### Jorge
#### Primary responsibilities
- Game Loop
#### Secondary responsibilities
- Implementing game data objects

### Samuel
#### Primary responsibilities
- Design and creation of user interface of game authoring environment layout (5 panes within the overall border pane, small windows that pop up, etc.)
#### Secondary responsibilities
- Assisting with links to controller and integration of frontend object classes and drag/drop capability

### Mary Stuart
#### Primary responsibilities
- Creation of frontend object classes (i.e. frontend representations of select game engine classes) for game authoring environment
- Implementing drag/drop capabilities
#### Secondary responsibilities
- Assisting in game authoring environment layout
- Contribute to shared authoring-player frontend utilities package

### Eric
#### Primary responsibilities
- Creation of controller for communication between the authoring environment and the game engine for updating values/rules (External API). Also responsible for communication between individual authoring components
#### Secondary responsibilities
- Assisting in game authoring environment layout

### Joanna
#### Primary responsibilities
- MapController, FloatingController, StoreController
- Creating different scene components
- Game Player application
#### Secondary responsibilities
- Integrating Game Player and Game Engine
### Mary
#### Primary responsibilities
- Making JavaFX objects for game display
- JavaFX Game Application
- AgentController, AttributeController, SceneController
#### Secondary responsibilities
- Working with other subteams

### Jake
#### Primary responsibilities
- Creation of the data serialization/deserialization functionalities
- Implemenentation of networked play via implementing the game engine and player interfaces for across networks
#### Secondary responsibilities
- Interfacing with Game Engine subteam
- Assisting other subteams if possible and necessary

### Luke
#### Primary responsibilities
- Communication with Authoring
- Communication with Data
#### Secondary responsibilities
- Networked Game Engine interface implementation for multiplayer/online play