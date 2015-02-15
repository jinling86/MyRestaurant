package ca.uottawa.ljin027.myrestaurant;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Ling on 2015/2/14.
 */
public class MainDatabase implements Parcelable {
    public static final Parcelable.Creator<MainDatabase> CREATOR
            = new Parcelable.Creator<MainDatabase>() {
        public MainDatabase createFromParcel(Parcel in) {
            return new MainDatabase(in);
        }
        public MainDatabase[] newArray(int size) {
            return new MainDatabase[size];
        }
    };

    final private static int CURRENT_USER_VALID = 1;
    final private static int CURRENT_USER_INVALID = 0;

    public void writeToParcel(Parcel out, int flags) {
        // Save order number
        out.writeInt(orderStatistics);

        // Save current user
        if(currentUser == null) {
            out.writeInt(CURRENT_USER_INVALID);
        } else {
            out.writeInt(CURRENT_USER_VALID);
            out.writeString(currentUser);
        }

        // Save user database
        out.writeInt(allUsers.size());
        Set<String> users = allUsers.keySet();
        for(String user: users) {
            out.writeString(user);
            out.writeString(allUsers.get(user));
        }
    }

    private MainDatabase(Parcel in) {
        // Restore order number
        orderStatistics = in.readInt();

        // Restore current user
        currentUser = null;
        int currentUserValid = in.readInt();
        if(currentUserValid == CURRENT_USER_VALID)
            currentUser = in.readString();

        allUsers = new TreeMap<String, String> ();
        int totalUsers = in.readInt();
        for(int i = 0; i < totalUsers; i+=2) {
            String name = in.readString();
            String password = in.readString();
            allUsers.put(name, password);
        }
    }

    public int describeContents() {
        return 0;
    }

    public MainDatabase() {
        orderStatistics = 0;
        currentUser = null;
        allUsers = new TreeMap<String, String> ();
    }

    // Statistics of orders
    static private int orderStatistics;
    // Current logged in user
    static private String currentUser;
    // Signed up users
    static private TreeMap<String, String> allUsers;

    public void newOrder() {
        orderStatistics++;
    }
    public int getOrderStatistics() {
        return orderStatistics;
    }
    public void newUser(String username) { currentUser = username; }
    public boolean hasUser() { return !(currentUser == null); }
    public String getCurrentUser() { return currentUser; }
    public boolean hasRegistered(String username) {
        return allUsers.containsKey(username);
    }
    public boolean hasRightPassword(String username, String password) {
        return allUsers.get(username).equals(password);
    }
    public void addUser(String username, String password) {
        allUsers.put(username, password);
    }

}
