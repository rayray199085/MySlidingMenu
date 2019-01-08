package com.project.stephencao.myslidingmenu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.project.stephencao.myslidingmenu.R;
import com.project.stephencao.myslidingmenu.view.SlidingMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mToggleMenuButton;
    private SlidingMenu mSlidingMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mToggleMenuButton = findViewById(R.id.id_toggle_menu);
        mSlidingMenu = findViewById(R.id.id_sliding_menu);
        mToggleMenuButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_toggle_menu:{
                mSlidingMenu.toggleMenu();
                break;
            }
        }
    }
}
