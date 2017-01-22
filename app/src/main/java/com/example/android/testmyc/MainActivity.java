package com.example.android.testmyc;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public int score = 0;
    public int flag = 1;

    public RadioButton answerQ1_A = null, answerQ1_B = null, answerQ1_C = null, answerQ1_D = null;
    public RadioButton answerQ2_A = null, answerQ2_B = null, answerQ2_C = null, answerQ2_D = null;
    public EditText name = null, answerQ3 = null, answerQ4 = null;
    public CheckBox answerQ5_A = null, answerQ5_B = null, answerQ5_C = null, answerQ5_D = null;
    public CheckBox answerQ6_A = null, answerQ6_B = null, answerQ6_C = null, answerQ6_D = null;
    public RelativeLayout options1 = null, options2 = null, options5 = null, options6 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // to stop keyboard being opened when app starts
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.changeFonts();
    }

    // this function is used in order to use 'courier' font for displaying code fragments wherever needed in the quiz app
    public void changeFonts() {

        // use  custom font 'courier bold'
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/courbd.ttf");

        // changing fonts of options of question 1
        // rb - short form for radio button object
        RadioButton rb = (RadioButton) findViewById(R.id.question1_A);
        rb.setTypeface(custom_font);

        rb = (RadioButton) findViewById(R.id.question1_B);
        rb.setTypeface(custom_font);

        rb = (RadioButton) findViewById(R.id.question1_C);
        rb.setTypeface(custom_font);

        rb = (RadioButton) findViewById(R.id.question1_D);
        rb.setTypeface(custom_font);

        // changing font of code (inside TextView) of question 2
        // txt - short form for text object
        TextView txt = (TextView) findViewById(R.id.question2_Text);
        txt.setTypeface(custom_font);

        // displaying code in textbox of question 2
        TextView codeSnippet = (TextView) findViewById(R.id.question2_Text);
        codeSnippet.setText("#include<stdio.h> \nint main( ) \n{ \n\tprintf(\"Udacity Rocks\"); \n\tmain( ); \n\treturn0; \n}");

        // changing font of options of question 5
        // cb - short form for checkbox object
        CheckBox cb = (CheckBox) findViewById(R.id.question5_A);
        cb.setTypeface(custom_font);

        cb = (CheckBox) findViewById(R.id.question5_B);
        cb.setTypeface(custom_font);

        cb = (CheckBox) findViewById(R.id.question5_C);
        cb.setTypeface(custom_font);

        cb = (CheckBox) findViewById(R.id.question5_D);
        cb.setTypeface(custom_font);

        // changing font of options of question 6
        cb = (CheckBox) findViewById(R.id.question6_A);
        cb.setTypeface(custom_font);

        cb = (CheckBox) findViewById(R.id.question6_B);
        cb.setTypeface(custom_font);

        cb = (CheckBox) findViewById(R.id.question6_C);
        cb.setTypeface(custom_font);

        cb = (CheckBox) findViewById(R.id.question6_D);
        cb.setTypeface(custom_font);
    }

    // this function is used in order to control the states of RadioButtons programmatically as RadioGroup will not work in current layout.
    public void checkStatus(View view) {

        RadioButton a, b, c, d; // RadioButtons corresponding to 4 options(a,b,c,d)

        int id = view.getId(); // get view id of the calling view

        /* we can't use RadioGroup for displaying the 4 options in the way they are being presented now.
          RadioGroup supports only horizontal or vertical display of RadioButtons.
          The layout in which 4 options of a question are presented is
          in RelativeLayout and RadioGroup is inherited from LinearLayout. Plus, if we try to display
          the 4 options using LinearLayout then we have to use two LinearLayout views in order to
          preserve the current layout style but then also RadioGroup will not work as RadioGroup
          requires RadioButtons as its immediate children. It doesn't support nesting of views.

          So we have to control the state of RadioButtons programmatically.

          below is the logic for selecting only one of the radio buttons.
        */
        if (id == R.id.question1_A ||
                id == R.id.question1_B ||
                id == R.id.question1_C ||
                id == R.id.question1_D) {

            // get view id's of all the 4 options
            a = (RadioButton) findViewById(R.id.question1_A);
            b = (RadioButton) findViewById(R.id.question1_B);
            c = (RadioButton) findViewById(R.id.question1_C);
            d = (RadioButton) findViewById(R.id.question1_D);

        } else {
            a = (RadioButton) findViewById(R.id.question2_A);
            b = (RadioButton) findViewById(R.id.question2_B);
            c = (RadioButton) findViewById(R.id.question2_C);
            d = (RadioButton) findViewById(R.id.question2_D);
        }

        // here we are checking which option is chosen by user and after finding it we will reset other 3 options
        switch (view.getId()) {

            case R.id.question1_A:
            case R.id.question2_A:
                b.setChecked(false);
                c.setChecked(false);
                d.setChecked(false);
                break;


            case R.id.question1_B:
            case R.id.question2_B:
                a.setChecked(false);
                c.setChecked(false);
                d.setChecked(false);
                break;

            case R.id.question1_C:
            case R.id.question2_C:
                b.setChecked(false);
                a.setChecked(false);
                d.setChecked(false);
                break;

            case R.id.question1_D:
            case R.id.question2_D:
                b.setChecked(false);
                c.setChecked(false);
                a.setChecked(false);
                break;
        }
    }

    // this function is used to calculate the score if all questions are attempted and display it in a toast message
    public void calculateScore(View view) {

        /* this is the toast message with default contents "you forgot". If the user forgot to enter his name or any questions, then
           we will append(concatenate) the Question No. to this string */
        String toastMessage = "you forgot ";

        /*
            ID's associated with resources will remain the same till the lifetime of Program.
            We don't want to trouble the compiler to calculate the ID every time when Submit Button is pressed.
            For ex: In first attempt User scores 3/5 and then he tries he to re-answer the wrong ones
            without closing the app. In that case View ID's will remain the same.
            This will save time and increase performance as compiler will not have to traverse the
            entire object tree again in order to find the View ID.

            Below we are finding the View ID's of all the RadioButtons, EditTexts, CheckBoxes etc..
         */
        if (flag == 1) {

            name = (EditText) findViewById(R.id.nameOfCandidate);

            options1 = (RelativeLayout) findViewById(R.id.options1);
            answerQ1_A = (RadioButton) findViewById(R.id.question1_A);
            answerQ1_B = (RadioButton) findViewById(R.id.question1_B);
            answerQ1_C = (RadioButton) findViewById(R.id.question1_C);
            answerQ1_D = (RadioButton) findViewById(R.id.question1_D);

            options2 = (RelativeLayout) findViewById(R.id.options2);
            answerQ2_A = (RadioButton) findViewById(R.id.question2_A);
            answerQ2_B = (RadioButton) findViewById(R.id.question2_B);
            answerQ2_C = (RadioButton) findViewById(R.id.question2_C);
            answerQ2_D = (RadioButton) findViewById(R.id.question2_D);

            answerQ3 = (EditText) findViewById(R.id.question3);

            answerQ4 = (EditText) findViewById(R.id.question4);

            options5 = (RelativeLayout) findViewById(R.id.options5);
            answerQ5_A = (CheckBox) findViewById(R.id.question5_A);
            answerQ5_B = (CheckBox) findViewById(R.id.question5_B);
            answerQ5_C = (CheckBox) findViewById(R.id.question5_C);
            answerQ5_D = (CheckBox) findViewById(R.id.question5_D);

            options6 = (RelativeLayout) findViewById(R.id.options6);
            answerQ6_A = (CheckBox) findViewById(R.id.question6_A);
            answerQ6_B = (CheckBox) findViewById(R.id.question6_B);
            answerQ6_C = (CheckBox) findViewById(R.id.question6_C);
            answerQ6_D = (CheckBox) findViewById(R.id.question6_D);
        } else
            flag = 0;

        /*
            In below six if statements we are confirming whether user has given input to all the
            questions or not. If not then we will append the appropriate message at the end of toast
         */
        if (name.getText().toString().matches("")) {
            toastMessage = toastMessage + "(your name) ";
        }

        if (!answerQ1_A.isChecked() &&
                !answerQ1_B.isChecked() &&
                !answerQ1_C.isChecked() &&
                !answerQ1_D.isChecked()) {
            toastMessage = toastMessage + "(Q1) ";
        }

        if (!answerQ2_A.isChecked() &&
                !answerQ2_B.isChecked() &&
                !answerQ2_C.isChecked() &&
                !answerQ2_D.isChecked()) {
            toastMessage = toastMessage + "(Q2) ";
        }

        if (answerQ3.getText().toString().matches("")) {
            toastMessage = toastMessage + "(Q3) ";
        }

        if (answerQ4.getText().toString().matches("")) {
            toastMessage = toastMessage + "(Q4) ";
        }

        if (!answerQ5_A.isChecked() &&
                !answerQ5_B.isChecked() &&
                !answerQ5_C.isChecked() &&
                !answerQ5_D.isChecked()) {
            toastMessage = toastMessage + "(Q5) ";
        }

        if (!answerQ6_A.isChecked() &&
                !answerQ6_B.isChecked() &&
                !answerQ6_C.isChecked() &&
                !answerQ6_D.isChecked()) {
            toastMessage = toastMessage + "(Q6) ";
        }

        // display the toast if user forgot to answer any question
        if (!toastMessage.equals("you forgot ")) {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        // calculate final score and set background color to red/white depending upon answer is wrong/right
        if (answerQ1_D.isChecked()) {
            score++;
            options1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            options1.setBackgroundColor(Color.parseColor("#F44336"));
        }

        if (answerQ2_C.isChecked()) {
            score++;
            options2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            options2.setBackgroundColor(Color.parseColor("#F44336"));
        }

        if (answerQ3.getText().toString().trim().toLowerCase().equals("pointer")) {
            score++;
            answerQ3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            answerQ3.setBackgroundColor(Color.parseColor("#F44336"));
        }

        if (answerQ4.getText().toString().trim().equals("return")) {
            score++;
            answerQ4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            answerQ4.setBackgroundColor(Color.parseColor("#F44336"));
        }

        if (answerQ5_A.isChecked() &&
                answerQ5_B.isChecked() &&
                answerQ5_D.isChecked() &&
                !answerQ5_C.isChecked()) {
            score++;
            options5.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            options5.setBackgroundColor(Color.parseColor("#F44336"));
        }

        if (answerQ6_C.isChecked() &&
                answerQ6_D.isChecked() &&
                !answerQ6_A.isChecked() &&
                !answerQ6_B.isChecked()) {
            score++;
            options6.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            options6.setBackgroundColor(Color.parseColor("#F44336"));
        }

        //display the score in toast and reset the score value to 0
        Toast.makeText(this, "Score = " + score + "/6", Toast.LENGTH_SHORT).show();
        score = 0;
    }
}
