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
import views.html.todos;

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
    //private static DbAccess db;
    //private static Jongo jongo;

    public TodosController() {
        //db = new DbAccess();
        //jongo = new Jongo(db.getDB());
    }

    // Get all the todos, display list
    public static Result getTodos() {
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        List<models.Todo> todoList = new ArrayList<>();

        MongoCollection todosCollection = jongo.getCollection("todos");
        MongoCursor<models.Todo> todoCursor = todosCollection.find().as(models.Todo.class);

        while(todoCursor.hasNext()) {
            models.Todo todoBuffer = todoCursor.next();
            todoList.add(todoBuffer);
        }

        return ok(play.libs.Json.toJson(todoList));
    }

    public static Result getTodo(String id) {
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        //List<models.Todo> todoList = new ArrayList<models.Todo>();
        models.Todo todo;
        String jsonStr = null;

        MongoCollection todosCollection = jongo.getCollection("todos");

        String query = "{'id': '" + id + "'}";

        //MongoCursor<models.Todo> todoCursor = todosCollection.find(query).as(models.Todo.class);
        todo = todosCollection.findOne(query).as(models.Todo.class);

        return ok(play.libs.Json.toJson(todo));
    }

    public static Result deleteTodos() {
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());

        MongoCollection todosCollection = jongo.getCollection("todos");
        todosCollection.remove("{}");

        return ok();
    }

    public static Result deleteTodo(String id) {
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        List<models.Todo> todoList = new ArrayList<models.Todo>();

        MongoCollection todosCollection = jongo.getCollection("todos");

        String query = "{'id': '" + id + "'}";

        todosCollection.remove(query);

        return ok();
    }

    public static Result addTodo() {
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        List<models.Todo> todoList = new ArrayList<models.Todo>();

        MongoCollection todosCollection = jongo.getCollection("todos");
        DynamicForm dynamicForm = Form.form().bindFromRequest();

        // create query
        String escapedText = StringEscapeUtils.escapeEcmaScript(dynamicForm.get("text"));
        String query = " { text: \"" + escapedText + "\", state: false, id: '" + UUID.randomUUID() + "' } ";

        // insert query
        todosCollection.insert(query);

        /*
        MongoCursor<models.Todo> todoCursor = todosCollection.find().as(models.Todo.class);

        while(todoCursor.hasNext()) {
            models.Todo todoBuffer = todoCursor.next();
            todoList.add(todoBuffer);
        }

        return ok(todos.render(todoList));
        */
        return ok();
    }

    public static Result toggleTodo(String id) {
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        models.Todo todoToChanged;

        MongoCollection todosCollection = jongo.getCollection("todos");

        String idQuery = "{'id': '" + id + "'}";
        todoToChanged = todosCollection.findOne(idQuery).as(models.Todo.class);
        if(todoToChanged.getState()) {
            todosCollection.update(idQuery).with("{$set: {state: false}}");
        }
        else {
            todosCollection.update(idQuery).with("{$set: {state: true}}");
        }

        return ok();

    }

    public static Result updateTodo(String id) {
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());

        MongoCollection todosCollection = jongo.getCollection("todos");

        DynamicForm dynamicForm = Form.form().bindFromRequest();
        // create query
        String escapedText = StringEscapeUtils.escapeEcmaScript(dynamicForm.get("text"));
        String newText = " { text: \"" + escapedText + "\" } ";
        String insertQuery = "{$set:" + newText + "}";
        String idQuery = "{'id': '" + id + "'}";

        todosCollection.update(idQuery).with(insertQuery);

        return ok();

    }
}
