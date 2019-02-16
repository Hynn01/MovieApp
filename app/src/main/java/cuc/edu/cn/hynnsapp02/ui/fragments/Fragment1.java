package cuc.edu.cn.hynnsapp02.ui.fragments;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cuc.edu.cn.hynnsapp02.R;

public class Fragment1 extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener{
	private ViewPager viewPager;
	private FragmentPagerAdapter fragmentPagerAdapter;
	private RelativeLayout one_layout,two_layout,three_layout;
	private Fragment oneChildFragment,twoChildFragment,threeChildFragment;
	private List<Fragment> mFragmentList;
	private TextView oneTextView,twoTextView,threeTextView;
	private ImageView img_cursor;

	private int offset = 0;//移动条图片的偏移量
	private int currIndex = 0;//当前页面的编号
	private int bmpWidth;// 移动条图片的长度
	private int one = 0; //移动条滑动一页的距离
	private int two = 0; //滑动条移动两页的距离


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment1, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setSelect(0);//0代表第一Fragemnt,1代表第二个Fragment
		oneTextView.setTextColor(Color.parseColor("#ec223b"));
	}


	private void initView() {
		viewPager=(ViewPager)getActivity().findViewById(R.id.viewpager);
		img_cursor = (ImageView) getActivity().findViewById(R.id.img_cursor);

		one_layout=(RelativeLayout)getActivity().findViewById(R.id.one_layout);
		two_layout=(RelativeLayout)getActivity().findViewById(R.id.two_layout);
		three_layout=(RelativeLayout)getActivity().findViewById(R.id.three_layout);

		oneTextView=(TextView)getActivity().findViewById(R.id.one_textView);
		twoTextView=(TextView)getActivity().findViewById(R.id.two_textView);
		threeTextView=(TextView)getActivity().findViewById(R.id.three_textView);

		one_layout.setOnClickListener(this);
		two_layout.setOnClickListener(this);
		three_layout.setOnClickListener(this);

		mFragmentList=new ArrayList<Fragment>();
		oneChildFragment=new Fragment1_1();
		twoChildFragment=new Fragment1_2();
		threeChildFragment=new Fragment1_3();

		mFragmentList.add(oneChildFragment);
		mFragmentList.add(twoChildFragment);
		mFragmentList.add(threeChildFragment);

		//下划线动画的相关设置：
		bmpWidth = BitmapFactory.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 4 - bmpWidth) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		img_cursor.setImageMatrix(matrix);// 设置动画初始位置
		//移动的距离
		one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3
		two = one * 2;// 移动两页的偏移量,比如1直接跳3

		viewPager.addOnPageChangeListener(this);

		fragmentPagerAdapter=new FragmentPagerAdapter(getChildFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return mFragmentList.get(position);
			}

			@Override
			public int getCount() {
				return mFragmentList.size();
			}
		};

		viewPager.setAdapter(fragmentPagerAdapter);

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				setLayout();
				switch (position){
					case 0:
						oneTextView.setTextColor(Color.parseColor("#ec223b"));
						setSelect(0);
						break;
					case 1:
						twoTextView.setTextColor(Color.parseColor("#ec223b"));
						setSelect(1);
						break;
					case 2:
						threeTextView.setTextColor(Color.parseColor("#ec223b"));
						setSelect(2);
						break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.one_layout:
				oneTextView.setTextColor(Color.parseColor("#000000"));
				setSelect(0);
				break;
			case R.id.two_layout:
				setSelect(1);
				twoTextView.setTextColor(Color.parseColor("#000000"));
				break;
			case R.id.three_layout:
				setSelect(2);
				twoTextView.setTextColor(Color.parseColor("#000000"));
				break;
		}
	}

	private void setSelect(int i) {
		switch (i){
			case 0:
				viewPager.setCurrentItem(0);
				break;
			case 1:
				viewPager.setCurrentItem(1);
				break;
			case 2:
				viewPager.setCurrentItem(2);
				break;
		}
	}


	//初始化Layout和文字
	private void setLayout(){
		oneTextView.setTextColor(Color.parseColor("#666666"));
		twoTextView.setTextColor(Color.parseColor("#666666"));
		threeTextView.setTextColor(Color.parseColor("#666666"));
	}

	@Override
	public void onPageScrolled(int i, float v, int i1) {

	}

	@Override
	public void onPageSelected(int index) {
		Animation animation = null;
		switch (index) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
		}
		currIndex = index;
		animation.setFillAfter(true);// true表示图片停在动画结束位置
		animation.setDuration(300); //设置动画时间为300毫秒
		img_cursor.startAnimation(animation);//开始动画
	}

	@Override
	public void onPageScrollStateChanged(int i) {

	}

/*
	private void initSearchView() {
		SearchView searchView = getView().findViewById(R.id.searchEdit);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			//输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发，表示现在正式提交了
			public boolean onQueryTextSubmit(String query) {
				if (TextUtils.isEmpty(query)) {
					Toast.makeText(getActivity(), "请输入查找内容111！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
				}
				return true;
			}

			//在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在输入法组词的时候不会触发
			public boolean onQueryTextChange(String newText) {
				if (TextUtils.isEmpty(newText)) {
					Toast.makeText(getActivity(), "请输入查找内容222！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), newText, Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
	}
	*/
}