package ro.pub.cs.systems.eim.priacticaltest01var06.practicaltest01var06;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvWeb, tvPass;
    Button bOk, bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String webString = intent.getStringExtra(Constants.WEB_VALUE);
        boolean pass = intent.getBooleanExtra(Constants.PASS_VALUE, false);
        String passString = pass ? getResources().getString(R.string.pass) : getResources().getString(R.string.fail);
        initView(webString, passString);

        bOk.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }


    private void initView(String webString, String passString) {
        tvWeb = (TextView) findViewById(R.id.text_view_web);
        if (!TextUtils.isEmpty(webString)) {
            tvWeb.setText(webString);
        }
        tvPass = (TextView) findViewById(R.id.text_view_fail_or_pass);
        if (!TextUtils.isEmpty(passString)) {
            tvPass.setText(passString);
        }

        bOk = (Button) findViewById(R.id.button_ok);
        bCancel = (Button) findViewById(R.id.button_cancel);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_ok:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.button_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
