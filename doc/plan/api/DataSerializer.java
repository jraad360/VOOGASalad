/**
 * Set of interfaces for saving any Serializable object into a file or into a string, or reversing any serialization.
 */
public interface DataSerializer {

    /**
     * Takes in a Serializable object and a File to save it to and writes it.
     */
    public void save(Serializable state, File fileLocation);

    /**
     * Takes in a Serializable object and returns the serialized string.
     */
    public String serialize(Serializable object);

    /**
     * Takes in a String and returns a Serializable object back.
     */
    public Serializable deserialize(String object);

    /**
     * Takes in a File and loads the serial object from this file.
     */
    public Serializable load(File fileLocation);

}