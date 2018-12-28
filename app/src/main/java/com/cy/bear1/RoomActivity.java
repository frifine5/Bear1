package com.cy.bear1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class RoomActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }


    /**
     * 跳转到SM4加解密Demo页面
     */
    public void g2AlgSf(View v){
        Intent intent = new Intent(RoomActivity.this, AlgSoftActivity.class);
        startActivity(intent);
//        finish();

    }

    /**
     * 跳转到发送消息页
     * @param v
     */
    public void g2MsgSender(View v){
        Intent intent = new Intent(RoomActivity.this, Send1Activity.class);
        startActivity(intent);
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
