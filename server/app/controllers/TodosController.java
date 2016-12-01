package controllers;

// processing JSON

import models.DbAccess;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// class to Json

/**
 * Created by constant on 29/11/2016.
 */
public class TodosController extends Controller {
    private DbAccess db;
    private Jongo jongo;
    private MongoCollection todosCollection;

    // constructor
    public TodosController() {
        this.db = new DbAccess();
        this.jongo = new Jongo(this.db.getDB());
        this.todosCollection = this.jongo.getCollection("todos");
    }

    // Get all the todos
    public Result getTodos() {
        List<models.Todo> todoList = new ArrayList<>();

        MongoCursor<models.Todo> todoCursor = this.todosCollection.find().as(models.Todo.class);

        while(todoCursor.hasNext()) {
            models.Todo todoBuffer = todoCursor.next();
            todoList.add(todoBuffer);
        }

        return ok(Json.toJson(todoList));
    }

    // get todo from id
    public Result getTodo(String id) {
        models.Todo todo;
        String query = "{'id': '" + StringEscapeUtils.escapeEcmaScript(id) + "'}";

        try {
            todo = this.todosCollection.findOne(query).as(models.Todo.class);
            return ok(Json.toJson(todo));

        } catch(Exception e) {
            // no match, return error
            return notFound("Object from id " + id + " not found");
        }
    }

    // delete all todos
    public Result deleteTodos() {
        try {
            this.todosCollection.remove("{}");
            return ok();
        } catch(Exception e) {
            return notFound("Error when deleting all todos: " + e.getMessage());
        }
    }

    // delete todo from id
    public Result deleteTodo(String id) {
        String query = "{'id': '" + StringEscapeUtils.escapeEcmaScript(id) + "'}";

        try {
            this.todosCollection.remove(query);
            return ok("OK");
        } catch(Exception e) {
            return notFound("Error while deleting todo from id (" + id + "): " + e.getMessage());
        }
    }

    // add todo
    public Result addTodo() {

        DynamicForm dynamicForm = Form.form().bindFromRequest();

        // create query
        String escapedText = StringEscapeUtils.escapeEcmaScript(dynamicForm.get("text"));
        String query = " { text: \"" + escapedText + "\", state: false, id: '" + UUID.randomUUID() + "' } ";

        // insert query
        try {
            this.todosCollection.insert(query);
            return created();
        } catch(Exception e) {
            return internalServerError("Error while inserting todo: " + e.getMessage());
        }
    }

    // toggle todo state from id
    public Result toggleTodo(String id) {
        models.Todo todoToChanged;
        // find id query
        String idQuery = "{'id': '" + StringEscapeUtils.escapeEcmaScript(id) + "'}";

        try {
            todoToChanged = this.todosCollection.findOne(idQuery).as(models.Todo.class);
            // toggle state
            try {
                if (todoToChanged.getState()) {
                    this.todosCollection.update(idQuery).with("{$set: {state: false}}");
                } else {
                    this.todosCollection.update(idQuery).with("{$set: {state: true}}");
                }
                return ok();
            } catch(Exception e) {
                return internalServerError("Error while toggling todo: " + e.getMessage());
            }
        } catch(Exception e) {
            return notFound();
        }
    }

    // update todo from id. Todo new text is wrapped into a form element which field is 'text'
    public Result updateTodo(String id) {
        // bind form
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        // create query
        String escapedText = StringEscapeUtils.escapeEcmaScript(dynamicForm.get("text"));
        String newText = " { text: \"" + escapedText + "\" } ";
        String insertQuery = "{$set:" + newText + "}";
        String idQuery = "{'id': '" + id + "'}";

        try {
            this.todosCollection.update(idQuery).with(insertQuery);
            return ok();
        } catch(Exception e) {
            return internalServerError(e.getMessage());
        }
    }
}
