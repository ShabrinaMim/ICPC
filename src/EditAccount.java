import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditAccount {
    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public TextField changeNameField;
    public TextField changeEmailField;
    public ChoiceBox<String> changeInstChoice;
    public ChoiceBox<String> changeCountryChoice;
    public PasswordField newPasswordField;
    public PasswordField retypePasswordField;
    public Button doneButton;

    public String instName, countryName;

    @FXML
    public void initialize() {
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        String tempQueryLine = "" +
                "select inst_name, inst_country\n" +
                "from accounts inner join institutes\n" +
                "on accounts.inst_id = institutes.inst_id\n" +
                "where username = '" + loggedout.username + "';";
        ResultSet tempQ = magic.query(tempQueryLine, false);
        try {
            if(tempQ.next()) {
                instName = tempQ.getString("inst_name");
                countryName = tempQ.getString("inst_country");
            }
        } catch (SQLException e) {
            System.out.println("Eikhane abar ki exception vai?");
            e.printStackTrace();
        }

        changeInstChoice.getItems().removeAll(changeInstChoice.getItems());
        ResultSet rs = magic.query("select distinct inst_name from institutes;", false);
        while(true) {
            try {
                if (!rs.next()) break;
                changeInstChoice.getItems().add(rs.getString("inst_name"));
                System.out.println("Inst: " + rs.getString("inst_name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        changeInstChoice.getSelectionModel().select(instName);

        changeCountryChoice.getItems().removeAll(changeCountryChoice.getItems());
        rs = magic.query("select distinct inst_country from institutes;", false);
        while(true) {
            try {
                if (!rs.next()) break;
                changeCountryChoice.getItems().add(rs.getString("inst_country"));
                System.out.println("Country: " + rs.getString("inst_country"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        changeCountryChoice.getSelectionModel().select(countryName);
    }

    @FXML
    public void setDoneButton() {
        System.out.println("You're done. Huh?");

        Boolean ok = true;
        String errorLog = "";
        int updCnt = 0;
        String updString = "";

        String newName = changeNameField.getText();
        if(!newName.isEmpty()) {
            if(updCnt > 0) updString += ",\n";
            updString += "full_name = '" + newName + "'";
            ++updCnt;
        }

        String newEmail = changeEmailField.getText();
        if(!newEmail.isEmpty()) {
            if(updCnt > 0) updString += ",\n";
            updString += "email = '" + newEmail + "'";
            ++updCnt;

            // query for existence already
            ResultSet rs = magic.query("select * from accounts where email = '" + newEmail +"';", false);
            try {
                if(rs.next()) {
                    ok = false;
                    errorLog += "Email already exists!\n";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String newPass = newPasswordField.getText();
        if(!newPass.isEmpty()) {
            if(updCnt > 0) updString += ",\n";
            updString += "password = '" + newPass + "'";
            ++updCnt;

            if(!newPass.equals(retypePasswordField.getText())) {
                ok = false;
                errorLog += "Passwords doesn't match!\n";
            }
        }

        if(ok) {
            System.out.println("Do nothing for now :p");

            // Update everything except institute
            if(updCnt > 0) {
                String updQueryLine = "" +
                        "update accounts\n" +
                        "set " + updString + "\n" +
                        "where username = '" + loggedout.username + "';";
                System.out.println("Update query: " + updQueryLine);
                magic.update(updQueryLine);
            }

            int instId = -1;
            String getInstQuery = "" +
                    "select inst_id from institutes\n" +
                    "where inst_name = '" + changeInstChoice.getValue() + "' and \n" +
                    "      inst_country = '" + changeCountryChoice.getValue() + "';";
            ResultSet rs = magic.query(getInstQuery, false);
            try {
                if(rs.next()) {
                    instId = rs.getInt("inst_id");
                    String updInstQuery = "" +
                        "update accounts\n" +
                        "set inst_id = " + instId + "\n" +
                        "where username = '" + loggedout.username + "';";
                    System.out.println("Update Inst: " + updInstQuery);
                    magic.update(updInstQuery);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ConfirmBox.display("Success!", "Changes made successfully!");
            Main.setScene("account.fxml");
        }
        else {
            ConfirmBox.display("Errors!", errorLog);
        }
    }

    @FXML
    public void setLogoutLink() {
        Main.setScene("loggedout.fxml");
    }

    @FXML
    public void setBaylorLink() { Main.setScene("home.fxml"); }

    @FXML
    public void setFinderLink() { Main.setScene("regionalFinder.fxml"); }

    @FXML
    public void setUpcomingLink() { Main.setScene("upcoming.fxml"); }

    @FXML
    public void setWorldfinalLink() { Main.setScene("worldFinal.fxml"); }

    @FXML
    public void setCreateTeamLink() {
        if(loggedout.username.equals("-1")) {
            ConfirmBox.display("No can do!", "Guests can't create teams!");
        }
        else Main.setScene("createTeam.fxml");
    }

    @FXML
    public void setAccountLink() {
        if(loggedout.username.equals("-1")) {
            ConfirmBox.display("No can do!", "Guests don't really have a profile!");
        }
        else Main.setScene("account.fxml");
    }
}
