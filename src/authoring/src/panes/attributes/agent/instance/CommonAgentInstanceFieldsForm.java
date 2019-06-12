package panes.attributes.agent.instance;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import panes.ConsolePane;
import panes.attributes.FormElement;
import panes.attributes.LabeledTextField;
import util.AuthoringContext;
import util.AuthoringUtil;

public class CommonAgentInstanceFieldsForm extends FormElement {

    private Label nameLabel;
    private LabeledTextField xField, yField, directionField;

    public CommonAgentInstanceFieldsForm(AuthoringContext context) {
        super(context);
        initCommonFields();
    }

    /**
     * <b>Do <em>not</em> use this method for this form element.</b> Rather, use the field-specific methods to get the data.
     * @return null
     */
    @Override
    public Object packageData() {
        return null;
    }

    public String getName() {
        return nameLabel.getText();
    }

    public double getX() {
        try {
            return Double.parseDouble(xField.packageData());
        } catch (NumberFormatException e) {
            return getContext().getDouble("ErrorDouble");
        }
    }

    public double getY() {
        try {
            return Double.parseDouble(yField.packageData());
        } catch (NumberFormatException e) {
            return getContext().getDouble("ErrorDouble");
        }
    }

    public double getDirection() {
        try {
            return Double.parseDouble(directionField.packageData());
        } catch (NumberFormatException e) {
            return getContext().getDouble("ErrorDouble");
        }
    }

    public void loadFromExisting(String name, double x, double y, double direction) {
        nameLabel.setText(name);
        xField.loadFromExisting("" + x);
        yField.loadFromExisting("" + y);
        directionField.loadFromExisting("" + direction);
    }

    /**
     * Initialize fields common to all agents.
     * @return a node that contains those common fields
     */
    private void initCommonFields() {
        VBox overallVBox = new VBox();
        getContentChildren().add(overallVBox);

        nameLabel = new Label();
        overallVBox.getChildren().add(nameLabel);

//        HBox xyHBox = new HBox();
//        overallVBox.getChildren().add(xyHBox);
        xField = new LabeledTextField(getContext(), getContext().getString("x"));
        yField = new LabeledTextField(getContext(), getContext().getString("y"));
        xField.accessContainer(overallVBox.getChildren()::add);
        yField.accessContainer(overallVBox.getChildren()::add);

        directionField = new LabeledTextField(getContext(), getContext().getString("Direction"));
        directionField.accessContainer(overallVBox.getChildren()::add);
    }
}
