package layout;


import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Conor on 02/04/2016.
 *
 *      Class creates Tournament_Data object that stores information about a tournament.
 *      Getters allow us to retrieve the information about the tournament.
 *          - This stops data injection, and manipulation of data.
 *      Setters are used for testing.
 *
 *      @Tournament_Data
 *      Contains all of the relevant info from the tournaments database, and records
 *      ecery table into a Tournament_Data object.
 */

public class Tournament_Data {

    private String[] tourNames;
    private String tour_name, tour_game, tour_region;
    private int id, num_players, max_p, min_p, entry_fee;
    private String tour_timestamp;

    public Tournament_Data(int id, String name, String game, String region, String timestamp, int num_players, int max_p, int min_p, int entry_fee) {

        this.id = id;
        this.tour_name = name;
        this.tour_game = game;
        this.tour_region = region;
        this.num_players = num_players;
        this.max_p = max_p;
        this.min_p = min_p;
        this.entry_fee = entry_fee;
        java.util.Date utilDate = new java.util.Date();
        this.tour_timestamp = timestamp;
    }

    public Tournament_Data(String[] tour_Names) {
        this.tourNames = tour_Names;
    }

    public Tournament_Data(){

    }

    int getId() {
        return id;
    }

    String[] getTourNames() {
        return tourNames;
    }

    void setTourNames(String[] tour_name_list) {
        tourNames = tour_name_list;
    }
    String getTour_name() {
        return tour_name;
    }

    String getTour_game() {
        return tour_game;
    }

    String getTour_region() {
        return tour_region;
    }

    int getNum_players() {
        return num_players;
    }

    int getMax_p() {
        return max_p;
    }

    int getMin_p() {
        return min_p;
    }

    int getEntry_fee() {
        return entry_fee;
    }

    String getTour_Date() {
        String s = tour_timestamp.substring(0, Math.min(tour_timestamp.length(), 10));
        s = s.replaceAll("-", "/");
        return s;
    }

    String getTour_Time() {
        String s = tour_timestamp.substring(11, Math.min(tour_timestamp.length(), tour_timestamp.length()));
        return s;
    }


    void printTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(tour_timestamp)); //this will print without ms
    }

}
