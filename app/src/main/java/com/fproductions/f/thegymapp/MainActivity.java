package com.fproductions.f.thegymapp;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends Activity {

    private Button CalculateBMIButton;
    private Spinner ProgramSpinner;
    private EditText HeightEdit;
    private EditText WeightEdit;
    private EditText EmailEdit;
    private ImageView ProgramImageView;
    private TextView BmiResultTextView;
    private ImageView MemberImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CalculateBMIButton = (Button)findViewById(R.id.ButtonCalculate);
        HeightEdit = (EditText)findViewById(R.id.EditTextHeight);
        WeightEdit = (EditText) findViewById(R.id.EditTextWeight);


        final Context myApp = this;

/* An instance of this class will be registered as a JavaScript interface */
        class MyJavaScriptInterface
        {
            @SuppressWarnings("unused")
            public void showHTML(String html)
            {
            }
        }

        final WebView browser = (WebView)findViewById(R.id.browser);
/* JavaScript must be enabled if you want it to work, obviously */
        browser.getSettings().setJavaScriptEnabled(true);

/* Register a new JavaScript interface called HTMLOUT */
        browser.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

/* WebViewClient must be set BEFORE calling loadUrl! */
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
        /* This call inject JavaScript into the page which just finished loading. */
                browser.loadUrl("javascript:HTMLOUT.processHTML(document.documentElement.outerHTML);");
            }
        });

