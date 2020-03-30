package com.example.edl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Editlist extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String phone;
    String name;
    ListView lStudents;
    ArrayAdapter<String> adp;
    ArrayList<String> l=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlist);

        lStudents=(ListView) findViewById(R.id.listview);

        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("nameS");
        lStudents.setOnItemClickListener(this);
        lStudents.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, l);
        lStudents.setAdapter(adp);
        l.add(phone);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected (MenuItem item){
        //menu
        String st = item.getTitle().toString();

        if (st.equals("Home screem")) {
            Intent in = new Intent(Editlist.this, TeacherLessons.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }
}
