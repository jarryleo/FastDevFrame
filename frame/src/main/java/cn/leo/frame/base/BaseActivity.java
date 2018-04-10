package cn.leo.frame.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Leo on 2018/4/9.
 */

public class BaseActivity extends AppCompatActivity {
    public Bundle getBundle() {
        return getIntent().getBundleExtra("bundle");
    }

    public void jumpTo(Class<Activity> activityClass) {
        super.startActivity(new Intent(this, activityClass));
    }

    public void jumpTo(Class<Activity> activityClass, int requestCode) {
        super.startActivityForResult(new Intent(this, activityClass), requestCode);
    }

    public void jumpTo(Class<Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("bundle", bundle);
        super.startActivity(intent);
    }

    public void jumpTo(Class<Activity> activityClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("bundle", bundle);
        super.startActivityForResult(intent, requestCode);
    }

    public void backTo(Class<Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.startActivity(intent);
    }

    public void backTo(Class<Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("bundle", bundle);
        super.startActivity(intent);
    }

}
