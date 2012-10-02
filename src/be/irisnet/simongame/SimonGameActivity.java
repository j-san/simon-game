package be.irisnet.simongame;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SimonGameActivity extends Activity implements ColorFragment.PushListener {
    /** Called when the activity is first created. */
	
	private ColorFragment red;
	private ColorFragment green;
	private ColorFragment blue;
	private ColorFragment yellow;
	
	private int sequenceIndex = 0;
	private ArrayList<ColorFragment> sequence;
	
	private ColorFragment[] colors;

	private TextView indicator;

	private boolean challenging;
	private int challengeIndex = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        blue = (ColorFragment)findViewById(R.id.blue);
        red = (ColorFragment)findViewById(R.id.red);
        green = (ColorFragment)findViewById(R.id.green);
        yellow = (ColorFragment)findViewById(R.id.yellow);
        
        indicator = (TextView)findViewById(R.id.indicator);
        
        colors = new ColorFragment[4];
        colors[0] = red;
        colors[1] = green;
        colors[2] = blue;
        colors[3] = yellow;

        blue.setColor(R.color.blue);
        red.setColor(R.color.red);
        green.setColor(R.color.green);
        yellow.setColor(R.color.yellow);
        
        blue.setPushListener(this);
        red.setPushListener(this);
        green.setPushListener(this);
        yellow.setPushListener(this);
        
        initSequence();
        doSequence();
    }
    
    private void initSequence() {
        sequence = new ArrayList<ColorFragment>();
        incSequence();
    }
    
	private void doSequence() {
		
		(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {
				sequence.get(sequenceIndex).on();

				(new Handler()).postDelayed(new Runnable() {
					@Override
					public void run() {
						sequence.get(sequenceIndex).off();

						Log.i("app","sequence "+sequenceIndex);
						sequenceIndex++;
						if(sequenceIndex < sequence.size()) {
							doSequence();
						} else {
							Log.i("app","chanlenge ?");
					        indicator.setText("?");
							sequenceIndex = 0;
							challenging = true;
						}
					}
				}, 300);
			}
		}, 1000);
	}
	
	private void incSequence() {
		challenging = false;
		challengeIndex = 0;
        indicator.setText("0");
		sequence.add(colors[(int) (Math.random() * colors.length)]);
		doSequence();
	}

	@Override
	public void onPush(View v) {
		if (challenging) {
			if (v == sequence.get(challengeIndex)) {
				challengeIndex++;
				Log.i("app", "good");
				
				if (challengeIndex >= sequence.size()) {
					Log.i("app", "Yahouuuuuuu");
			        indicator.setText("V");

			        (new Handler()).postDelayed(new Runnable() {
						@Override
						public void run() {
							incSequence();
						}
					}, 1000);
				}
			} else {
				Log.i("app", "Bouhouhou");
		        indicator.setText("X");

		        (new Handler()).postDelayed(new Runnable() {
					@Override
					public void run() {
						initSequence();
					}
				}, 3000);
			}
		}
	}
}