package com.example.cll.caolilu20170927;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView lv;
    private InputStream inputStream;
    private ByteArrayOutputStream outputStream;
    private List<bean> been;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        init();
    }
    public void init(){
        new Thread(){


            String path="http://www.toutiao.com/api/pc/focus/";

            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    int code = connection.getResponseCode();
                    if (code==200){
                        inputStream = connection.getInputStream();
                        outputStream = new ByteArrayOutputStream();
                        byte[] read=new byte[1024];
                        int  len=-1;
                        while((len=inputStream.read(read))!=-1){
                            outputStream.write(read,0,len);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }runOnUiThread(new Runnable() {


                    @Override
                    public void run() {
                        been = new ArrayList<bean>();
                        try {
                            //Toast.makeText(MainActivity.this,outputStream.toString(),Toast.LENGTH_SHORT).show();
                            JSONObject object = new JSONObject(outputStream.toString());
                            JSONObject data = object.getJSONObject("data");
                            JSONArray array = data.getJSONArray("pc_feed_focus");
                            for (int a=0;a<array.length();a++){
                                JSONObject jsonObject = array.getJSONObject(a);
                                String name = jsonObject.getString("title");
                                String shi = jsonObject.getString("group_id");
                                String image = jsonObject.getString("image_url");
                              //  Toast.makeText(MainActivity.this,name,Toast.LENGTH_SHORT).show();
                                bean bean = new bean();
                                bean.setImage(image);
                                bean.setName(name);
                                bean.setShi(shi);
                                been.add(bean);
                                Myter myter = new Myter();
                                lv.setAdapter(myter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }
    class  Myter extends BaseAdapter{

        private Hnader hnader;

        @Override
        public int getCount() {
            return been.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                hnader = new Hnader();
                convertView = View.inflate(MainActivity.this, R.layout.tuem, null);
               hnader.name= (TextView) convertView.findViewById(R.id.name);
                hnader.image= (ImageView) convertView.findViewById(R.id.images);
                hnader.shi= (TextView) convertView.findViewById(R.id.shi);
                convertView.setTag(hnader);
             }else{
                hnader= (Hnader) convertView.getTag();
            }
            bean bean = been.get(position);
            hnader.name.setText(bean.getName());
            hnader.shi.setText(bean.getShi());
            ImageLoader.getInstance().displayImage(bean.getImage(),hnader.image);
            return convertView;
        }
        class Hnader{
            ImageView image;
            TextView name;
            TextView shi;
        }
    }
}
