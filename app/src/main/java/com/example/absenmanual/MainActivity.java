package com.example.absenmanual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextInputEditText inputDate, inputTime, inputDesc;
    Button btnSubmit;
    Spinner spinnerStatus;
    Boolean showDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputDate = findViewById(R.id.input_date);
        inputTime = findViewById(R.id.input_time);
        inputDesc = findViewById(R.id.input_desc);
        btnSubmit = findViewById(R.id.btn_submit);
        spinnerStatus = findViewById(R.id.spinner_status);

        inputDate.setKeyListener(null);
        inputTime.setKeyListener(null);
        loadSpinner();

        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        inputDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showDatePicker();
            }
        });

        inputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });
        inputTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showTimePicker();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateField()) {
                    showAlertDialog();
                }
            }
        });

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Konfirmasi");
        alertBuilder.setMessage("Apakah kamu yakin data yang akan kamu kirim sudah sesuai?");

        alertBuilder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Absen Berhasil", Toast.LENGTH_SHORT).show();
                inputDate.getText().clear();
                inputTime.getText().clear();
                spinnerStatus.setSelection(0);
                inputDesc.getText().clear();
            }
        });
        alertBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBuilder.show();
    }

    private void loadSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labels_array, android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        if (spinnerStatus != null) {
            spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        inputDesc.setVisibility(View.INVISIBLE);
                        showDesc = false;
                    } else {
                        inputDesc.setVisibility(View.VISIBLE);
                        showDesc = true;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void showDatePicker() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "date-picker");
    }

    private void showTimePicker() {
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "time-picker");
    }

    public void processDatePickerResult(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        inputDate.setText(sdf.format(date.getTime()));
        inputDate.setError(null);
    }

    public void processTimePickerResult(Calendar time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        inputTime.setText(sdf.format(time.getTime()));
        inputTime.setError(null);
    }

    private boolean validateField() {
        boolean valid = true;
        String date = inputDate.getText().toString();
        String time = inputTime.getText().toString();

        if (date.isEmpty()) {
            valid = false;
            inputDate.setError("This field cannot be left empty");
        }
        if (time.isEmpty()) {
            valid = false;
            inputTime.setError("This field cannot be left empty");
        }

        if (showDesc) {
            String desc = inputDesc.getText().toString();
            if (desc.isEmpty()) {
                valid = false;
                inputDesc.setError("This field cannot be left empty");
            }
        }

        return valid;
    }
}