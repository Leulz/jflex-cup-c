package codegen;

public class Registers {
    private int registerCounter;
    private int argRegisterCounter;
    private int retRegisterCounter;

    private static Registers instance = null;

    public static Registers getInstance() {
        if (instance == null) {
            instance = new Registers();
        }

        return instance;
    }

    public Registers() {
        initialize();
    }

    public void initialize() {
        resetRegisterCounter();
        argRegisterCounter = 1;
        retRegisterCounter = 1;
    }

    public void resetRegisterCounter() {
        registerCounter = 1;
    }

    public String previousRegister() {
        return "R" + String.valueOf(this.registerCounter - 1);
    }

    public String newRegister() {
        return "R" + String.valueOf(this.registerCounter++);
    }

    public String newArgRegister() {
        return "AR" + String.valueOf(this.argRegisterCounter++);
    }

    public String newReturnRegister() {
        return "RR" + String.valueOf(this.retRegisterCounter++);
    }
}
