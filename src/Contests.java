import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Contests {
    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public Label contestDateLabel;
    public Label contestNameLabel;
    public Hyperlink registerTeamLink;
    public Hyperlink problemsetLink;
    public Hyperlink standingsLink;

    public static int contestId;
    public static String contestName;

    @FXML
    public void initialize() {
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        System.out.println("Contest ID: " + contestId);
        System.out.println("Contest Name: " + contestName);

        contestNameLabel.setText(contestName);

        ResultSet rs = magic.query("select contest_date from contests where contest_id = " + contestId, false);
        try {
            if(rs.next()) {
                String contestDate = rs.getString("contest_date");
                System.out.println("Contest Date: " + contestDate);
                contestDateLabel.setText(contestDate);
            }
            else {
                contestDateLabel.setText("");
            }
        } catch (SQLException e) {
            System.out.println("WTF!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("FUCKING HELL");
            e.printStackTrace();
        }


        rs = magic.query("select * from active_contest(" + contestId + ");", false);
        try {
            if(rs.next()) {
                ResultSet checkRegional = magic.query("select regional_id from contests where contest_id = " + contestId + ";", false);
                checkRegional.next();
                int regionalId = checkRegional.getInt("regional_id");
                if(regionalId > 0 && rs.getBoolean(1) && !loggedout.username.equals("-1")) {
                    registerTeamLink.setText("Register Your Team");
                    registerTeamLink.setOnAction(e -> {
                        setRegisterTeamLink();
                    });
                }
                else {
                    registerTeamLink.setText("");
                }
            }
            else {
                System.out.println("Contest doesn't exist? :|");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        rs = magic.query("select * from contests where contest_id = " + contestId + " and contest_date < CURRENT_DATE;", false);
        try {
            if(rs.next()) {
                problemsetLink.setText("Problemset");
                ResultSet tempRS = magic.query("select prob_link from problemset where prob_id = " + rs.getString("prob_id") + ";", false);

                if(tempRS.next()) {
                    String prob_link = tempRS.getString("prob_link");
                    problemsetLink.setOnAction(e -> {
                        setProblemsetLink();
                    });
                }

                standingsLink.setText("Standings");
                standingsLink.setOnAction(e -> {
                    setStandingsLink();
                });
            }
            else {
                problemsetLink.setText("");
                standingsLink.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setRegisterTeamLink() {
        System.out.println("Register at this contest");
        CreateTeam.contestName = contestName;
        CreateTeam.contestId = contestId;
        Main.setScene("createTeam.fxml");
    }

    @FXML
    public void setProblemsetLink() {
        System.out.println("Patience!");

        Problemset.contestId = contestId;
        Problemset.contestName = contestName;
        Main.setScene("problemset.fxml");
    }

    @FXML
    public void setStandingsLink() {
        System.out.println("A real long patience!!");

        Standings.contestId = contestId;
        Standings.contestName = contestName;
        Main.setScene("standings.fxml");
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
