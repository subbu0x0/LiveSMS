package com.example.talentmicro.smsgold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.talentmicro.smsgold.Dto.LocationDto;
import com.example.talentmicro.smsgold.Dto.NationalityDto;
import com.example.talentmicro.smsgold.Dto.ProfessionDto;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    Context context = ProfileActivity.this;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.sp_location)
    Spinner spLocality;

    @Bind(R.id.sp_nationality)
    Spinner spNationality;

    @Bind(R.id.sp_profession)
    Spinner spProfession;

    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private ArrayList<NationalityDto> nationalityArrayList;
    private ArrayList<ProfessionDto> professionArrayList;
    private ArrayList<LocationDto> locationArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        initToolbar();
        initializeUiElements();
        handleUiElement();
    }

    private void initToolbar() {
        try {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(Html.fromHtml("Profile Settings"));
            setSupportActionBar(mToolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setNavigationIcon(context.getResources().getDrawable(R.drawable.ic_action_back));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUiElements() {
        professionArrayList = new ArrayList<>();
        nationalityArrayList = new ArrayList<>();
        locationArrayList = new ArrayList<>();

        locationArrayList.add(new LocationDto(0, "Select Location"));
        professionArrayList.add(new ProfessionDto(0, "Select Profession"));
        nationalityArrayList.add(new NationalityDto(0, "Select Nationality"));
    }

    private void handleUiElement() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });

        populateLocationSpinner();
        populateProfessionSpinner();
        populateNationalitySpinner();
    }

    private void populateNationalitySpinner() {
        try {
            ArrayAdapter<NationalityDto> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nationalityArrayList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spNationality.setAdapter(spinnerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateProfessionSpinner() {
        try {
            ArrayAdapter<ProfessionDto> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, professionArrayList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProfession.setAdapter(spinnerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateLocationSpinner() {
        try {
            ArrayAdapter<LocationDto> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationArrayList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spLocality.setAdapter(spinnerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
