package melvinlin.com.study_ioc_sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import melvinlin.com.library.annotation.ContentView;
import melvinlin.com.library.annotation.InjectView;
import melvinlin.com.library.annotation.OnClick;

@ContentView(R.layout.activity_main) //setContentView(R.layout.activity_main);
public class MainActivity extends BaseActivity {

    @InjectView(R.id.btn)
    private Button btn;

    @InjectView(R.id.tv)
    private TextView tv;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        Button btn = (Button)findViewById(R.id.btn);
//        Toast.makeText(MainActivity.this, btn.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.tv, R.id.btn})
    public void click(View view) {
        Toast.makeText(MainActivity.this, "有帶參數", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.tv, R.id.btn})
    public void click() {
        Toast.makeText(MainActivity.this, "無帶參數", Toast.LENGTH_SHORT).show();
    }


}
