public Interface IPathRegion {

/**
 * Allows other modules in the authoring environment to define the path of travel
 */
public void setPath(Path path);


/**
 * Allows other modules within the environment to obtain the path
 */
public void getPath(Path path);


}
//// These are the different methods we had in game engine that now are internal to you. If you already have methods that do these things then delete them.
//
///**
// * Takes in an agent object and adds it to the current state of the game (adds it to the list of agents available to the Player)
// */
//        void defineAgent(Agent agent);
//
//        /**
//         * Takes in a rule object and adds it to the current state of the game
//         */
//        void setRule(Rule rule);
//
//        /**
//         * Takes in an object object and adds it to the current state of the game
//         */
//        void defineObjective(Objective objective);
//
//        }