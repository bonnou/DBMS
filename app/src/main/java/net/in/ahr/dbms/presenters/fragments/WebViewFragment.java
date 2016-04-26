package net.in.ahr.dbms.presenters.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.presenters.tabManagers.BaseFragment;

/**
 * Created by str2653z on 2016/04/23.
 */
public class WebViewFragment extends BaseFragment {
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);

        webView= (WebView)v.findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("http://zasa.sakura.ne.jp/dp/rank.php");

        return v;
    }
}
