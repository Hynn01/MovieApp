package cuc.edu.cn.hynnsapp02.domain;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import cuc.edu.cn.hynnsapp02.greendao.StringConverter;

@Entity
public class CollectionList {
    @Id(autoincrement = true)
    private Long id;

    private int movieNum;//此收藏单里面电影的数量

    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> movieIdList;//此列表中装着收藏的电影的Id

    public CollectionList(Long id) {
        this.id = id;
        this.movieNum=0;
    }
    
    public CollectionList() {
        this.movieNum=0;
    }


    @Generated(hash = 1025749747)
    public CollectionList(Long id, int movieNum, List<String> movieIdList) {
        this.id = id;
        this.movieNum = movieNum;
        this.movieIdList = movieIdList;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMovieNum() {
        return this.movieNum;
    }

    public void setMovieNum(int movieNum) {
        this.movieNum = movieNum;
    }

    public List<String> getMovieIdList() {
        return this.movieIdList;
    }

    public void setMovieIdList(List<String> movieIdList) {
        this.movieIdList = movieIdList;
    }
}
