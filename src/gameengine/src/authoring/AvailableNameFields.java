package authoring;

import java.util.List;

public class AvailableNameFields {

    private String name;
    private List<Field> fields;

    public AvailableNameFields(String name, List<Field> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public List<Field> getFields() {
        return fields;
    }
}
