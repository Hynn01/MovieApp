//用于调试
//package cuc.edu.cn.hynnsapp02.ui;
//
//import android.os.Bundle;
//import android.view.Window;
//
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.header.DropboxHeader;
//import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//
//import cuc.edu.cn.hynnsapp02.R;
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Window;
//
//public class MainActivity extends Activity{
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.main);
//
//		RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
//		//refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//		//refreshLayout.setRefreshHeader(new DropboxHeader(this));
//		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//			@Override
//			public void onRefresh(RefreshLayout refreshlayout) {
//				refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//			}
//		});
//		refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//			@Override
//			public void onLoadMore(RefreshLayout refreshlayout) {
//				refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//			}
//		});
//
//	}
//}