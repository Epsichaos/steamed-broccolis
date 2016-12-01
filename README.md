# steamed-broccolis

Play Framework / GWT todo app

## Testing

*With httpie*

```
http DELETE localhost:9000/todos/todo/6b97817d-93f3-4e94-91b5-787d1e1b81de
http DELETE localhost:9000/todos
http -f POST localhost:9000/todos text="my new todo"
```
