package com.np.fitnessapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.np.fitnessapp.R;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.Sex;
import com.np.fitnessapp.database.entity.User;

public class UserCreateActivity extends AppCompatActivity {

    public static final String CREATED_USER_ID_EXTRA = "createdUserId";

    private EditText nameInput;
    private Spinner sexInput;
    private EditText ageInput;
    private EditText heightInput;
    private EditText weightInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        nameInput = findViewById(R.id.add_user_name_edittext);
        ageInput = findViewById(R.id.add_user_age_edittext);
        heightInput = findViewById(R.id.add_user_height_edittext);
        weightInput = findViewById(R.id.add_user_weight_edittext);
        sexInput = findViewById(R.id.add_user_sex_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.sex_array,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexInput.setAdapter(adapter);


        Button addButton = findViewById(R.id.add_user_button);
        addButton.setOnClickListener(v -> {
            if(!validateFields()) {
                Snackbar.make(
                        v.findViewById(R.id.add_user_container),
                        getString(R.string.fill_all_fields),
                        Snackbar.LENGTH_LONG
                ).show();
                return;
            }

            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            long userId = db.userDao().insertUser(inputDataToUser());

            Intent result = new Intent();
            result.putExtra(CREATED_USER_ID_EXTRA, userId);
            setResult(Activity.RESULT_OK, result);
            finish();
        });
    }



    private User inputDataToUser() {
        Sex sex;
        switch(sexInput.getSelectedItemPosition()) {
            case 0:
                sex = Sex.MALE;
                break;
            default:
                sex = Sex.FEMALE;
        }

        return new User(
                nameInput.getText().toString(),
                sex,
                Integer.parseInt(ageInput.getText().toString()),
                Float.parseFloat(weightInput.getText().toString()),
                Integer.parseInt(heightInput.getText().toString())
            );
    }

    private boolean validateFields() {
        return nameInput.getText().length() > 0
                && ageInput.getText().length() > 0 && Integer.parseInt(ageInput.getText().toString()) > 0
                && heightInput.getText().length() > 0 &&  Integer.parseInt(heightInput.getText().toString()) > 0
                && weightInput.getText().length() > 0 &&  Float.parseFloat(weightInput.getText().toString()) > 0;
    }
}