package com.cy.bear1.compent.page;

import android.view.View;

import com.cy.bear1.R;
import com.cy.bear1.compent.BasePageTitleFragment;

public class HomePageFragment extends BasePageTitleFragment{


    @Override
    protected View initView() {
        setTitleIcon("首页", false);
        View fragement  = View.inflate(getContext(), R.layout.fg_homepage, null);
        return fragement;
    }

    @Override
    protected void initData() {

    }
}