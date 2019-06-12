package panes.attributes;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import util.AuthoringContext;
import util.AuthoringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class FormList extends FormElement {

    private Button addButton;
    private VBox vBox;
    private List<FormElement> elements;

    /**
     * Creates a new form list. It defaults to undeletable by not adding the delete button to a visible node.
     *
     * @param context the context that makes relevant instances available
     */
    public FormList(AuthoringContext context) {
        super(context);
        addButton = AuthoringUtil.createSquareImageButton(
                getContext().getString("AddButtonImageFile"),
                getContext().getDouble("ButtonSize"),
                null);
        vBox = new VBox();
        getContentChildren().add(vBox);
        elements = new ArrayList<>();
    }

    /**
     * Set the action that occurs when the add button is clicked/activated.
     * @param onAdd the lambda method that fires when the add button is clicked/activated
     */
    public void setOnAdd(EventHandler onAdd) {
        addButton.setOnAction(onAdd);
    }

    /**
     * Add the add button to a visible node's children to make it visible and make this list addable to.
     * Remove it from anything visible to make it uninteractable and therefore this form element unexpandable.
     * @param accessMethod the Consumer that allows accessing of the add button
     */
    protected void accessAddButton(Consumer<Button> accessMethod) {
        accessMethod.accept(addButton);
    }

    /**
     * Add a form element to the end of this form list. Adds both visibly and in the list of form elements.
     * @param e the form element to add
     */
    protected void add(FormElement e) {
        elements.add(e);
        e.accessContainer(vBox.getChildren()::add);
    }

    /**
     * Remove a form element from the end of this form list. Removes both visibly and in the list of form elements.
     * @param e the form element to remove
     */
    protected void remove(FormElement e) {
        elements.remove(e);
        e.accessContainer(vBox.getChildren()::remove);
    }

    /**
     * Iterate through the form elements list of this form list.
     * @param iterateMethod the Consumer that allows iterating through the elements list
     */
    protected void iterateElements(Consumer<FormElement> iterateMethod) {
        for (FormElement e : elements) {
            iterateMethod.accept(e);
        }
    }

    /**
     * Access the VBox from outside of this class.
     * @param accessMethod the Consumer that allows accessing of the VBox
     */
    protected void accessVBox(Consumer<VBox> accessMethod) {
        accessMethod.accept(vBox);
    }

}
