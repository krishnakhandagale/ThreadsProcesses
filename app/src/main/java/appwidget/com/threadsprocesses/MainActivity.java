package appwidget.com.threadsprocesses;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Handler;


public class MainActivity extends ActionBarActivity {
    ImageView mImageView=null,mImageView1=null;
    ProgressDialog progressDialog=null;
    ProgressDialog progressDialog1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView= (ImageView) findViewById(R.id.imageView);
        mImageView1= (ImageView) findViewById(R.id.imageView1);
        progressDialog =new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog1 =new ProgressDialog(MainActivity.this);
        progressDialog1.setMessage("Loading...");
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new Thread(new Runnable() {
            public void run() {
                Bitmap b = null,b1=null;
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                       progressDialog.show();
                   }
               });
                try {
                    b = loadImageFromNetwork("http://www.gettyimages.in/gi-resources/images/CreativeImages/Hero-527920799.jpg");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                final Bitmap finalB = b; final Bitmap finalB1 = b1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            
                        progressDialog.dismiss();
                        mImageView.setImageBitmap(finalB);
                    }
                });

            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog1.show();
                    }
                });
                Bitmap b = null,b1=null;
                try {

                    b1= loadImageFromNetwork("http://feelgrafix.com/data/wallpaper-hd/Wallpaper-HD-16.jpg");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                final Bitmap finalB = b; final Bitmap finalB1 = b1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1.dismiss();
                        mImageView1.setImageBitmap(finalB1);

                    }
                });
            }
        }).start();

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

    private Bitmap loadImageFromNetwork(String url ) throws MalformedURLException {
        Bitmap result= null;
        URL urlToRead=new URL(url);
        try {
            HttpURLConnection connection= (HttpURLConnection) urlToRead.openConnection();
            BitmapFactory.Options options= new BitmapFactory.Options();
            options.inSampleSize=0;

            result=BitmapFactory.decodeStream(connection.getInputStream(),null, options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}
