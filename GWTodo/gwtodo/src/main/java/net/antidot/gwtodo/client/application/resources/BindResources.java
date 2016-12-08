package net.antidot.gwtodo.client.application.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by constant on 08/12/2016.
 */
public interface BindResources extends ClientBundle {
    public static final BindResources GWT_RESOURCES = GWT.create(BindResources.class);

    @Source("css/GWTodo.css")
    BindCssResources cssresources();

    @Source("images/psyduck.jpg")
    ImageResource psyDuckImage();
}
