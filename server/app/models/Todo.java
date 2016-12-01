package models;

import org.jongo.marshall.jackson.oid.MongoId;

/**
 * Created by constant on 29/11/2016.
 */
public class Todo {

    // Todo object variables
    private String text;
    private boolean state;
    private String id;
    // map Mongo Id
    @MongoId
    private String mongoId;

    public Todo() {
        this.text = "";
        this.state = false;
    }

    /*
    public void models.Todo(text) {
        this.text = text;
        this.state = false;
    }
    public void models.Todo(text, state, id) {
        this.text = text;
        this.completed = state;
        this.id = id;
    }
    */

    public String getText() {
        return this.text;
    }
    public boolean getState() {
        return this.state;
    }
    public String getId() {
        return this.id;
    }
}
