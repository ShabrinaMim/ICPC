import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;

import javax.swing.plaf.synth.Region;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionalFinder {
    public Hyperlink accountLink;
    public Hyperlink finderLink;
    public Hyperlink baylorLink;
    public Hyperlink logoutLink;
    public Hyperlink upcomingLink;
    public Hyperlink worldfinalLink;
    public Hyperlink createTeamLink;

    public ChoiceBox<String> regionChoice;
    public ChoiceBox<String> yearChoice;
    public Button findItButton;

    @FXML
    public void initialize() {
        System.out.println("In Regional Finder!");
        if(loggedout.username.equals("-1")) accountLink.setText("Guest");
        else accountLink.setText(loggedout.username);

        System.out.println("HERE - 1");
//        regionChoice.getItems().removeAll(regionChoice.getItems());
        System.out.println("HERE - 2");
        ResultSet rs = magic.query("select * from regions where region_id > 0;", false);
        System.out.println("At least here ! ");
        while(true) {
            try {
                if (!rs.next()) break;
                regionChoice.getItems().add(rs.getString("region_name"));
            } catch (SQLException e) {
                System.out.println("Exception caught at init");
                e.printStackTrace();
            }
        }
        regionChoice.getSelectionModel().select(0);

//        yearChoice.getItems().removeAll(yearChoice.getItems());
        rs = magic.query("select distinct extract(year from contest_date) as contest_year from contests order by contest_year desc;", false);
        while(true) {
            try {
                if (!rs.next()) break;
                yearChoice.getItems().add(rs.getString("contest_year"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        yearChoice.getSelectionModel().select(0);
    }

    @FXML
    public void setFindIt() {
        System.out.println("So far nothing now");

        String chosenRegion = regionChoice.getValue();
        int year = Integer.valueOf(yearChoice.getValue());
        System.out.println(chosenRegion + ", " + year);

        ResultSet rs = magic.query("select region_id from regions where region_name = '" + chosenRegion + "';", false);
        try {
            if(rs.next()) {
                int region_id = rs.getInt("region_id");
                Regional.regionId = region_id;
                Regional.year = year;
                System.out.println(region_id + " : " + year);
                Main.setScene("regional.fxml");
            }
            else {
                ConfirmBox.display("My Bad!", "No such region exists actually :|");
            }
        } catch (SQLException e) {
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
