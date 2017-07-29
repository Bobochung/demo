package com.lsnu.idle.campus_life;

import com.lsnu.idle.campus_life.R;
import com.lsnu.idle.campus_life.R.id;
import com.lsnu.idle.entity.Users;
import com.lsnu.idle.service.MyApplication;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity implements OnClickListener{
        private Button btn_back, btn_right;
        private Intent intent;
        private MyApplication myApplication;
        
        
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		
		btn_back=(Button) this.findViewById(R.id.button_back);
		btn_back.setOnClickListener(this);
		btn_right=(Button) this.findViewById(R.id.button_right);
		btn_right.setOnClickListener(this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
	    // TODO Auto-generated method stub
	    switch(v.getId()) {
	    case id.button_back:
		intent = new Intent(AboutActivity.this,MainActivity.class);
		AboutActivity.this.startActivity(intent);
		AboutActivity.this.finish();
		break;
	    case id.button_right:
		if (!myApplication.userMap.isEmpty()) {
			View view = getLayoutInflater().inflate(R.layout.my_dialog,
					null, false);
			TextView title = (TextView) view.findViewById(R.id.title);
			title.setText("温馨提示");
			TextView message = (TextView) view
					.findViewById(R.id.message);
			message.setVisibility(View.VISIBLE);
			message.setText("你确定要注销吗？");
			TextView gallery = (TextView) view
					.findViewById(R.id.gallery);
			gallery.setVisibility(View.GONE);
			TextView camera = (TextView) view.findViewById(R.id.camera);
			camera.setVisibility(View.GONE);
			final Dialog dialog = new Dialog(AboutActivity.this,
					R.style.myDialogTheme);
			dialog.setContentView(view);
			dialog.show();
			Button cancel = (Button) view
					.findViewById(R.id.button_cancel);
			cancel.setVisibility(View.VISIBLE);
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			Button ok = (Button) view.findViewById(R.id.button_ok);
			ok.setVisibility(View.VISIBLE);
			ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					myApplication.userMap.clear();
					Intent intent = new Intent(AboutActivity.this,
							MainActivity.class);
					startActivity(intent);
					AboutActivity.this.finish();
				}
			});
		}
		break;
		default:
		    break;
	    
	    }
	    
	}

}
