import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class createaccount {
    public Button backButton;
    public TextField usernameField;
    public TextField fullnameField;
    public TextField emailField;
    public PasswordField passwordField;
    public PasswordField repasswordField;
    public ChoiceBox<String> instituteChoice;
    public ChoiceBox<String> countryChoice;
    public Button createButton;

    @FXML
    public void initialize() {
        System.out.println("FUCKING A");

        instituteChoice.getItems().removeAll(instituteChoice.getItems());
        ResultSet rs = magic.query("select distinct inst_name from institutes;", false);
        while(true) {
            try {
                if (!rs.next()) break;
                instituteChoice.getItems().add(rs.getString("inst_name"));
                System.out.println("Inst: " + rs.getString("inst_name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instituteChoice.getSelectionModel().select(0);

        countryChoice.getItems().removeAll(countryChoice.getItems());
        rs = magic.query("select distinct inst_country from institutes;", false);
        while(true) {
            try {
                if (!rs.next()) break;
                countryChoice.getItems().add(rs.getString("inst_country"));
                System.out.println("Country: " + rs.getString("inst_country"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        countryChoice.getSelectionModel().select(0);
    }

    @FXML
    public void creatIt() {
        System.out.println("YEAH!");

        String username = usernameField.getText();
        String fullname = fullnameField.getText();
        String email = emailField.getText();
        String inst = instituteChoice.getValue();
        String country = countryChoice.getValue();
        String password = passwordField.getText();
        String repass = repasswordField.getText();

        if(!password.equals(repass)) {
            ConfirmBox.display("Error", "Passwords don't match!");
            return ;
        }

        String queryline = "select * from create_account('" + username + "', '" + password + "', '" + fullname + "', '" + email + "', '" + inst + "', '" + country + "');";
        ResultSet rs = magic.query(queryline, true);

        try {
            if(rs.next()) {
                String msg = rs.getString(1);
                if(msg.equals("OK")) {
                    ResultSet rs2 = magic.query("select user_id from accounts where username = '" + username + "';", false);
                    if(rs2.next()) loggedout.userId = rs2.getInt("user_id");
                    else loggedout.userId = -1;
                    loggedout.username = username;
                    loggedout.state = true;
                    Main.setScene("home.fxml");
                }
                else {
                    ConfirmBox.display("Error!", msg);
                }
            }
            else {
                ConfirmBox.display("My bad!", "I don't know what caused this :) Leave :) ");
            }
        } catch (SQLException e) {
            System.out.println("Got exception at createIt.createaccount");
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack() {
        Main.setScene("loggedout.fxml");
    }
}
