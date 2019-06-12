package utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Set of interfaces for saving any Serializable object into a file or into a string, or reversing any serialization.
 * @author Jake Mullett
 */
public interface Serializer {

    /**
     * Takes in a Serializable object and a File to save it to and writes it.
     */
    public void save(Serializable state, File fileLocation) throws SerializationException;

    /**
     * Method for saving a game to a folder. This should be pointed at a folder directory, where
     * the xml file of the Serializable state will be saved, along with a folder containing all of the resources packages which should be moved.
     * @param state State to save
     * @param fileLocation File pointing to the location of the top-level directory of this game where the XML and resources will be saved
     * @param resourcesToMove List of filepaths to resources which should be copied to this folder.
     * @throws SerializationException Any exception encountered during runtime of this utility.
     */
    public void save(Serializable state, File fileLocation, List<String> resourcesToMove) throws SerializationException;

    /**
     * Takes in a Serializable object and returns the serialized string.
     */
    public String serialize(Serializable object) throws SerializationException;

    /**
     * Takes in a String and returns a Serializable object back.
     */
    public Object deserialize(String object) throws SerializationException;

    /**
     * Takes in a File and loads the serial object from this file.
     */
    public Object load(File fileLocation) throws SerializationException, IOException;

}