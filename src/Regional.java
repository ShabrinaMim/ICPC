import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Regional {
    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public ListView<Hyperlink> listView;
    public Label regionName;

    public static int regionId;
    public static int year;

    @FXML
    public void initialize() {
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        String queryRegionalContests =
                "select contest_id, regionals.regional_name as regional_name, extract(year from contest_date) as contest_year\n" +
                "from contests inner join regionals\n" +
                "on contests.regional_id = regionals.regional_id \n" +
                "where regionals.region_id = " + regionId + " and extract(year from contest_date) = " + year + ";";

//        System.out.println("query to : " + queryRegionalContests);

        final ResultSet rs = magic.query(queryRegionalContests, false);
        while(true) {
            try {
                if (!rs.next()) break;

                String tempContestName = rs.getString("regional_name") + " " + rs.getString("contest_year");
                int tempContestId = rs.getInt("contest_id");

                System.out.println("Contest " + tempContestId + ": " + tempContestName);

                Hyperlink temp = new Hyperlink();
                temp.setText(tempContestName);
                temp.setOnAction(e -> {
                    setContestLink(tempContestId, tempContestName);
                });
                listView.getItems().add(temp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // set regional name (optional)
        ResultSet rs2 = magic.query("select region_name from regions where region_id = " + regionId + ";", false);
        try {
            if(rs2.next()) {
                String temp = rs2.getString("region_name");
                System.out.println("REGION NAME: " + temp);
                try {
                    regionName.setText("in " + temp);
                } catch (Exception e) {
                    System.out.println("Ooops! Can't add region_name.");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public static void setContestLink(int contest_id, String contest_name) {
        System.out.println("Setting Contest wait!");
        System.out.println("Req: " + contest_id);

        Contests.contestId = contest_id;
        Contests.contestName = contest_name;
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