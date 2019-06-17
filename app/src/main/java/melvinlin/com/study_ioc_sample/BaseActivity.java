package melvinlin.com.study_ioc_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import melvinlin.com.library.InjectManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //幫助子類進行佈局、控件、事件的注入
        InjectManager.inject(this);

    }
}
