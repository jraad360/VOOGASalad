CompSci 308: Final Project Analysis
===================

Design Review
=======

### Overall Design
Reflect on the design of the entire project by considering all of the APIs in the separate modules.
* Describe the overall design of the complete program(s):
    * What service does each sub-part's API provide and how do they work together?
    
        Data - Data provides Engine with the ability to create a SerializerSingleton that then creates an instance of an object with the Serializer interface. This interface provides the Game Engine (which manages the loading and saving of games) with methods such as save and load that enable the serialization and deserialization of the games state as well as everything it stores.
        
        Game Engine - Game engine provides the largest amount of external API calls. The Game Engine must be called upon by both the Player and Authoring Environment in order to display or make changes to a game State stored in the Game class. The Game object implements both an IGameDefinition interface (which provides Authoring with methods like saveState and loadState) and an IPlayerGame interface (which is used by player and contains methods like step(), play(), stop(), and loadState()). These methods are called in order to prompt Game on what it should be doing with the State it holds.
                      Game Engine also holds the entire structure of a game state (the State which holds several Levels which hold Attributes and Objectives and each hold a LevelState which hold several Agents, etc.). Each of these elements implements two interfaces, one for use by Authoring and one for use by Player. This enabled us to restrict each module to have access only to the methods they need.
        
        Player - The Player does not provide any API calls to any of the other modules since it only depends on Game Engine in order to display it.
        
        Authoring - Authoring does not provide any API calls either because it only relies on Game Engine in order to create a game with the structure stored in that module.
    
    * What is the high level design of each sub-part and how do they work together (i.e., what behavior, data, or resources does each part depends on from the others)?
            
        Data - Data does not depend on any other modules because it basically provides the Game Engine with the tools necessary to serialize and de-serialize a state. Its SerializerSingleton provides Game with a getInstance() method that returns a Serializer, which allows the user to call save and load.
        
        Game Engine - Game Engine has three main purposes.
        
        1. It depends on Data because it controls the saving and loading of game States. As described above, this is done through the save and load methods which call the Serializer interface's save and load methods.
            
        2. It contains the hierarchical structure of the State and its contents and controls the other modules' access to these components by implementing at two interfaces to be used by Authoring and Player. The interfaces meant for the Authoring Environment are quite extensive, giving Authoring a lot of control in the creation and modification of the components of the State. Although the Game Engine's GameFactory is used to create every component, the Authoring Environment needs access to calls such as IAgentDefinition.addActionDecision and IStateDefinition.addLevel in order to modify the game according to the user's input. Meanwhile the interfaces Player has access to are mostly just getters necessary to update the visual representation of the agent.
            
        3. It controls the progression of the game when it is running. It does this in Game's step method, which IPlayerGame gives Player access to. Every time the JavaFX step method is called, it call's IPlayerGame's step method that progresses the game.
        
        Player - Player's primary responsibility is to display what is occurring in the Game Engine. Because of this it naturally depends on the Game Engine. This is done by adding listeners to elements in the State held within Game, which Player has access to through IPlayerGame. In order to listen to the necessary values, it requires access to the current LevelState, which it accesses through IPlayerGame's getLevelState() method which returns an IPlayerLevelState. From there it can use IPlayerLevelState's methods such as getImmutableAgents().
        
        Authoring - Authoring has access to the State's internal hierarchy in order to enable the user to create a State. To do this, it needs access to Game Engine's GameFactory to create a State and its components. However, the Authoring Environment needs to be able to take in user input to modify the the State according to this input. Because of this, it relies on Action and Condition xml files that contain the necessary parameters to create them. State then needs to call methods from the interfaces intended for Authoring (IActionDefinition, IConditionDefinition, etc.) to change existing State components.
    
    * How is the design tied (or not) to the chosen game genre. What assumptions need to be changed (if anything) to make it handle different genres?
        
        While most of the design is not closely tied to tower defense games, I would say that one area that was greatly affected by the genre was the creation of ActionDecisions and their components. In tower defense games, most of the agents' actions depend on another agent, whether moving towards another agent, attacking another agent, launching a projectile at another agent. For this reason, we thought that an action should have access to the list of current agents, and that the parameter passed into the Action should be the Agent the Action is to be executed on. Conditions then would narrow down this list of current agent so that by the time the Agents reach the Action, only the Agents you want to use the Action on remain. While the tower defense genre did play a major part in arriving a this design, this does not mean that non-tower-defense games cannot be played. The Structure of ActionDecisions, even if planned around TD games, is flexible enough to enable many types of games.
   
    * What is needed to represent a specific game (such as new code or resources or asset files)?
   
        To represent a new game, the only thing necessary is the images you want to use. The files do not even need to be in the project resources as you can access the images from anywhere on your computer. However, if you want to add extensive features that are not possible to produce using the current Actions and Conditions, you can create more Actions and Conditions by (a) creating the class that extends Action or Condition, (b) adding the class's location to gameengine/resources/actions.properties or gameengine/resources/conditions.properties to enable Reflection, and (c) adding the necessary parameters needed to create the Action or Condition in the gameengine/resources/actions.xml or gameengine/resources/conditions.xml files. These steps enable the new Actions and Conditions to be created and allow the Authoring Environment to display them to the user properly.

