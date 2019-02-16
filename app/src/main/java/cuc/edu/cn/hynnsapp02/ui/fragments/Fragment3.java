package cuc.edu.cn.hynnsapp02.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cuc.edu.cn.hynnsapp02.R;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

public class Fragment3 extends Fragment {

	ViewPager viewpager;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		view=inflater.inflate(R.layout.fragment3, null);

		viewpager = (ViewPager) view.findViewById(R.id.viewpager1);
		viewpager.setAdapter(new MyPagerAdapter());
		initTab1();

		return view;
	}

	private void initTab1() {
		VerticalTabLayout tablayout =view.findViewById(R.id.tablayout1);
		tablayout.setupWithViewPager(viewpager);
	}

	private class MyPagerAdapter extends PagerAdapter implements TabAdapter {
		List<String> titles;
		List<Integer> layouts;

		public MyPagerAdapter() {
			titles = new ArrayList<>();
			Collections.addAll(titles, "热门精选", "喜剧片", "动作片", "爱情片", "科幻片",
					"动画片", "纪录片", "悬疑片", "犯罪片", "奇幻片", "歌舞片", "青春片", "文艺片", "励志片");
			layouts=new ArrayList<>();
			Collections.addAll(layouts, R.layout.pageviewers_hot, R.layout.pageviewers_comedy, R.layout.pageviewers_action, R.layout.pageviewers_love, R.layout.pageviewers_science,
					R.layout.pageviewers_animation, R.layout.pageviewers_document,R.layout.pageviewers_suspen, R.layout.pageviewers_crime, R.layout.pageviewers_fantasy, R.layout.pageviewers_dance, R.layout.pageviewers_young, R.layout.pageviewers_literary, R.layout.pageviewers_encourage);
		}

		@Override
		public int getCount() {
			return titles.size();
		}

		@Override
		public TabView.TabBadge getBadge(int position) {
			return null;
		}

		@Override
		public TabView.TabIcon getIcon(int position) {
			return null;
		}

		@Override
		public TabView.TabTitle getTitle(int position) {

			return new TabView.TabTitle.Builder()
					.setContent(titles.get(position))
					.setTextColor(Color.WHITE, 0xBBFFFFFF)
					.build();
		}

		@Override
		public int getBackground(int position) {
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
//			TextView tv = new TextView(getActivity());
//			tv.setTextColor(Color.WHITE);
//			tv.setGravity(Gravity.CENTER);
//			tv.setText(titles.get(position));
//			tv.setTextSize(18);

			//布局填充器
			LayoutInflater mLayoutInflater = LayoutInflater.from(getActivity());
			View view1 = mLayoutInflater.inflate(layouts.get(position), null);
			container.addView(view1);

			return view1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

}