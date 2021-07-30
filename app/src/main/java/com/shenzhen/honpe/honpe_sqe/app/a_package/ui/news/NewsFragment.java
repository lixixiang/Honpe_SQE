package com.shenzhen.honpe.honpe_sqe.app.a_package.ui.news;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenzhen.honpe.honpe_sqe.R;
import com.shenzhen.honpe.honpe_sqe.base.BaseBackFragment;
import com.shenzhen.honpe.honpe_sqe.utils.LatteLogger;
import com.shenzhen.honpe.honpe_sqe.utils.gson.NetUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;

/**
 * FileName: NewsFragment
 * Author: asus
 * Date: 2021/3/26 16:24
 * Description:
 */
public class NewsFragment extends BaseBackFragment {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.web)
    WebView contentWebView;
    public final static String PARAM_URL = "param_url";
    public final static String PARAM_MODE = "param_mode";
    @BindView(R.id.ll_web)
    LinearLayout llWeb;


    public static NewsFragment newInstance(String NewsContent) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("NewsContent", NewsContent);
        fragment.setArguments(bundle);
        return fragment;
    }
 //
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        initToolbarNav(llBack);
        tvTitle.setText("资讯详情");
        Bundle bundle = getArguments();


        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.getSettings().setAppCacheEnabled(true);
        contentWebView.getSettings().setDatabaseEnabled(true);
        contentWebView.getSettings().setDomStorageEnabled(true);
        contentWebView.loadUrl(NetUtil.ascii2Native(bundle.getString("NewsContent")));

    }

    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }
}



















