package view;

import controller.Controller;

public class View {
    private static Controller controller;

    public static void setController(Controller controller1) {
        controller = controller1;
    }

    public static Controller getController() {
        return controller;
    }
}
