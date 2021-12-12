package net.redside.bingchilling;

public abstract class BingChillingSingle {
    private final String command;
    private String name;
    private int color;

    protected BingChillingSingle(String command, String name, int color) {
        this.command = command;
        this.name = name;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public abstract void run(String command);
}
