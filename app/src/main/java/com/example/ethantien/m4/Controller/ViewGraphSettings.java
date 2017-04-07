package com.example.ethantien.m4.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ethantien.m4.model.vars;
import com.example.ethantien.m4.R;

public class ViewGraphSettings extends AppCompatActivity {

    private TextView lat;
    private TextView longi;
    private RadioButton virus;
    private RadioButton contaminant;
    private TextView year;
    private boolean virusOn;
    private boolean contaminantOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graph_settings);
        virusOn = false;
        contaminantOn = false;


        lat = (TextView) findViewById(R.id.enterLat);
        longi = (TextView) findViewById(R.id.enterLong);
        virus = (RadioButton) findViewById(R.id.virusButton);
        contaminant = (RadioButton) findViewById(R.id.contaminantButton);
        year = (TextView) findViewById(R.id.enterYear);
        Button generate = (Button) findViewById(R.id.generateGraph);
        Button cancel = (Button) findViewById(R.id.cancel);

        /**
         * button handler for the cancel button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewGraphSettings.this, startApplication.class));
                finish();
            }
        });

        /**
         * button handler for the generate Graph button
          */
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (validInput(lat.getText().toString(), longi.getText().toString(),
                            year.getText().toString(), virus.isChecked(), contaminant.isChecked())) {
                        vars.getInstance().setGraphLat(Double.parseDouble(lat.getText().toString()));
                        vars.getInstance().setGraphLong(Double.parseDouble(longi.getText().toString()));
                        vars.getInstance().setGraphYear(Integer.parseInt(year.getText().toString()));
                        vars.getInstance().setGraphChoice(virus.isChecked() ? "Virus" : "Contaminant");
                        startActivity(new Intent(ViewGraphSettings.this, ViewGraph.class));
                        finish();
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(ViewGraphSettings.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    /**
     * returns true whether or not the input is valid
     * valid = no empty parameters, longitude/ latitude are valid, year is valid
     * @param lat latitude
     * @param longi longitude
     * @param year year
     * @param virus virusPPM
     * @param contaminant contaminant PPM
     * @return true of valid is input, throws exception otherwise
     */
    private boolean validInput(String lat, String longi, String year, Boolean virus, Boolean contaminant) {
        if (lat.equals("") || longi.equals("") || year.equals("") || (virus == contaminant)) {
            throw new IllegalArgumentException("Please enter all information");
        } else {
            if (Double.parseDouble(lat) > 90 || Double.parseDouble(lat) < -90
                || Double.parseDouble(longi) > 180 || Double.parseDouble(longi) < -180) {
                throw new IllegalArgumentException("Please enter valid coordinates");
            } else if (year.length() != 4) {
                throw new IllegalArgumentException("Please enter a valid year");
            } else {
                return true;
            }
        }
    }

    public void onVirusButtonClicked(View view) {
        virus = (RadioButton)view;
        contaminant = (RadioButton) findViewById(R.id.contaminantButton);

        if (virusOn) {
            virus.setChecked(false);
        }
        if (contaminantOn){
            contaminant.setChecked(false);
            contaminantOn = false;
        }
        virusOn = !virusOn;

    }

    public void onContaminantButtonClicked(View view) {
        virus = (RadioButton) findViewById(R.id.virusButton);
        contaminant = (RadioButton)view;

        if (contaminantOn){
            contaminant.setChecked(false);
        }
        if (virusOn) {
            virus.setChecked(false);
            virusOn = false;

        }
        contaminantOn = !contaminantOn;
    }

}
