package net.antidot.gwtodo.client.application.service;

public class TodoRestService_Generated_RestServiceProxy_ implements net.antidot.gwtodo.client.application.service.TodoRestService, org.fusesource.restygwt.client.RestServiceProxy {
  private org.fusesource.restygwt.client.Resource resource = null;
  
  public void setResource(org.fusesource.restygwt.client.Resource resource) {
    this.resource = resource;
  }
  public org.fusesource.restygwt.client.Resource getResource() {
    if (this.resource == null) {
      String serviceRoot = org.fusesource.restygwt.client.Defaults.getServiceRoot();
      this.resource = new org.fusesource.restygwt.client.Resource(serviceRoot);
    }
    return this.resource;
  }
  private org.fusesource.restygwt.client.Dispatcher dispatcher = null;
  
  public void setDispatcher(org.fusesource.restygwt.client.Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }
  
  public org.fusesource.restygwt.client.Dispatcher getDispatcher() {
    return this.dispatcher;
  }
  public void addTodo(java.lang.String text, org.fusesource.restygwt.client.MethodCallback<net.antidot.gwtodo.client.application.model.Todo> callback) {
    final java.lang.String final_text = text;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/todos")
    .addQueryParam("text", text)
    .post();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<net.antidot.gwtodo.client.application.model.Todo>(__method, callback) {
        protected net.antidot.gwtodo.client.application.model.Todo parseResult() throws Exception {
          try {
            return net.antidot.gwtodo.client.application.model.Todo_Generated_JsonEncoderDecoder_.INSTANCE.decode(com.google.gwt.json.client.JSONParser.parse(__method.getResponse().getText()));
          } catch (Throwable __e) {
            throw new org.fusesource.restygwt.client.ResponseFormatException("Response was NOT a valid JSON document", __e);
          }
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
  public void deleteTodo(java.lang.String id, org.fusesource.restygwt.client.MethodCallback<java.lang.Void> callback) {
    final java.lang.String final_id = id;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/todos/todo/"+(id== null? null : com.google.gwt.http.client.URL.encodePathSegment(id))+"")
    .delete();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<java.lang.Void>(__method, callback) {
        protected java.lang.Void parseResult() throws Exception {
          return (java.lang.Void) null;
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
  public void deleteTodos(org.fusesource.restygwt.client.MethodCallback<java.lang.Void> callback) {
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/todos")
    .delete();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<java.lang.Void>(__method, callback) {
        protected java.lang.Void parseResult() throws Exception {
          return (java.lang.Void) null;
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
  public void getTodo(java.lang.String id, org.fusesource.restygwt.client.MethodCallback<net.antidot.gwtodo.client.application.model.Todo> callback) {
    final java.lang.String final_id = id;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/todos/todo/"+(id== null? null : com.google.gwt.http.client.URL.encodePathSegment(id))+"")
    .get();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<net.antidot.gwtodo.client.application.model.Todo>(__method, callback) {
        protected net.antidot.gwtodo.client.application.model.Todo parseResult() throws Exception {
          try {
            return net.antidot.gwtodo.client.application.model.Todo_Generated_JsonEncoderDecoder_.INSTANCE.decode(com.google.gwt.json.client.JSONParser.parse(__method.getResponse().getText()));
          } catch (Throwable __e) {
            throw new org.fusesource.restygwt.client.ResponseFormatException("Response was NOT a valid JSON document", __e);
          }
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
  public void getTodos(org.fusesource.restygwt.client.MethodCallback<java.util.List<net.antidot.gwtodo.client.application.model.Todo>> callback) {
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/todos")
    .get();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<java.util.List<net.antidot.gwtodo.client.application.model.Todo>>(__method, callback) {
        protected java.util.List<net.antidot.gwtodo.client.application.model.Todo> parseResult() throws Exception {
          try {
            return org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.toList(com.google.gwt.json.client.JSONParser.parse(__method.getResponse().getText()), net.antidot.gwtodo.client.application.model.Todo_Generated_JsonEncoderDecoder_.INSTANCE);
          } catch (Throwable __e) {
            throw new org.fusesource.restygwt.client.ResponseFormatException("Response was NOT a valid JSON document", __e);
          }
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
  public void toggleTodo(java.lang.String id, org.fusesource.restygwt.client.MethodCallback<java.lang.Void> callback) {
    final java.lang.String final_id = id;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/todos/todo/"+(id== null? null : com.google.gwt.http.client.URL.encodePathSegment(id))+"/toggle")
    .put();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<java.lang.Void>(__method, callback) {
        protected java.lang.Void parseResult() throws Exception {
          return (java.lang.Void) null;
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
  public void updateTodo(java.lang.String id, java.lang.String text, org.fusesource.restygwt.client.MethodCallback<net.antidot.gwtodo.client.application.model.Todo> callback) {
    final java.lang.String final_id = id;
    final java.lang.String final_text = text;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/todos/todo/"+(id== null? null : com.google.gwt.http.client.URL.encodePathSegment(id))+"")
    .addQueryParam("text", text)
    .put();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<net.antidot.gwtodo.client.application.model.Todo>(__method, callback) {
        protected net.antidot.gwtodo.client.application.model.Todo parseResult() throws Exception {
          try {
            return net.antidot.gwtodo.client.application.model.Todo_Generated_JsonEncoderDecoder_.INSTANCE.decode(com.google.gwt.json.client.JSONParser.parse(__method.getResponse().getText()));
          } catch (Throwable __e) {
            throw new org.fusesource.restygwt.client.ResponseFormatException("Response was NOT a valid JSON document", __e);
          }
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
}
