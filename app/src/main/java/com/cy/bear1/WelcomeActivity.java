//package com.cy.bear1;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.TextView;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//
//public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
//
//    private int recLen = 5;//跳过倒计时提示5秒
//    private TextView tv;
//    Timer timer = new Timer();
//    private Handler handler;
//    private Runnable runnable;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // 1. 定义全屏参数
//        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        // 设置当前窗体为全屏显示
//        getWindow().setFlags(flag, flag);
//        setContentView(R.layout.activity_main);
//        // 初始化倒计时
//        initView();
//        timer.schedule(task, 1000, 1000);// 等待1秒，停顿1秒
//
//        /**
//         * 正常情况下不点击跳过
//         */
//        handler = new Handler();
//        handler.postDelayed(runnable = new Runnable() {
//            @Override
//            public void run() {
//                //从闪屏界面跳转到首界面
//                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 5000);//延迟5S后发送handler信息
//
//    }
//
//
//
//    private void initView(){
//        tv = (TextView)findViewById(R.id.tv);
//        tv.setOnClickListener(this);
//    }
//
//
//    TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    recLen--;
//                    tv.setText("跳过 "+recLen);
//                    if(recLen<0){
//                        timer.cancel();
//                        tv.setVisibility(View.GONE);// 倒计时到0 隐藏字体
//                    }
//                }
//            });
//        }
//    };
//
//    /**
//     * 点击跳过
//     */
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv:
//                //从闪屏界面跳转到首界面
//                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//                if (runnable != null) {
//                    handler.removeCallbacks(runnable);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//}
