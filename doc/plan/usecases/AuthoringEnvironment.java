import java.util.List;

public class AttributesPanel extends IDynamic {
    private IDynamic correspondingAgent;

    // GETTERS AND SETTERS THE SAME AS AGENT

}

public class Agent implements IDynamic {

    private Double health;
    private Double attack;
    private String image;
    private IDynamic correspondingAttributeObject;

    public Agent(Double health, Double attack, String image) {
        this.health = health;
        this.attack = attack;
        this.image = image;
    }

    public List<Obect> getAttributes() {
        List<Object> attributes = new List<>();
        attributes.add(health);
        attributes.add(attack);
        attributes.add(image);
        return attributes;
    }

    public void setAttributes(List<Object> attributes) {
        health = attributes.get(0);
        attack = attributes.get(1);
        image = attributes.get(2);
    }

    // ON PRESS OF THIS
    correspondingAttributeObect.setAttributes(getAttributes())

}

public class AgentCreator {

    private AuthorController myController;
    public AgentCreator() {}

    // ON CREATE BUTTON PRESS WITH VALUES OF HEALTH, ATTACK, AND IMAGE URL CHOSEN FROM DIALOGUE....
    IDynamic newAgent = new Agent(chosenHealth, chosenAttack, chosenImage);
        myController.defineAgent(newAgent);
    // This call will call the controller to call on the IState interface within the game engine to check and define the new agent type

}