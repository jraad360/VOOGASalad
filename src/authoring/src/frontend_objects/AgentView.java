package frontend_objects;

import javafx.scene.image.ImageView;
import util.AuthoringContext;

public abstract class AgentView extends ImageView {

    /**
     * Extends frontend_objects.DraggableView for dragging the tower image into the map
     * @author Mary Stuart Elder and Eric Lin
     */

    private AuthoringContext context;

    public AgentView(String url){
        super(url);
        this.setFitWidth(getContext().getDouble("AgentSize"));
        this.setFitHeight(getContext().getDouble("AgentSize"));
    }
    public AgentView(AuthoringContext authoringContext){
        super();
        this.setFitWidth(getContext().getDouble("AgentSize"));
        this.setFitHeight(getContext().getDouble("AgentSize"));
        //selected.set(false);
        this.context = authoringContext;
        //selected.addListener((observable, oldValue, newValue) -> {
        //});
    }

    public AgentView(AuthoringContext authoringContext, String url) {
        super(url);
        this.context = authoringContext;
        this.setFitWidth(getContext().getDouble("AgentSize"));
        this.setFitHeight(getContext().getDouble("AgentSize"));
    }

    protected AuthoringContext getContext() {
        return context;
    }

}
