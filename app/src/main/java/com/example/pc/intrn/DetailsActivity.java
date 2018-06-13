package com.example.pc.intrn;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.pc.intrn.Model.items;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title;
    private TextView price;
    private TextView details;
    private TextView provider;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        toolbar=findViewById(R.id.toolbardetails);
        toolbar.setTitle("Intel");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView = findViewById(R.id.image_text);
        title = findViewById(R.id.title_text);
        price = findViewById(R.id.price_text);
        provider = findViewById(R.id.provider_text);
        details = findViewById(R.id.details_text);
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image1));

        title.setText("Intel G4560 7th Genenration Pentium Dual Core Processor");
        provider.setText("by Intel");
        price.setText("Rs.4,633.00");
        details.setText(" * The G4560 and the other Kaby Lake-derived Pentiums lack support for AVX, AVX2, and Intel's Turbo Mode\n" +
                " * LGA1151, Supports Intel 110, 150, 170, 250, 270 Chipsets with DDR4 Support\n" +
                " * Intel® HD Graphics 610\n" +
                " * Intel® Virtualization Technology (VT-x)\n" +
                " * Intel® Hyper-Threading Technology");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(DetailsActivity.this, MainActivity.class));

    }
}
