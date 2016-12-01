package controllers;

// processing JSON

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import models.Todo;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Database
// Play
// Forms
// Java lists
// generate UUID
// Escape Strings

public class TodosController extends Controller {

    private MongoCollection todos;

    public TodosController() {
        DB db = new MongoClient().getDB("test");
        Jongo jongo = new Jongo(db);
        this.todos = jongo.getCollection("todos");
    }

    // Get all the todos, display list
    public Result getTodos() {
        List<models.Todo> todoList = new ArrayList<>();
        for (models.Todo todo : todos.find().as(models.Todo.class)) {
            todoList.add(todo);
        }
        return ok(Json.toJson(todoList));
    }

    public Result getTodo(String id) {
        Result res = notFound("Unknown id : " + id);
        try {
            models.Todo todo = todos.findOne(new ObjectId(id)).as(models.Todo.class);
            res = ok(Json.toJson(todo));
        }
        catch (IllegalArgumentException invalidId) {
            Logger.info("GET : Unknown id : " + id, invalidId);
        }
        return res;
    }

    public Result addTodo() {
        Result res =  badRequest("Text not defined");
        Map<String, String[]> todoContent = request().body().asFormUrlEncoded();
        if (todoContent != null && todoContent.get("text") != null) {
            models.Todo todo = new models.Todo(todoContent.get("text")[0]);
            todos.insert(todo);
            res = created("Todo created : " + todo.getText());
        }
        else {
            Logger.info("POST : Invalid request body : " + Json.toJson(todoContent));
        }
        return res;
    }

    public Result deleteTodos() {
        WriteResult removeAll = todos.remove();
        return ok(Json.toJson(removeAll));
    }

    // Handle delete requests
    public Result deleteTodo(String id) {
        Result res = notFound("Unknown id : " + id);
        WriteResult removedTodo = todos.remove(new ObjectId(id));
        if (removedTodo.getN() > 0) {
            res = ok(Json.toJson(removedTodo));
        }
        else {
            Logger.info("DELETE : Unknown id : " + id);
        }
        return res;
    }

    // Handle change state requests
    public Result toggleTodo(String id) {
        Result res = notFound("Unknown id : " + id);
        try {
            models.Todo todo = todos.findOne(new ObjectId(id)).as(models.Todo.class);
            todo.toggleState();
            todos.update(new ObjectId(id)).with(todo);
            res = ok(Json.toJson(todo));
        }
        catch (IllegalArgumentException invalidId) {
            Logger.info("TOGGLE : Unknown id : " + id, invalidId);
        }
        return res;
    }

    public Result updateTodo(String id) {
        Result res = notFound("Unknown id : " + id);
        try {
            Map<String, String[]> todoContent = request().body().asFormUrlEncoded();
            Todo todo = todos.findOne(new ObjectId(id)).as(Todo.class);
            todo.setText(todoContent.get("text")[0]);
            todos.update(new ObjectId(id)).with(todo);
            res = ok(Json.toJson(todo));
        }
        catch (IllegalArgumentException invalidId) {
            Logger.info("UPDATE : Unknown id : " + id, invalidId);
        }
        return res;
    }
}
