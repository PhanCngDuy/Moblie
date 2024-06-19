package com.phancongduy.de4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phancongduy.de4.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Handler handler = new Handler();
    Random random = new Random();
    int count = 0 ;

    LinearLayout currentRow;



    //====================== de 5
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            if (count % 2 == 0) {
//                currentRow = new LinearLayout(MainActivity.this);
//                currentRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                currentRow.setOrientation(LinearLayout.HORIZONTAL);
//                binding.container.addView(currentRow);
//            }
//
//            for (int i = 0; i < 2; i++) {
//                int randNumb = random.nextInt(10);
//
//                Button button = new Button(MainActivity.this);
//                button.setText(String.valueOf(randNumb));
//                button.setTextSize(24);
//                button.setTextColor(Color.WHITE);
//
//                LinearLayout.LayoutParams params;
//                if (randNumb % 2 == 0) {
//                    button.setBackgroundColor(Color.rgb(0, 150, 156));
//                    params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2); // Chẵn: trọng số 2
//                } else {
//                    button.setBackgroundColor(Color.rgb(224, 67, 54));
//                    params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 4); // Lẻ: trọng số 4
//                }
//
//                params.setMargins(15, 15, 15, 15);
//                button.setLayoutParams(params);
//
//                currentRow.addView(button);
//                count++;
//            }
//        }
//    };


    //=======================de2

//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(10,10,10,10);
//
//            int rand = random.nextInt(100);
//
//            Button button = new Button(MainActivity.this);
//            button.setText(String.valueOf(rand));
//            button.setTextColor(Color.BLUE);
//            button.setLayoutParams(params);
//
//            EditText textView = new EditText(MainActivity.this);
//            textView.setText(String.valueOf(rand));
//            textView.setTextColor(Color.RED);
//            textView.setLayoutParams(params);
//
//            if (rand % 2 == 0) {
//                params.gravity = Gravity.CENTER;
//                binding.container.addView(button);
//            }else {
//                params.gravity = Gravity.START;
//                binding.container.addView(textView);
//            }
//        }
//    };




    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // thiet giao dien chung cho textView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 100, 1);
            params.setMargins(10,10,10,10);

            // o chua text view
            LinearLayout.LayoutParams bg_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout bg_textView = new LinearLayout(MainActivity.this);
            bg_textView.setLayoutParams(bg_params);
            bg_textView.setWeightSum(3);
            bg_textView.setOrientation(LinearLayout.HORIZONTAL);

            //xu ly
            for (int item = 0 ;item < 3; ++item ) {
                if (count >= Integer.parseInt(binding.editNumber.getText().toString())){
                    break;
                }
                int rand = random.nextInt(10);
                TextView textView = new TextView(MainActivity.this);
                textView.setLayoutParams(params);
                textView.setText(String.valueOf(rand));
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                if (rand % 2 == 0) {
                    textView.setBackgroundColor(Color.BLUE);
                }
                else
                    textView.setBackgroundColor(Color.GRAY);

                bg_textView.addView(textView);

                count++;
            }
            binding.container.addView(bg_textView);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        addEvent();
    }

    private void addEvent() {
        binding.btndraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBackGround();
            }
        });
    }

    private void drawBackGround() {
        binding.container.removeAllViews();
        count = 0;
        int numbOfViews = Integer.parseInt(binding.editNumber.getText().toString()); // lay edittext
        //tao luong
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //dk chay ...
                for (int item = 1 ; item <= numbOfViews; item ++) {
                    if (count % 3 == 0) {
                        handler.post(runnable);
                    }
                    if (count == numbOfViews) {
                        handler.removeCallbacks(runnable);
                    }
                    SystemClock.sleep(1000);
                }
            }
        });
        thread.start();
    }
}