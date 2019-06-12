module data {

    requires xstream;
    requires org.junit.jupiter.api;
    requires kxml2;
    requires java.logging;

    opens utils to xstream;

    exports utils;
}
