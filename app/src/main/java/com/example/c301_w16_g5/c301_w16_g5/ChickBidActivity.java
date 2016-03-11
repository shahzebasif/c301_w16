package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Observer;

/**
 * This activity is responsible for placing the toolbar at the top of every
 * activity screen.  It will be inherited from by the other activities (except
 * the welcome screen).
 *
 * @author  Satyen
 * @version 1.4, 03/02/2016
 */
public abstract class ChickBidActivity extends AppCompatActivity implements GenericView {

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                /*
                FIXME: doesn't work as wanted
                should go to previous activity instance not to parent
                 */
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.home_button:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.notifications_button:
                startActivity(new Intent(this, NotificationsActivity.class));
                break;
        }
        return false;
    }

    // TODO: reference source
    // used to make home icon disappear
    // http://stackoverflow.com/questions/5440601/android-how-to-enable-disable-option-menu-item-on-button-click answer by Frank
    // accessed on Mar 2 2016 at 3:20 pm by Satyen
    // FIXME: smells horrendous
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.getClass().equals(HomeActivity.class) || this.getClass().equals(NotificationsActivity.class)) {
            menu.clear();
            onCreateOptionsMenu(menu);
        } else {
            return true;
        }

        if (this.getClass().equals(HomeActivity.class)) {
            menu.removeItem(R.id.home_button);
        } else if (this.getClass().equals(NotificationsActivity.class)) {
            menu.removeItem(R.id.notifications_button);
        }
        return true;
    }
}
