package cuc.edu.cn.hynnsapp02.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cuc.edu.cn.hynnsapp02.BaseApp;
import cuc.edu.cn.hynnsapp02.R;
import cuc.edu.cn.hynnsapp02.domain.CollectionList;
import cuc.edu.cn.hynnsapp02.greendao.CollectionListDao;


public class Fragment4 extends Fragment{

	private Button bt1;
	private Button bt2;
	private ListView lv;
	private BaseAdapter adapter;//要实现的类
	private List<CollectionList> collectionList = new ArrayList();//实体类
	private View view;

	private TextView tv1;
	public CollectionListDao clDao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment4, null);
		clDao = BaseApp.getInstances().getDaoSession().getCollectionListDao();
		initView();
		return view;
	}

	private void initView() {

		bt1=view.findViewById(R.id.new_movielist_button1);
		bt2=view.findViewById(R.id.new_movielist_button2);
		lv=view.findViewById(R.id.collection_listView);

		CollectionList c11=new CollectionList();
		collectionList.add(c11);

		adapter = new BaseAdapter() {
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return collectionList.size();//数目
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View view1;

				if (convertView==null) {
					view1 = inflater.inflate(R.layout.list_item_collection, null);
				}else{
					view1=convertView;
//					Log.i("info","有缓存，不需要重新生成"+position);
				}

//				tv1 = (TextView) view1.findViewById(R.id.collection_num);//找到Textview
//				String a=String.valueOf(clDao.load((long) 0).getMovieNum());
//				tv1.setText(a);//设置参数
				return view1;
			}

			@Override
			public long getItemId(int position) {//取在列表中与指定索引对应的行id
				return 0;
			}

			@Override
			public Object getItem(int position) {//获取数据集中与指定索引对应的数据项
				return null;
			}

		};

		lv.setAdapter(adapter);

		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CollectionList cl=new CollectionList();
				collectionList.add(cl);
				adapter.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(lv);

			}
		});

//		bt2.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				CollectionList cl=new CollectionList();
//				collectionList.add(cl);
//				adapter.notifyDataSetChanged();
//
//			}
//		});

//		tv1 = view.findViewById(R.id.username);//找到Textview
//		String a=String.valueOf(clDao.load((long) 0).getMovieNum());
//		tv1.setText(a);//设置参数

	}

	/**
	 * 解决ScrollView嵌套ListView只显示一条的问题
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i,null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() *
				(listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
		listView.invalidate();
	}

}