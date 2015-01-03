package com.fproductions.f.thegymapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class AdminActivity extends Activity {

    ListView emailListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        DBHandler db = new DBHandler(getApplicationContext());
        List<String> emails = db.getAllEmail();
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, emails);
        emailListview = (ListView)findViewById(R.id.MemberListView);
        emailListview.setAdapter(dataAdapter);
        emailListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                final String selected = (String)(emailListview.getItemAtPosition(position));
                AlertDialog.Builder ADbuilder = new AlertDialog.Builder(AdminActivity.this);
                ADbuilder.setTitle("Delete");
                ADbuilder.setPositiveButton("Delete " + selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHandler db = new DBHandler(getApplicationContext());
                        db.deleteMember(selected);
                        dataAdapter.remove(selected);
                        dataAdapter.notifyDataSetChanged();


                    }
                });
                AlertDialog AD = ADbuilder.create();
                AD.show();
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_admin);
        DBHandler db = new DBHandler(getApplicationContext());
        List<String> emails = db.getAllEmail();
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, emails);
        emailListview = (ListView)findViewById(R.id.MemberListView);
        emailListview.setAdapter(dataAdapter);
        emailListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                final String selected = (String)(emailListview.getItemAtPosition(position));
                AlertDialog.Builder ADbuilder = new AlertDialog.Builder(AdminActivity.this);
                ADbuilder.setTitle("Delete");
                ADbuilder.setPositiveButton("Delete " + selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHandler db = new DBHandler(getApplicationContext());
                        db.deleteMember(selected);
                        dataAdapter.remove(selected);
                        dataAdapter.notifyDataSetChanged();


                    }
                });
                AlertDialog AD = ADbuilder.create();
                AD.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
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

    public void AddOnClick(View view) {
        Intent intent = new Intent(this,AddMemberActivity.class);
        startActivity(intent);
    }
}
