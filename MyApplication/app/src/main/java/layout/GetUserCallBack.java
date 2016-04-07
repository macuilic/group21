package layout;

/**
 * Created by Lotte on 29.03.2016.
 *  Acts as a listener to let the app now that a connection's been made
 *  and that it's finished.
 */
public interface GetUserCallBack {
    public  abstract void done(Contact returnedcontact);
}
