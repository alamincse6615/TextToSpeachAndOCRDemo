package com.ae.texttospeachdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech mTTS;

    private TextView text;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    Button btn_ocr;
    String song = "মাঝে মাঝে তব দেখা পাই, চিরদিন কেন পাই না\n" +
            "মাঝে মাঝে তব দেখা পাই, চিরদিন কেন পাই না\n" +
            "ওহে কী করিলে বল পাইব তোমারে, রাখিব আঁখিতে আঁখিতে-\n" +
            "ওহে কী করিলে বল পাইব তোমারে, রাখিব আঁখিতে আঁখিতে-\n" +
            "ওহে এত প্রেম আমি কোথা পাব, নাথ\n" +
            "এত প্রেম আমি কোথা পাব, নাথ, তোমারে হৃদয়ে রাখিতে\n" +
            "আমার সাধ্য কিবা তোমারে-\n" +
            "দয়া না করিলে কে পারে-\n" +
            "তুমি আপনি না এলে কে পারে হৃদয়ে রাখিতে\n" +
            "মাঝে মাঝে তব দেখা পাই, চিরদিন কেন পাই না\n" +
            "ওহে আর-কারো পানে চাহিব না আর, করিব হে আজই প্রাণপণ-\n" +
            "ওহে আর-কারো পানে চাহিব না আর, করিব হে আজই প্রাণপণ-\n" +
            "ওহে তুমি যদি বল এখনি করিব...\n" +
            "তুমি যদি বল এখনি করিব বিষয় -বাসনা বিসর্জন\n" +
            "দিব শ্রীচরণে বিষয়- দিব অকাতরে বিষয়-\n" +
            "দিব তোমার লাগি বিষয় -বাসনা বিসর্জন\n" +
            "মাঝে মাঝে তব দেখা পাই, চিরদিন কেন পাই না";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButtonSpeak = findViewById(R.id.button_speak);
        btn_ocr = findViewById(R.id.btn_ocr);
        text = findViewById(R.id.text);
        text.setText(song);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
        btn_ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OcrActivity.class);
                startActivity(intent);

            }
        });


        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {


        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(song, TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }


}