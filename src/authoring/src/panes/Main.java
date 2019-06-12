package panes;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
    /**
     * Start the program.
     */
    public static void main (String[] args) throws Exception {
        System.setErr(new PrintStream(new FileOutputStream("temp.txt")));
        AuthoringEnvironment.main(args);
    }
}
