package com.example.acer.githubsearchquery;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText job_edit;
    Button job_submit;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        job_edit=findViewById(R.id.edit_text_job);
        job_submit=findViewById(R.id.submit_button_job);
    }

    public void submit_button(View view) {

        value=job_edit.getText().toString();
        if (value.isEmpty()){
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
            alertdialog.setTitle("Alert message");
            alertdialog.setMessage("No data found");
            alertdialog.setCancelable(true);
            alertdialog.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = alertdialog.create();
            alert.show();
        }
        else {
            Intent i = new Intent(this, JobActivity.class);
            i.putExtra("job", job_edit.getText().toString());
            startActivity(i);
        }
    }
}
