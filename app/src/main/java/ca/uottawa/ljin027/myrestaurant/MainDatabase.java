package ca.uottawa.ljin027.myrestaurant;

/**
 * Created by Ling on 2015/2/14.
 */
public class MainDatabase {
    // Statistics of orders
    static private int orderStatistics = 0;
    // Current logged in user
    static private String currentUser = null;

    public void newOrder() {
        orderStatistics++;
    }
    public int getOrderStatistics() {
        return orderStatistics;
    }
    public void newUser(String username) { currentUser = username; }
    public boolean hasUser() { return !(currentUser == null); }
}
