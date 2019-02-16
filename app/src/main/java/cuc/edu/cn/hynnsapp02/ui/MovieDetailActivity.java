package cuc.edu.cn.hynnsapp02.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import cuc.edu.cn.hynnsapp02.BaseApp;
import cuc.edu.cn.hynnsapp02.R;
import cuc.edu.cn.hynnsapp02.domain.CollectionList;
import cuc.edu.cn.hynnsapp02.greendao.CollectionListDao;

public class MovieDetailActivity extends Activity {

    private WebView webView;
    private ProgressBar progressBar;
    private String url="https://movie.douban.com/subject/26394152/";
    private ImageView toolbarButton;
    private ImageView likeButton;

    public CollectionListDao clDao;
    public CollectionList cList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Intent intent =getIntent();
        url=intent.getStringExtra("data");

        progressBar= (ProgressBar)findViewById(R.id.progressbar);//进度条

        webView = (WebView) findViewById(R.id.webview);
//        webView.loadUrl("file:///android_asset/test.html");//加载asset文件夹下html
        webView.loadUrl(url);//加载url

        //使用webview显示html代码
//        webView.loadDataWithBaseURL(null,"<html><head><title> 欢迎您 </title></head>" +
//                "<body><h2>使用webview显示 html代码</h2></body></html>", "text/html" , "utf-8", null);

        webView.addJavascriptInterface(this,"android");//添加js监听 这样html就能调用客户端
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        //不显示webview缩放按钮
//        webSettings.setDisplayZoomControls(false);

        creatCollectionListTable();

        toolbarButton=findViewById(R.id.btn_back);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        likeButton=findViewById(R.id.movie_like);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(likeButton.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.ic_small_heart_normal).getConstantState())) {
                    likeButton.setImageResource(R.drawable.ic_small_heart_selected);
                    cList.setMovieNum(cList.getMovieNum()+1);
                    clDao.update(cList);
                    //String a= String.valueOf(clDao.load((long) 0).getMovieNum());
                    Toast.makeText(MovieDetailActivity.this,"收藏成功~", Toast.LENGTH_LONG).show();
                }else{
                    likeButton.setImageResource(R.drawable.ic_small_heart_normal);
                    cList.setMovieNum(cList.getMovieNum()-1);
                    clDao.update(cList);
                    Toast.makeText(MovieDetailActivity.this, "取消收藏成功~", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen","拦截url:"+url);
            if(url.equals("http://www.google.com/")){
                Toast.makeText(MovieDetailActivity.this,"拦截url",Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient=new WebChromeClient(){
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定",null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen","网页标题:"+title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen","是否有上一个页面:"+webView.canGoBack());
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * JS调用android的方法
     * @param str
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void  getClient(String str){
        Log.i("ansen","html调用客户端:"+str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        webView.destroy();
        webView=null;
    }


    //创建一张数据库中的收藏清单表
    public void creatCollectionListTable(){
        clDao = BaseApp.getInstances().getDaoSession().getCollectionListDao();
        //clDao.deleteAll();
        cList = new CollectionList((long) 0);
        if(!clDao.hasKey(cList)) {
            clDao.insert(cList);
        }
    }

}
