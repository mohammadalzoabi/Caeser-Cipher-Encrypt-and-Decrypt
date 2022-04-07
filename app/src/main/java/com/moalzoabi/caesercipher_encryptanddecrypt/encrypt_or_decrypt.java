package com.moalzoabi.caesercipher_encryptanddecrypt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class encrypt_or_decrypt extends AppCompatActivity {

    EditText input;
    TextView output;
    Button encrypt, decrypt;
    TextView KeyTextView;
    SeekBar seekBar;
    ImageView deleteText;
    ImageView copyText;
    int keyFromSeekBar = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_or_decrypt);

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        input = findViewById(R.id.inputEditText);
        output = findViewById(R.id.outputEditText);
        deleteText = findViewById(R.id.deleteTextImageView);
        copyText = findViewById(R.id.copyImageView);
        encrypt = findViewById(R.id.encrypt_BTN);
        decrypt = findViewById(R.id.decrypt_BTN);
        KeyTextView = findViewById(R.id.key_BTN);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(25);
        seekBar.setMin(1);
        seekBar.setProgress(1);

        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
                Toast.makeText(encrypt_or_decrypt.this, "Input Cleared.", Toast.LENGTH_SHORT).show();
            }
        });

        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Cipher Output", output.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(encrypt_or_decrypt.this, "Output Copied to Clipboard.", Toast.LENGTH_SHORT).show();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                keyFromSeekBar = progress;
                KeyTextView.setText("Key: " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = input.getText().toString().toUpperCase();
                int key = keyFromSeekBar;
                String encryptedMessage = "";
                for(int i = 0; i< temp.length(); i++) {
                    String letter = Character.toString(temp.charAt(i));
                    if(alphabet.contains(letter)) {
                        int letterIndex = (alphabet.indexOf(letter) + key) % 26;
                        encryptedMessage = encryptedMessage + alphabet.charAt(letterIndex);
                    } else {
                        encryptedMessage = encryptedMessage + letter;
                    }
                }
                output.setText(encryptedMessage);
            }
        });



        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = input.getText().toString().toUpperCase();
                int key = 26 - keyFromSeekBar;
                String decryptedMessage = "";

                for(int i = 0; i< temp.length(); i++) {
                    String letter = Character.toString(temp.charAt(i));
                    if(alphabet.contains(letter)) {
                        int letterIndex = (alphabet.indexOf(letter) + key) % 26;
                        decryptedMessage = decryptedMessage + alphabet.charAt(letterIndex);
                    } else {
                        decryptedMessage = decryptedMessage + letter;
                    }
                }
                output.setText(decryptedMessage);
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(encrypt_or_decrypt.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}