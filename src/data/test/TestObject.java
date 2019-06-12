/**
 * This interface and the TestObject exist here for unit testing, as integration testing
 * in this package would add a dependency on the authoring module.
 */
public class TestObject implements BasicTestInterface {

    private String string;
    private String[] args;
    private Object[] objects;

    public TestObject() {
        string = "";
        args = new String[0];
        objects = new Object[0];
    }

    @Override
    public void storeObjects(Object... objects) {
        this.objects = objects;
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    @Override
    public void changeString(String string) {
        this.string = string;
    }

    @Override
    public void storeArgs(String... args) {
        this.args = args;
    }

    @Override
    public String getString() {
        return string;
    }
}
