package utils.serializers;

import utils.SerializationException;
import utils.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * SerializerBase class which has closed implementations of save and load.
 * This does not limit the serialization/deserialization implementations, they simply just need to implement
 * serialize() and deserialize().
 * @author Jake Mullett
 */
public abstract class SerializerBase implements Serializer {

    protected static final String SERIALIZATION_ERR = "Object could not be serialized due to ";
    protected static final String DESERIALIZATION_ERR = "Object could not be deserialized due to ";
    private static final String SUBFOLDER_KEY = "/";
    private static final String GAME_XML_NAME = "game.xml";
    private static final String RESOURCES_FOLDER_NAME = "resources";

    /**
     * Takes in a Serializable object and a File to save it to and writes it.
     * @param state Serializable state to save
     * @param fileLocation Location
     * @throws SerializationException Any exception encountered during runtime of this utility.
     */
    @Override
    public final void save(Serializable state, File fileLocation) throws SerializationException{
        String json = serialize(state);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation));
            writer.write(json);
            writer.close();
        } catch (IOException ex) {
            throw new SerializationException(SERIALIZATION_ERR, ex);
        }
    }

    /**
     * Method for saving a game to a folder. This should be pointed at a folder directory, where
     * the xml file of the Serializable state will be saved, along with a folder containing all of the resources packages which should be moved.
     * @param state State to save
     * @param fileLocation File pointing to the location of the top-level directory of this game where the XML and resources will be saved
     * @param resourcesToMove List of filepaths to resources which should be copied to this folder.
     * @throws SerializationException Any exception encountered during runtime of this utility.
     */
    @Override
    public void save(Serializable state, File fileLocation, List<String> resourcesToMove) throws SerializationException {
        save(state, new File(fileLocation.getAbsolutePath() + SUBFOLDER_KEY + GAME_XML_NAME));
        try {
            moveResources(fileLocation.getAbsolutePath(), resourcesToMove);
        } catch (IOException ex) {
            throw new SerializationException(SERIALIZATION_ERR, ex);
        }
    }

    /**
     * Takes in a File and loads the serial object from this file.
     * @param fileLocation Location of the object to load
     * @return Object representating the deserailzed object
     * @throws SerializationException Any exception encountered during runtime of this utility.
     */
    @Override
    public final Object load(File fileLocation) throws SerializationException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
        StringBuilder builder = new StringBuilder();
        String curline = reader.readLine();
        while (curline != null) {
            builder.append(curline);
            curline = reader.readLine();
        }
        String json = builder.toString();
        return deserialize(json);
    }

    private void moveResources(String baseFilePath, List<String> resources) throws IOException {
        String folderFileName = baseFilePath + SUBFOLDER_KEY + RESOURCES_FOLDER_NAME;
        File resourceFile = new File(folderFileName);
        if (!resourceFile.exists())
            Files.createDirectory(resourceFile.toPath());
        for (String resourcePath : resources) {
            File resourceToCopy = new File(resourcePath);
            File targetResourceDest = new File(folderFileName + SUBFOLDER_KEY + resourceToCopy.getName());
            Files.copy(resourceToCopy.toPath(), targetResourceDest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
