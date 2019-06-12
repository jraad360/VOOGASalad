package panes.attributes.agent.define;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import panes.ConsolePane;
import panes.attributes.FormElement;
import util.AuthoringContext;
import util.AuthoringUtil;

public class CommonAgentFieldsForm extends FormElement {

    private ChoiceBox<String> agentTypeField;
    private TextField nameField, widthField, heightField;
    private ImageView imageField;
    private String imageUrl;

    public CommonAgentFieldsForm(AuthoringContext context) {
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
        return nameField.getText();
    }

    public int getWidth() {
        try {
            return Integer.parseInt(widthField.getText());
        } catch (NumberFormatException e) {
            return getContext().getInt("ErrorInt");
        }
    }

    public int getHeight() {
        try {
            return Integer.parseInt(heightField.getText());
        } catch (NumberFormatException e) {
            return getContext().getInt("ErrorInt");
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void loadFromExisting(String name, int width, int height, String imageUrl) {
        nameField.setText(name);
        widthField.setText("" + width);
        heightField.setText("" + height);
        imageField.setImage(new Image(AuthoringUtil.switchAuthorEngineURL(imageUrl, true)));
        this.imageUrl = AuthoringUtil.switchAuthorEngineURL(imageUrl, true);
    }

    /**
     * Initialize fields common to all agents.
     * @return a node that contains those common fields
     */
    private void initCommonFields() {
        GridPane gridPane = new GridPane();

        // Agent type
        Label typeLabel = new Label(getContext().getString("Type"));
        agentTypeField = new ChoiceBox<>();
        agentTypeField.getItems().addAll(getContext().getString("Custom"));
        agentTypeField.getSelectionModel().selectFirst();
        gridPane.add(typeLabel, 0, 0);
        gridPane.add(agentTypeField, 1, 0, 3, 1);


        // Name
        Label nameLabel = new Label(getContext().getString("Name"));
        nameLabel.setId(getContext().getString("LabelStyleId"));
        nameField = new TextField();
        nameField.setPromptText(getContext().getString("NamePrompt"));
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1, 3, 1);


        // Image
        StackPane imageStackPane = new StackPane();
        imageField = new ImageView(getContext().getString("DefaultImageName"));
        imageField.setFitWidth(getContext().getDouble("ImageFieldSize"));
        imageField.setFitHeight(getContext().getDouble("ImageFieldSize"));
        imageField.setPreserveRatio(true);
        imageField.setSmooth(true);
        imageField.setCache(true);
        imageUrl = "";//imageField.getImage().getUrl();

        AnchorPane imageLabelAnchorPane = new AnchorPane();
        Label chooseImageLabel = new Label(getContext().getString("ChooseImage"));
        AnchorPane.setBottomAnchor(chooseImageLabel, 0.0);
        Rectangle chooseImageLabelBackground = new Rectangle(imageField.getFitWidth(), imageField.getFitHeight() / 5,
                new Color(0, 0, 0, 0.5));
        AnchorPane.setBottomAnchor(chooseImageLabelBackground, 0.0);
        imageLabelAnchorPane.getChildren().addAll(chooseImageLabelBackground, chooseImageLabel);

        imageStackPane.getChildren().addAll(imageField, imageLabelAnchorPane);
        imageStackPane.setOnMouseClicked(e -> chooseAgentImage());
        gridPane.add(imageStackPane, 0, 2, 2, 2);


        // Width and height
        Label widthLabel = new Label(getContext().getString("Width"));
        widthLabel.setId(getContext().getString("LabelStyleId"));
        widthField = new TextField();
        widthField.setPromptText(getContext().getString("WidthHeightPrompt"));
        Label heightLabel = new Label(getContext().getString("Height"));
        heightLabel.setId(getContext().getString("LabelStyleId"));
        heightField = new TextField();
        heightField.setPromptText(getContext().getString("WidthHeightPrompt"));
        gridPane.add(widthLabel, 2, 2);
        gridPane.add(widthField, 3, 2);
        gridPane.add(heightLabel, 2, 3);
        gridPane.add(heightField, 3, 3);

        getContentChildren().add(gridPane);
    }

    private void chooseAgentImage() {
        AuthoringUtil.openFileChooser(
                getContext().getString("ImageFile"), AuthoringUtil.IMAGE_EXTENSIONS, false, null,
                file -> {
                    imageUrl = file.toURI().toString();
                    imageField.setImage(new Image(imageUrl));
                },
                () -> getContext().displayConsoleMessage(getContext().getString("AgentImageError"), ConsolePane.Level.ERROR)
        );
    }
}
