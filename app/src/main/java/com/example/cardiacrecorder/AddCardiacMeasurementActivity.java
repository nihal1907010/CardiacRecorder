package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCardiacMeasurementActivity extends AppCompatActivity {

    private EditText heartRateText;
    private EditText systolicPressureText;
    private EditText diastolicPressureText;
    private EditText commentText;
    private Button addBtn;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cardiac_measurement);

        heartRateText = findViewById(R.id.heartRate);
        systolicPressureText = findViewById(R.id.systolicPressure);
        diastolicPressureText = findViewById(R.id.diastolicPressure);
        commentText = findViewById(R.id.comment);
        addBtn = findViewById(R.id.addData);

        mdatabase = FirebaseDatabase.getInstance().getReference();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int heartRate = Integer.valueOf(heartRateText.getText().toString());
                int systolic = Integer.valueOf(systolicPressureText.getText().toString());
                int diastolic = Integer.valueOf(diastolicPressureText.getText().toString());

                String comment = commentText.getText().toString();
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String today = simpleDateFormat.format(date);

                addData(new CardiacMeasurement(today, systolic, diastolic, heartRate, comment));
            }
        });
    }

    public void addData(CardiacMeasurement patient) {
        String key = mdatabase.push().getKey();
        mdatabase.child("measurements").child(key).setValue(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddCardiacMeasurementActivity.this, "Data Added", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCardiacMeasurementActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}