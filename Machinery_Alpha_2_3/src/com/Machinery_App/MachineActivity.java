package com.Machinery_App;

import com.Machinery_App.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MachineActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);
        
        TextView text = (TextView) findViewById(R.id.MAtitle);
        text.setText("Set text properly");

        Button backButton = (Button) findViewById(R.id.MAbackbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Closing SecondScreen Activity
                finish();
            }
        });
    }
}
