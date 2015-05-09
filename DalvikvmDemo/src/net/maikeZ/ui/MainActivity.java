package net.maikeZ.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import net.maikeZ.dalvikvm.R;
import net.maikeZ.util.MainThread;

public class MainActivity extends Activity implements OnClickListener {
	private TextView tv_01;
	private Button btn_start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_01 = (TextView) findViewById(R.id.tv_01);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_start.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_start:
			tv_01.setText("»’÷æŒª÷√/mnt/sdcard/Dalvikvm.txt");
			btn_start.setEnabled(false);
			MainThread mThread = new MainThread(MainActivity.this);
			mThread.start();
			break;

		default:
			break;
		}

	}
}
