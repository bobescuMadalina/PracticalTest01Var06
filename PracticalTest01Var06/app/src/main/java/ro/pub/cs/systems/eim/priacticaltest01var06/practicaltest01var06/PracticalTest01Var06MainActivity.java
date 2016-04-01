package ro.pub.cs.systems.eim.priacticaltest01var06.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PracticalTest01Var06MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etName,etSite;
    Button bMoreLessDetails, bPassFail, bNavigate;
    LinearLayout llContainer;
    boolean areDetailsVisible = true;
    boolean passValues = false;
    boolean serviceStarted = false;

    private static final String NAME_VALUE = "name_value";
    private static final String WEB_VALUE = "web_value";

    private IntentFilter intentFilter;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(Constants.MESSAGE);
            Log.d("APP_TAG", message);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.FIRST_ACTION);

        initViews();
        initListeners();

    }

    private void initViews() {
        etName = (EditText) findViewById(R.id.edit_text_name);
        etSite = (EditText) findViewById(R.id.edit_text_web);
        bMoreLessDetails = (Button) findViewById(R.id.button_more_less_details);
        bPassFail = (Button) findViewById(R.id.button_pass_or_fail);
        bNavigate = (Button) findViewById(R.id.button_navigate_next);
        llContainer = (LinearLayout) findViewById(R.id.web_container);
    }

    private void initListeners() {
        bMoreLessDetails.setOnClickListener(this);
        bNavigate.setOnClickListener(this);
        etSite.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyPass();
            }
        });
    }

    private void verifyPass() {
        String webSite = etSite.getText().toString();
        if (webSite.startsWith("http://")) {
            bPassFail.setBackgroundResource(R.color.grenn);
            bPassFail.setText(getResources().getString(R.string.pass));
            passValues = true;
            if (!serviceStarted) {
                serviceStarted = true;
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                intent.putExtra(Constants.WEB_VALUE, etSite.getText().toString());
                getApplicationContext().startService(intent);
            }


        } else {

            bPassFail.setBackgroundResource(R.color.red);
            bPassFail.setText(getResources().getString(R.string.fail));
            passValues = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_var06_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_more_less_details:
                if (areDetailsVisible) {
                    areDetailsVisible = false;
                    bMoreLessDetails.setText(getResources().getString(R.string.more_details));
                    llContainer.setVisibility(View.INVISIBLE);
                } else {
                    areDetailsVisible = true;
                    bMoreLessDetails.setText(getResources().getString(R.string.less_details));
                    llContainer.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.button_navigate_next:
                goToNext();
                break;

        }
    }


    private void goToNext() {
        Intent intent = new Intent(this, PracticalTest01Var06SecondaryActivity.class);
        intent.putExtra(Constants.WEB_VALUE, etSite.getText().toString());
        intent.putExtra(Constants.PASS_VALUE, passValues);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NAME_VALUE, etName.getText().toString());
        outState.putString(WEB_VALUE, etSite.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String name = savedInstanceState.getString(NAME_VALUE);
        if (!TextUtils.isEmpty(name)) {
            etName.setText(name);
            Toast.makeText(PracticalTest01Var06MainActivity.this, name, Toast.LENGTH_SHORT).show();
        }
        String web = savedInstanceState.getString(WEB_VALUE);
        if (!TextUtils.isEmpty(web)) {
            etSite.setText(web);
            Toast.makeText(PracticalTest01Var06MainActivity.this, web, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE) {
            switch (resultCode){
                case RESULT_OK:
                    Toast.makeText(PracticalTest01Var06MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(PracticalTest01Var06MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onPause() {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        getApplicationContext().stopService(intent);
        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }
}