* Describe two exported module packages that you did not implement (include specific examples with each question):
    * What helps (or hurts) this API to be flexible and encourage good design in users code?
   
        (1) The Data module does provide the necessary classes and methods to serialize and deserialize a game. However, one thing that hurts the Data API is its lack of unification under one interface. In order to serialize a game, you must first create a Serializer Singleton and then create a Serializer instance from it. After that, you use the resulting Serializer's save and load methods. However, this slightly hurts the flexibility of the Data module because it is now limited to to keeping these two classes structured as they are. Since the Serializer Singleton is only ever used one time to create the serializer that will then be used to load and save. I think it may have been a slightly better idea to hide the structure of these to classes into one class than implements an interface with simply the load and save methods. That way, the user does not have to worry about the creation of the Serializer Singleton first only to create the Serializer. Instead, the class that implements this interface would have access to a Serializer Singleton, perhaps in the constructor, and just provide the load and save methods, which is the only thing the Game Engine needs for this project.
        
        (2) The other two modules (Authoring and Player) do not have external APIs.
   
    * How does this API help (or hurt) encapsulating implementation decisions?
   
        (1) This API helps the encapsulation of the implementation decisions by giving the user access to the basic methods required, rather than by providing them the more specific methods that are combined and used to create the save and load methods. However, as discussed above, the need to now use two of the classes created in the Data Module in order to serve one function hurts the encapsulation. Since the project must refer to these two classes to simply save and load, the Data module is now restricted to keeping these two classes separate.
        
        (2) The other two modules (Authoring and Player) do not have external APIs.
   
    * Which of these public classes that are concrete (i.e., not abstract superclasses or interfaces) could be replaced by an abstraction and which do you think really need to be concrete and why?
   
        (1) The SerializerSingleton is concrete. I would introduce an interface in order to allow the creation of different kinds of Serializers. However, I am not sure I would even have a separate interface or the singleton. The class is so simple and short that it could just be written as a method in th e Serializer class, OR, better yet, not at all. We could allow the Serializer to decide when to do that privately and then continue to use the output to save.
        
        (2) The other two modules (Authoring and Player) do not have external APIs.
   
    * What have you learned about design (either good or bad) by reading your team mates' code?
   
        (1) The Data module is full of short, concise code. I learned a few things by looking at these small classes. First, I now understand what a singleton does and how exactly it works (which is quite simple). I also looked at the SerializerBase class which implements the Serializer class and saw exactly how the classes were serialized, although I realized not much work has to be done since most of it is done by XStream.
        
        (2) I have learned a lot of positive knowledge about using lambdas. I have not used too many in my past and sheer number of lambda's used made it so I have a reference to several. Looking at classes like AuthoringEnvironment also gave me a better idea of where these tools can be used.
   
