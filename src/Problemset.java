import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Problemset {
    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public Label contestNameLabel;
    public Label problemsetLabel;
    public Hyperlink backLink;

    public static String contestName;
    public static int contestId;

    @FXML
    public void initialize() {
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        contestNameLabel.setText(contestName);

        String queryLine = "" +
                "select prob_link\n" +
                "from problemset inner join contests\n" +
                "on problemset.prob_id = contests.prob_id\n" +
                "where contest_id = " + contestId + ";";
        System.out.println("Query: " + queryLine);

        String problemset = null;
        ResultSet rs = magic.query(queryLine, false);
        try {
            if(rs.next()) {
                problemset = rs.getString("prob_link");
            }
            else {
                problemset = "Oops! Problemset not found!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        problemsetLabel.setText(problemset);
    }

    @FXML
    public void setBackLink() {
        Contests.contestId = contestId;
        Contests.contestName = contestName;
        Main.setScene("contests.fxml");
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
