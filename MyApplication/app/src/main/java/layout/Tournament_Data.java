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
 */

public class Tournament_Data {

    private String[] tourNames;
    private String tour_name, tour_game, tour_region;
    private int num_players, max_p, min_p, entry_fee;
    private Timestamp tour_timestamp;

    public Tournament_Data(String name, String game, String region, int  num_players, int max_p, int min_p, int entry_fee) {
        this.tour_name = name;
        this.tour_game = game;
        this.tour_region = region;
        this.num_players = num_players;
        this.max_p = max_p;
        this.min_p = min_p;
        this.entry_fee = entry_fee;
        java.util.Date utilDate = new java.util.Date();
        tour_timestamp = new java.sql.Timestamp(utilDate.getTime());
    }

    public Tournament_Data(String[] tour_Names) {
        this.tourNames = tour_Names;
    }

    public Tournament_Data(){

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

    //Is of type java.sql.Timestamp
    Timestamp getTour_timestamp() {
        return tour_timestamp;
    }

    void printTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(tour_timestamp)); //this will print without ms
    }

}
