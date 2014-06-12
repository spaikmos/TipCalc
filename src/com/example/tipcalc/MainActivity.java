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
		setupEditTextListener((EditText)findViewById(R.id.etNumPeople));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.gDetect.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	// Need to add this to detect gestures in scrollview
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
	    super.dispatchTouchEvent(ev);    
	    return gDetect.onTouchEvent(ev); 
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
		
		// Get the number of people.
		EditText numPeopleEt = (EditText) findViewById(R.id.etNumPeople);
		int numPeople;
		
		// Make sure number of people is valid.  Otherwise set to 1.
		try {
			numPeople = Integer.parseInt(numPeopleEt.getText().toString());
		} catch (NumberFormatException e) {
			numPeople = 1;
		}
		
		TextView tvPercent = (TextView)findViewById(R.id.tvPercent);
		TextView tvTipPerson = (TextView)findViewById(R.id.tvTipPerson);
		TextView tvBillPerson = (TextView)findViewById(R.id.tvBillPerson);
		TextView tvTipTotal = (TextView)findViewById(R.id.tvTipTotal);
		TextView tvBillTotal = (TextView)findViewById(R.id.tvBillTotal);
		
		// Clear the text views
		tvPercent.setText("");
		tvTipPerson.setText("");
		tvBillPerson.setText("");
		tvTipTotal.setText("");
		tvBillTotal.setText("");
		
		// Generate the output
		for(int i = 1; i <= 25; i = i+1)
		{
			tvPercent.append(String.format("%d\n", i));
			tvTipPerson.append(String.format("%.2f\n", (billAmtF * i / 100 / numPeople)));
			tvBillPerson.append(String.format("%.2f\n", (billAmtF * (100+i) / 100 / numPeople)));
			tvTipTotal.append(String.format("%.2f\n", (billAmtF * i / 100)));
			tvBillTotal.append(String.format("%.2f\n", (billAmtF * (100+i) / 100)));
		}
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
