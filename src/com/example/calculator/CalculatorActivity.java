package com.example.calculator;

import java.util.List;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.R.bool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends ActionBarActivity {

	private Button button_1;
	private Button button_2;
	private Button button_3;
	private Button button_4;
	private Button button_5;
	private Button button_6;
	private Button button_7;
	private Button button_8;
	private Button button_9;
	private Button button_0;
	private Button button_add;
	private Button button_subtract;
	private Button button_multiply;
	private Button button_divide;
	private Button button_dot;
	private Button button_equal;
	private Button button_clear;
	private Button button_bin;
	private Button button_oct;
	private Button button_hex;
	private TextView expr_history;
	private TextView expr_n_result;
	private String expr_buffer;
	private String expr_number;
	private List<String> expr_stack;

	private OnClickListener operandListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Button btn = (Button) v;
			typist(expr_n_result, btn.getText().toString(), true);
		}
	};

	private OnClickListener convertFormatListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Button btn = (Button) v;
			String converted = "";
			try {
				switch (btn.getText().charAt(0)) {
				case 'B':
					converted = Integer.toBinaryString(Integer
							.parseInt(expr_number));
					break;
				case 'O':
					converted = Integer.toOctalString(Integer
							.parseInt(expr_number));
					break;
				case 'H':
					converted = Integer.toHexString(Integer
							.parseInt(expr_number));
					break;
				}
			} catch (Exception ex) {

			}
			typist(expr_n_result, converted, false);
			clear(false);
		}
	};

	private OnClickListener evaluateListener = new OnClickListener() {

		@Override
		public void onClick(View v) {		    
			Double result = evaluate();
			String resultToPrint = "";
			if(!result.isNaN()) {
				if (result.intValue() == result) {
					Integer intResult = result.intValue();
					resultToPrint = intResult.toString();
				} else {
					resultToPrint = result.toString();
				}				
				clear(false);								
			} else {
				resultToPrint = "NaN";
				typist(expr_n_result, "NaN", false);
				clear(false);				
			}
			
			typist(expr_n_result, resultToPrint, false);
			clear(false);
			if(!result.isNaN() && result != 0) {
				expr_number = resultToPrint;
			}
			
			
		}
	};

	private OnClickListener clearListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			clear(true);
		}
	};

	private void clear(Boolean reset) {
		expr_history.setText("");
		expr_stack = new ArrayList<String>();
		expr_number = "";
		expr_buffer = "";
		if (reset) {
			expr_history.setText(expr_buffer);
			expr_n_result.setText("");
		}
	}

	private double evaluate() {
		double a;
		double b;
		char operator;
		double result = 0;
		try {

			if (!expr_number.isEmpty() && !expr_number.endsWith(".")) {
				expr_stack.add(expr_number);
			}
			for (int i = 0; i < expr_stack.size(); i++) {
				if (i == 0) {
					a = Double.parseDouble(expr_stack.get(i));
					i++;
				} else {
					a = result;
				}

				if (i < expr_stack.size()) {
					operator = expr_stack.get(i).charAt(0);
					i++;
					if (i < expr_stack.size()) {
						b = Double.parseDouble(expr_stack.get(i));

						switch (operator) {
						case '+':
							result = a + b;
							break;
						case '-':
							result = a - b;
							break;
						case '*':
							result = a * b;
							break;
						case '÷':
							if(b != 0) {
								result = a / b;
							} else {
								result = Double.NaN;
							}
							
							break;
						}

					}
				}

			}
		} catch (Exception ex) {
			result = Double.NaN;
		}
		return result;
	}

	private void typist(TextView typeArea, String typeText, Boolean appendMode) {
		if (appendMode) {
			// expr_buffer = typeArea.getText().toString() + typeText;
			if (typeText.equals("+") || typeText.equals("-")
					|| typeText.equals("*") || typeText.equals("÷")) {
				if (!expr_number.isEmpty()) {
					/*
					 * && (expr_buffer.charAt(expr_buffer.length()-1) != '+' ||
					 * expr_buffer.charAt(expr_buffer.length()-1) != '-' ||
					 * expr_buffer.charAt(expr_buffer.length()-1) != '*' ||
					 * expr_buffer.charAt(expr_buffer.length()-1) != '÷')
					 */
					expr_buffer += expr_number + typeText;
					expr_stack.add(expr_number);
					expr_stack.add(typeText);
					expr_history.setText(expr_buffer);
					expr_n_result.setText("");
					expr_number = "";

				}

			} else {
				expr_number = expr_number + typeText;
				expr_n_result.setText(expr_number);
			}

		} else {
			expr_n_result.setText(typeText);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		expr_stack = new ArrayList<String>();
		expr_number = "";
		expr_buffer = "";

		button_0 = (Button) findViewById(R.id.button_zero);
		button_0.setOnClickListener(operandListener);

		button_1 = (Button) findViewById(R.id.button_one);
		button_1.setOnClickListener(operandListener);

		button_2 = (Button) findViewById(R.id.button_two);
		button_2.setOnClickListener(operandListener);

		button_3 = (Button) findViewById(R.id.button_three);
		button_3.setOnClickListener(operandListener);

		button_4 = (Button) findViewById(R.id.button_four);
		button_4.setOnClickListener(operandListener);

		button_5 = (Button) findViewById(R.id.button_five);
		button_5.setOnClickListener(operandListener);

		button_6 = (Button) findViewById(R.id.button_six);
		button_6.setOnClickListener(operandListener);

		button_7 = (Button) findViewById(R.id.button_seven);
		button_7.setOnClickListener(operandListener);

		button_8 = (Button) findViewById(R.id.button_eight);
		button_8.setOnClickListener(operandListener);

		button_9 = (Button) findViewById(R.id.button_nine);
		button_9.setOnClickListener(operandListener);

		button_add = (Button) findViewById(R.id.button_add);
		button_add.setOnClickListener(operandListener);

		button_subtract = (Button) findViewById(R.id.button_subtract);
		button_subtract.setOnClickListener(operandListener);

		button_multiply = (Button) findViewById(R.id.button_multiply);
		button_multiply.setOnClickListener(operandListener);

		button_divide = (Button) findViewById(R.id.button_divide);
		button_divide.setOnClickListener(operandListener);

		button_dot = (Button) findViewById(R.id.button_dot);
		button_dot.setOnClickListener(operandListener);

		button_equal = (Button) findViewById(R.id.button_equal);
		button_equal.setOnClickListener(evaluateListener);

		button_clear = (Button) findViewById(R.id.button_clear);
		button_clear.setOnClickListener(clearListener);

		button_bin = (Button) findViewById(R.id.button_bin);
		button_bin.setOnClickListener(convertFormatListener);
		
		button_oct = (Button) findViewById(R.id.button_oct);
		button_oct.setOnClickListener(convertFormatListener);
		
		button_hex = (Button) findViewById(R.id.button_hex);
		button_hex.setOnClickListener(convertFormatListener);

		expr_history = (TextView) findViewById(R.id.expr_history);
		expr_n_result = (TextView) findViewById(R.id.expr_n_result);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
