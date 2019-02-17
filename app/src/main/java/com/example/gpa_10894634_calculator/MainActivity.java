package com.example.gpa_10894634_calculator;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

  TextView result;
  ConstraintLayout layout;
  ArrayList<EditText> list = new ArrayList<>();
  HashMap<String, Double> gradeWeight = new HashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Program starts
    result = findViewById(R.id.result);
    layout = findViewById(R.id.lay);
    getAllIns();
    startWeights();
  }

  /**
   * Triggers when the button is pressed
   * If the inputs are valid it computes the GPA and displays it on the result label
   * If otherwise the inputs are invalid it displays a snackbar directing the user how to fix
   * the error
   * @param view
   */
  public void fun(View view) {
    if (validState()) {
      double gpa = computeGPA();
      DecimalFormat df = new DecimalFormat("#.##");
      result.setText(Double.valueOf(df.format(gpa)).toString());
      if(gpa >= 3.20){
        layout.setBackgroundColor(Color.parseColor("#5CD89F"));
      } else if(gpa >= 2.44){
        layout.setBackgroundColor(Color.parseColor("#FFD36E"));
      } else {
        layout.setBackgroundColor(Color.parseColor("#FF5C3E"));
      }

    } else {
      Snackbar.make(view,
          "Your entries are not valid, must enter capital letters with or without '+' or '-' ",
          Snackbar.LENGTH_LONG).show();
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
    String pattern = "[A-F][+-]{0,1}";
    String pat = "(A\\+)|(F)[-+]{1}";
    Pattern regex = Pattern.compile(pattern);
    Pattern r = Pattern.compile(pat);
    String str = in.getText().toString();
    return regex.matcher(str).matches() && !r.matcher(str).matches();
  }

  /**
   * Checks that all the input fields have a valid letter grade
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
   * Populates the hash map of key value pairs with their respective weights for computation
   */
  public void startWeights() {
    gradeWeight.put("A", 4.00);
    gradeWeight.put("A-", 3.70);
    gradeWeight.put("B+", 3.33);
    gradeWeight.put("B", 3.00);
    gradeWeight.put("B-", 2.70);
    gradeWeight.put("C+", 2.30);
    gradeWeight.put("C", 2.00);
    gradeWeight.put("C-", 1.70);
    gradeWeight.put("D+", 1.30);
    gradeWeight.put("D", 1.00);
    gradeWeight.put("D-", 1.70);
    gradeWeight.put("F", 0.00);
  }

  /**
   * Computes the gpa
   * @return  gpa = Sigma_i^n[(grade*credit)/credit]
   */
  public double computeGPA() {
    double res = 0;
    int credit = 3;
    double grade;
    String str;
    for (EditText i : list) {
      str = i.getText().toString();
      grade = gradeWeight.get(str);
      res += (grade * credit);
    }
    res /= (credit * list.size());

    return res;
  }


}
