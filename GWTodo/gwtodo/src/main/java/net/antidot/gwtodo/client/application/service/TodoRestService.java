package net.antidot.gwtodo.client.application.service;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

import net.antidot.gwtodo.client.application.model.Todo;

public interface TodoRestService extends RestService {
    @GET
    @Path("/todos")
    public void getTodos(MethodCallback<List<Todo>> callback);

    @GET
    @Path("/todos/todo/{id}")
    public void getTodo(@PathParam("id") String id, MethodCallback<Todo> callback);

    @PUT
    @Path("/todos/todo/{id}/toggle")
    public void toggleTodo(@PathParam("id") String id, MethodCallback<Void> callback);

    @DELETE
    @Path("/todos")
    public void deleteTodos(MethodCallback<Void> callback);

    @DELETE
    @Path("/todos/todo/{id}")
    public void deleteTodo(@PathParam("id") String id, MethodCallback<Void> callback);

    @POST
    @Path("/todos")
    public void addTodo(@QueryParam("text") String text, MethodCallback<Todo> callback);

    @PUT
    @Path("/todos/todo/{id}")
    public void updateTodo(@PathParam("id") String id, @QueryParam("text") String text, MethodCallback<Todo> callback);

}