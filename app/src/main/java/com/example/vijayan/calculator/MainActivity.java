package com.example.vijayan.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private TextView txtOne;
    boolean lastNumeric;
    boolean stateError;
    boolean lastDot;
    int[] numericButtons = {R.id.bt0, R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9};
    int[] operatorButtons = {R.id.btplus, R.id.btmin, R.id.btnmulti, R.id.btdiv};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtOne = (TextView) findViewById(R.id.txtOne);
        setNumericOnClickListener();
        setOperatorOnClickListener();
    }



private void setNumericOnClickListener() {
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            if (stateError) {
                txtOne.setText(button.getText());
                stateError = false;
            } else {
                txtOne.append(button.getText());
            }
            lastNumeric = true;
        }
    };
    for (int id : numericButtons) {
        findViewById(id).setOnClickListener(listener);
    }
}
private void setOperatorOnClickListener() {
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (lastNumeric && !stateError) {
                Button button = (Button) v;
                txtOne.append(button.getText());
                lastNumeric = false;
                lastDot = false;
            }
        }
    };
    for (int id : operatorButtons) {
        findViewById(id).setOnClickListener(listener);
    }
    findViewById(R.id.btpoint).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (lastNumeric && !stateError && !lastDot) {
                txtOne.append(".");
                lastNumeric = false;
                lastDot = true;
            }
        }
    });

    findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtOne.setText("");
            lastNumeric = false;
            stateError = false;
            lastDot = false;
        }
    });
    findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onEqual();
        }
    });
}

    private void onEqual() {
        if (lastNumeric && !stateError) {
            String txt = txtOne.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                txtOne.setText(Double.toString(result));
                lastDot = true;
            } catch (ArithmeticException ex) {
                txtOne.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }
}
