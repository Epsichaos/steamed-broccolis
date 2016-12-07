package net.antidot.gwtodo.client.application.model;

/**
 * Created by constant on 06/12/2016.
 */
public class Todo {

    // Todo object variables
    private String text;
    private boolean state;
    private String id;

    public Todo() {
        this.text = "";
        this.state = false;
        this.id = "";
    }

    public String getText() {
        return this.text;
    }
    public boolean getState() {
        return this.state;
    }
    public String getId() {
        return this.id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void toggleState() {
        if(this.state == true) {
            this.state = false;
        }
        else {
            this.state = true;
        }
    }
}