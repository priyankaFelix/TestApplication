package com.example.admin.student_data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentMainActivity extends AppCompatActivity {

    EditText eroll_no, ename, emarks;
    Button add, delete, modify, show, view, viewall;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eroll_no = (EditText) findViewById(R.id.roll_no);
        ename = (EditText) findViewById(R.id.name);
        emarks = (EditText) findViewById(R.id.marks);
        add = (Button) findViewById(R.id.addbtn);
        delete = (Button) findViewById(R.id.deletebtn);
        modify = (Button) findViewById(R.id.modifybtn);
        show = (Button) findViewById(R.id.showbtn);
        view = (Button) findViewById(R.id.viewbtn);
        viewall = (Button) findViewById(R.id.viewallbtn);

        db = openOrCreateDatabase("Student_manage", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER,name VARCHAR,marks INTEGER)");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eroll_no.getText().toString().trim().length() == 0 ||
                        ename.getText().toString().trim().length() == 0 ||
                        eroll_no.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please Enter All Values");
                    return;

                }
                db.execSQL("INSERT INTO student VALUES('" + eroll_no.getText() + "','" + ename.getText() + "','" + emarks.getText() + "');");
                showMessage("Success", "Data added successfully");
                clearText();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eroll_no.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Enter roll no");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + eroll_no.getText() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("DELETE FROM student WHERE rollno='" + eroll_no.getText() + "'");
                    showMessage("Success", "Record deleted");
                } else {
                    showMessage("Error", "Invalid rollno");
                }
                clearText();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eroll_no.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Enter roll no");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + eroll_no.getText() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("UPDATE student SET name='" + ename.getText() + "',marks='" + emarks.getText() + "' WHERE rollno='" + eroll_no.getText() + "'");
                    showMessage("Success", "Record modified");
                } else {
                    showMessage("Error", "Invalid rollno");
                }
                clearText();
            }

        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eroll_no.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Enter roll no");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + eroll_no.getText() + "'", null);
                StringBuffer buffer=new StringBuffer();
                if (c.moveToNext()) {
                    /*ename.setText(c.getString(1));
                    emarks.setText(c.getString(2));*/
                    buffer.append("Roll no:" + c.getString(0) + "\n");
                    buffer.append("Name:" + c.getString(1) + "\n");
                    buffer.append("Marks:" + c.getString(2)+"\n\n");
                    showMessage("Student Details:" + buffer.toString(),""+buffer.toString());
                } else {
                    showMessage("Error", "Invalid rollno");
                }
                clearText();
            }

        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * FROM student", null);
                if (c.getCount() == 0) {
                    showMessage("Error", "No record found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("Roll no:" + c.getString(0) + "\n");
                    buffer.append("Name:" + c.getString(1) + "\n");
                    buffer.append("Marks:" + c.getString(2)+"\n\n");
                }
                showMessage("Student Details:" + buffer.toString(),""+buffer.toString());
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(" Student Application", "Develop by Priyanka");
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void clearText() {
        eroll_no.setText("");
        ename.setText("");
        emarks.setText("");
        emarks.requestFocus();
    }
}