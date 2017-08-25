package view;

import controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (int) req.getSession().getAttribute("user");
        req.setAttribute("contacts", View.getController().getContactController().getAll(userId));
        req.setAttribute("groups", View.getController().getGroupController().getAll(userId));
        req.getRequestDispatcher("menu.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (int) req.getSession().getAttribute("user");
        Controller controller = View.getController();

        if (req.getAttribute("deleteContact") != null) {
            controller.getContactController().delete(Integer.parseInt((String) req.getAttribute("deleteContact")));
        }
        if (req.getAttribute("deleteGroup") != null) {
            controller.getGroupController().delete(Integer.parseInt((String) req.getAttribute("deleteGroup")));
        }

        if (req.getAttribute("contactId") != null) {
            Map<String, Object> contact = new HashMap<>();
            contact.put("id", Integer.parseInt((String) req.getAttribute("contactId")));
            contact.put("firstName", req.getAttribute("firstName"));
            contact.put("lastName", req.getAttribute("lastName"));
            contact.put("phoneNumber", req.getAttribute("phoneNumber"));
            contact.put("userId", userId);
            controller.getContactController().add(contact);
        }
        if (req.getAttribute("groupId") != null) {
            Map<String, Object> group = new HashMap<>();
            group.put("id", req.getAttribute("groupId"));
            group.put("groupName", req.getAttribute("groupName"));
            group.put("userId", userId);
            controller.getGroupController().add(group);
        }
    }
}
