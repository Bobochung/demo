/**
 * 
 */
package com.lsnu.idle.campus_life;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lsnu.idle.adapter.ShopListAdapter;
import com.lsnu.idle.common.AppException;
import com.lsnu.idle.common.Constants;
import com.lsnu.idle.common.HttpHelper;
import com.lsnu.idle.common.PageModel;
import com.lsnu.idle.common.HttpHelper.Callback;
import com.lsnu.idle.entity.ItemList;
import com.lsnu.idle.entity.Users;
import com.lsnu.idle.service.MyApplication;
import com.lsnu.idle.view.PullDownView;
import com.lsnu.idle.view.PullDownView.UpdateHandler;
import com.lsnu.idle.campus_life.R;


public class ShopListActivity extends Activity implements UpdateHandler {
	private PullDownView pdv;
	private ListView lv;
	private LinearLayout footer;// listview的底部
	private ProgressBar listview_foot_progress;// listview的底部进度条
	private TextView listview_foot_more, title;
	private ShopListAdapter adapter;
	private List<ItemList> listDatas = new ArrayList<ItemList>();
	private Button btn_back, btn_right;
	private LinearLayout list_all, list_school;
	private int pageNo = 1;
	private String string;
	private String condition = "category";// 查询条件是根据账户查询还是根据类型查询
	private boolean hasMore = false;
	private int lastItem;
	private MyApplication myApplication;
	private String school = "";// 根据学校查询

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myrelease);
		myApplication = (MyApplication) this.getApplicationContext();
		myApplication.addActivity(this);
		Intent intent = getIntent();
		string = intent.getStringExtra("type");
		initPullDownView();
		loadData(pageNo);
		title = (TextView) this.findViewById(R.id.title_tv);
		title.setText(string);
		// 返回按钮监听
		btn_back = (Button) this.findViewById(R.id.button_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShopListActivity.this,
						MainActivity.class);
				ShopListActivity.this.startActivity(intent);
				ShopListActivity.this.finish();
			}
		});
		btn_right = (Button) this.findViewById(R.id.button_right);
		btn_right.setText("求购信息");
		btn_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShopListActivity.this,
						LookListActivity.class);
				intent.putExtra("type", string);
				ShopListActivity.this.startActivity(intent);
			}
		});
		list_all = (LinearLayout) this.findViewById(R.id.list_all);
		list_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				school = "";
				onUpdate();
			}
		});
		list_school = (LinearLayout) this.findViewById(R.id.list_school);
		list_school.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Users users = (Users) myApplication.userMap.get("user");
				if (users != null) {
					school = users.getSchool();
					onUpdate();
				} else {
					View dialogView = getLayoutInflater().inflate(
							R.layout.my_dialog, null, false);
					TextView title = (TextView) dialogView
							.findViewById(R.id.title);
					title.setText("温馨提示");
					TextView message = (TextView) dialogView
							.findViewById(R.id.message);
					message.setVisibility(View.VISIBLE);
					message.setText("你好没有登录，请先登录");
					TextView gallery = (TextView) dialogView
							.findViewById(R.id.gallery);
					gallery.setVisibility(View.GONE);
					TextView camera = (TextView) dialogView
							.findViewById(R.id.camera);
					camera.setVisibility(View.GONE);
					final Dialog dialog = new Dialog(ShopListActivity.this,
							R.style.myDialogTheme);
					dialog.setContentView(dialogView);
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
					Button ok = (Button) dialogView
							.findViewById(R.id.button_ok);
					ok.setVisibility(View.VISIBLE);
					ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							Intent intent = new Intent(ShopListActivity.this,
									LoginActivity.class);
							ShopListActivity.this.startActivity(intent);
						}
					});
				}
			}
		});
	}

	// 初始化下拉刷新
	private void initPullDownView() {
		pdv = (PullDownView) this.findViewById(R.id.pdv);
		pdv.setUpdateHandler(this);// 设置下拉刷新处理器
		lv = (ListView) pdv.findViewById(R.id.lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == lv.getCount()-1) {
				} else {
					ItemList itemList = listDatas.get(position);
					Bundle data = new Bundle();
					data.putSerializable("shopinfo", itemList);
					Intent intent = new Intent(ShopListActivity.this,
							ShopInfoActivity.class);
					intent.putExtras(data);
					ShopListActivity.this.startActivity(intent);
				}
			}
		});
		// 初始化底部视图
		this.footer = ((LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.listview_footer, null));
		listview_foot_progress = (ProgressBar) footer
				.findViewById(R.id.listview_foot_progress);
		listview_foot_more = (TextView) footer
				.findViewById(R.id.listview_foot_more);

		lv.addFooterView(footer);// 添加底部视图 必须在setAdapter前
		lv.setFooterDividersEnabled(false);

		// /////////////////////////////
		adapter = new ShopListAdapter(this);
		lv.setAdapter(adapter);

		lv.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 数据为空
				if (listDatas.isEmpty()) {
					return;
				}

				// 判断是否滚动到底部
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
						&& lastItem == adapter.getCount()) {
					if (hasMore) {
						listview_foot_progress.setVisibility(View.VISIBLE);
						listview_foot_more.setText("加载中...");
						loadData(++pageNo);
					}
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lastItem = firstVisibleItem + visibleItemCount - 1;
			}
		});
	}

	// 回调方法
	public void onUpdate() {
		pageNo = 1;
		lv.setSelection(0);
		loadData(pageNo);
	}

	// 加载数据
	public void loadData(int pn) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageNo", pn);
		params.put("value", string);
		params.put("condition", condition);
		params.put("school", school);
		if (pageNo == 1) {
			listDatas.clear();
			footer.setVisibility(View.GONE);
			lv.setFooterDividersEnabled(false);
		} else {
			footer.setVisibility(View.VISIBLE);
			lv.setFooterDividersEnabled(true);
		}
		HttpHelper.asyncPost(Constants.URL + "/second-hand/shop_info.do",
				params, new Callback() {
					@Override
					public void dataLoaded(Message msg) {
						footer.setVisibility(View.GONE);
						if (HttpStatus.SC_OK != msg.what) {
							AppException.http(msg.what).makeToast(
									ShopListActivity.this);
							pdv.endUpdate();
							return;
						}
						String json = (String) msg.obj;
						Log.v("dddddddddddddddddddddd", json);
						PageModel<ItemList> pm = PageModel.jsonConvert(json);
						listDatas.addAll(pm.getData());
						adapter.setItemList(listDatas);
						adapter.notifyDataSetChanged();
						pdv.endUpdate();

						if (pageNo < pm.getPageCount()) {
							hasMore = true;
						} else {
							hasMore = false;
							footer.setVisibility(View.VISIBLE);
							listview_foot_progress
									.setVisibility(View.INVISIBLE);
							listview_foot_more.setText("就这么多了，客官！");
						}
					}
				});
	}
}
