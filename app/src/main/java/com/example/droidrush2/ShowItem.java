package com.example.droidrush2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowItem extends AppCompatActivity {
    ExpandableCustomAdapter expandableCustomAdapter;
    ExpandableListView expandableListView;
    List<String> headerData;
    HashMap<String, ArrayList<ToDoListModel>> childData;
    ToDoListModel childDataModel;
    Context mContext;
    private static String DB_PATH = "";
    private static String DB_NAME ="DemoDataBase";

    SQLiteDatabase mDatabase;

    private int lastExpandedPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        mContext = this;

        headerData = new ArrayList<>();
        childData = new HashMap<String,ArrayList<ToDoListModel>>();
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = this.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + this.getPackageName() + "/databases/";
        }
        String mPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);
        expandableListView = (ExpandableListView)findViewById(R.id.expandAbleListView);



        Cursor cursorTodo = mDatabase.rawQuery("SELECT * FROM demoTable;", null);

        //if the cursor has some data
        if (cursorTodo.moveToFirst()) {
            //looping through all the records
            int i = 0;
            do {
                ArrayList<ToDoListModel> arrayList= new ArrayList<>();
                //pushing each record in the employee list
                headerData.add(cursorTodo.getString(1));
                arrayList.add(new ToDoListModel(
                        cursorTodo.getInt(0),
                        cursorTodo.getString(1),
                        cursorTodo.getString(2)));

                childData.put(headerData.get(i), arrayList);
                i++;
            } while (cursorTodo.moveToNext());
        }
        //closing the cursor
        cursorTodo.close();





        //childDataModel = new ToDoListModel(1,"Hello", "How r u?");
        //arrayList.add(childDataModel);

        expandableCustomAdapter = new ExpandableCustomAdapter(mContext,headerData,childData);
        expandableListView.setAdapter(expandableCustomAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(final int headPosition) {

                final Button button = (Button)findViewById(R.id.delete);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            mDatabase.execSQL("DELETE FROM demoTable WHERE name = \""+headerData.get(headPosition) + "\";");
                        headerData.remove(headPosition);
                        expandableCustomAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), headerData.get(headPosition), Toast.LENGTH_LONG).show();
                    }
                });

                if (lastExpandedPosition != -1
                        && headPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = headPosition;
                Toast.makeText(mContext,
                        headerData.get(headPosition) + " continent expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //group collapsed
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int headPosition) {
                Toast.makeText(mContext,
                        headerData.get(headPosition) + " continent collapsed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                parent.smoothScrollToPosition(groupPosition);


                return false    ;
            }
        });
    }
}
