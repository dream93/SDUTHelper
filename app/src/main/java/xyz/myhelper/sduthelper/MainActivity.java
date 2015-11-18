package xyz.myhelper.sduthelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * @author dream
 * @version 1.0
 *  这是主类，所有一级功能都展示在这
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
