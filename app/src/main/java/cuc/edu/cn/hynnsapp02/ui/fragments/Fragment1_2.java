package cuc.edu.cn.hynnsapp02.ui.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cuc.edu.cn.hynnsapp02.R;
import cuc.edu.cn.hynnsapp02.domain.Movie;

public class Fragment1_2 extends Fragment1_1{

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment1_2, null);
//    }

    /**
     * 模拟数据
     */
    protected Collection<Movie> loadMovies() {
        return Arrays.asList(
                new Movie("'大'人物",R.drawable.test1_4, "动作","五百","王千源","2019"),
                new Movie("密室逃生",R.drawable.test1_2, "悬疑","亚当·罗比特尔","泰勒·拉塞尔","2019"),
                new Movie("大黄蜂",R.drawable.test1_1,"动作","特拉维斯·奈特","海莉·斯坦菲尔德","2018"),
                new Movie("白蛇：缘起",R.drawable.test1_3, "爱情","黄家康","张喆","2019")
//              new Movie(),
        );
    }

    protected List<Movie> getMovieList(List<Movie> results1){
        List<Movie> list=new ArrayList<>();
        for(int i = 0; i< results1.size(); i++){
            list.add(results1.get((i+3)% results1.size()));
        }
        return list;
    }

}