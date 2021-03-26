import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.soap.Text;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateTeam {
    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public Label contestNameLabel;
    public TextField teamNameField;
    public Label coachNameLabel;
    public TextField contestAField;
    public TextField contestBField;
    public TextField contestCField;
    public Button submitButton;

    public static String contestName;
    public static int contestId;

    @FXML
    public void initialize() {
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        contestNameLabel.setText(contestName);
        coachNameLabel.setText(loggedout.username);
    }

    @FXML
    public void setSubmitButton() {
        String queryLine = "" +
                "select * from create_team(" + contestId + ", '" +
                teamNameField.getText() + "', '" +
                loggedout.username + "', '" +
                contestAField.getText() + "', '" +
                contestBField.getText() + "', '" +
                contestCField.getText() + "');";
        System.out.println("queryLine: " + queryLine);
        System.out.println("That'd do for now!");

        ResultSet rs = magic.query(queryLine, true);
        try {
            if(rs.next()) {
                String msg = rs.getString(1);
                if(msg.equals("OK")) {
                    ConfirmBox.display("Success!", "Successfully registered!");
                    Main.setScene("home.fxml");
                }
                else ConfirmBox.display("Umm...", msg);
            }
        } catch (SQLException e) {
            System.out.println("Abar ki exception vai?");
            e.printStackTrace();
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