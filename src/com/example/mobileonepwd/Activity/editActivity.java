package com.example.mobileonepwd.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import com.example.mobileonepwd.R;

/**
 * Created by CheerS17 on 5/22/15.
 */
public class editActivity extends Activity {

    private NumberPicker lPicker;
    private NumberPicker nPicker;
    private NumberPicker pPicker;
    private Button enterButton;
    private EditText editText1;
    private EditText editText2;

    private int l=2;
    private int n=2;
    private int p=2;

    Intent intent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        lPicker = (NumberPicker) findViewById(R.id.edit_numberPicker1);
        nPicker = (NumberPicker) findViewById(R.id.edit_numberPicker4);
        pPicker = (NumberPicker) findViewById(R.id.edit_numberPicker3);
        enterButton = (Button) findViewById(R.id.edit_enter_button);
        editText1 = (EditText) findViewById(R.id.edit_editText1);
        editText2 = (EditText) findViewById(R.id.edit_editText2);

        lPicker.setMinValue(2);
        lPicker.setMaxValue(6);
        nPicker.setMinValue(2);
        nPicker.setMaxValue(6);
        pPicker.setMinValue(2);
        pPicker.setMaxValue(6);

        lPicker.setDisplayedValues(new String[] {"2","3","4","5","6"});
        nPicker.setDisplayedValues(new String[] {"2","3","4","5","6"});
        pPicker.setDisplayedValues(new String[] {"2","3","4","5","6"});

        lPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                l = i1;
            }
        });

        nPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                n = i1;
            }
        });

        pPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                p = i1;
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText1.getText().toString().trim().equals(""))
                    Toast.makeText(editActivity.this,"请输入网站名",Toast.LENGTH_SHORT).show();
                else if (editText2.getText().toString().trim().equals(""))
                    Toast.makeText(editActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("editActivity_editText1",editText1.getText().toString());
                    bundle.putString("editActivity_editText2",editText2.getText().toString());
                    bundle.putInt("editActivity_l", l);
                    bundle.putInt("editActivity_n",n);
                    bundle.putInt("editActivity_p",p);
                    intent.setClass(editActivity.this,homeActivity.class);
                    intent.putExtras(bundle);
                    setResult(100,intent);
                    finish();
//                    Toast.makeText(editActivity.this,editText1.getText().toString() + " " + editText2.getText().toString() + " " + l + " " + n + " " + p, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}