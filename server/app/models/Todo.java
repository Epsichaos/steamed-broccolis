package models;

import org.jongo.marshall.jackson.oid.MongoId;

public class Todo {

    private String text;
    private boolean state;

    @MongoId
    private String mongoId;

    private Todo() {
        this.text = "";
        this.state = false;
    }

    public Todo(String text) {
        this.text = text;
        this.state = false;
    }

    public String getText() {
        return this.text;
    }
    public boolean getState() {
        return this.state;
    }
    public String getId() {
        return this.mongoId;
    }

    public void toggleState() {
        this.state = !this.state;
    }
    public void setText(String text) {
        this.text = text;
    }

}
