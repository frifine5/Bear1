package com.cy.bear1.compent.page;

import android.view.View;

import com.cy.bear1.R;
import com.cy.bear1.compent.BasePageTitleFragment;

public class MinePageFragment extends BasePageTitleFragment{


    @Override
    protected View initView() {
        setTitleIcon("个人中心", true);
        View fragement  = View.inflate(getContext(), R.layout.fg_minepage, null);
        return fragement;
    }

    @Override
    protected void initData() {

    }
}