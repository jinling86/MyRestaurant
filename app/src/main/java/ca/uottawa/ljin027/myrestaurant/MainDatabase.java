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
        out.writeInt(food_1_count);
        out.writeInt(food_2_count);
        out.writeInt(food_3_count);
        out.writeInt(food_4_count);
        out.writeInt(food_1_total);
        out.writeInt(food_2_total);
        out.writeInt(food_3_total);
        out.writeInt(food_4_total);

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
        food_1_count = in.readInt();
        food_2_count = in.readInt();
        food_3_count = in.readInt();
        food_4_count = in.readInt();
        food_1_total = in.readInt();
        food_2_total = in.readInt();
        food_3_total = in.readInt();
        food_4_total = in.readInt();

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

    @Override
    public int describeContents() {
        return 0;
    }

    public MainDatabase() {
        currentUser = null;
        allUsers = new TreeMap<String, String> ();
    }

    // Current logged in user
    static private String currentUser;
    // Signed up users
    static private TreeMap<String, String> allUsers;

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

    // Statistics of orders
    private int orderStatistics = 0;
    private String food_1_name = "Dueling Lobster Tails";
    private String food_2_name = "Lobster Bake";
    private String food_3_name = "Lobster Lovers Dream";
    private String food_4_name = "Lobster Shrimp Salmon";
    private final double price_dueling_lobster_tails = 8.99;
    private final double price_lobster_bake = 9.99;
    private final double price_lobster_lovers_dream = 10.99;
    private final double price_lobster_shrimp_salmon = 11.99;

    private int food_1_count = 0;
    private int food_2_count = 0;
    private int food_3_count = 0;
    private int food_4_count = 0;
    private int food_1_total = 0;
    private int food_2_total = 0;
    private int food_3_total = 0;
    private int food_4_total = 0;
    private final int MAX_FOOD_COUNT = 10;

    public void newOrder() {
        food_1_total += food_1_count;
        food_2_total += food_2_count;
        food_3_total += food_3_count;
        food_4_total += food_4_count;
        food_1_count = 0;
        food_2_count = 0;
        food_3_count = 0;
        food_4_count = 0;
        orderStatistics++;
    }

    public String getOrderStatistics() {
        if(orderStatistics == 0) {
            return "We just open ...";
        } else {
            String statistics = "Today, we sold " + orderStatistics + (orderStatistics == 1 ? " order :\n\n" : " orders :\n\n");
            if (food_1_total != 0)
                statistics += food_1_total + " " + food_1_name + "\n";
            if (food_2_total != 0)
                statistics += food_2_total + " " + food_2_name + "\n";
            if (food_3_total != 0)
                statistics += food_3_total + " " + food_3_name + "\n";
            if (food_4_total != 0)
                statistics += food_4_total + " " + food_4_name + "\n";
            return statistics;
        }
    }

    public String getCount(int index)  {
        if(index < 1 || index > 4)
            return null;
        else {
            switch (index) {
                case 1:
                    return Integer.toString(get_food_1_count());
                case 2:
                    return Integer.toString(get_food_2_count());
                case 3:
                    return Integer.toString(get_food_3_count());
                case 4:
                    return Integer.toString(get_food_4_count());
            }
        }
        return null;
    }

    public String getPrice(int index)  {
        if(index < 1 || index > 4)
            return null;
        else {
            switch (index) {
                case 1:
                    return Double.toString(get_food_1_price())+"\n($)";
                case 2:
                    return Double.toString(get_food_2_price())+"\n($)";
                case 3:
                    return Double.toString(get_food_3_price())+"\n($)";
                case 4:
                    return Double.toString(get_food_4_price())+"\n($)";
            }
        }
        return null;
    }

    public String getTotalPrice() {
        double totalPrice = get_food_1_price()+get_food_2_price()+get_food_3_price()+get_food_4_price();
        return String.format("%.2f $", totalPrice);
    }

    public void more_food_1() { if(food_1_count < MAX_FOOD_COUNT) food_1_count++; }
    public void less_food_1() { if(food_1_count>0) food_1_count--; }
    public int get_food_1_count() { return food_1_count; }
    public double get_food_1_price() { return food_1_count*price_dueling_lobster_tails; }
    public String get_food_1_description() { return food_1_name + " only " + price_dueling_lobster_tails + " $"; }

    public void more_food_2() { if(food_2_count < MAX_FOOD_COUNT) food_2_count++; }
    public void less_food_2() { if(food_2_count>0) food_2_count--; }
    public int get_food_2_count() { return food_2_count; }
    public double get_food_2_price() { return food_2_count*price_lobster_bake; }
    public String get_food_2_description() { return food_2_name + " only " + price_lobster_bake + " $"; }

    public void more_food_3() { if(food_3_count < MAX_FOOD_COUNT) food_3_count++; }
    public void less_food_3() { if(food_3_count>0) food_3_count--; }
    public int get_food_3_count() { return food_3_count; }
    public double get_food_3_price() { return food_3_count*price_lobster_lovers_dream; }
    public String get_food_3_description() { return food_3_name + " only " + price_lobster_lovers_dream + " $"; }

    public void more_food_4() { if(food_4_count < MAX_FOOD_COUNT) food_4_count++; }
    public void less_food_4() { if(food_4_count>0) food_4_count--; }
    public int get_food_4_count() { return food_4_count; }
    public double get_food_4_price() { return food_4_count*price_lobster_shrimp_salmon; }
    public String get_food_4_description() { return food_4_name + " only " + price_lobster_shrimp_salmon + " $"; }

    public boolean orderEnabled() {
        return food_1_count != 0 || food_2_count != 0 || food_3_count != 0 || food_4_count != 0;
    }

    public String getCurrentOrder() {
        String currentOrder = "You ordered :\n\n";
        if(food_1_count != 0)
            currentOrder += food_1_count + " " + food_1_name + "\n";
        if(food_2_count != 0)
            currentOrder += food_2_count + " " + food_2_name + "\n";
        if(food_3_count != 0)
            currentOrder += food_3_count + " " + food_3_name + "\n";
        if(food_4_count != 0)
            currentOrder += food_4_count + " " + food_4_name + "\n";
        currentOrder += "\nTotal " + getTotalPrice();

        return currentOrder;
    }

}
