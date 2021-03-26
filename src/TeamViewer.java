import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamViewer {
    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public static int teamId;
    public static String contestName;

    public Label instNameLabel;
    public Label teamNameLabel;
    public Label contestNameLabel;
    public Label coachNameLabel;
    public Label xNameLabel;
    public Label yNameLabel;
    public Label zNameLabel;

    @FXML
    public void initialize() {
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        int contestId = -1, instId = -1, coachId = -1, xId = -1, yId = -1, zId = -1;
        String instName = null, teamName = null, coachName = null, xName = null, yName = null, zName = null;

        try {
            ResultSet teamResults = magic.query("select * from teams where team_id = " + teamId + ";", false);
            if(teamResults.next()) {
                contestId = teamResults.getInt("contest_id");
                teamName = teamResults.getString("team_name");
                instId = teamResults.getInt("team_institute");
                coachId = teamResults.getInt("coach_id");
                xId = teamResults.getInt("member_1");
                yId = teamResults.getInt("member_2");
                zId = teamResults.getInt("member_3");
            }
        } catch (SQLException e) {
            System.out.println("Exception at initialize.TeamViewer - 1");
            e.printStackTrace();
        }

        try {
            ResultSet rs = magic.query("select inst_name from institutes where inst_id = " + instId + ";", false);
            if(rs.next()) instName = rs.getString("inst_name");

            rs = magic.query("select full_name from accounts where user_id = " + coachId + ";", false);
            if(rs.next()) coachName = rs.getString("full_name");

            rs = magic.query("select full_name from accounts where user_id = " + xId + ";", false);
            if(rs.next()) xName = rs.getString("full_name");

            rs = magic.query("select full_name from accounts where user_id = " + yId + ";", false);
            if(rs.next()) yName = rs.getString("full_name");

            rs = magic.query("select full_name from accounts where user_id = " + zId + ";", false);
            if(rs.next()) zName = rs.getString("full_name");
        } catch (SQLException e) {
            System.out.println("Exception at initialize.TeamViewer - 2");
            e.printStackTrace();
        }

        instNameLabel.setText(instName);
        teamNameLabel.setText(teamName);
        contestNameLabel.setText(contestName);
        coachNameLabel.setText(coachName);
        xNameLabel.setText(xName);
        yNameLabel.setText(yName);
        zNameLabel.setText(zName);
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
