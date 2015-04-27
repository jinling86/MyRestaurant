package ca.uottawa.ljin027.myrestaurant;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import ca.uottawa.ljin027.myrestaurant.R;

/**
 * @author Ling Jin
 * Created by Ling on 10/02/2015.
 * This class implements the main activity of our app. Moreover, there is only one activity in our
 * app. The consider is that althought the four activities can have a operations chain like:
 * show welcome screen -> show contact of the restaurant -> make order -> log in
 * But it is more reasonable to give these activities more independency.
 * So we use a Action Bar (Requirement 1), not a menu to implement it.
 * Action bar is based on Fragments, whereas menu is based on actions.
 *
 * In this class, an action bar adaptor is used to manage the four fragments of our apps.
 * Also, it need to process the save and resume operations.
 * The menu of our app has two buttons.
 * The resume button (also called exit) put the app into the system background, the state will be saved.
 * The exit button (also called discard) kill the app directly, no state will be saved.
 *
 * (Requirement 5) is implemented in every fragments. It involves two ImageButtons.
 * (Requirement 7) is documented in the comments part of all source files.
 */
public class MainActivity extends ActionBarActivity {

    private static final String TAG = "~~~MainActivity";
    TabsAdapter tabsAdapter;
    ViewPager viewPager;

    final private int opaque = 255;
    final private int semi_opaque = 255;

    static public MainDatabase data = null;

    public static int WELCOME_FRAGMENT = 0;
    public static int CONTACT_FRAGMENT = 1;
    public static int ORDER_FRAGMENT = 2;
    public static int LOGIN_FRAGMENT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new MainDatabase();

        // Initialize the action bar
        viewPager = (ViewPager) findViewById(R.id.pager);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        tabsAdapter = new TabsAdapter(this, viewPager);
        tabsAdapter.addTab(actionBar.newTab().setIcon(R.drawable.ic_welcome),WelcomeFragment.class, null);
        tabsAdapter.addTab(actionBar.newTab().setIcon(R.drawable.ic_contact),ContactFragment.class, null);
        tabsAdapter.addTab(actionBar.newTab().setIcon(R.drawable.ic_order),OrderFragment.class, null);
        tabsAdapter.addTab(actionBar.newTab().setIcon(R.drawable.ic_login),LoginFragment.class, null);

        // http://stackoverflow.com/questions/25867376/animation-in-viewpager-tab-change-fadein-fadeout-as-like-linkedin-introduction
        final LayerDrawable background = (LayerDrawable) viewPager.getBackground();
        background.getDrawable(WELCOME_FRAGMENT).setAlpha(opaque);
        background.getDrawable(LOGIN_FRAGMENT).setAlpha(0);
        background.getDrawable(ORDER_FRAGMENT).setAlpha(0);
        background.getDrawable(CONTACT_FRAGMENT).setAlpha(0);

        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
            int index = (Integer) view.getTag();
            Drawable currentDrawable = background.getDrawable(index);

            if(position <= -1 || position >= 1) {
                currentDrawable.setAlpha(0);
            } else if(position == 0) {
                currentDrawable.setAlpha(opaque);
            } else {
                // The square operation make the effect smooth
                currentDrawable.setAlpha((int)(opaque - position*position*semi_opaque));
            }
            }
        });

        // Restore previous system state
        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab position", 0));
            data = savedInstanceState.getParcelable("order state");
            Log.i(TAG, "Restore state");
        }
    }

    // We save two sets of values when the app is temporarily stopped
    // The first is the position of the fragment tab; The second is current signed up/ logged in users
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab position", getSupportActionBar().getSelectedNavigationIndex());
        outState.putParcelable("order state", data);
        Log.i(TAG, "Save state");
    }

    // Create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Two buttons of the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            // Move activity to background
            moveTaskToBack(true);
            return true;
        } else if (id == R.id.action_discard) {
            // Kill activity directly
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Switch among fragments, hope it work well
    public void gotoFragment(int fragmentIndex) {
        if(fragmentIndex == WELCOME_FRAGMENT
            || fragmentIndex == LOGIN_FRAGMENT
            || fragmentIndex == ORDER_FRAGMENT
            || fragmentIndex == CONTACT_FRAGMENT)
            tabsAdapter.onPageSelected(fragmentIndex);
    }

    // The code of the adapter refers to
    // http://developer.android.com/reference/android/support/v4/view/ViewPager.html
    // We modified the class in order to implement our app function.
    // (Set meaningful tags, close soft keyboards... )
    public static class TabsAdapter extends FragmentPagerAdapter
        implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

        private final Context mContext;
        private final Activity mActivity;
        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }

        public TabsAdapter(ActionBarActivity activity, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mActivity = activity;
            mActionBar = activity.getSupportActionBar();
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
           return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mActionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            Object tag = tab.getTag();
            for (int i=0; i<mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }

            // Close soft keyboard
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = mActivity.getCurrentFocus();
            if(view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if(object instanceof WelcomeFragment) {
                view.setTag(WELCOME_FRAGMENT);
            }
            if(object instanceof LoginFragment) {
                view.setTag(LOGIN_FRAGMENT);
            }
            if(object instanceof OrderFragment) {
                view.setTag(ORDER_FRAGMENT);
            }
            if(object instanceof ContactFragment) {
                view.setTag(CONTACT_FRAGMENT);
            }

            return super.isViewFromObject(view, object);
        }
    }

}
