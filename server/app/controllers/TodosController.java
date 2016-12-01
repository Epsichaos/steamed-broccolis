package controllers;

// processing JSON

import models.DbAccess;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

// class to Json
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Database
// Play
// Forms
// Java lists
// generate UUID
// Escape Strings

/**
 * Created by constant on 29/11/2016.
 */
public class TodosController extends Controller {
    private DbAccess db;
    private Jongo jongo;
    private MongoCollection todosCollection;

    public TodosController() {
        this.db = new DbAccess();
        this.jongo = new Jongo(this.db.getDB());
        this.todosCollection = this.jongo.getCollection("todos");
    }

    // Get all the todos, display list
    public Result getTodos() {
        List<models.Todo> todoList = new ArrayList<>();

        MongoCursor<models.Todo> todoCursor = this.todosCollection.find().as(models.Todo.class);

        while(todoCursor.hasNext()) {
            models.Todo todoBuffer = todoCursor.next();
            todoList.add(todoBuffer);
        }

        return ok(Json.toJson(todoList));
    }

    public Result getTodo(String id) {
        models.Todo todo;
        String jsonStr = null;

        String query = "{'id': '" + id + "'}";
        todo = this.todosCollection.findOne(query).as(models.Todo.class);

        return ok(Json.toJson(todo));
    }

    public Result deleteTodos() {
        this.todosCollection.remove("{}");

        return ok();
    }

    public Result deleteTodo(String id) {
        String query = "{'id': '" + id + "'}";
        this.todosCollection.remove(query);

        return ok();
    }

    public Result addTodo() {

        DynamicForm dynamicForm = Form.form().bindFromRequest();

        // create query
        String escapedText = StringEscapeUtils.escapeEcmaScript(dynamicForm.get("text"));
        String query = " { text: \"" + escapedText + "\", state: false, id: '" + UUID.randomUUID() + "' } ";

        // insert query
        this.todosCollection.insert(query);

        return ok();
    }

    public Result toggleTodo(String id) {
        models.Todo todoToChanged;

        String idQuery = "{'id': '" + id + "'}";
        todoToChanged = this.todosCollection.findOne(idQuery).as(models.Todo.class);
        if(todoToChanged.getState()) {
            this.todosCollection.update(idQuery).with("{$set: {state: false}}");
        }
        else {
            this.todosCollection.update(idQuery).with("{$set: {state: true}}");
        }

        return ok();

    }

    public Result updateTodo(String id) {

        DynamicForm dynamicForm = Form.form().bindFromRequest();
        // create query
        String escapedText = StringEscapeUtils.escapeEcmaScript(dynamicForm.get("text"));
        String newText = " { text: \"" + escapedText + "\" } ";
        String insertQuery = "{$set:" + newText + "}";
        String idQuery = "{'id': '" + id + "'}";

        this.todosCollection.update(idQuery).with(insertQuery);

        return ok();

    }
}
