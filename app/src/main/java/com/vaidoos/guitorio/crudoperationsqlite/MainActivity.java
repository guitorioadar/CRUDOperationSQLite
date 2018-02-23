package com.vaidoos.guitorio.crudoperationsqlite;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCreateStudent = findViewById(R.id.buttonCreateStudent);

        buttonCreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Context context = view.getRootView().getContext();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                final View fromElementsView = inflater.inflate(R.layout.student_input_form,null,false);

                final EditText editTextStudentFirstname = (EditText) fromElementsView.findViewById(R.id.editTextStudentFirstname);
                final EditText editTextStudentEmail = (EditText) fromElementsView.findViewById(R.id.editTextStudentEmail);

                new AlertDialog.Builder(context)
                        .setView(fromElementsView)
                        .setTitle("Create Student")
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        String studentFirstname = editTextStudentFirstname.getText().toString();
                                        String studentEmail = editTextStudentEmail.getText().toString();

                                        ObjectStudent objectStudent = new ObjectStudent();
                                        objectStudent.firstname= studentFirstname;
                                        objectStudent.email= studentEmail;

                                        boolean createSuccessful = new TableControllerStudent(context).create(objectStudent);

                                        if (createSuccessful){
                                            Toast.makeText(context, "Student info was saved", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(context, "Unable to save student info", Toast.LENGTH_SHORT).show();
                                        }

                                        //((MainActivity) context).readRecords();
                                        //((MainActivity) context).readRecords();
                                        //((MainActivity) context).countRecords();
                                        //((MainActivity) context).readRecords();

                                        Intent intent = getIntent();

                                        finish();
                                        startActivity(intent);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                                        dialogInterface.cancel();

                                    }
                                })
                        .show();

            }
        });

        ((MainActivity) this).readRecords();
        ((MainActivity) this).countRecords();
        ((MainActivity) this).readRecords();

        countRecords();

        readRecords();

    }

    public void countRecords(){

        int recordCount = new TableControllerStudent(this).count();
        TextView textViewRecordCount = findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount+" records found.");

    }


    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectStudent> students = new TableControllerStudent(this).read();

        if (students.size() > 0) {

            for (ObjectStudent obj : students) {

                int id = obj.id;
                String studentFirstname = obj.firstname;
                String studentEmail = obj.email;

                String textViewContents = studentFirstname + " - " + studentEmail;

                TextView textViewStudentItem= new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));

                linearLayoutRecords.addView(textViewStudentItem);

                //textViewStudentItem.setTag(Integer.toString(id));

                textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());

            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }

}
