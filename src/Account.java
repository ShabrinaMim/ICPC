import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public Label fullNameLabel;
    public Label instLabel;
    public Label emailLabel;
    public Hyperlink editAccountLink;
    public ListView<Hyperlink> listView;

    @FXML
    public void initialize() {
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        String queryLine = "" +
                "select username, full_name, email, inst_name, inst_country\n" +
                "from accounts inner join institutes\n" +
                "on accounts.inst_id = institutes.inst_id\n" +
                "where username = '" + loggedout.username + "';";
        ResultSet rs = magic.query(queryLine, false);
        try {
            if(rs.next()) {
                fullNameLabel.setText(rs.getString("full_name"));
                instLabel.setText(rs.getString("inst_name") + ", " + rs.getString("inst_country"));
                emailLabel.setText(rs.getString("email"));
            }
            else {
                fullNameLabel.setText("Darn it!");
                instLabel.setText("Darn it!");
                emailLabel.setText("Darn it!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryForCoach = "" +
                "select t.team_id as team_id, t.team_name as team_name, r.regional_name as regional_name, extract(year from c.contest_date) as contest_year\n" +
                "from teams t, contests c, regionals r\n" +
                "where (\n" +
                "\tt.contest_id = c.contest_id and\n" +
                "\tc.regional_id = r.regional_id and\n" +
                "\tt.coach_id = " + loggedout.userId + "\n" +
                ")" + "order by c.contest_date desc" + ";";
        System.out.println("query to find asCoach: " + queryForCoach);
        final ResultSet asCoach = magic.query(queryForCoach, false);
        try {
            while (asCoach.next()) {
                int teamId = asCoach.getInt("team_id");
                String teamName = asCoach.getString("team_name");
                String contestName = asCoach.getString("regional_name") + " " + asCoach.getString("contest_year");
                Hyperlink temp = new Hyperlink();
                temp.setText(teamName + "\t\tCoach\t\t" + contestName);
                temp.setOnAction(e -> {
                    setTeamViewer(teamId, contestName);
                });

                listView.getItems().add(temp);
            }
        } catch (SQLException e) {
            System.out.println("Exception at coach fetch");
            e.printStackTrace();
        }

        String queryForContestant = "" +
                "select t.team_id as team_id, t.team_name as team_name, r.regional_name as regional_name, extract(year from c.contest_date) as contest_year\n" +
                "from teams t, contests c, regionals r\n" +
                "where (\n" +
                "\tt.contest_id = c.contest_id and\n" +
                "\tc.regional_id = r.regional_id and\n" +
                "\tt.member_1 = " + loggedout.userId + " or\n" +
                "\tt.member_2 = " + loggedout.userId + " or\n" +
                "\tt.member_3 = " + loggedout.userId + "\n" +
                ")" + "order by c.contest_date desc" +";";
        System.out.println("query to find asContestant: " + queryForContestant);
        final ResultSet asContestant = magic.query(queryForContestant, false);
        try {
            while (asContestant.next()) {
                int teamId = asContestant.getInt("team_id");
                String teamName = asContestant.getString("team_name");
                String contestName = asContestant.getString("regional_name") + " " + asContestant.getString("contest_year");
                Hyperlink temp = new Hyperlink();
                temp.setText(teamName + "\t\tContestant\t\t" + contestName);
                temp.setOnAction(e -> {
                    setTeamViewer(teamId, contestName);
                });

                listView.getItems().add(temp);
            }
        } catch (SQLException e) {
            System.out.println("Exception at contestant fetch");
            e.printStackTrace();
        }
    }

    @FXML
    public void setTeamViewer(int teamId, String contestName) {
        System.out.println("Hold on to your horses boy!");
        System.out.println("Team ID: " + teamId);
        System.out.println("Contest: " + contestName);

        TeamViewer.teamId = teamId;
        TeamViewer.contestName = contestName;
        Main.setScene("teamviewer.fxml");
    }

    @FXML
    public void setEditAccountLink() {
        System.out.println("We'll get there!");
        Main.setScene("editaccount.fxml");
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