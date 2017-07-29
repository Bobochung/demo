package com.lsnu.idle.campus_life;

import com.lsnu.idle.campus_life.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Thread() {
            @Override
            public void run() {
                super.run();
                try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                Intent  intent=new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
                         }
        }.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
