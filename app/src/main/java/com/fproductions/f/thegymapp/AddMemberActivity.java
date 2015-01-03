package com.fproductions.f.thegymapp;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class AddMemberActivity extends Activity {

    private static final int CAMERA_REQUEST = 1888;

    EditText nameEditText;
    EditText emailEditText;
    ImageView addphotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        this.addphotoImageView = (ImageView)this.findViewById(R.id.addMemberImageView);
        Button addMemberButton = (Button)this.findViewById(R.id.AddPhotoButton);
        addphotoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_member, menu);
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


    public boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void AddMemberOnCLick(View view) {
        nameEditText = (EditText) findViewById(R.id.NameEditText);
        emailEditText = (EditText) findViewById(R.id.EmailEditText);
        String getemail = emailEditText.getText().toString();
        String getname = nameEditText.getText().toString();



        if(getname.matches("")){

            Toast.makeText(this, "Please Input a valid Name", Toast.LENGTH_SHORT).show();
        }
        if(getemail.matches("")){
            Toast.makeText(this, "Please Input a valid Email", Toast.LENGTH_SHORT).show();
        }
        if(isEmailValid(getemail) == false){
            Toast.makeText(this, "Please Input a valid Email", Toast.LENGTH_SHORT).show();
        }
        if(addphotoImageView.getDrawable() == null){
            Toast.makeText(this, "Please add Photo", Toast.LENGTH_SHORT).show();
        }
        else if (isEmailValid(getemail)){
            DBHandler db = new DBHandler(getApplicationContext());
            db.insertMember(getname,getemail);
            emailEditText.setText("");
            nameEditText.setText("");

            addphotoImageView.buildDrawingCache();
            Bitmap bm = addphotoImageView.getDrawingCache();
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("MembersImages", Context.MODE_PRIVATE);
            File mypath = new File(directory, getemail + ".png");
            FileOutputStream fos = null;
            addphotoImageView.setImageDrawable(null);
            try{
                fos = new FileOutputStream(mypath);
                bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                addphotoImageView.setImageBitmap(photo);
            }
        }
    }
}
