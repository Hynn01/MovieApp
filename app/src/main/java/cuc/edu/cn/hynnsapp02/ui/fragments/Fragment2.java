package cuc.edu.cn.hynnsapp02.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import cuc.edu.cn.hynnsapp02.R;
import cuc.edu.cn.hynnsapp02.adapter.BaseRecyclerAdapter_GridView;
import cuc.edu.cn.hynnsapp02.adapter.SmartViewHolder_Improved;
import cuc.edu.cn.hynnsapp02.domain.Movie;
import cuc.edu.cn.hynnsapp02.domain.MovieResponse;
import cuc.edu.cn.hynnsapp02.http.HttpUtility;
import cuc.edu.cn.hynnsapp02.ui.MovieDetailActivity;


public class Fragment2 extends Fragment implements OnRefreshLoadMoreListener {

	private RefreshLayout refreshLayout;
	RecyclerView recyclerView;
	private BaseRecyclerAdapter_GridView<Movie> mAdapter;

	private static final int SHOW_RESPONSE_CONTENT = 0;
	private final int WHAT_DID_MOVIE_REFRESH = 1;
	private final int WHAT_DID_MOVIE_MORE = 2;

    private static boolean isFirstEnter = true;
    private static boolean isFirstRefresh = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment2, null);
		refreshLayout = view.findViewById(R.id.refreshLayout);
		refreshLayout.setPrimaryColorsId(android.R.color.holo_red_light, android.R.color.white);
		initRecyclerView(view);
		isFirstRefresh = true;

        onRefresh(refreshLayout);

        //第一次进入演示刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            refreshLayout.autoRefresh();
        }

		return view;
	}

	public void initRecyclerView(View view){
		recyclerView = view.findViewById (R.id.recyclerView);

		//createAdapter = new BaseRecyclerAdapter_GridView<Movie>(getActivity (),list);
		//recyclerView.setAdapter (createAdapter);
		recyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter_GridView<Movie>(loadMovies(), R.layout.list_item_2) {
			@Override
			protected void onBindViewHolder(SmartViewHolder_Improved holder, Movie movie, int position) {
				holder.text(R.id.movieTitle222, movie.getTitle());
				holder.text(R.id.ratingStarText, new String(String.valueOf(movie.getRate().getAverage())));
				holder.rating(R.id.ratingStar, (movie.getRate().getAverage()/2));
				if(movie.getImageId()==0){
					holder.image(R.id.image222,movie.getImages().getSmall(),getContext());
				}else{
					holder.image(R.id.image222, movie.getImageId());
				}


			}
		});

		//recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setLayoutManager (new GridLayoutManager (getActivity (),2,GridLayoutManager.VERTICAL,false));
		recyclerView.setItemAnimator (new DefaultItemAnimator());

		refreshLayout.setOnRefreshLoadMoreListener(this);
		mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
				Movie movie1= (Movie) mAdapter.getItem(position);
				intent.putExtra("data", movie1.getAlt());//movie.getAlt()
				startActivity(intent);
			}
		});

	}


	/**
	 * 模拟数据
	 */
	private Collection<Movie> loadMovies() {
		return Arrays.asList(
//				new Movie("肖申克的救赎","https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp", (float) 9.6),
//				new Movie("霸王别姬","https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1910813120.webp", (float) 9.6),
//				new Movie("这个杀手不太冷","https://img3.doubanio.com/view/photo/s_ratio_poster/public/p511118051.webp", (float) 9.4),
//				new Movie("阿甘正传","https://img1.doubanio.com/view/photo/s_ratio_poster/public/p510876377.webp", (float) 9.4));
				new Movie("肖申克的救赎",R.drawable.test2_1, (float) 9.6),
				new Movie("霸王别姬",R.drawable.test2_2, (float) 9.6),
				new Movie("这个杀手不太冷",R.drawable.test2_3, (float) 9.4),
				new Movie("阿甘正传",R.drawable.test2_4, (float) 9.4));
//				new Movie(),
//				new Movie(),
//				new Movie()
	}

	private List<Movie> GetMovie(Movie movie) {

		List<Movie> movielist = new ArrayList<Movie>();

		Random random = new Random();

		int result = random.nextInt();
		if(result < 0)
			return null;

		Movie movie1 = new Movie();
		movie1=movie;
		movielist.add(movie1);

		return movielist;
	}

	private void sendRequestWithHttpURLConnection() {
		new Thread(new Runnable() {
			@Override
			public void run() {

				List<NameValuePair> params = new ArrayList<>();//请求参数

				//使用Message
				Message message = new Message();
				message.what = SHOW_RESPONSE_CONTENT;
				try {
					message.obj = new HttpUtility().doGet(params,"https://api.douban.com/v2/movie/top250",5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//Datas data=new Datas();
				//message.obj=data.getJson();
				handler.sendMessage(message);

			}
		}).start();
	}


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {

				case WHAT_DID_MOVIE_MORE:
					sendRequestWithHttpURLConnection();
					Fragment2.this.refreshLayout.finishRefresh();
					Fragment2.this.refreshLayout.resetNoMoreData();
					break;

				case WHAT_DID_MOVIE_REFRESH:
					sendRequestWithHttpURLConnection();
					Fragment2.this.refreshLayout.finishLoadMore();
					break;

				case SHOW_RESPONSE_CONTENT:
					String json = (String) msg.obj;

					Gson gson = new Gson();
					MovieResponse response = gson.fromJson(json, MovieResponse.class);

					int randomNum = (int) (Math.random() * 10);
					// List<Movie> results = GetMovie(response.getSubjects().get(randomNum));
					List<Movie> results = response.getSubjects();
					if (results != null) {
						List<Movie> movieList = results;
                        if (isFirstRefresh) {
                            isFirstRefresh = false;
                            mAdapter.clean();
                            //refreshLayout.autoRefresh();
                        }
						mAdapter.loadMore(movieList);
						mAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), "暂时没有新数据", Toast.LENGTH_SHORT)
								.show();
					}

					break;
			}
		}
	};

	private void sendMessage(Object obj, int msgCode) {
		Message msg = handler.obtainMessage(msgCode);
		msg.obj = obj;
		msg.sendToTarget();
	}

	private void sendMessage(int msgCode) {
		Message msg = handler.obtainMessage(msgCode);
		//msg.obj = obj;
		msg.sendToTarget();
	}


	@Override
	public void onLoadMore(final RefreshLayout refreshLayout) {
		refreshLayout.getLayout().postDelayed(new Runnable() {
			@Override
			public void run() {
				sendMessage(WHAT_DID_MOVIE_REFRESH);
			}
		}, 1000);

	}

	@Override
	public void onRefresh(final RefreshLayout refreshLayout) {

		refreshLayout.getLayout().postDelayed(new Runnable() {
			@Override
			public void run() {
				sendMessage(WHAT_DID_MOVIE_MORE);
			}
		}, 1000);

	}


}