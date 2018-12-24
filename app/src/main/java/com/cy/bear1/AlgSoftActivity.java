package com.cy.bear1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.bear1.dbm.PChkUtil;
import com.cy.common.bus.sm4.SM4Util;


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
    }

    /**
     * sm4加密
     */
    public void sm4Enc(View v){
        Log.i("bus", ">>>>SM4加密");
        String setCtx = etCtx.getText().toString();
        String setSM4Key = etSM4Key.getText().toString();
        result.setText("");// 清空历史显示数据
        Log.i("bus", String.format(">>>>SM4入参:密钥= %s, 原文= %s",setSM4Key, setCtx));
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
            Log.i("bus", ">>>>SM4加密结果=\t"+retStr);
            Toast.makeText(this, "SM4加密完成", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * sm4解密
     */
    public void sm4Dec(View v){
        Log.i("bus", ">>>>SM4解密");
        String setCtx = etCtx.getText().toString();
        String setSM4Key = etSM4Key.getText().toString();
        result.setText("");// 清空历史显示数据
        Log.i("bus", String.format(">>>>SM4入参:密钥= %s, 密文= %s",setSM4Key, setCtx));
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
            Log.i("bus", ">>>>SM4解密结果=\t"+retStr);
            Toast.makeText(this, "SM4解密完成", Toast.LENGTH_SHORT).show();
        }
    }


    public void dotest(){

        Log.i("bus", ">>>>测试sm4");

        String ctx = "我是明文12345678";
        String key = "1234567812345678";
        SM4Util sm4 = new SM4Util();
        sm4.secretKey = key;
        sm4.hexString = false;

        String rt = sm4.encryptData_ECB(ctx);
        Log.i("bus", ">>>>原文ctx:\t"+ctx);
        Log.i("bus", ">>>>SM4(ECB)加密结果:\t"+rt);
        String ctx2 = sm4.decryptData_ECB(rt);
        Log.i("bus", ">>>>SM4(ECB)解密结果:\t"+ctx2);

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