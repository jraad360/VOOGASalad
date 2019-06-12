# Overall Notes
## Pathing
* Paths are lists of points
    * Need to be able to assist path to an agent
* Make a tool in toolbar (Path builder)
    * Double click on map to make point
    * Add a path pane
* Visually display lines
* Right click offers editing menu (delete)
* Drag and reposition
## Images
* Type in width and height of agent to modify
    * Need to be able
    * Do scaling by type, not instance
    
## Levels/Waves
* Need a win condition
* Level = new Game except for Agent Pane/Path pane
* Any current level can be a template for a new one
## Undo/Redo
* Possibly use internal memory in map?
## General Bugs
* Bounds checking
    * If you drag an agent off screen: returns to start
    * On drag, a trash icon appears, drag there to delete agent
    
* Add general, well stylized background for Map area
    * Image stays one scale, but the background scales with the change
    * 
# Requests for Game Engine
* Ability to clone current game state
    * Helps us with undoing
    * 
* Coordinate system/size of the map