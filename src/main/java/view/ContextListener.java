package view;

import controller.Controller;
import model.Model;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private static SessionFactory sessionFactory;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Model model = Model.getInstance();
        Controller controller = new Controller(model);
        View.setController(controller);

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (getSessionFactory() != null) {
            getSessionFactory().close();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