### Your Design
Reflect on the coding details of your part of the project.
* Describe how your code is designed at a high level (focus on how the classes relate to each other through behavior (methods) rather than their state (instance variables)).

    Most of my code is contained within the hierarchy of classes that make up the contents of the State class such as Agent, Action, Condition, ActionDecision, etc.
    
    The State basically contains all the information that makes up a game to be run by the Game class. It contains Levels and a list of defined Agents which are the agents that are allowed to exist in the game. The most important method in State is the step method which calls the step method in the level, iterates through all the current Agents stored in its LevelState and calls their update method. This is the core of the game since most tower defense games (and most games in general) consist mostly of the interaction between the Agents on the screen. The Level also controls the addition and removal of Agents in its LevelState through its use of the eventMaster that listens for fired removal and spawn events.
    
    Within the Agent, there are several aspects that can be customized. Agents have Properties which are basically a String and a value (which can possibly be a String, Integer, or Double). These Properties allow Agents to be Identified according to different groups of Agent they belong to (such as "zombie" or "impermeable") or can be used to extract overwritten values like the speed of an Agent in a Movement Action or the name of the path to be followed in  a MoveOnPointPath Action.
    
    Agents can also be given ActionDecisions which determine how an Agent acts. One ActionDecision class exists which owns a singular Action and a list of Conditions. Each Agent owns a list of ActionDecisions which defines its repertoire of possible activities. An action is one simply behavior of an agent, for exmple moving towards an agent, or spawning another agent. A condition is a filter that decides when to run an action. For every frame, all of the conditions are evaluated, and actions called accordingly. 

    Flow of the code in gameengine.state:
        
    State step => Level step => update every Agent => iterate through each Agent's ActionDecisions => filter down the list of current Agents in the level to a smaller list as the list is passed from condition to condition. Finally the Action is performed on every Agent in the final list

* Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed if you had more time). Note, the checklist tool used in lab can be useful in automatically finding many checklist issues.

    Some issues include magic numbers of 180 in methods where I use trigonometry, converting from degrees to radians. This could be solved by assigning 180 to a variable or by using Math.toRadians(). Some of our Action code, especially within our Movement Actions, does not correctly handle exceptions. We definitely should have gone back to that sooner and properly addressed the exceptions. Sometimes a catch clause was set to catch any Exception even though it should have only caught the NullPointerException that could be created. I also see on the website that the clone() methods should not be overwritten like I did. Instead I could have used a copy constructor or copy factory. 

