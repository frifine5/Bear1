package com.cy.bear1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.bear1.dbm.PChkUtil;
import com.cy.common.bus.L;
import com.cy.common.bus.sm3.SM3Util;
import com.cy.common.bus.sm4.SM4Util;
import com.hoofoo.android.client.SoftAlgJni;

import org.bouncycastle.util.encoders.Hex;


public class AlgSoftActivity extends AppCompatActivity{

    private Button btnEnc;// 加密按钮
    private Button btnDec;// 解密按钮
    private Button btnCln;// 清除按钮
    private EditText etCtx;// 原文（in）
    private EditText etSM4Key;// sm4的密钥
    private TextView result;// 计算结果（out）


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sf_algorithm);

        // 获得指定参数或对象
        etCtx = (EditText) findViewById(R.id.ctx);
        etSM4Key = (EditText)findViewById(R.id.sm4key);
        btnEnc =  (Button) findViewById(R.id.btn_enc);
        btnDec =  (Button) findViewById(R.id.btn_dec);
        btnCln = (Button)findViewById(R.id.btn_clean);
        result = (TextView) findViewById(R.id.result);

    }

    public void cleanData(View v){
        etCtx.setText("");
        result.setText("");
        dotest2();
        dotest3();
    }

    /**
     * sm4加密
     */
    public void sm4Enc(View v){
        Log.i(L.BUS, ">>>>SM4加密");
        String setCtx = etCtx.getText().toString();
        String setSM4Key = etSM4Key.getText().toString();
        result.setText("");// 清空历史显示数据
        Log.i(L.BUS, String.format(">>>>SM4入参:密钥= %s, 原文= %s", setSM4Key, setCtx));
        if(PChkUtil.checkNull(setCtx, setSM4Key)){
            Toast.makeText(this, "原文和密钥不能为空", Toast.LENGTH_SHORT).show();
        }else if(setSM4Key.length() !=32 || !PChkUtil.isHexString(setSM4Key)){
            Toast.makeText(this, "密钥长度或格式错误", Toast.LENGTH_SHORT).show();
        }else{
            SM4Util sm4 = new SM4Util();
            sm4.hexString = true;
            sm4.secretKey = setSM4Key;
            String retStr = sm4.encryptData_ECB(setCtx);
            result.setText(retStr);
            Log.i(L.BUS, ">>>>SM4加密结果=\t"+retStr);
            Toast.makeText(this, "SM4加密完成", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * sm4解密
     */
    public void sm4Dec(View v){
        Log.i(L.BUS, ">>>>SM4解密");
        String setCtx = etCtx.getText().toString();
        String setSM4Key = etSM4Key.getText().toString();
        result.setText("");// 清空历史显示数据
        Log.i(L.BUS, String.format(">>>>SM4入参:密钥= %s, 密文= %s",setSM4Key, setCtx));
        if(PChkUtil.checkNull(setCtx, setSM4Key)){
            Toast.makeText(this, "密文和密钥不能为空", Toast.LENGTH_SHORT).show();
        }else if(setSM4Key.length() !=32 || !PChkUtil.isHexString(setSM4Key)) {
            Toast.makeText(this, "密钥长度或格式错误", Toast.LENGTH_SHORT).show();
        }else if(!PChkUtil.isBase64Str(setCtx)){
            Toast.makeText(this, "密文不符合Base64字符格式", Toast.LENGTH_SHORT).show();
        }else{
            SM4Util sm4 = new SM4Util();
            sm4.hexString = true;
            sm4.secretKey = setSM4Key;
            String retStr = sm4.decryptData_ECB(setCtx);
            result.setText(retStr);
            Log.i(L.BUS, ">>>>SM4解密结果=\t"+retStr);
            Toast.makeText(this, "SM4解密完成", Toast.LENGTH_SHORT).show();
        }
    }


    public void dotest1(){

        Log.i(L.BUS, ">>>>测试sm4");

        String ctx = "我是明文12345678";
        String key = "1234567812345678";
        SM4Util sm4 = new SM4Util();
        sm4.secretKey = key;
        sm4.hexString = false;

        String rt = sm4.encryptData_ECB(ctx);
        Log.i(L.BUS, ">>>>原文ctx:\t"+ctx);
        Log.i(L.BUS, ">>>>SM4(ECB)加密结果:\t" + rt);
        String ctx2 = sm4.decryptData_ECB(rt);
        Log.i(L.BUS, ">>>>SM4(ECB)解密结果:\t"+ctx2);

    }

    public void dotest2(){

        Log.i(L.BUS, ">>>>测试SoftAlgJni的使用-sm3");
        String ctx = "我是明文12345678";
        byte[] input = ctx.getBytes();

        SoftAlgJni softAlgJni = SoftAlgJni.getInstance();
        int[] outPutLen = new int[1];
        outPutLen[0] = 0;
        int ret = softAlgJni.hf_a_sm3(input, input.length, null, outPutLen);
        byte[] outPut = new byte[outPutLen[0]];
        long st1 = System.nanoTime();
        ret = softAlgJni.hf_a_sm3(input, input.length, outPut, outPutLen);
        Log.i(L.BUS, "ret="+Integer.toHexString(ret));
        Log.i(L.BUS, "摘要结果1:\t" + Hex.toHexString(outPut));
        long et1 = System.nanoTime();
        long st2 = System.nanoTime();
        byte[] md = SM3Util.sm3Digest(input);
        Log.i(L.BUS, "摘要2用软摘"+Integer.toHexString(ret));
        Log.i(L.BUS, "摘要结果2:\t" + Hex.toHexString(md));
        long et2 = System.nanoTime();
        Log.i(L.BUS, "t1="+(et1-st1));
        Log.i(L.BUS, "t2="+(et2-st2));


    }

    private byte[] getRnd(int len){

        String str = "";
        for (int i = 0; i < 32; i++) {
            str += ((int)(Math.random()*10))+"";
        }
        return str.getBytes();
    }

    public void dotest3(){
        SoftAlgJni softAlgJni = SoftAlgJni.getInstance();

        softAlgJni.hf_a_bw_sm2_sys_init();

        byte[] rnd = getRnd(32);
        byte[] sk = new byte[64];
        int[] skLen = {sk.length};
        byte[] outData = new byte[65];
        int[] outDataLen = {outData.length};
        Log.i(L.BUS,  "函数调用前随机数="+ Base64.encodeToString(rnd, Base64.DEFAULT));

        int ret = softAlgJni.hf_a_bw_sm2_tps_client_keygen(rnd, 32, sk, skLen, outData, outDataLen);

        Log.i(L.BUS, "执行结果ret="+ret);
        if(0 == ret){
            Log.i(L.BUS,  "函数调用后随机数="+ Base64.encodeToString(rnd, Base64.DEFAULT));
            Log.i(L.BUS,  "客户端私钥="+ Base64.encodeToString(sk, Base64.DEFAULT));
            Log.i(L.BUS,  "输出中间数据="+ Base64.encodeToString(outData, Base64.DEFAULT));
        }
        softAlgJni.hf_a_bw_sm2_sys_clear();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