/* load a web page */
        browser.loadUrl("http://www.huffingtonpost.com/news/fitness/");

        //http://www.sciencedaily.com/news/health_medicine/fitness/
        //http://www.huffingtonpost.com/news/fitness/

        addItemsToProgramSpinner();
        addListenerToProgramSpinner();


        TabHost tabhost = (TabHost) findViewById(R.id.tabhost);
        tabhost.setup();

        TabHost.TabSpec specs1 = tabhost.newTabSpec("tab1");
        specs1.setContent(R.id.linearLayout);
        specs1.setIndicator("BMI Calculator");
        tabhost.addTab(specs1);

        TabHost.TabSpec specs2 = tabhost.newTabSpec("tab2");
        specs2.setContent(R.id.linearLayout2);
        specs2.setIndicator("Workout Charts");
        tabhost.addTab(specs2);

        TabHost.TabSpec specs3 = tabhost.newTabSpec("tab3");
        specs3.setContent(R.id.linearLayout3);
        specs3.setIndicator("Latest News on Fitness");
        tabhost.addTab(specs3);

        TabHost.TabSpec specs4 = tabhost.newTabSpec("tab4");
        specs4.setContent(R.id.linearLayout4);
        specs4.setIndicator("Rules & Regulations");
        tabhost.addTab(specs4);

        TabHost.TabSpec specs5 = tabhost.newTabSpec("tab5");
        specs5.setContent(R.id.LinearLayout5);
        specs5.setIndicator("LogIn");
        tabhost.addTab(specs5);
    }




    private void addListenerToProgramSpinner() {
        ProgramSpinner = (Spinner)findViewById(R.id.program_spinner);

        ProgramSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String itemSelectedInSpinner = parent.getItemAtPosition(position).toString();

                makeText(itemSelectedInSpinner);

            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void makeText(String itemSelectedInSpinner) {
        if(itemSelectedInSpinner.equals("Chest Workout")){
            ProgramImageView = (ImageView)findViewById(R.id.program_imageview);
            ProgramImageView.setImageResource(R.drawable.chest_workout);
        }
        else if(itemSelectedInSpinner.equals("Biceps and Forearm Workout")){
            ProgramImageView = (ImageView)findViewById(R.id.program_imageview);
            ProgramImageView.setImageResource(R.drawable.biceps_forearm_workout);
        }
        else if(itemSelectedInSpinner.equals("Leg Workout")){
            ProgramImageView = (ImageView)findViewById(R.id.program_imageview);
            ProgramImageView.setImageResource(R.drawable.leg_workout);
        }
        else if(itemSelectedInSpinner.equals("Exercise Stretches")){
            ProgramImageView = (ImageView)findViewById(R.id.program_imageview);
            ProgramImageView.setImageResource(R.drawable.exercise_stretches);
        }
        else if(itemSelectedInSpinner.equals("Abdominal Workout")){
            ProgramImageView = (ImageView)findViewById(R.id.program_imageview);
            ProgramImageView.setImageResource(R.drawable.abdominal_workout);
        }
        else if(itemSelectedInSpinner.equals("Triceps Workout")){
            ProgramImageView = (ImageView)findViewById(R.id.program_imageview);
            ProgramImageView.setImageResource(R.drawable.triceps_workout);
        }
        else if(itemSelectedInSpinner.equals("Back Workout")){
            ProgramImageView = (ImageView)findViewById(R.id.program_imageview);
            ProgramImageView.setImageResource(R.drawable.back_workout);
        }
        else if(itemSelectedInSpinner.equals("Shoulder Workout")){
            ProgramImageView = (ImageView)findViewById(R.id.program_imageview);
            ProgramImageView.setImageResource(R.drawable.shoulder_workout);
        }
    }




    private void addItemsToProgramSpinner() {
        ProgramSpinner = (Spinner)findViewById(R.id.program_spinner);
        ArrayAdapter<CharSequence> ProgramSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.program_types ,
                android.R.layout.simple_spinner_item);
        ProgramSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ProgramSpinner.setAdapter(ProgramSpinnerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void btnOnClick(View view) {

        String userHeightincmString = String.valueOf(HeightEdit.getText());
        String userWeightinkgString = String.valueOf(WeightEdit.getText());




        if(userHeightincmString.isEmpty() || userWeightinkgString.isEmpty()){
            Toast.makeText(this, "You did not enter your height or weight", Toast.LENGTH_SHORT).show();
        }
        else {
            Double userHeightincmInt = Double.valueOf(userHeightincmString);
            Double userWeightinkgInt = Double.valueOf(userWeightinkgString);


            Double userHeightinmInt = userHeightincmInt/100;


            Double BMIDouble = Double.valueOf(userWeightinkgInt / (userHeightinmInt * userHeightinmInt));
            Double BMIDoubleRounded = (double)Math.round(BMIDouble * 10)/10;
            String BMIString = String.valueOf(BMIDoubleRounded);
            BmiResultTextView = (TextView)findViewById(R.id.result_text_view);

            BmiResultTextView.setText(BMIString);

             if (BMIDoubleRounded < 18) {
                Toast.makeText(this, "Eat more, You are Underweight", Toast.LENGTH_LONG).show();
            } else if (BMIDoubleRounded >= 18 && BMIDoubleRounded <= 24.99) {
                Toast.makeText(this, "Congratulations, You are at Normal Weight", Toast.LENGTH_LONG).show();
            } else if (BMIDoubleRounded >= 25 && BMIDoubleRounded <= 29.99) {
                Toast.makeText(this, "Exercise more and have a proper diet, You are Overweight", Toast.LENGTH_SHORT).show();
            } else if (BMIDoubleRounded >= 30) {
                Toast.makeText(this, "Seek a doctor's help, You are Obese", Toast.LENGTH_LONG).show();
            }
        }

    }

    public boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loadImageFromStorage(String email) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("MembersImages", Context.MODE_PRIVATE);
        String path = directory.getAbsolutePath();
        File f = new File(path, email + ".png");
        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            MemberImageView = (ImageView)findViewById(R.id.MemberImageView);
            MemberImageView.setImageBitmap(b);
            MemberImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void LoginOnClick(View view) {
        EmailEdit = (EditText)findViewById(R.id.EmailEditText);
        String getemail = EmailEdit.getText().toString();
        DBHandler db = new DBHandler(getApplicationContext());


        if(getemail.matches("")){
            Toast.makeText(this, "Please Input a valid Email", Toast.LENGTH_SHORT).show();
        }
        if(isEmailValid(getemail) == false){
            Toast.makeText(this, "Please Input a valid Email", Toast.LENGTH_SHORT).show();
        }
        if(getemail.matches("admin@admin.com")){
            EmailEdit.setText("");
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        }
        if (db.CheckIsEmailRegistered(getemail) == false && getemail.matches("admin@admin.com") == false){
            Toast.makeText(this, "Email is not yet registered", Toast.LENGTH_SHORT).show();
        }
        else if(db.CheckIsEmailRegistered(getemail) == true) {
            String name = db.getNameFromEmail(getemail).toString();
            Toast.makeText(this, "Welcome " + name, Toast.LENGTH_SHORT).show();
            loadImageFromStorage(getemail);


        }
    }
}