* Describe two features that you implemented in detail — one that you feel is good and one that you feel could be improved:
    * Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging.
    
        (1) One feature I implemented with Luke was the ability to change Property values in Actions or look at properties in Conditions in order to narrow down the list of Agents. This was a great addition because it allowed us to perform actions on Agents with only a certain Property value for example. The ability to alter properties also added a lot more possibilities because the speed of an Agent, for example, no longer has to be the one set in the movement's parameters. Instead, the movement's speed can depend on a speed Property which can be altered throughout the game by Property changing actions such as IncrementProperty. Property Conditions narrow down the list of Agents passed in by removing any Agents that don't have the desired Property and value. The Property Actions work by changing the value of a Property if it exists and often not doing anything if it doesn't. I hid the problem by catching these exceptions.
    
        (2) Although this feature does provide a lot of flexibility, one feature that I would change is how Conditions work. While it was cool to think of Conditions as a filter that narrow down the amount of Agents an Action will be potentially carried out on, it did complicate things. This is because not every Action is carried out on other Agents. Sometimes you want an action to happen once per step to oneself. Additionally, sometimes you want something to occur if something is true or if something is false. This led to special conditions like DoOnceWithSelf that had to be applied in certain situations in a certain order or Conditions like isNotColliding that returns one Agent if the given list of Agents does not have an Agent colliding with the base Agent. Overall, this structure just turned out to be kind of confusing for people who didn't have experience with it.
        
    * Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how did you hide or remove them?
    
        (1) There is no assumption made about the Properties being checked or modified. If the Property doesn't exist within an Agent and is being inspected in a Condition, the Agent is removed from the list of Agents. Same with the modifying of Properties. In this case, if the value of a Property is attempted to be changed, but it doesn't even exist, then we will continue like nothing happened.
    
        (2) This structure of the Conditions affects the structure of the Action and vice-versa. The Conditions came to be as they are because we decided that an Agent should be passed into an Action's execute method. However, this is not the case. If we could separate Conditions into true-false conditions that determine whether an Action should happen and another aspect that selects who should be acted upon if it meant to affect another Agent, constructing ActionDecisions would be much simpler since the order wouldn't matter as much anymore and we would no longer be trying to force true-false conditions to work with conditions responsible for selecting the recipient of the Action. We would then no longer have to assume that every Agent needs an Agent passed in (right now if they don't, they must still be passed in a dummy Agent).
        
### Flexibilty
Reflect on what makes a design flexible and extensible.
* Describe how the final API that you were most involved with balances
    * Power (flexibility and helping users to use good design practices) and
    
        I worked more on Game-Engine's Authoring API. This API consists of the IGameDefinition as well all the I___Definition classes create for the components of the State. These API calls do help the user to use good design practices because they limit the Authoring Environment to only the methods they should be using. Additionally, there is much flexibility with these as well since Authoring is freed from the implementation of the method through the interface's 
    
    * Simplicity (ease of understanding and preventing users from making mistakes)
    
        I would say that Game's Authoring API is very simple to use, consisting of methods like load and state. When looking at the interfaces for the components of the State though, many more methods are provided as the Authoring Environment must have access to the ability to modify Agents and their ActionDecisions.
    
* Describe two features that you did not implement in detail — one that you feel is good and one that you feel could be improved:
    * What is interesting about this code (why did you choose it)?
        
        (1) The implementation of Objectives interests me because it should work very similarly to how our Actions and Conditions work, except that it should have access to the whole state rather than just to the Agents.
    
        (2) The creation of the GameFactory is interesting because of how it used injection to give certain elements access to necessary things.
        
      * What classes or resources are required to implement this feature?
          
        (1) To implement this feature, you need an ObjectiveCondition and an ObjectiveOutcome. If you want to create your ow, you must add their class locations to the objective-conditions.properties and objective-outcomes.properties to enable reflection and add the required parameters to the objective-conditions.xml and objective-outcomes.xml files so that Authoring Environment can display the required parameters to the user.
    
        (2) To implement this feature, you simply need access to all of the objects that you want to factory to be able to make (Game, State, Agent, ActionDecision, etc.). The GameFactory is also crucial in the communication between Authoring and Engine. It checks whether or not the inputs given for the creation of an element are valid. It also needs reflection to create these elements. So, it will need access several xml files containing the parameters required for creation and several properties files containing the location of every class to possibly be created.
        
      * Describe the design of this feature in detail (what parts are closed? what implementation details are encapsulated? what assumptions are made? do they limit its flexibility?).
         
        (1) An Objective is used to make a change to the game once something has happened - such as switching a level when a certain scored has been reached. This is done by matching an ObjectiveOutcome with an ObjectiveCondition. Any previously created classes of these would be closed to the implementation fo new classes, but the objective-conditions.properties, objective-outcomes.properties, objective-conditions.xml, and objective-outcomes.xml files NEED to be updated in order for Authoring to be able to create one. One assumption made is that nobody will ever want to mix two of these (like both of these or either must occur for the outcome to be carried out). This is very limiting because even if you have two conditions that would work together very well, you have to make a third condition that mixes the two.
    
        (2) The GameFactory class itself is not closed to any new features; for every new feature to be introduced and enable creation by the factory, you must create the new method to instantiate it. A lot of cool things like the injection of an eventMaster and of baseAgents is encapsulated in the factory and crucial for other parts of the project to work. Assumptions made are that the xml and properties files representing each of the possible elements that can be created with be accurate and up-to-date. If not, you can expect a lot of reflection exceptions.
        
      * How extensible is the design for this feature (is it clear how to extend the code as designed or what kind of change might be hard given this design)?
            
        (1) I would say that this design is not very extensible. Because Objectives only have one ObjectiveCondition, you will need to create a new one if you want to maybe mix the conditions under which the outcome should occur.
    
        (2) I would say that this design is pretty extensible. Let's say we want to add a new element to the game to be created by the factory. Yes, you would need to go back into the class and write the code, but it is very straightforward. You can just follow the steps of several of the other methods.
        
### Alternate Designs
Reflect on alternate designs for the project based on your analysis of the current design or project discussions.
* Describe an API that changed over the course of the project:
    * Why was it changed and how much impact did the changes have on other parts of the project?
    
        One API that changed throughout the course of the project was Game Engine's Player API. It was originally thought that Game would be running its own game loop and that Players would connect to it. However, it ended up being much easier to take advantage of JavaFX's step method instead and allowing the Player control the stepping through the game.
        This decision to change caused us to go from having IPlayerGame (implemented by Game) no longer use run (which would have simply started a loop within Game) and instead simply provide Player with a step method that would be called from within JavaFX's step method.
    
    * How were the changes discussed and the decisions ultimately made?
    
        This was discussed in detail when we were trying to figure out how we would be able to take in user input from Player if we were trapped in a loop running continuously within Game.
        We discussed keeping the control of the loop within the engine and possibly using threads to allow us to simultaneously listen for user input on the Player side. However, in the end we decided that it was much simpler to have Player call our step method.
    
    * Do you feel the changes improved the API (or not) (i.e., did they make the API more abstract or more concrete? more encapsulated or not? more flexible or not?)?
        
        I don't necessarily think the change improved the API, but it did make the process easier since it allowed us to take advantage of JavaFX. It did naturally make the API more concrete since we now had to provide Player with our step method and delegate to them the task of implementing what we previously had in the run method. This API change made it less encapsulated since the step method was no long hidden in this more general run method.
    
* Describe two design decisions made during the project in detail:
    * What alternate designs were considered?
                
        (1) At one point, we were deciding how we wanted the engine communicate with Player: through listeners on each Agent or through the passing of the entire State to Player.
    
        (2) We pondered between staying at our then-current structure of ActionConditions vs. switching to a new structure that implemented our Conditions.
        
    * What are the trade-offs of the design choice (describe the pros and cons of the different designs)?
               
        (1) The only reason passing the entire state was even considered was because it would be very easy to do. That is the only pro. Some cons include that it may be very slow since we are replacing the state every step. Another con is simply that is not good design. Meanwhile, applying listeners to the agents would be much tougher. Not only would we need to attach listeners to the Agents, but we would need to listen to additions and removals of Agents. However, it would be more efficient and just better practice.
    
        (2) The ActionDecisions we had at the time held the Action to be carried out and the class itself would decide the Agents that the Action would be applied to. Basically, it was like our current structure, but with one, hyper-specific Condition. The only pro in this was that we would not have to make a big switch and replace all the classes we had made to that point. The con to staying with the current structure was that we would need to create countless classes, with each one enabling, one very specific behavior. The pro of switching was that we could then mix and match all the different Conditions and create new behaviors while avoiding having to create more classes. Using multiple Conditions was much more flexible. The con was having to scrap all our previous work and move towards a new design relatively close to the midpoint demo.
        
    * Which would you prefer and why (it does not have to be the one that is currently implemented)?
            
        (1) I definitely preferred the use of several listeners on the different parts that needed to be updated at each step. It just doesn't make sense to me to have to replace everything on the screen just because one tiny thing in the State changed. That just seems really inefficient and wasteful to me.
    
        (2) I prefer the system we currently have because, even though our current structure of Conditions narrowing down the list of Agents brings some problems, it is WAY better than basically just having one of these Conditions.
        
### Conclusions
Reflect on what you have learned about designing programs through this project, using any code in the project as examples:
* Describe the best feature of the project's current design and what did you learn from reading or implementing it?
    
    I think the best feature of the project's design is how the creation of State components is handled. I think the GameFactory, which does this, is very well-designed. Aside from instantiating each of the State components, it also solved issues such as how do actions that spawn and destroy agents have access to eventMaster that controls the addition and removal of agents? Also how will we pass the baseAgent to an Action that requires it. In the end, David took care of this with his GameFactory, using it to inject Actions and Levels with the same eventMaster as well as to inject the baseAgent into Actions and Conditions with the IRequiresBaseAgent. I think this was a pretty cool decision that solved a lot of problems we otherwise would have dealt with less efficiently. Through the factory, I learned about the "injection" of necessary objects into other objects through a factory.

* Describe the worst feature that remains in the project's current design and what did you learn from reading or implementing it?

    The worst feature was the Authoring Environment's saving of the defined agents in the State being created. There was a bug in the authoring environment that resulted in edited Agents not properly replacing themselves. As a result, the list of defined agent would simply grow and placed agents would result in the creation of the original defined Agent, not the new, edited one. This was a huge issue that would occur only when a state was saved and loaded again. After looking at the code, I realized that this was to be taken care of in the AuthoringEnvironment class. However, instead of writing lambas in the AuthoringEnvironment to mysteriously act on the defined agents, it would perhaps be more intuitive and better design to have the defined agents handle their own removal. That way, it would be easier to find the code causing the problems, rather than attempting to read a class that uses things such as text fields to check and the definedAgent. 

* Consider how your coding perspective and practice has changed during the semester:
    * What is your biggest strength as a coder/designer?
    
        This was a hard question, and I know this sounds like a generic answer, but I think my biggest strength as a coder is in my ability to come up with flexible ideas to solve issues. Several times during discussions I have come up with ideas that change the way we think about a problem when I've found that we have been too focused on a certain line of thinking. For example, we were discussing early on how much of a pain it would be implement our previous iteration of an ActionDecision, which was basically the matching of an Action with one hyper-specific condition. This resulted in us having to create many specific and under-utilized classes. For some reason we fell into the trap of thinking that this was set and I was the one to suggest the possibility of giving Actions several Conditions to follow. Another example is when I was listening in on two team members discussing how path-following agents would be implemented if the paths would be custom to each level. How could we pass in different paths to follow for different instances of the same Agent? They were going along the lines of giving all agents of that kind access to all of the paths and then hard-coding the path they should follow as that with a specific index. That's when I suggested the use of Properties to specify which paths to follow, as well as the possibility of a Property-injection spawner that would enable the spawner to add Properties differing from the default defined Agent's properties (which would allow spawners to select the path an Agent should follow). I am not sure why but I often end up finding seemingly obvious solutions to problems before the rest, which is a strength since it avoids us getting stuck with a poor design choice we otherwise would've settled on. 
    
    * What is your favorite part of coding/designing?
    
        My favorite part of coding/designing (apart from just seeing the project run correctly after so much time working on it) is in making design decisions. Often times, I find myself pondering the best way to make something abstract enough to be reusable and flexible while also not making it so abstract that it is hard to follow or understand. I think the planning is half of the fun because as you come up with different use cases, you must try to go towards an abstract design that will accommodate all of the relevant cases. Finally stumbling across a good idea to solve this issue is always a good feeling. 
    
    * What are two specific things you have done this semester to improve as a programmer this semester?
        
        This semester I have been a very active contributor in all of my teams' meetings, often challenging design propositions I didn't agree with. While this would lead to lengthier arguments, it also resulted in both sides further explaining their ideas in detail and a deeper pondering of the pros and cons of both propositions. I think led to a lot more insight to design ideas that I would have never thought of myself. This was a very helpful learning tool, as I learned quite  a lot this way.
        
        Another thing I have done is to place emphasis on the lab assignments. The lab assignments gave us time to learn new and very useful concepts that could benefit us in the upcoming projects. While it could have been easy at times to coast through it, I always tried to give my utmost attention to the assignments at hand. This is true especially for the labs where we cloned a project and were given a task to improve its design. Those proved to be the most helpful. 
        