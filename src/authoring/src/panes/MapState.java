package panes;

import authoring.ILevelDefinition;
import frontend_objects.AgentView;
import frontend_objects.DraggableAgentView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import state.AgentReference;
import util.AuthoringContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MapState {

    private String backgroundURL;
    private List<DraggableAgentView> agents;
    private List<Path> paths;
    private SimpleIntegerProperty selectCount = new SimpleIntegerProperty();
    private AuthoringContext context;

    // FRONT END STUFF NOT BACK-END. ONLY THING THIS NEEDS TO HOLD IS THE SELECTCOUNT.
    public MapState(AuthoringContext context, String backgroundURL, List<DraggableAgentView> agents, List<Path> paths) {
        this.context = context;
        this.backgroundURL = backgroundURL;
        this.agents = agents;
        selectCount.set(0);
        this.paths = paths;
    }

    public MapState(MapState other, MapPane map) {
        this.context = other.context;
        this.backgroundURL = other.backgroundURL;
        this.agents = new ArrayList<>();
        List<? extends ILevelDefinition> levels = context.getState().getLevels();
        for (AgentReference ref : levels.get(levels.size() - 1).getCurrentAgents()) {
            this.agents.add(new DraggableAgentView(context, ref));
        }
        for (DraggableAgentView agent : this.agents) {
            agent.setMouseActionsForDrag(map);
        }
        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setMouseActionsForDrag(map);
            this.agents.get(i).setTranslateX(other.agents.get(i).getTranslateX());
            this.agents.get(i).setTranslateY(other.agents.get(i).getTranslateY());
        }
        this.paths = new ArrayList<>();
        for(Path path: other.paths){
            this.paths.add(new Path(path.getID(), path.getPathLocations()));
        }
        selectCount.set(0);
    }


    public void addToPaths(Path path){
        paths.add(path);

    }

    public void removePath(Path path){
        if(paths.contains(path)){
            paths.remove(path);
        }
    }

    public List<Path> getPaths(){
        return paths;
    }

    public void setBackgroundURL(String url) {
        backgroundURL = url;
    }

    public String getBackgroundURL() {
        return backgroundURL;
    }

    public void addToAgents(DraggableAgentView agent) {
        agents.add(agent);
    }

    List<DraggableAgentView> getAgents() {
        return agents;
    }

    int getAgentCount() {
        return agents.size();
    }

    public void removeAgent(AgentView agent) {
        agents.remove(agent);
    }

    public void accessSelectCount(Consumer<SimpleIntegerProperty> accessListener) {accessListener.accept(selectCount);}

    public void selectAgents(Ellipse lassoEllipse){
        selectCount.set(0);
        for(DraggableAgentView agent: agents){
            agent.setSelect(lassoSelects(lassoEllipse, agent));
        }
        for (DraggableAgentView agent: agents) {
            if(agent.getSelect()) {
                selectCount.set(selectCount.get() + 1);
            }
        }
    }

    private boolean lassoSelects(Ellipse lassoEllipse, AgentView agent){
        return lassoEllipse.intersects(agent.getBoundsInParent());
    }

    public void updateMap(MapPane map) {
        map.clearMap();
        map.setMapImage(map.getLevel(), backgroundURL, false);

        for (AgentView agent : agents) {
            map.getMapPane().getChildren().add(agent);
        }
        for(Path path: paths){
            for(Line l: path.getLines()){
                if(!map.getMapPane().getChildren().contains(l))
                    map.getMapPane().getChildren().add(l);
            }
            for(Circle c: path.getPoints()){
                if(!map.getMapPane().getChildren().contains(c))
                    map.getMapPane().getChildren().add(c);
            }
        }
    }


}
