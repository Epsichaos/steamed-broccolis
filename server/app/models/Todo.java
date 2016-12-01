package models;

import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * Created by constant on 29/11/2016.
 */
public class Todo {

    // Todo object variables
    private String text;
    private boolean state;
    private String id;
    // map Mongo Id
    @ObjectId
    private String _id;

    public Todo() {
        this.text = "";
        this.state = false;
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
}
