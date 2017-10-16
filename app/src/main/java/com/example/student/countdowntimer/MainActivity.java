package com.example.student.countdowntimer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4;
    Handler handler;
    MyAsyncTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);

        handler = new Handler();
    }

    public void click1(View v) {
        new CountDownTimer(8500, 1000) {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onTick(long l) {
                tv1.setText("s:" + l / 1000);
            }

            @Override
            public void onFinish() {
                tv1.setText("done!");
            }

            //            public void onTick(long millisUntilFinished) {
//                tv1.setText("s:" + millisUntilFinished / 1000);
//            }
//            public void onFinish() {
//                tv1.setText("done!");
//            }
        }.start();
    }


    public void click2(View v) {
        new Thread() {
            int i = 6;

            @Override
            public void run() {
                super.run();
                do {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv2.setText(String.valueOf(i));
                            i--;
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (i >= 0);

            }
        }.start();
    }

    public void click3(View v) {
        new Thread() {
            int i = 5;

            @Override
            public void run() {
                super.run();
                do {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv3.setText(String.valueOf(i));
                            i--;
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (i >= 0);

            }
        }.start();
    }

    public void click4(View v) {
        if (task == null) {
            task = new MyAsyncTask(tv4);
            task.execute(5);

        }
    }
    public void click5(View v) {
        if (task != null) {
            task.cancel(false);
        }
    }

    class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

        TextView tv;

        public MyAsyncTask(TextView tv) {
            this.tv = tv;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int n = integers[0];
            int i;

            for (i = n; i >= 0; i--) {
                Log.d("Task", "i:" + i);
                publishProgress(i);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled() == true) {
                    break;
                }
            }

            return "ok";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Task", s);
            task = null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            tv.setText("Canceled");
            task = null;
        }
    }

}