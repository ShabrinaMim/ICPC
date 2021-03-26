import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Standings {
    public static class Data {
        private Integer place;
        private String team;
        private Integer score;
        private Integer penalty;

        public Data(Integer place, String team, Integer score, Integer penalty) {
            this.place = place;
            this.team = team;
            this.score = score;
            this.penalty = penalty;
        }

        public Integer getPlace() {
            return place;
        }

        public String getTeam() {
            return team;
        }

        public Integer getScore() {
            return score;
        }

        public Integer getPenalty() {
            return penalty;
        }
    }

    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public Label contestNameLabel;
    public Hyperlink backLink;
    public TableView tab;
    public TableColumn colPlace;
    public TableColumn colTeam;
    public TableColumn colScore;
    public TableColumn colPenalty;

    public static int contestId;
    public static String contestName;

    @FXML
    public void initialize() {
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        contestNameLabel.setText(contestName);

        tab.setEditable(true);

        colPlace.setCellValueFactory(new PropertyValueFactory<Data, Integer>("place"));
        colTeam.setCellValueFactory(new PropertyValueFactory<Data, String>("team"));
        colScore.setCellValueFactory(new PropertyValueFactory<Data, Integer>("score"));
        colPenalty.setCellValueFactory(new PropertyValueFactory<Data, Integer>("penalty"));

        tab.getColumns().removeAll(tab.getColumns());
        tab.getColumns().addAll(colPlace, colTeam, colScore, colPenalty);

//        tab.getItems().add(new Data(1, "DU Epinephrine", 7, 672));
//        tab.getItems().add(new Data(2, "BUET Awww!", 7, 725));
//        tab.getItems().add(new Data(3, "BUET MGGA", 7, 989));
//        tab.getItems().add(new Data(4, "BUET KnightMare", 6, 354));

        String queryRank = "" +
                "SELECT TEAM_NAME, SCORE, PENALTY\n" +
                "FROM STANDINGS INNER JOIN TEAMS\n" +
                "ON STANDINGS.TEAM_ID = TEAMS.TEAM_ID AND STANDINGS.CONTEST_ID = TEAMS.CONTEST_ID\n" +
                "WHERE STANDINGS.CONTEST_ID = " + contestId + "\n" +
                "ORDER BY SCORE DESC, PENALTY ASC;";

        ResultSet rs = magic.query(queryRank, false);
        int cnt = 0;
        try {
             while(rs.next()) {
                 Integer place = ++cnt;
                 String teamName = rs.getString("team_name");
                 Integer score = rs.getInt("score");
                 Integer penalty = rs.getInt("penalty");
                 Data data = new Data(place, teamName, score, penalty);
                 tab.getItems().add(data);
             }
        } catch (SQLException e) {
            System.out.println("Exception in Standings Grab");
            e.printStackTrace();
        }
    }

    @FXML
    public void setBackLink() {
        Contests.contestName = contestName;
        Contests.contestId = contestId;
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