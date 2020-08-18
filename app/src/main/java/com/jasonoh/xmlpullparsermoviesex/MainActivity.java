package com.jasonoh.xmlpullparsermoviesex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView listView_movies;

    MyAdapter adapter;
    ArrayList<MemberMovies> memberMovies = new ArrayList<>();

    String key = "a6674e6a6b1460e7db396feb9fe986cd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView_movies = findViewById(R.id.listView_movies);
        adapter = new MyAdapter(memberMovies, getLayoutInflater());
        listView_movies.setAdapter( adapter );

    }//onCreate method

    public void clickBtn(View view) {

        new Thread() {
            @Override
            public void run() {

                //오늘의 시간 가져오기
                Date date = new Date();
                date.setTime( date.getTime() - (1000*60*60*24) ); //현재 시간 가져오기
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyyMMdd" );
                String dateStr = simpleDateFormat.format( date ); //현재 시간을 포멧

                //네트워크에서 정보 얻어오기
                String address = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml"
                                    + "?key=" + key
                                    + "&targetDt=" + dateStr;
                try {
                    URL url = new URL(address);
                    InputStream inputStream = url.openStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput( inputStreamReader );

                    int eventType = xpp.getEventType();

                    String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";

                    StringBuffer stringBuffer = new StringBuffer();

                    while ( eventType != XmlPullParser.END_DOCUMENT ) {

                        eventType = xpp.next();

                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT :
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "검색 시작", Toast.LENGTH_SHORT).show();
                                    }//run
                                });//runOnUiThread
                                break;
                            case XmlPullParser.START_TAG :
                                String tagName_start = xpp.getName();
                                if(tagName_start.equals("dailyBoxOffice")) stringBuffer = new StringBuffer();
                                else if(tagName_start.equals("rank")) {
                                    stringBuffer.append("순위 : ");
                                    xpp.next();
                                    stringBuffer.append(xpp.getText() + "\n");
                                    s1 = stringBuffer.toString();
                                    //arrayList.add( stringBuffer.toString() );
                                    stringBuffer = null;
                                    stringBuffer = new StringBuffer();

                                } else if(tagName_start.equals("movieNm")) {
                                    stringBuffer.append("영화 제목 : ");
                                    xpp.next();
                                    stringBuffer.append(xpp.getText() + "\n");
                                    s2 = stringBuffer.toString();
                                    //arrayList.add( stringBuffer.toString() );
                                    stringBuffer = null;
                                    stringBuffer = new StringBuffer();

                                } else if(tagName_start.equals("openDt")) {
                                    stringBuffer.append("개봉일 : ");
                                    xpp.next();
                                    stringBuffer.append(xpp.getText() + "\n");
                                    s3 = stringBuffer.toString();
                                    //arrayList.add( stringBuffer.toString() );
                                    stringBuffer = null;
                                    stringBuffer = new StringBuffer();

                                } else if(tagName_start.equals("audiCnt")) {
                                    stringBuffer.append("일 관객수 : ");
                                    xpp.next();
                                    stringBuffer.append(xpp.getText() + "\n");
                                    s4 = stringBuffer.toString();
                                    //arrayList.add( stringBuffer.toString() );
                                    stringBuffer = null;
                                    stringBuffer = new StringBuffer();

                                } else if(tagName_start.equals("audiAcc")) {
                                    stringBuffer.append("누적 관객수 : ");
                                    xpp.next();
                                    stringBuffer.append(xpp.getText() + "\n");
                                    s5 = stringBuffer.toString();
                                    //arrayList.add( stringBuffer.toString() );
                                    stringBuffer = null;
                                    stringBuffer = new StringBuffer();
                                }
                                break;
                            case XmlPullParser.TEXT :
                                break;
                            case XmlPullParser.END_TAG :
                                if(xpp.getName().equals("dailyBoxOffice")) memberMovies.add( new MemberMovies( s1, s2, s3, s4, s5 ) );
                                //if(xpp.getName().equals("dailyBoxOffice")) memberMovies.add( new MemberMovies( "1", "2", "3", "4", "5" ) );
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                                break;
                        }//switch

                    }//while

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "검색 종료", Toast.LENGTH_SHORT).show();
                        }
                    });

                    inputStreamReader.close();
                    inputStream.close(); //검색 종료시 스트림 닫기

                } catch (Exception e) { }

            }//run
        }.start();

    }//clickBtn method
}//MainActivity class
