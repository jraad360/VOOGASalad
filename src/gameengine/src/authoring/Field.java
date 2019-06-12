package authoring;


public class Field {

    private String name;
    private String type;

    public Field(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Get the name of the field.
     * @return the name of the field as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Get the type of the field.
     * @return the type of the field as a string
     */
    public String getType() {
        return type;
    }
}
