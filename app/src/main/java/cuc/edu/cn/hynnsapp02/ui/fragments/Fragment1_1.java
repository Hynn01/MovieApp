package cuc.edu.cn.hynnsapp02.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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

import cuc.edu.cn.hynnsapp02.R;
import cuc.edu.cn.hynnsapp02.adapter.BaseRecyclerAdapter;
import cuc.edu.cn.hynnsapp02.adapter.SmartViewHolder_Improved;
import cuc.edu.cn.hynnsapp02.domain.Movie;
import cuc.edu.cn.hynnsapp02.domain.MovieResponse;
import cuc.edu.cn.hynnsapp02.http.HttpUtility;
import cuc.edu.cn.hynnsapp02.ui.MovieDetailActivity;

public class Fragment1_1 extends Fragment implements OnRefreshLoadMoreListener {

    private RefreshLayout refreshLayout;
    private BaseRecyclerAdapter<Movie> mAdapter;
    RecyclerView recyclerView;
    private static boolean isFirstEnter = true;
    private static boolean isFirstRefresh = true;
    private static final int SHOW_RESPONSE_CONTENT = 0;
    private final int WHAT_DID_MOVIE_REFRESH = 1;
    private final int WHAT_DID_MOVIE_MORE = 2;
    private static final int SHOW_RESPONSE_CONTENT1 = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_1, null);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setPrimaryColorsId(android.R.color.holo_red_light, android.R.color.white);
        isFirstRefresh = true;

        onRefresh(refreshLayout);

        //第一次进入演示刷新
        if (isFirstEnter) {
            //isFirstEnter = false;
            refreshLayout.autoRefresh();
        }

        //初始化列表和监听
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<Movie>(loadMovies(), R.layout.list_item_1) {
            @Override
            protected void onBindViewHolder(SmartViewHolder_Improved holder, Movie movie, int position) {
                holder.text(R.id.movieName1, movie.getTitle());
                holder.text(R.id.movieType, "类型: "+movie.getType());
                holder.text(R.id.movieDirector, "导演: "+movie.getDirectorName());
                holder.text(R.id.movieActors, "演员: "+movie.getActors());
                holder.text(R.id.movieTime, "上映时间: "+movie.getYear()+"年");
                if(movie.getImageId()==0){
                    holder.image(R.id.image111,movie.getImages().getSmall(),getContext());
                }else{
                    holder.image(R.id.image111, movie.getImageId());
                }

            }
        });

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
        //Toast.makeText(getActivity(), "222222222222", Toast.LENGTH_SHORT).show();
        return view;
    }


    /**
     * 使用模拟数据加载页面
     */
    protected Collection<Movie> loadMovies() {
        return Arrays.asList(
                new Movie("大黄蜂",R.drawable.test1_1,"动作","特拉维斯·奈特","海莉·斯坦菲尔德","2018"),
                new Movie("密室逃生",R.drawable.test1_2, "悬疑","亚当·罗比特尔","泰勒·拉塞尔","2019"),
                new Movie("白蛇：缘起",R.drawable.test1_3, "爱情","黄家康","张喆","2019"),
                new Movie("'大'人物",R.drawable.test1_4, "动作","五百","王千源","2019")
                );
    }

//    private List<Movie> GetMovie(Movie movie) {
//
//        List<Movie> movielist = new ArrayList<Movie>();
//
//        Random random = new Random();
//
//        int result = random.nextInt();
//        if(result < 0)
//            return null;
//
//        Movie movie1 = new Movie();
//        movie1=movie;
//        movielist.add(movie1);
//
//        return movielist;
//    }

    /**
     * 请求网络连接获取数据
     */
    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<NameValuePair> params = new ArrayList<>();//请求参数

                //使用Message
                Message message = new Message();
                message.what = SHOW_RESPONSE_CONTENT;
                try {
                    message.obj = new HttpUtility().doGet(params,"https://api.douban.com/v2/movie/in_theaters",5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Datas data=new Datas();
                //message.obj=data.getJson();
                handler.sendMessage(message);

            }
        }).start();
    }

    private void sendRequestWithHttpURLConnection1() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<NameValuePair> params = new ArrayList<>();//请求参数

                //使用Message
                Message message = new Message();
                message.what = SHOW_RESPONSE_CONTENT1;
                try {
                    message.obj = new HttpUtility().doGet(params,"https://api.douban.com/v2/movie/in_theaters",5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Datas data=new Datas();
                //message.obj=data.getJson();
                handler.sendMessage(message);

            }
        }).start();
    }

    //接收处理消息
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {

                case WHAT_DID_MOVIE_MORE:
                    //setImageToImageView(newsImage, news.getThumbnail_pic_s());
                    // 告诉它更新完毕
                    //listview.onMoreComplete();
                    sendRequestWithHttpURLConnection1();
    //                    mAdapter.loadMore(loadMovies());
                    Fragment1_1.this.refreshLayout.finishLoadMore();
                    break;

                case WHAT_DID_MOVIE_REFRESH:
                    sendRequestWithHttpURLConnection();
                    Fragment1_1.this.refreshLayout.finishRefresh();
                    Fragment1_1.this.refreshLayout.resetNoMoreData();
//                    refreshLayout.finishRefresh();
//                    refreshLayout.resetNoMoreData();
                    //RefreshLayout refreshLayout = Fragment1_1.this.refreshLayout.setNoMoreData();//恢复上拉状态
                    break;

                case SHOW_RESPONSE_CONTENT:
                    String json = (String) msg.obj;

                    Gson gson = new Gson();
                    MovieResponse response = gson.fromJson(json, MovieResponse.class);

                    int randomNum = (int) (Math.random() * 10);
                   // List<Movie> results = GetMovie(response.getSubjects().get(randomNum));
                    List<Movie> results =new ArrayList<>();
                    results=response.getSubjects();
                    if (results != null) {
                        //List<Movie> movieList = results;
                        List<Movie> movieList=getMovieList(results);
                        if (isFirstRefresh) {
                            //isFirstRefresh = false;
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

                case SHOW_RESPONSE_CONTENT1:
                    String json1 = (String) msg.obj;

                    Gson gson1 = new Gson();
                    MovieResponse response1 = gson1.fromJson(json1, MovieResponse.class);

                    int randomNum1 = (int) (Math.random() * 10);
                    // List<Movie> results = GetMovie(response.getSubjects().get(randomNum));
                    List<Movie> results1 =new ArrayList<>();
                    results1=response1.getSubjects();
                    if (results1 != null) {
                        //List<Movie> movieList = results;
                        List<Movie> movieList=getMovieList(results1);
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
                sendMessage(WHAT_DID_MOVIE_MORE);
            }
        }, 1000);

    }

    @Override
    public void onRefresh(final RefreshLayout refreshLayout) {

        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage(WHAT_DID_MOVIE_REFRESH);
            }
        }, 1000);

    }

    protected List<Movie> getMovieList(List<Movie> results1){
        return results1;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
//        startActivity(intent);
//    }

//    public Bitmap getBitmap(String path) throws IOException {
//        try {
//            URL url = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setRequestMethod("GET");
//            if (conn.getResponseCode() == 200) {
//                InputStream inputStream = conn.getInputStream();
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                return bitmap;
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
//    }


}