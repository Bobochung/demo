package com.lsnu.idle.campus_life;

import java.util.ArrayList;
import java.util.HashMap;

import com.lsnu.idle.service.MyApplication;
import com.lsnu.idle.campus_life.R;
import com.lsnu.idle.entity.Users;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
	private int[] images = { R.drawable.gv_1, R.drawable.gv_2, R.drawable.gv_3,
			R.drawable.gv_4, R.drawable.gv_5, R.drawable.gv_6, R.drawable.gv_7,
			R.drawable.gv_8, R.drawable.gv_9 };// gridview的图片
	private String[] titles = { "二手体育用品","二手生活用品", "二手自行车", "二手电子产品", "二手图书",
			"二手办公用品","二手电脑配件",  "个人中心", "联系我们" };// gridview的标题
	private GridView gridView;
	private Button title_back, title_right;
	private MyApplication myApplication;
	private Intent intent;
	private TextView toReleaseTextView,toAllInfo,toUserInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myApplication = (MyApplication) this.getApplicationContext();
		myApplication.addActivity(this);
		title_back = (Button) this.findViewById(R.id.button_back);
		title_back.setVisibility(View.GONE);
		title_right = (Button) this.findViewById(R.id.button_right);
		title_right.setVisibility(View.GONE);
		toReleaseTextView=(TextView) findViewById(R.id.tx_toRelease);
		toReleaseTextView.setOnClickListener(new OnClickListener() {
		    //打开发布页面
		    @Override
		    public void onClick(View v) {
			Users users = (Users) myApplication.userMap.get("user");
			if (users != null) {
			    Intent intent = new Intent(MainActivity.this,
					ReleaseActivity.class);
			    MainActivity.this.startActivity(intent);
			    
			    
			} else {
				View dialogView = getLayoutInflater().inflate(R.layout.my_dialog,
						null, false);
				TextView title = (TextView) dialogView.findViewById(R.id.title);
				title.setText("温馨提示");
				TextView message = (TextView) dialogView.findViewById(R.id.message);
				message.setVisibility(View.VISIBLE);
				message.setText("你还没有登录，请先登录");
				TextView gallery = (TextView) dialogView.findViewById(R.id.gallery);
				gallery.setVisibility(View.GONE);
				TextView camera = (TextView) dialogView.findViewById(R.id.camera);
				camera.setVisibility(View.GONE);
				final Dialog dialog = new Dialog(MainActivity.this,
						R.style.myDialogTheme);
				dialog.setContentView(dialogView);
				dialog.setCancelable(false);
				dialog.show();
				Button cancel = (Button) dialogView
						.findViewById(R.id.button_cancel);
				cancel.setVisibility(View.VISIBLE);
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
						
					}
				});
				Button ok = (Button) dialogView.findViewById(R.id.button_ok);
				ok.setVisibility(View.VISIBLE);
				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						Intent intent = new Intent(MainActivity.this,
								LoginActivity.class);
						MainActivity.this.startActivity(intent);
					}
				});
			}
			
		    }
		});
		toAllInfo=(TextView) findViewById(R.id.tx_All);
		toAllInfo.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
			Users users = (Users) myApplication.userMap.get("user");
			if (users != null) {
			    Intent intent = new Intent(MainActivity.this,
					ShopListActivity.class);
			    intent.putExtra("type", "体育用品");
			    MainActivity.this.startActivity(intent);
			    
			    
			} else {
				View dialogView = getLayoutInflater().inflate(R.layout.my_dialog,
						null, false);
				TextView title = (TextView) dialogView.findViewById(R.id.title);
				title.setText("温馨提示");
				TextView message = (TextView) dialogView.findViewById(R.id.message);
				message.setVisibility(View.VISIBLE);
				message.setText("你还没有登录，请先登录");
				TextView gallery = (TextView) dialogView.findViewById(R.id.gallery);
				gallery.setVisibility(View.GONE);
				TextView camera = (TextView) dialogView.findViewById(R.id.camera);
				camera.setVisibility(View.GONE);
				final Dialog dialog = new Dialog(MainActivity.this,
						R.style.myDialogTheme);
				dialog.setContentView(dialogView);
				dialog.setCancelable(false);
				dialog.show();
				Button cancel = (Button) dialogView
						.findViewById(R.id.button_cancel);
				cancel.setVisibility(View.VISIBLE);
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
					}
				});
				Button ok = (Button) dialogView.findViewById(R.id.button_ok);
				ok.setVisibility(View.VISIBLE);
				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						Intent intent = new Intent(MainActivity.this,
								LoginActivity.class);
						MainActivity.this.startActivity(intent);
					}
				});
			}
						
		    }
		});
		toUserInfo=(TextView) findViewById(R.id.tx_MyInfo);
		toUserInfo.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
			Users users = (Users) myApplication.userMap.get("user");
			if (users != null) {
			    Intent intent = new Intent(MainActivity.this,
					PersonnalActivity.class);
			    MainActivity.this.startActivity(intent);
			    
			    
			} else {
				View dialogView = getLayoutInflater().inflate(R.layout.my_dialog,
						null, false);
				TextView title = (TextView) dialogView.findViewById(R.id.title);
				title.setText("温馨提示");
				TextView message = (TextView) dialogView.findViewById(R.id.message);
				message.setVisibility(View.VISIBLE);
				message.setText("你还没有登录，请先登录");
				TextView gallery = (TextView) dialogView.findViewById(R.id.gallery);
				gallery.setVisibility(View.GONE);
				TextView camera = (TextView) dialogView.findViewById(R.id.camera);
				camera.setVisibility(View.GONE);
				final Dialog dialog = new Dialog(MainActivity.this,
						R.style.myDialogTheme);
				dialog.setContentView(dialogView);
				dialog.setCancelable(false);
				dialog.show();
				Button cancel = (Button) dialogView
						.findViewById(R.id.button_cancel);
				cancel.setVisibility(View.VISIBLE);
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
					}
				});
				Button ok = (Button) dialogView.findViewById(R.id.button_ok);
				ok.setVisibility(View.VISIBLE);
				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						Intent intent = new Intent(MainActivity.this,
								LoginActivity.class);
						MainActivity.this.startActivity(intent);
					}
				});
			}
			
		    }			
		    
		});
		gridView = (GridView) this.findViewById(R.id.sub_gv);
		gridView.setAdapter(getMenuAdapter(titles, images));
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					intent = new Intent(MainActivity.this,
							ShopListActivity.class);
					intent.putExtra("type", "体育用品");
					MainActivity.this.startActivity(intent);
					break;
				case 1:
					intent = new Intent(MainActivity.this,
							ShopListActivity.class);
					intent.putExtra("type", "生活用品");
					MainActivity.this.startActivity(intent);
					break;

				case 2:
					intent = new Intent(MainActivity.this,
							ShopListActivity.class);
					intent.putExtra("type", "自行车");
					MainActivity.this.startActivity(intent);
					break;

				case 3:
					intent = new Intent(MainActivity.this,
							ShopListActivity.class);
					intent.putExtra("type", "电子产品");
					MainActivity.this.startActivity(intent);
					break;

				case 4:
					intent = new Intent(MainActivity.this,
							ShopListActivity.class);
					intent.putExtra("type", "图书");
					MainActivity.this.startActivity(intent);
					break;

				case 5:
					intent = new Intent(MainActivity.this,
							ShopListActivity.class);
					intent.putExtra("type", "办公用品");
					MainActivity.this.startActivity(intent);
					break;

				case 6:
					intent = new Intent(MainActivity.this,
							ShopListActivity.class);
					intent.putExtra("type", "电脑配件");
					MainActivity.this.startActivity(intent);
					break;

				case 7:
					intent = new Intent(MainActivity.this,
							PersonnalActivity.class);
					startActivity(intent);
					break;
				case 8:
					intent = new Intent(MainActivity.this,
							AboutActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * @param titles
	 *            gridview的文字数组
	 * @param imagesArray
	 *            gridview的图片数组
	 * @return
	 */
	private SimpleAdapter getMenuAdapter(String[] titles, int[] images) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < titles.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", images[i]);
			map.put("itemText", titles[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.girdview_item,
				new String[] { "itemImage", "itemText" }, new int[] {
						R.id.mygv_iv, R.id.mygv_tv });
		return simperAdapter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
