package com.cy.bear1.compent.page;

import android.view.View;

import com.cy.bear1.R;
import com.cy.bear1.compent.BasePageTitleFragment;

public class RecommendPageFragment extends BasePageTitleFragment{


    @Override
    protected View initView() {
        setTitleIcon("推荐", true);
        View fragement  = View.inflate(getContext(), R.layout.fg_recommendpage, null);
        return fragement;
    }

    @Override
    protected void initData() {

    }
}