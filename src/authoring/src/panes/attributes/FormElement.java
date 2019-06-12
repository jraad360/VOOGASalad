package panes.attributes;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import panes.AuthoringPane;
import util.AuthoringContext;
import util.AuthoringUtil;

import java.util.function.Consumer;

// TODO: add loadFromExisting() as a method to this since it's used in almost every single subclass

public abstract class FormElement extends AuthoringPane {

    private Button deleteButton;

    /**
     * Creates a new form element. It defaults to undeletable by not adding the delete button to a visible node.
     * @param context the context that makes relevant instances available
     */
    public FormElement(AuthoringContext context) {
        super(context);
        deleteButton = AuthoringUtil.createSquareImageButton(
                getContext().getString("TrashImageFile"),
                getContext().getInt("ButtonSize"),
                null);
    }

    /**
     * Set the action that occurs when the delete button is clicked/activated.
     * @param onDelete the lambda method that fires when the delete button is clicked/activated
     */
    public void setOnDelete(EventHandler onDelete) {
        deleteButton.setOnAction(onDelete);
    }

    /**
     * Add the delete button to a visible node's children to make it visible and make this form element deletable.
     * Remove it from anything visible to make it uninteractable and therefore this form element undeletable.
     * @param accessMethod the Consumer that allows accessing of the delete button
     */
    protected void accessDeleteButton(Consumer<Button> accessMethod) {
        accessMethod.accept(deleteButton);
    }

    /**
     * Packages the data in this form element.<br>
     * <b>Important:</b> When implementing this method, return the specific data type that is intended for packaging.
     * @return the packaged data
     */
    public abstract Object packageData();
}
