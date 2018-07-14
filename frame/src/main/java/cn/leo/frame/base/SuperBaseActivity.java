package cn.leo.frame.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Leo on 2018/4/9.
 */

public class SuperBaseActivity extends AppCompatActivity implements BaseViewInterface {
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    public void jumpTo(Class<? extends Activity> activityClass) {
        super.startActivity(new Intent(this, activityClass));
    }

    public void jumpTo(Class<? extends Activity> activityClass, int requestCode) {
        super.startActivityForResult(new Intent(this, activityClass), requestCode);
    }

    public void jumpTo(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        super.startActivity(intent);
    }

    public void jumpTo(Class<? extends Activity> activityClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        super.startActivityForResult(intent, requestCode);
    }

    public void backTo(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.startActivity(intent);
    }

    public void backTo(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        super.startActivity(intent);
    }

}
