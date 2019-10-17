package com.example.colorgame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class gameActivity extends AppCompatActivity {

    int xSize, ySize;
    boolean randomGeneration;
    int lightColor, darkColor;
    View[][] allViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Adding the BACK button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Установка размера поля
        // Параметр получен из другйо активности
        Intent cur = getIntent();
        this.xSize = cur.getExtras().getInt("x");
        this.ySize = cur.getExtras().getInt("y");
        this.randomGeneration = cur.getExtras().getBoolean("randomGeneration");

        // Получение цветов поля
        Resources res = getResources();
        this.allViews = new View[this.xSize][this.ySize];
        this.lightColor = res.getColor(R.color.lightColor);
        this.darkColor = res.getColor(R.color.darkColor);

        // Формирование игрового поля
        createTable();
    }

    void createTable(){

        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f);

        for (int i = 0; i < this.xSize; i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(params);

            for (int j = 0; j < this.ySize; j++){
                View view = new View(this);
                view.setLayoutParams(params);
                if (randomGeneration){
                    Random r = new Random();
                    if (r.nextInt(2) == 0){
                        view.setBackgroundColor(this.lightColor);
                    }else {
                        view.setBackgroundColor(this.darkColor);
                    }
                }else {
                    if ((i + j) % 2 == 0) {
                        view.setBackgroundColor(this.lightColor);
                    } else {
                        view.setBackgroundColor(this.darkColor);
                    }
                }
                layout.addView(view);
                this.allViews[i][j] = view;
                view.setTag(i + "" + j);

                // Установка обработчика событий
                // Я не понял как это сделать нормально (помогите)
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeColor(v);

                        int xPos = v.getTag().toString().charAt(0) - 48;
                        int yPos = v.getTag().toString().charAt(1) - 48;
                        for (int i = 0; i < xSize; i++){
                            changeColor(allViews[i][yPos]);
                        }
                        for (int i = 0; i < ySize; i++){
                            changeColor(allViews[xPos][i]);
                        }
                        checkAllColors();
                    }

                    void changeColor(View v) {
                        ColorDrawable d = (ColorDrawable) v.getBackground();
                        if (d.getColor() == darkColor) {
                            v.setBackgroundColor(lightColor);
                        } else {
                            v.setBackgroundColor(darkColor);
                        }
                    }
                });
            }

            mainLayout.addView(layout);
        }
    }

    public void checkAllColors(){

        ColorDrawable d = (ColorDrawable) this.allViews[0][0].getBackground();
        int curColor = d.getColor();

        for (int i = 0; i < xSize; i++){
            for(int j = 0; j < ySize; j++){
                d = (ColorDrawable)this.allViews[i][j].getBackground();
                if (curColor != d.getColor()){
                    return;
                }
            }
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                "YOU WIN!!!", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
