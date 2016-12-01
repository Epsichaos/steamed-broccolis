package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

/**
 * Created by constant on 29/11/2016.
 */
public class HelloController extends Controller {

    public static Result index() {
        return ok(hello.render("Hello page"));
    }
}