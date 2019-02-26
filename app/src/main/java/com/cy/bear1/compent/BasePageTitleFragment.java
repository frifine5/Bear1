package com.cy.bear1.compent;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cy.bear1.R;

public abstract  class BasePageTitleFragment extends Fragment {
    private View mFragmentView;// 父控件
    private ImageView mlvLogoPage;
    private TextView mTvTitlePage;
    private TextView mTvPaypalPage;
    private FrameLayout mFlTitleContentPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected Context getContext(){
        return mFlTitleContentPage.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.base_top_title_page, container, false);   // 通用布局
        mlvLogoPage = (ImageView)mFragmentView.findViewById(R.id.iv_logo_page);
        mTvTitlePage = (TextView)mFragmentView.findViewById(R.id.tv_title_page);
        mTvPaypalPage = (TextView)mFragmentView.findViewById(R.id.tv_paypal_page);
        mFlTitleContentPage = (FrameLayout)mFragmentView.findViewById(R.id.fl_title_content_page);
        View view = initView();
        mFlTitleContentPage.addView(view);
        return mFragmentView;
    }

    public void setTitleIcon(String msg, boolean show) { //设置标题和图标
        mTvTitlePage.setText(msg);  //设置标题
        mTvTitlePage.setVisibility(show ? View.VISIBLE : View.GONE);     //设置标题显示  true就是显示  false就是不显示
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract View initView();

    protected abstract void initData();


}