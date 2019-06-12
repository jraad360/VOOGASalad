package state;

import state.agent.IPlayerAgent;
import state.attribute.Attribute;
import state.attribute.IPlayerAttribute;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * @Author:Luke_Truitt
 * @author David Miron
 * This is the version of LevelState used by Player
 */
public interface IPlayerLevelState {

    List<IPlayerAgent> getImmutableOptions();
    List<IPlayerAgent> getImmutableAgents();
    List<IPlayerAttribute> getImmutableAttributes();
    boolean addAgentFromStore(int index, double x, double y);
/*
    void addAttribute(Attribute attribute);
    void removeAttribute(Attribute attribute);*/
    void addPropertyChangeListener(PropertyChangeListener listener);

     String getBackgroundImageURL();
    void setBackgroundImageURL(String imageURL);
}
