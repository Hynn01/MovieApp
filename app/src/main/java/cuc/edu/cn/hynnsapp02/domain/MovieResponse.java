package cuc.edu.cn.hynnsapp02.domain;

import java.util.List;

public class MovieResponse {

    int count;
    int start;
    int total;
    private List<Movie> subjects;
    String title;

    public List<Movie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Movie> subjects) {
        this.subjects = subjects;
    }
}
