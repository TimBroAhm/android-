package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText etFirstName, etFatherName, etLastName, etDOB, etPhone, etKebele, etQualification;
    RadioGroup rgSex;
    Spinner spinnerRole;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Initialize views
        etFirstName = findViewById(R.id.etFirstName);
        etFatherName = findViewById(R.id.etFatherName);
        etLastName = findViewById(R.id.etLastName);
        etDOB = findViewById(R.id.etDOB);
        etPhone = findViewById(R.id.etPhone);
        etKebele = findViewById(R.id.etKebele);
        etQualification = findViewById(R.id.etQualification);
        rgSex = findViewById(R.id.rgSex);
        spinnerRole = findViewById(R.id.spinnerRole);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Apply a text watcher to the phone number field
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = editable.toString();
                if (phone.length() > 4 && !phone.startsWith("+251")) {
                    // Ensure the phone starts with +251
                    phone = "+251" + phone.substring(1);
                    etPhone.setText(phone);
                    etPhone.setSelection(phone.length());  // Move the cursor to the end
                }
            }
        });

        // Populate Spinner with roles
        String[] roles = {"Select Role", "System Admin", "Director General", "General Service Executive Office", "Deputy Director General", "Stockclerk", "Employee"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        // DatePicker for Date of Birth
        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set OnClickListener for Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    // Proceed with the registration process if validation is successful
                    onReg(v);
                } else {
                    Toast.makeText(Register.this, "Please fill all required fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onReg(View view) {
        String firstName = etFirstName.getText().toString();
        String fatherName = etFatherName.getText().toString();
        String lastName = etLastName.getText().toString();
        String dob = etDOB.getText().toString();
        String phone = etPhone.getText().toString();
        String kebele = etKebele.getText().toString();
        String qualification = etQualification.getText().toString();
        String role = spinnerRole.getSelectedItem().toString();

        // Check if a sex (gender) is selected from the RadioGroup
        int selectedSexId = rgSex.getCheckedRadioButtonId();
        if (selectedSexId == -1) {
            Toast.makeText(Register.this, "Please select gender.", Toast.LENGTH_SHORT).show();
            return; // Don't proceed with registration if sex is not selected
        }

        // Extract the selected gender value
        String sex = ((RadioButton) findViewById(selectedSexId)).getText().toString();

        // Format the date of birth to yyyy-mm-dd
        String formattedDOB = formatDOB(dob);

        // Create BackgroundWorker instance and execute the registration process
        String type = "register";
        a backgroundWorker = new a(this);
        backgroundWorker.execute(type, firstName, fatherName, lastName, formattedDOB, phone, kebele, qualification, role, sex);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Register.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private String formatDOB(String dob) {
        try {
            // Parse the date from the EditText in dd/mm/yyyy format
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = sdf.parse(dob);

            // Format the date to yyyy-MM-dd format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Return empty string if parsing fails
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Reset backgrounds to default before validation
        resetFieldBackground(etFirstName, etFatherName, etLastName, etDOB, etPhone, etKebele, etQualification);

        // Check if fields are empty and highlight if needed
        if (etFirstName.getText().toString().trim().isEmpty()) {
            etFirstName.setBackgroundResource(R.drawable.error_background);
            isValid = false;
        }
        if (etFatherName.getText().toString().trim().isEmpty()) {
            etFatherName.setBackgroundResource(R.drawable.error_background);
            isValid = false;
        }
        if (etLastName.getText().toString().trim().isEmpty()) {
            etLastName.setBackgroundResource(R.drawable.error_background);
            isValid = false;
        }
        if (etDOB.getText().toString().trim().isEmpty()) {
            etDOB.setBackgroundResource(R.drawable.error_background);
            isValid = false;
        }
        if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setBackgroundResource(R.drawable.error_background);
            isValid = false;
        }
        if (etKebele.getText().toString().trim().isEmpty()) {
            etKebele.setBackgroundResource(R.drawable.error_background);
            isValid = false;
        }
        if (etQualification.getText().toString().trim().isEmpty()) {
            etQualification.setBackgroundResource(R.drawable.error_background);
            isValid = false;
        }

        // Gender validation
        if (rgSex.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Spinner validation
        if (spinnerRole.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a role.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void resetFieldBackground(EditText... fields) {
        for (EditText field : fields) {
            field.setBackgroundResource(android.R.drawable.edit_text);
        }
    }
}
