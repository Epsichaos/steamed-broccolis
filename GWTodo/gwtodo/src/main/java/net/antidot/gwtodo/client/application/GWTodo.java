package net.antidot.gwtodo.client.application;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
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

import javax.xml.soap.Text;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTodo implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */

    // Todo Service variable
    private final TodoRestService todoService = GWT.create(TodoRestService.class);
    // Todo List
    private List<Todo> todoList = new ArrayList<>();
    // GWT Widgets
    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable todoTable = new FlexTable();
    private HorizontalPanel formPanel = new HorizontalPanel();
    private HorizontalPanel imagePanel = new HorizontalPanel();
    private TextBox todoTextBox = new TextBox();
    private Button addTodoButton = new Button();
    private Image homeImage = new Image(BindResources.GWT_RESOURCES.psyDuckImage());

    /**
     * DialogBox which is opened onClick event. This DialogBox allows to update the text of the todo according an id
     * in parameters
     * @param id, identifier of the element
     * @return DialogBox d, where d is the dialog box created for the element of id id.
     */
    private DialogBox createDialogBox(String id) {
        // dialogbox
        final DialogBox dialogBox = new DialogBox();
        // variables
        VerticalPanel mainModalPanel = new VerticalPanel();
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        TextBox textInput = new TextBox();
        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Cancel");

        // Style
        okButton.setStyleName(BindResources.GWT_RESOURCES.cssresources().addTodoButton());
        cancelButton.setStyleName(BindResources.GWT_RESOURCES.cssresources().btnDelete());

        // Add elements to panels
        mainModalPanel.add(textInput);
        mainModalPanel.add(buttonsPanel);
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(okButton);

        // default parameters
        dialogBox.setText("Update todo");
        dialogBox.setAnimationEnabled(true);
        dialogBox.setGlassEnabled(true);

        // buttons click handler
        // cancelButton click handler
        cancelButton.addClickHandler(clickEvent -> dialogBox.hide());
        // okButton click handler
        okButton.addClickHandler(clickEvent -> todoService.updateTodo(id, textInput.getText(), new MethodCallback<Todo>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                // log failure
            }
            @Override
            public void onSuccess(Method method, Todo todo) {
                // update
                int index = 0;
                while(index < todoList.size() && !(todoList.get(index).getId()).equals(todo.getId())) {
                    index++;
                    Window.alert(String.valueOf(index));
                }
                if(index < todoList.size()) {
                    Todo todoElement = todoList.get(index);
                    todoElement.setText(todo.getText());
                    todoList.set(index, todoElement);
                    todoTable.setText(index+1, 1, todoElement.getText());
                    dialogBox.hide();
                }
                else {
                    dialogBox.hide();
                }
            }
        }));
        dialogBox.add(mainModalPanel);
        return dialogBox;
    }

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
        todoTable.setText(0, 3, "update");
        todoTable.setText(0, 4, "toggle");
        todoTable.setText(0, 5, "delete");

        // style first row
        //todoTable.getCellFormatter().setStyleName(0, 0, "cellStyle");
        //todoTable.getCellFormatter().setStyleName(0, 1, "cellStyle");
        //todoTable.getCellFormatter().setStyleName(0, 2, "cellStyle");

        // add input / button to form panel
        formPanel.add(todoTextBox);
        formPanel.add(addTodoButton);

        // addTodo Button definition & Click Handler
        addTodoButton.setText("Add Todo");
        addTodoButton.addClickHandler(event -> todoService.addTodo(todoTextBox.getText(), new MethodCallback<Todo>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                // on Failure handle
            }
            @Override
            public void onSuccess(Method method, Todo response) {
                todoTextBox.setText("");
                addTodo(response);
            }
        }));

        // Get all todos and add then to the panel
        todoService.getTodos(new MethodCallback<List<Todo>>() {
            @Override
            public void onSuccess(Method method, List<Todo> response) {
                for(Todo todo: response) {
                    addTodo(todo);
                }
            }
            @Override
            public void onFailure(Method method, Throwable exception) {
                // exception
            }
        });

        // Write in root panel
        RootPanel.get("root-panel").add(mainPanel);
    }

    /**
     * Add a Todo from parameters into the table and the ArrayList private variable of the class
     * @param todo, Todo to add
     */
    public void addTodo(Todo todo) {
        // add Todo to ArrayList
        todoList.add(todo);

        // ad Todo to TodoTable
        int rowCount = todoTable.getRowCount();
        // Buttons init
        Button btnToggle = new Button();
        Button btnDelete = new Button();
        Button btnUpdate = new Button();

        // Toggle Button definition
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
        btnDelete.setText("Delete");
        btnDelete.setStyleName(BindResources.GWT_RESOURCES.cssresources().btnDelete());
        btnDelete.addClickHandler(clickEvent -> todoService.deleteTodo(todo.getId(), new MethodCallback<Void>() {
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
        }));
        btnUpdate.setText("Update");
        btnUpdate.setStyleName(BindResources.GWT_RESOURCES.cssresources().btnToggle());
        btnUpdate.addClickHandler(clickEvent -> {
            DialogBox todoModalBox = createDialogBox(todo.getId());
            // positions
            int left = Window.getClientWidth()/ 2 - 80;
            int top = Window.getClientHeight()/ 2 - 55;
            todoModalBox.setPopupPosition(left, top);
            // show box
            todoModalBox.show();
        });
        todoTable.setText(rowCount, 0, todo.getId());
        todoTable.setText(rowCount, 1, todo.getText());
        todoTable.setText(rowCount, 2, String.valueOf(todo.getState()));
        todoTable.setWidget(rowCount, 3, btnUpdate);
        todoTable.setWidget(rowCount, 4, btnToggle);
        todoTable.setWidget(rowCount, 5, btnDelete);

    }
}