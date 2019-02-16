package cuc.edu.cn.hynnsapp02.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import cuc.edu.cn.hynnsapp02.R;
import cuc.edu.cn.hynnsapp02.ui.fragments.Fragment1;
import cuc.edu.cn.hynnsapp02.ui.fragments.Fragment2;
import cuc.edu.cn.hynnsapp02.ui.fragments.Fragment3;
import cuc.edu.cn.hynnsapp02.ui.fragments.Fragment4;

public class TabsActivity extends FragmentActivity{
	
	/**
	 * FragmentTabhost
	 */
	private FragmentTabHost mTabHost;
	
	/**
	 * 布局填充器
	 * 
	 */
	private LayoutInflater mLayoutInflater;

	/**
	 * Fragment数组界面
	 * 
	 */
	private Class mFragmentArray[] = { Fragment1.class, Fragment2.class,
			Fragment3.class, Fragment4.class};
	
	/**
	 * 存放图片数组
	 * 
	 */
	private int mImageArray[] = { R.layout.tab_home_btn,
			R.layout.tab_rank_btn, R.layout.tab_classify_btn,
			R.layout.tab_selfinfo_btn};

	/**
	 * 选项卡文字
	 * 
	 */
	private String mTextArray[] = { "首页", "榜单", "分类", "我的" };
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab);
		if (Build.VERSION.SDK_INT >= 21) {
			getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
		}
		initView();
	}
	
	/**
	 * 初始化组件
	 */
	@SuppressLint("ResourceType")
    private void initView() {
		mLayoutInflater = LayoutInflater.from(this);

		// 找到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// 得到fragment的个数
		int count = mFragmentArray.length;
		for (int i = 0; i < count; i++) {
			// 给每个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, mFragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.layout.selector_tab_background);
		}
	}
	
	/**
	 *
	 * 给每个Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageArray[index]);
		
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextArray[index]);
		
		return view;
	}
}
