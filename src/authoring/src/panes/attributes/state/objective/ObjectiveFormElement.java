package panes.attributes.state.objective;

import authoring.IObjectiveConditionDefinition;
import authoring.IObjectiveDefinition;
import authoring.IObjectiveOutcomeDefinition;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import panes.attributes.FormElement;
import util.AuthoringContext;

public class ObjectiveFormElement extends FormElement {

    private ObjectiveConditionFormElement condition;
    private ObjectiveOutcomeFormElement outcome;

    public ObjectiveFormElement(AuthoringContext context) {
        super(context);
        init();
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

        VBox vBox = new VBox();
        actionDecisionTitledPane.setContent(vBox);

        condition = new ObjectiveConditionFormElement(getContext());
        condition.addSelectedNameListener(((observableValue, oldString, newString) ->
                titleLabel.setText(newString)));
        condition.accessContainer(vBox.getChildren()::add);

        outcome = new ObjectiveOutcomeFormElement(getContext());
        outcome.accessContainer(vBox.getChildren()::add);

        getContentChildren().add(actionDecisionTitledPane);
    }

    public void loadFromExisting(IObjectiveDefinition objective) {
        condition.loadFromExisting(objective.getCondition());
        outcome.loadFromExisting(objective.getOutcome());
    }

    /**
     * Packages the data from this form into an IObjectiveDefinition
     * @return the IObjectiveDefinition containing the data from this form.
     */
    @Override
    public IObjectiveDefinition packageData() {
        IObjectiveConditionDefinition conditionDef = condition.packageData();
        IObjectiveOutcomeDefinition outcomeDef = outcome.packageData();
        if (conditionDef == null || outcomeDef == null) {
            return null;
        }
        return getContext().getGameFactory().createObjective(conditionDef, outcomeDef);
    }

}
