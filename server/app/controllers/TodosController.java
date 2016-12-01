package controllers;

// processing JSON
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Database
import models.DbAccess;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

// Play
import play.mvc.Controller;
import play.mvc.Result;
import views.html.todos;

// Forms
import play.data.DynamicForm;
import play.data.Form;

// Java lists
import java.util.ArrayList;
import java.util.List;

// generate UUID
import java.util.UUID;

// Escape Strings
import  org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by constant on 29/11/2016.
 */
public class TodosController extends Controller {

    //private DbAccess db;
    //private Jongo jongo;

    public TodosController() {
        //this.db = new DbAccess();
        //this.jongo = new Jongo(db.getDB());
    }
/*
    public static Result index() {mana
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());

        MongoCollection todosCollection = jongo.getCollection("todos");
        models.Todo todoElement = todosCollection.findOne().as(models.Todo.class);
        //return ok(todos.render(todo.getText()));
        return ok(todos.render(todoElement));

    }
    */

    // Get all the todos, display list
    public static Result getTodos() {
        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        List<models.Todo> todoList = new ArrayList<models.Todo>();

        MongoCollection todosCollection = jongo.getCollection("todos");
        //MongoCursor<models.Todo> todoCursor = todosCollection.find().as(models.Todo.class);
        MongoCursor<models.Todo> todoCursor = todosCollection.find().as(models.Todo.class);

        while(todoCursor.hasNext()) {
            models.Todo todoBuffer = todoCursor.next();
            todoList.add(todoBuffer);
        }

        ObjectMapper mapper = new ObjectMapper();

        /* Object -> JSON Template */
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(todoList);
            System.out.println(jsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // return play template rendering
        return ok(todos.render(todoList));
    }

    // Add todo method
    public static Result addTodo() {
        List<models.Todo> todoList = new ArrayList<models.Todo>();

        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        MongoCollection todosCollection = jongo.getCollection("todos");
        DynamicForm dynamicForm = Form.form().bindFromRequest();

        // create query
        String escapedText = StringEscapeUtils.escapeEcmaScript(dynamicForm.get("text"));
        String query = " { text: \"" + escapedText + "\", state: false, id: '" + UUID.randomUUID() + "' } ";

        // insert query
        todosCollection.insert(query);

        MongoCursor<models.Todo> todoCursor = todosCollection.find().as(models.Todo.class);

        while(todoCursor.hasNext()) {
            models.Todo todoBuffer = todoCursor.next();
            todoList.add(todoBuffer);
        }

        return ok(todos.render(todoList));
    }

    // Handle delete requests
    public static Result deleteTodo(String id) {
        List<models.Todo> todoList = new ArrayList<models.Todo>();

        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        MongoCollection todosCollection = jongo.getCollection("todos");

        String query = "{'id': '" + id + "'}";
        todosCollection.remove(query);

        MongoCursor<models.Todo> todoCursor = todosCollection.find().as(models.Todo.class);

        while(todoCursor.hasNext()) {
            models.Todo todoBuffer = todoCursor.next();
            todoList.add(todoBuffer);
        }

        return ok(todos.render(todoList));
    }

    // Handle change state requests
    public static Result changeState(String id) {
        List<models.Todo> todoList = new ArrayList<models.Todo>();
        models.Todo todoToChanged;

        DbAccess db = new DbAccess();
        Jongo jongo = new Jongo(db.getDB());
        MongoCollection todosCollection = jongo.getCollection("todos");

        String idQUery = "{'id': '" + id + "'}";
        todoToChanged = todosCollection.findOne(idQUery).as(models.Todo.class);
        if(todoToChanged.getState()) {
            todosCollection.update(idQUery).with("{$set: {state: false}}");
        }
        else {
            todosCollection.update(idQUery).with("{$set: {state: true}}");
        }

        MongoCursor<models.Todo> todoCursor = todosCollection.find().as(models.Todo.class);

        while(todoCursor.hasNext()) {
            models.Todo todoBuffer = todoCursor.next();
            todoList.add(todoBuffer);
        }

        return ok(todos.render(todoList));

    }
}
