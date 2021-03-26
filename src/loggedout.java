import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class loggedout {
    public Button createAccount;
    public Button login;
    public Button guest;

    public static int userId;   // global vairable
    public static String username;     //global variable all over the program. "-1" means guest
    public static Boolean state = false;    // logged in or not

    @FXML
    public void initialize() {
        System.out.println("Logged out page initialized");
    }

    @FXML
    public void funcCreateAccount() {
        Main.setScene("createaccount.fxml");
    }

    @FXML
    public void funcLogin() {
        Main.setScene("login.fxml");
    }

    @FXML
    public void funcGuest() {
        state = true;
        username = "-1";
        userId = -1;
        Main.setScene("home.fxml");
    }
}