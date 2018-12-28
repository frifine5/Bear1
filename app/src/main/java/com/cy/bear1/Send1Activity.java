package com.cy.bear1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class Send1Activity extends AppCompatActivity {

    private Button btn_send;// 解密按钮
    private Button btnCln;// 清除按钮
    private EditText etCtx;// 原文（in）
    private TextView result;// 计算结果（out）

    Handler h = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_send1);

        // 获得指定参数或对象
        etCtx = (EditText) findViewById(R.id.ctx);
        btn_send = (Button) findViewById(R.id.btn_send);
        btnCln = (Button) findViewById(R.id.btn_clean);
        result = (TextView) findViewById(R.id.result);



    }

    public void cleanData(View v) {
        etCtx.setText("");
        result.setText("");
    }


    /**
     * 发送消息
     */
    public void send(View v) {
        Log.i("bus", ">>>>发送消息");
        String setCtx = etCtx.getText().toString();
        result.setText("");// 清空历史显示数据
        Log.i("bus", String.format(">>>>准备发送的数据是:\t%s", setCtx));

        DemoHttpAsynTask task = new DemoHttpAsynTask();
        task.execute(setCtx);

    }

    /**
     * AsyncTask的执行分为四个步骤，每一步都对应一个回调方法，这些方法不应该由应用程序调用，开发者需要做的就是实现这些方法。
     　　1) 子类化AsyncTask
     　　2) 实现AsyncTask中定义的下面一个或几个方法
     　　   onPreExecute(), 该方法将在执行实际的后台操作前被UI thread调用。可以在该方法中做一些准备工作，如在界面上显示一个进度条。
     　　  doInBackground(Params...), 将在onPreExecute 方法执行后马上执行，该方法运行在后台线程中。这里将主要负责执行那些很耗时的后台计算工作。可以调用 publishProgress方法来更新实时的任务进度。该方法是抽象方法，子类必须实现。
     　　  onProgressUpdate(Progress...),在publishProgress方法被调用后，UI thread将调用这个方法从而在界面上展示任务的进展情况，例如通过一个进度条进行展示。
     　　  onPostExecute(Result), 在doInBackground 执行完成后，onPostExecute 方法将被UI thread调用，后台的计算结果将通过该方法传递到UI thread.

     为了正确的使用AsyncTask类，以下是几条必须遵守的准则：
     　　1) Task的实例必须在UI thread中创建
     　　2) execute方法必须在UI thread中调用
     　　3) 不要手动的调用onPreExecute(), onPostExecute(Result)，doInBackground(Params...), onProgressUpdate(Progress...)这几个方法
     　　4) 该task只能被执行一次，否则多次调用时将会出现异常
     doInBackground方法和onPostExecute的参数必须对应，这两个参数在AsyncTask声明的泛型参数列表中指定，第一个为doInBackground接受的参数，第二个为显示进度的参数，第第三个为doInBackground返回和onPostExecute传入的参数。
     */
    class DemoHttpAsynTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String[] params) {
            String msg =  params[0];
            Log.i("bus", String.format(">>>>in asynTask-msg:\t%s", msg));
            List<NameValuePair> reqList = new ArrayList<>();
            reqList.add(new BasicNameValuePair("msg", msg));
            String url = "http://192.168.7.145:11111/app/rev";
            HttpClient client = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            UrlEncodedFormEntity uefEntity;
            String result = "";
            try {
                uefEntity = new UrlEncodedFormEntity(reqList, "UTF-8");
                httppost.setEntity(uefEntity);
                System.out.println("executing request " + httppost.getURI());
                HttpResponse response = client.execute(httppost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = httppost.getEntity();
                    result = EntityUtils.toString(entity, "UTF-8");
                } else {
                    Log.i("bus", "返回结果为空");
                }
            } catch (Exception e) {
                Log.e("bus", "http请求失败", e);
            }
            return result;
        }

        protected void onPostExecute(String msg) {
            Log.i("bus", "in onPostExecute 返回结果:"+msg);
            result.setText(msg);
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
