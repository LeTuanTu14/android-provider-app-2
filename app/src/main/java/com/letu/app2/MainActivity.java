package com.letu.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String AUTHORITY= "com.letu.app1";
    static final String CONTENT_PROVIDER= "contentprovider";
    static final String URL="content://"+AUTHORITY+"/"+CONTENT_PROVIDER;
    static final Uri CONTENT_URI=Uri.parse(URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText et_id= findViewById(R.id.ed_id);
        EditText et_name= findViewById(R.id.ed_name);
        EditText et_unit= findViewById(R.id.ed_unit);
        EditText et_madein= findViewById(R.id.ed_madein);

        GridView gv= findViewById(R.id.grig_view);

        Button bt_save= findViewById(R.id.save);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values= new ContentValues();
                values.put("id",et_id.getText().toString());
                values.put("name",et_name.getText().toString());
                values.put("unit",et_unit.getText().toString());
                values.put("madein",et_madein.getText().toString());
                Uri insert_uri= getContentResolver().insert(CONTENT_URI,values);
                Toast.makeText(getApplicationContext(),"Lưu thành công",Toast.LENGTH_SHORT).show();
            }
        });

        Button bt_select= findViewById(R.id.select);
        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> string_list=new ArrayList<>();
                Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, "name");

                if(cursor!=null){
                    cursor.moveToFirst();
                    do{
                        string_list.add(cursor.getInt(0)+"");
                        string_list.add(cursor.getString(1));
                        string_list.add(cursor.getString(2));
                        string_list.add(cursor.getString(3));
                    }while(cursor.moveToNext());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,string_list);
                    gv.setAdapter(adapter);
                }else {
                    Toast.makeText(getApplicationContext(),"Không có kết quả",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button bt_update= findViewById(R.id.update);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values= new ContentValues();
                values.put("id",et_id.getText().toString());
                values.put("name",et_name.getText().toString());
                values.put("unit",et_unit.getText().toString());
                values.put("madein",et_madein.getText().toString());

                int update_row= getContentResolver().update(CONTENT_URI,values,"id = ?",new String[]{et_id.getText().toString()});
                if(update_row!=0)
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Cập nhật không thành công",Toast.LENGTH_SHORT).show();
            }
        });
        Button bt_delete=findViewById(R.id.delete);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=et_id.getText().toString();
                int delete_row= getContentResolver().delete(CONTENT_URI,null,new String[]{et_id.getText().toString()});
                if(delete_row!=0)
                    Toast.makeText(getApplicationContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Xóa không thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }
}