package cuc.edu.cn.hynnsapp02.domain;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class Movie {

    private String id;
    private String title;
    private String url;
    private String directorName;
    private String year;
    private List<Actor> casts;
    private List<Director> directors;
    private Image images;
    private Rate rating;
    private List<String> genres;
    private Bitmap bitmap;
    private String alt;
    private int imageId=0;

    public Movie(){
        this.title="肖申克的救赎";
        //this.rate.average=5;
    }

    public Movie(String title,int picId,float rateAverage){
        this.title=title;
        rating=new Rate();
        this.rating.average=rateAverage;
        this.imageId=picId;
    }


    public Movie(String title,int picId,String type,String director,String actor,String year){
        this.title=title;
        this.imageId=picId;
        this.genres=new ArrayList<>();
        this.setType(type);
        this.directors=new ArrayList<>();
        this.setDirectorName(director);
        this.casts=new ArrayList<>();
        this.setActors(actor);
        this.year=year;

    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url=url;
    }
    public Image getImages(){
        return this.images;
    }

    public void setImages(String image1){
    }
    public String getActors()
    {
        if(this.casts.size()>0){
            return this.casts.get(0).name;
        }else{
            return " ";
        }

    }

    public void setActors(String actor){
        Actor a=new Actor(actor);
        this.casts.add(a);
    }

    public String getDirectorName(){
        return this.directors.get(0).name;
    }

    public void setDirectorName(String directorName){
        Director d=new Director(directorName);
        this.directors.add(d);
    }

    public Rate getRate(){
        return this.rating;
    }

    public void setRate(String year){}

    public String getYear(){
        return this.year;
    }

    public void setYear(String year){
        this.year=year;
    }

    public String getType(){
        return this.genres.get(0);
    }

    public void setType(String type){
        String s=new String(type);
        this.genres.add(s);
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public void setBitmap(Bitmap b){
        this.bitmap=b;
    }

    public String getAlt(){
        return this.alt;
    }

    public void setAlt(){
    }

    public int getImageId(){
        return this.imageId;
    }

    public void setImageId(int imageId){
        this.imageId=imageId;
    }

    public class Rate{
        int max;
        float average;
        String stars;
        int min;

        public int getMax(){
            return this.max;
        }

        public void setMax(String image){}

        public float getAverage(){
            return this.average;
        }

        public void setAverage(String image1){}

    }

    public class Director{
        String alt;
        String name;
        String id;

        public Director(String d){
            this.name=d;
        }
    }

    public class Actor{
        String alt;
        String name;
        String id;

        public Actor(String a){
            this.name=a;
        }
    }

    public class Image{
        String small;
        String large;
        String medium;

        public String getSmall(){
            return this.small;
        }

        public void setSmall(String image1){}

    }


}
