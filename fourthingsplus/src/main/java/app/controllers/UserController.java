package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addRoutes(Javalin app) {
        app.get("/", ctx -> ctx.render("index"));
        app.post("/login", ctx -> login(ctx));
        app.get("/task", ctx -> task(ctx));
        app.get("/createaccount", ctx -> ctx.render("createaccount"));
        app.post("/createUser", ctx -> createAccount(ctx));
    }

    private void createAccount(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String retypePassword = ctx.formParam("retype-password");

        try {
            User user = userService.createUser(username, password);
            ctx.redirect("/");
        } catch (DatabaseException e) {
            ctx.attribute("errorUsername","Error: Username taken");
            ctx.attribute("errorPassword","Error: Password mismatch");
        }
    }

    private void task(Context ctx) {
        User currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.render("index");
            return;
        }

        ctx.attribute("welcomeuser", "Welcome " + currentUser.getUserName());
        ctx.render("task");
    }

    private void login(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        try {
            User user = userService.login(username, password);

            if (user != null) {
                ctx.sessionAttribute("currentUser", user);
                ctx.redirect("/task");
            } else {
                ctx.attribute("errorLogin", "Invalid username or password");
                ctx.render("index");
            }

        } catch (DatabaseException e) {
            ctx.attribute("errorLogin", "System error. Please try again later.");
            ctx.render("index");
        }
    }
}
