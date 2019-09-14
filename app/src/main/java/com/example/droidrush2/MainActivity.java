package com.example.droidrush2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText todolist_item,description;
    Button Adding,Finishing;
    String SQLiteQuery;
    String Name,descriptionName;
    SQLiteDatabase SQLITEDATABASE;
    boolean CheckEditTextEmpty;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todolist_item = (EditText) findViewById(R.id.list_item);
        description = (EditText)findViewById(R.id.Description);
        Adding = (Button) findViewById(R.id.AddingButton);
        Finishing = (Button) findViewById(R.id.Finish);




        Adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                DBCreate();

                SubmitData2SQLiteDB();

            }
        });

        Finishing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ShowItem.class );
                startActivity(intent);
            }
        });
    }

    public void DBCreate(){

        SQLITEDATABASE = openOrCreateDatabase("DemoDataBase", Context.MODE_PRIVATE, null);
       //SQLITEDATABASE.execSQL("DROP TABLE demoTable;");
        SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS demoTable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, Description VARCHAR);");
    }
    public void SubmitData2SQLiteDB(){

        Name = todolist_item.getText().toString();
        descriptionName = description.getText().toString();

        CheckEditTextIsEmptyOrNot( Name);

        if(CheckEditTextEmpty == true)
        {

            SQLiteQuery = "INSERT INTO demoTable (name,Description) VALUES('"+Name+"','"+descriptionName+"');";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(MainActivity.this,"Data Submit Successfully", Toast.LENGTH_LONG).show();

            ClearEditTextAfterDoneTask();

        }
        else {

            Toast.makeText(MainActivity.this,"Please Fill All the Fields", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckEditTextIsEmptyOrNot(String Name ){

        if(TextUtils.isEmpty(Name)){

            CheckEditTextEmpty = false ;

        }
        else {
            CheckEditTextEmpty = true ;
        }
    }

    public void ClearEditTextAfterDoneTask(){

        todolist_item.getText().clear();


    }

}
