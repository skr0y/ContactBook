package view;

import controller.Controller;
import model.Model;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Model model = Model.getInstance();
        Controller controller = new Controller(model);
        View.setController(controller);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
