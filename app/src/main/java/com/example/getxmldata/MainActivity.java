package com.example.getxmldata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mReturnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReturnText = (TextView)findViewById(R.id.Output);
    }

    public void fetchData(View view) {
        new GetCar(mReturnText).execute("");
    }
}
