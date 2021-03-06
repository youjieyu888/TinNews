

package com.laioffer.tinnews;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.laioffer.tinnews.common.TinBasicActivity;


public class WebViewActivity extends TinBasicActivity implements PopupMenu.OnMenuItemClickListener{
    public static final String URL = "url";
    private String url;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // super did setContentView
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressBar = findViewById(R.id.progress_bar);
        final WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());//chrome as webview
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url); //user input url, webview load this new url
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);//prograssbar gone after loading is finished
            }
        });
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            Bundle innerBundle = bundle.getBundle(BUNDLE);
//            if (innerBundle != null) {
//                url = innerBundle.getString(URL);
//                webView.loadUrl(url);
//            }
//        }
        //default homepage
        url = "https://www.google.com";
        webView.loadUrl(url);

        //dropdown
        findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showMenu(v); //this callback functions calls function in the current activity, so showMenu should be in WebViewActivity
            }
        });



    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web_view;
    }

    @SuppressLint("RestrictedApi")
    private void showMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.setOnMenuItemClickListener(this);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.web_view_items, menu.getMenu());
        MenuPopupHelper menuHelper = new MenuPopupHelper(this, (MenuBuilder) menu.getMenu(), view);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                //implicit intent, the destination is the all acitivities that register ACTION_SEND in manifest's filter
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "From TinNews: \n" + url;
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                this.startActivity(Intent.createChooser(sharingIntent, "Share TinNews"));
                break;
            case R.id.menu_copy:
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", url);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Link Copied", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return true;
    }

    @Override
    public void showSnackBar(String message) {

    }
}


