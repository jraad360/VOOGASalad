package panes.attributes.agent.define;

import authoring.IActionDecisionDefinition;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import panes.attributes.FormElement;
import util.AuthoringContext;

public class ActionDecisionFormElement extends FormElement {

    private ActionFormElement action;
    private ConditionsForm conditionsForm;

    public ActionDecisionFormElement(AuthoringContext context) {
        super(context);
        init();
    }

    @Override
    public IActionDecisionDefinition packageData() {
        return getContext().getGameFactory().createActionDecision(action.packageData(), conditionsForm.packageData());
    }

    private void init() {
        TitledPane actionDecisionTitledPane = new TitledPane();

        // Add trash button to label of TitledPane
        // Used https://stackoverflow.com/questions/49085827/javafx-titledpane-with-button
        // and http://javawiki.sowas.com/doku.php?id=javafx:titledpane-with-checkbox
        HBox titleHBox = new HBox();
        Label titleLabel = new Label();
        titleHBox.getChildren().add(titleLabel);
        accessDeleteButton(titleHBox.getChildren()::add);
        actionDecisionTitledPane.setGraphic(titleHBox);

        VBox actionDecisionVBox = new VBox();
        actionDecisionTitledPane.setContent(actionDecisionVBox);

        // Action
        //"Poop", "Defecate", "Utilize one's anus", "Dispense of fecal matter in a pleasurable way");
        action = new ActionFormElement(getContext());
        action.addSelectedNameListener(((observableValue, oldString, newString) ->
                titleLabel.setText(String.format(getContext().getString("ActionDecisionFormTitle"),
                        getContext().getString("ActionDecision"), newString))));
        action.accessContainer(actionDecisionVBox.getChildren()::add);

        // Conditions
        conditionsForm = new ConditionsForm(getContext());
        conditionsForm.accessContainer(actionDecisionVBox.getChildren()::add);

        getContentChildren().add(actionDecisionTitledPane);
    }

    public void loadFromExisting(IActionDecisionDefinition actionDecision) {
        action.loadFromExisting(actionDecision.getAction());
        conditionsForm.loadFromExisting(actionDecision.getConditions());
    }

}
