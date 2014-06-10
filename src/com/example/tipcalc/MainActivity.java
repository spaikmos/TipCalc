package com.example.tipcalc;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private GestureDetectorCompat gDetect;
	private float flingMin = 100;
	private float velocityMin = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gDetect = new GestureDetectorCompat(this, new GestureListener());
		
		setupEditTextListener((EditText)findViewById(R.id.etBillAmt));
		setupEditTextListener((EditText)findViewById(R.id.etTipPercent));
		setupEditTextListener((EditText)findViewById(R.id.etNumPeople));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.gDetect.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	private void setupEditTextListener(EditText etValue)
	{
		etValue.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		        // Fires right as the text is being changed (even supplies the range of text)
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
		            int after) {
		        // Fires right before text is changing
		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		        // Fires right after the text has changed
		        updateOutputs();
		    }
		});
	}
	
	private void updateOutputs()
	{
		// Get the total bill amount
		EditText billAmtEt = (EditText) findViewById(R.id.etBillAmt);
		float billAmtF;
		
		// Make sure the bill amount is valid.  Otherwise set to 0.
		try {
			billAmtF = Float.parseFloat(billAmtEt.getText().toString());			
		} catch (NumberFormatException e) {
			billAmtF = 0;
		}
		
		// Get the tip percentage
		EditText tipPercentEt = (EditText) findViewById(R.id.etTipPercent);
		float tipPercent;
		
		// Make sure tip percentage is valid.  Otherwise set to 0.
		try {
			tipPercent = Float.parseFloat(tipPercentEt.getText().toString()) / 100;
		} catch (NumberFormatException e) {
			tipPercent = 0;
		}
		
		// Get the number of people.  Otherwise set to 1.
		EditText numPeopleEt = (EditText) findViewById(R.id.etNumPeople);
		int numPeople;
		
		// Make sure number of people is valid.  Otherwise set to 0.
		try {
			numPeople = Integer.parseInt(numPeopleEt.getText().toString());
		} catch (NumberFormatException e) {
			numPeople = 1;
		}
		
		float totalTipF = billAmtF * tipPercent;
		float totalBillF = billAmtF + totalTipF;
		float tipPerPersonF = totalTipF / numPeople;
		float totalPerPersonF = totalBillF / numPeople;
		
		// Generate the output
		TextView t = (TextView)findViewById(R.id.tvTotalTip);
		t.setText(String.format("%.2f", totalTipF));
		
		t = (TextView)findViewById(R.id.tvTotalBill);
		t.setText(String.format("%.2f",  totalBillF));
		
		t = (TextView)findViewById(R.id.tvTipPerPerson);
		t.setText(String.format("%.2f",  tipPerPersonF));

		t = (TextView)findViewById(R.id.tvTotalPerPerson);
		t.setText(String.format("%.2f",  totalPerPersonF));
	}
	
	public class GestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent event) {
			return true;
		}
		
		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2,
		    float velocityX, float velocityY) {
			//determine what happens on fling events
			//calculate the change in X position within the fling gesture
			float horizontalDiff = event2.getX() - event1.getX();
			//calculate the change in Y position within the fling gesture
			float verticalDiff = event2.getY() - event1.getY();
			
			float absHDiff = Math.abs(horizontalDiff);
			float absVDiff = Math.abs(verticalDiff);
			float absVelocityX = Math.abs(velocityX);
			//float absVelocityY = Math.abs(velocityY);
			
			if(absHDiff>absVDiff && absHDiff>flingMin && absVelocityX>velocityMin) {
				// Get the number of people.  Otherwise set to 1.
				EditText numPeopleEt = (EditText) findViewById(R.id.etNumPeople);
				int numPeople;
				
				// Make sure number of people is valid.  Otherwise set to 0.
				try {
					numPeople = Integer.parseInt(numPeopleEt.getText().toString());
				} catch (NumberFormatException e) {
					numPeople = 1;
				}

				if(velocityX < 0)
				{
					// Fling to left detected.
					numPeople++;
					numPeopleEt.setText(Integer.toString(numPeople));
					updateOutputs();
				} else if(numPeople > 1)
				{
					// Fling to right detected.
					numPeople--;
					numPeopleEt.setText(Integer.toString(numPeople));
					updateOutputs();
				}
			}
			
			return true;
		}
	}
}
