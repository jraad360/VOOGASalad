package utils;

import utils.serializers.XStreamSerializer;

/**
 * Singleton class for creating and passing a single XStream instance.
 * One instance is used to cut down on network response time with instantiating a Serialzer for each request.
 * @author Jake Mullett
 */
public class SerializerSingleton {

    private static Serializer serializer;

    private SerializerSingleton() {

    }

    /**
     * Get the singleton Serializer instance that exists, or create one if there is none.
     * @return Serializer serializer
     */
    public static Serializer getInstance() {
        if (serializer == null) {
            buildXMLInstance();
        }
        return serializer;
    }

    private static void buildXMLInstance() {
        serializer = new XStreamSerializer();
    }

}
