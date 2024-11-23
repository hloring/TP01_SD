import view.UserView;
import controller.UserController;

import javax.swing.SwingUtilities;

public class Main { // Usando UDP e TCP
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserView view = new UserView();
            new UserController(view);
        });
    }
}