package com.example.absenmanual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    TabLayoutMediator mediator;
    SampleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        SampleAdapter adapter = new SampleAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        mediator = new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            if (position == 0) {
                tab.setText("Masuk");
            } else if (position == 1) {
                tab.setText("Daftar");
            }
        }));
        mediator.attach();
    }
}