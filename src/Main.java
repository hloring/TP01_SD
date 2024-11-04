import view.UserView;
import controller.UserController;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserView view = new UserView();
            new UserController(view);
        });
    }
}