import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public Hyperlink backLink;

    @FXML
    public void goBack() {
        Main.setScene("loggedout.fxml");
    }

    @FXML
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        ResultSet rs = magic.query("select user_id, password from accounts where username = '" + username + "';", false);
        try {
            if(rs.next()) {
                String temp = rs.getString("password");
                if(password.equals(temp)) {
                    System.out.println("Logs in correctly!");
                    loggedout.userId = rs.getInt("user_id");
                    loggedout.username = username;
                    loggedout.state = true;
                    Main.setScene("home.fxml");
                }
                else {
                    ConfirmBox.display("ERROR!", "Passwords doesn't match. :thinking:");
                }
            }
            else {
                ConfirmBox.display("Error!", "No such username!");
            }
        } catch (SQLException e) {
            System.out.println("Exception at login :'(");
            e.printStackTrace();
        }
    }
}