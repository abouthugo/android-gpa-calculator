package com.example.gpa_10894634_calculator;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  TextView result_text_view;
  ConstraintLayout layout;
  ArrayList<EditText> list = new ArrayList<>();
  Button btn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Program starts
    result_text_view = findViewById(R.id.result);
    layout = findViewById(R.id.lay);
    btn = findViewById(R.id.button);
    getAllIns();
  }

  /**
   * Triggers when the button is pressed If the inputs are valid it computes the GPA and displays it
   * on the result_text_view label If otherwise the inputs are invalid it displays a snackbar
   * directing the user how to fix the error
   */
  public void fun(View view) {
    if (btn.getText().toString().equalsIgnoreCase("Clear")) {
      resetValues();
      btn.setText("Calculate");
    } else {
      if (validState()) {
        int gpa = computeGPA();
        String msg = "Your GPA is: " + gpa;
        result_text_view.setText(msg);
        btn.setText("Clear");
        if (gpa >= 80) {
          layout.setBackgroundColor(Color.parseColor("#5CD89F"));
        } else if (gpa >= 61) {
          layout.setBackgroundColor(Color.parseColor("#FFD36E"));
        } else {
          layout.setBackgroundColor(Color.parseColor("#FF5C3E"));
        }

      } else {
        Snackbar.make(view,
            "Your entries must all be in the range of 0-100 ",
            Snackbar.LENGTH_LONG).show();
      }
    }
  }

  /**
   * Isolates all the input fields and puts them in an array called 'list'
   */
  public void getAllIns() {
    for (int i = 0; i < layout.getChildCount(); i++) {
      if (layout.getChildAt(i) instanceof EditText) {
        list.add((EditText) layout.getChildAt(i));
      }
    }
  }

  /**
   * Checks that the input is valid
   *
   * @param in The input to check
   */
  public boolean valid(EditText in) {
    String str = in.getText().toString();
    if (str.length() <= 0) {
      return false;
    }
    int val = Integer.parseInt(str);
    return (val >= 0 && val <= 100);
  }

  /**
   * Checks that all the input fields have a valid Grade
   */
  public boolean validState() {
    for (EditText i : list) {
      if (!valid(i)) {
        return false;
      }
    }
    return true;
  }


  /**
   * Computes the gpa
   *
   * @return gpa = (grade*number_of_courses)/(100*number_of_courses)
   */
  public int computeGPA() {
    int res = 0;
    int grade;
    String str;
    for (EditText i : list) {
      str = i.getText().toString();
      grade = Integer.parseInt(str);
      res += grade;
    }
    res /= (list.size());

    return res;
  }

  public void resetValues() {
    for (EditText input : list) {
      input.setText("");
    }
    result_text_view.setText("");
    layout.setBackgroundColor(Color.parseColor("#FAFAFA"));
  }

}
