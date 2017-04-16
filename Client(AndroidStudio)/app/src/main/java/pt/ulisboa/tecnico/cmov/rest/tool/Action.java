package pt.ulisboa.tecnico.cmov.rest.tool;

public class Action {
    private MessageType type;
    private String username;
    private String password;
    public Action(MessageType type, String username, String password) {
        this.type = type;
        this.username=username;
        this.password=password;
    }
    public MessageType getType() {
        return type;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}