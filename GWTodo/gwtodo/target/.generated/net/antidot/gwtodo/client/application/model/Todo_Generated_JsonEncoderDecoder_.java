package net.antidot.gwtodo.client.application.model;

public class Todo_Generated_JsonEncoderDecoder_ extends org.fusesource.restygwt.client.AbstractJsonEncoderDecoder<net.antidot.gwtodo.client.application.model.Todo> {
  
  public static final Todo_Generated_JsonEncoderDecoder_ INSTANCE = new Todo_Generated_JsonEncoderDecoder_();
  
  public com.google.gwt.json.client.JSONValue encode(net.antidot.gwtodo.client.application.model.Todo value) {
    if( value==null ) {
      return getNullType();
    }
    com.google.gwt.json.client.JSONObject rc = new com.google.gwt.json.client.JSONObject();
    net.antidot.gwtodo.client.application.model.Todo parseValue = (net.antidot.gwtodo.client.application.model.Todo)value;
    isNotNullValuePut(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.STRING.encode(parseValue.getText()), rc, "text");
    isNotNullValuePut(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.BOOLEAN.encode(parseValue.getState()), rc, "state");
    isNotNullValuePut(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.STRING.encode(parseValue.getId()), rc, "id");
    return rc;
  }
  
  public net.antidot.gwtodo.client.application.model.Todo decode(com.google.gwt.json.client.JSONValue value) {
    if( value == null || value.isNull()!=null ) {
      return null;
    }
    com.google.gwt.json.client.JSONObject object = toObject(value);
    net.antidot.gwtodo.client.application.model.Todo rc = new net.antidot.gwtodo.client.application.model.Todo();
    rc.setText(getValueToSet(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.STRING.decode(object.get("text")), null));
    rc.setState(getValueToSet(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.BOOLEAN.decode(object.get("state")), false));
    rc.setId(getValueToSet(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.STRING.decode(object.get("id")), null));
    return rc;
  }
  
}
