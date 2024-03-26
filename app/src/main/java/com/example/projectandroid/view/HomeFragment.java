package com.example.projectandroid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends AppCompatActivity {

    LinearLayout personLinearLayout;
    FloatingActionButton favorite;
    CardView cactusCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 뷰 바인딩
        personLinearLayout = findViewById(R.id.person_linear_layout);
        favorite = findViewById(R.id.favorite);
        cactusCardView = findViewById(R.id.cactus_card_view);

        // 각 뷰의 클릭 이벤트 설정
        personLinearLayout.setOnClickListener(view -> {
            // Main3Activity로 이동
            Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
            startActivity(intent);
        });

        favorite.setOnClickListener(view -> {
            // Main4Activity로 이동
            Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
            startActivity(intent);
        });

        cactusCardView.setOnClickListener(view -> {
            // Main5Activity로 이름과 가격 정보 전달
            Intent intent = new Intent(getApplicationContext(), Main5Activity.class);
            intent.putExtra("itemName1", "비나이더짐"); // 이름 데이터 추가
            intent.putExtra("itemPrice1", "15,000원"); // 가격 데이터 추가
            startActivity(intent);
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 화면 터치 시 키보드 숨김 처리
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof android.widget.EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
