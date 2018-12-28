package com.cy.bear1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cy.bear1.dbm.DBOpenHelper;
import com.cy.bear1.dbm.PChkUtil;
import com.cy.common.bus.L;


public class LoginActivity extends AppCompatActivity {

    private EditText eusername;
    private EditText euserpassword;
    private Button btnlogin;
    private Button btncancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // view中的对象定位赋值
        eusername = (EditText) findViewById(R.id.e_user_name);
        euserpassword = (EditText) findViewById(R.id.e_user_password);

        btnlogin = (Button) findViewById(R.id.b_btn_login);
        btncancel = (Button) findViewById(R.id.b_btn_cancel);

    }

    /**
     * 登录事件<br/>
     *
     * @param v
     */
    public void onMyLoginClick(View v){
        String name = eusername.getText().toString();
        String pwd =  euserpassword.getText().toString();
        if(PChkUtil.checkNull(name, pwd)){
            Toast.makeText(this, "登录名和密码不能为空", Toast.LENGTH_SHORT).show();

        }else {
            Log.i(L.BUS, String.format(">>>> name=%s, pwd=%s", name, pwd));
            // call db to find data on condition
            DBOpenHelper helper = new DBOpenHelper(this, "lg.db", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor c = db.query("lg_user_tb", null, "name=? and pwd=?", new String[]{name, pwd}, null, null, null);
            if (c != null && c.getCount() > 0) {
                String[] cols = c.getColumnNames();
                while (c.moveToNext()) {
                    for (String cluName : cols) {
                        Log.i(L.BUS, String.format(">>>> cluName=%s, value=%s", cluName, c.getString(c.getColumnIndex(cluName))));
                    }
                }
                c.close();
                db.close();

                // 将登录信息存储到sharedPreferences中
                SharedPreferences mySharedPreferences = getSharedPreferences("setting", Activity.MODE_PRIVATE); //实例化SharedPreferences对象
                SharedPreferences.Editor editor = mySharedPreferences.edit();//实例化SharedPreferences.Editor对象
                editor.putString("name", name); //用putString的方法保存数据
                editor.commit(); //提交当前数据

                Intent intent = new Intent(LoginActivity.this, RoomActivity.class);
                startActivity(intent);
//                finish();
            } else {
                Toast.makeText(this, "用户和密码输入错误", Toast.LENGTH_SHORT).show();
            }

        }
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
