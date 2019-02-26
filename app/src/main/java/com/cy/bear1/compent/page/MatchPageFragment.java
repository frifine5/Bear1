package com.cy.bear1.compent.page;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.bear1.R;
import com.cy.bear1.compent.BasePageTitleFragment;
import com.cy.bear1.dbm.PChkUtil;
import com.cy.common.bus.L;
import com.cy.common.bus.sm3.SM3Util;

import org.bouncycastle.util.encoders.Hex;

public class MatchPageFragment extends BasePageTitleFragment{

    private Button btnHash;// 摘要按钮
    private Button btnCln;// 清除按钮
    private EditText etCtx;// 原文（in）
    private TextView result;// 计算结果（out）

    @Override
    protected View initView() {
        setTitleIcon("摘要", true);
        View fragement  = View.inflate(getContext(), R.layout.fg_matchpage, null);

        etCtx = (EditText) fragement.findViewById(R.id.ctx);
        btnHash = (Button) fragement.findViewById(R.id.btn_sm3hash);
        btnCln = (Button) fragement.findViewById(R.id.btn_clean);
        result = (TextView) fragement.findViewById(R.id.result);
        btnHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(L.BUS, ">>>>SM3摘要");
                String setCtx = etCtx.getText().toString();
                result.setText("");// 清空历史显示数据

                if(PChkUtil.checkNull(setCtx)){
                    Toast.makeText(getContext(),"原文不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    byte[] input = setCtx.getBytes();
                    byte[] md = SM3Util.sm3Digest(input);
                    Toast.makeText(getContext(), "SM3摘要完成", Toast.LENGTH_SHORT).show();
                    String rtn = Hex.toHexString(md);
                    result.setText(rtn);
                }
            }
        });

        btnCln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCtx.setText("");
                result.setText("");
            }
        });

        return fragement;
    }

    @Override
    protected void initData() {

    }



}