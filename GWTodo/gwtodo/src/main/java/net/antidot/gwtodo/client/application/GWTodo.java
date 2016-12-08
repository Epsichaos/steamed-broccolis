package net.antidot.gwtodo.client.application;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.antidot.gwtodo.client.application.resources.BindResources;
import net.antidot.gwtodo.client.application.service.TodoRestService;
import net.antidot.gwtodo.client.application.model.Todo;
import java.util.ArrayList;
import java.util.List;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTodo implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    /*
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";
    */

    private final TodoRestService todoService = GWT.create(TodoRestService.class);
    private List<Todo> todoList = new ArrayList<>();

    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable todoTable = new FlexTable();
    private HorizontalPanel formPanel = new HorizontalPanel();
    private HorizontalPanel imagePanel = new HorizontalPanel();
    private TextBox todoTextBox = new TextBox();
    private Button addTodoButton = new Button();
    private Image homeImage = new Image(BindResources.GWT_RESOURCES.psyDuckImage());

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Inject CSS
        BindResources.GWT_RESOURCES.cssresources().ensureInjected();
        // set root for http requests
        Defaults.setServiceRoot("/");

        // center elements
        mainPanel.setStyleName(BindResources.GWT_RESOURCES.cssresources().centeredRule());
        formPanel.setSpacing(10);
        formPanel.setStyleName(BindResources.GWT_RESOURCES.cssresources().centeredRule());
        //imagePanel.setStyleName("centered-style");

        // add styles to widgets
        // todoTextBox
        todoTextBox.setStyleName(BindResources.GWT_RESOURCES.cssresources().textBox());
        // addTodoButton
        addTodoButton.setStyleName(BindResources.GWT_RESOURCES.cssresources().addTodoButton());
        // todoTable
        todoTable.setStyleName(BindResources.GWT_RESOURCES.cssresources().flexTable());
        // homeImage
        homeImage.setStyleName(BindResources.GWT_RESOURCES.cssresources().homeImageStyle());

        HTML htmlDivider = new HTML("<br><hr><br>");

        // add panels
        mainPanel.add(imagePanel);
        mainPanel.add(formPanel);
        mainPanel.add(htmlDivider);
        mainPanel.add(todoTable);

        imagePanel.add(homeImage);

        // set todo table first row
        todoTable.setText(0, 0, "id");
        todoTable.setText(0, 1, "text");
        todoTable.setText(0, 2, "state");
        todoTable.setText(0, 3, "toggle");
        todoTable.setText(0, 4, "delete");

        // style first row
        //todoTable.getCellFormatter().setStyleName(0, 0, "cellStyle");
        //todoTable.getCellFormatter().setStyleName(0, 1, "cellStyle");
        //todoTable.getCellFormatter().setStyleName(0, 2, "cellStyle");

        // add input / button to form panel
        formPanel.add(todoTextBox);
        formPanel.add(addTodoButton);

        // addTodo Button definition & Click Handler
        addTodoButton.setText("Add Todo");
        addTodoButton.addClickHandler(event -> {
           todoService.addTodo(todoTextBox.getText(), new MethodCallback<Todo>() {
               @Override
               public void onFailure(Method method, Throwable throwable) {
                   // on Failure handle
               }

               @Override
               public void onSuccess(Method method, Todo response) {
                   todoTextBox.setText("");
                   addTodo(response);
               }
           });
        });

        // Get all todos and add then to the panel
        todoService.getTodos(new MethodCallback<List<Todo>>() {
            public void onSuccess(Method method, List<Todo> response) {
                for(Todo todo: response) {
                    addTodo(todo);
                }
            }

            public void onFailure(Method method, Throwable exception) {
                // exception
            }
        });

        // Write in root panel
        RootPanel.get("root-panel").add(mainPanel);
    }

    // add Todo element on the table / ArrayList
    public void addTodo(Todo todo) {
        // add Todo to ArrayList
        todoList.add(todo);

        // ad Todo to TodoTable
        int rowCount = todoTable.getRowCount();
        // Set toogle button
        Button btnToggle = new Button();
        Button btnDelete = new Button();
        btnToggle = new Button();
        btnToggle.setText("Toggle");
        btnToggle.setStyleName(BindResources.GWT_RESOURCES.cssresources().btnToggle());
        btnToggle.addClickHandler(clickEvent -> {
            todoService.toggleTodo(todo.getId(), new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    // Log failure
                }

                @Override
                public void onSuccess(Method method, Void aVoid) {
                    int index = todoList.indexOf(todo);
                    Todo todoElement = todoList.get(index);
                    todoElement.toggleState();
                    todoList.set(index, todoElement);
                    todoTable.setText(index+1, 2, String.valueOf(todoElement.getState()));
                }
            });
        });
        // Set delete button
        btnDelete = new Button();
        btnDelete.setText("Delete");
        btnDelete.setStyleName(BindResources.GWT_RESOURCES.cssresources().btnDelete());
        btnDelete.addClickHandler(clickEvent -> {
            todoService.deleteTodo(todo.getId(), new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    // Log failure
                }

                @Override
                public void onSuccess(Method method, Void aVoid) {
                    int index = todoList.indexOf(todo);
                    todoList.remove(index);
                    todoTable.removeRow(index + 1);
                }
            });
        });
        todoTable.setText(rowCount, 0, todo.getId());
        todoTable.setText(rowCount, 1, todo.getText());
        todoTable.setText(rowCount, 2, String.valueOf(todo.getState()));
        todoTable.setWidget(rowCount, 3, btnToggle);
        todoTable.setWidget(rowCount, 4, btnDelete);

    }
}