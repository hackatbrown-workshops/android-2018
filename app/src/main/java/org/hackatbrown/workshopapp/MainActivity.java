package org.hackatbrown.workshopapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    public static final String PREFS_NAME = "PlantState";

    private ImageView flower;
    private int flowerGrowth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.flower = findViewById(R.id.flower_image);

        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);

        this.flowerGrowth = prefs.getInt("flowerGrowth", 0);
        updateImage();

        EditText flowerName = findViewById(R.id.flower_name);
        flowerName.setText(prefs.getString("flowerName", ""), TextView.BufferType.EDITABLE);
        flowerName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("o.h.w.MainActivity", "New name: " + v.getText());

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("flowerName", v.getText().toString());
                    editor.apply();
                }
                return false;
            }
        });
    }

    /**
     * Called when the user touches the button to change the displayed flower.
     */
    public void changeFlower(View button) {
        if (flowerGrowth < 2) {
            flowerGrowth++;
        }
        updateImage();

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("flowerGrowth", flowerGrowth);
        editor.apply();
    }

    private void updateImage() {
        if (flowerGrowth == 0) {
            flower.setImageResource(R.drawable.flower_sprout);
        } else if (flowerGrowth == 1) {
            flower.setImageResource(R.drawable.flower_leaf);
        } else if (flowerGrowth == 2) {
            flower.setImageResource(R.drawable.flower_grown);
        }
    }
}
