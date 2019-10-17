package com.example.colorgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        EditText ySizeV = findViewById(R.id.xSize);
        EditText xSizeV = findViewById(R.id.ySize);

        String dataX = xSizeV.getText().toString();
        String dataY = ySizeV.getText().toString();
        if (dataX.equals("") || dataY.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Поле осталось не заполненным", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        int xSize, ySize;
        xSize = Integer.parseInt(dataX);
        ySize = Integer.parseInt(dataY);

        if (xSize > 9 || ySize > 9){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Значения в полях не должны превышать 9", Toast.LENGTH_SHORT);
            toast.show();
        } else if (xSize < 3 || ySize < 3){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Значения в полях не должны быть меньше 3", Toast.LENGTH_SHORT);
            toast.show();
        } else{
            Intent intent = new Intent(this, gameActivity.class);
            intent.putExtra("x", xSize);
            intent.putExtra("y", ySize);
            CheckBox checkbox = findViewById(R.id.checkBox);
            if (checkbox.isChecked()){
                intent.putExtra("randomGeneration", true);
            }else{
                intent.putExtra("randomGeneration", false);
            }
            startActivity(intent);
        }
    }
}
