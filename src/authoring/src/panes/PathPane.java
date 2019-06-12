package panes;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import panes.tools.PathPenTool;
import util.AuthoringContext;
import util.AuthoringUtil;

import java.util.HashMap;
import java.util.List;

public class PathPane extends AuthoringPane {

    private ScrollPane scrollPane;
    private ObservableList<Path> pathOptions;
    private PathPenTool pen;
    private VBox fullBox;
    private VBox pathsBox;
    private HashMap<Path, HBox> pathDisplayMap;

    public PathPane(AuthoringContext context, MapPane otherMap, Scene otherScene, ObservableList<Path> paths, PathPenTool toolbarPen) {
        super(context);
        pathDisplayMap = new HashMap<>();
        pen = toolbarPen;
        setNewPathList(paths);
        initializePathDisplays();
    }

    // Code adapted from https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ListChangeListener.Change.html
    private void onPathListChange(ListChangeListener.Change<? extends Path> c){
        pathDisplayMap.clear();
        pathsBox.getChildren().clear();
        for(Path path: pathOptions){
            addPathRow(path);
        }
    }

    public void setNewPathList(ObservableList<Path> newPathList){
        //pen.setNewPathList(newPathList);
        if(pathOptions != null){
            pathDisplayMap.clear();
            pathsBox.getChildren().clear();
        }
        pathOptions = newPathList;
        pathOptions.addListener((ListChangeListener<Path>) c -> onPathListChange(c));
        initPathsBox();
    }

    private void initializePathDisplays(){
        fullBox = buildFullBox();

        pathsBox = new VBox();
        scrollPane = buildScrollPane(pathsBox);
        pathsBox.setFillWidth(true);
        initPathsBox();

        var title = new Text(getContext().getString("PathsTitle"));
        title.setId(getContext().getString("PathsTitleId"));
        fullBox.getChildren().addAll(title, scrollPane);
        getContentChildren().add(fullBox);
    }

    private void initPathsBox(){
        for(Path p: pathOptions){
            addPathRow(p);
        }
    }

    public void removePathRow(Path path){
        var row = pathDisplayMap.get(path);
        pathsBox.getChildren().remove(row);
    }

    public void addPathRow(Path path){
        var row = new HBox();
        var id = path.getID();
        var pathLabel = new Label(/*getContext().getString("PathLabel") + */id);
        pathLabel.setId(getContext().getString("PathLabelId"));

        var addButton = AuthoringUtil.createSquareImageButton(getContext().getString("PenFile"), getContext().getDouble("ButtonSize"), e -> pen.enableToolWithPath(id));
        var deleteButton = AuthoringUtil.createSquareImageButton(getContext().getString("PathTrashFile"), getContext().getDouble("ButtonSize"), e -> pen.removePathFromID(id));
        row.getChildren().addAll(pathLabel, addButton, deleteButton);
        pathDisplayMap.put(path, row);
        pathsBox.getChildren().add(row);
    }

    // TODO: sets bounds for dragging points to be the Map

    private VBox buildFullBox(){
        var box = new VBox();
        box.getStylesheets().add(getContext().getString("PathPaneStyle"));
        box.setId(getContext().getString("PathGeneralId"));
        box.setPrefSize(getContext().getDouble("PathPaneWidth"), getContext().getDouble("ConsoleHeight"));
        return box;
    }

    private ScrollPane buildScrollPane(VBox content){
        var pane = new ScrollPane();
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setContent(content);
        return pane;
    }

    @Override
    public void updateSize(double width, double height) {
        fullBox.setPrefSize(width, height);
    }
}
