package damicolo.le100fiabe;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class MyActivityMain extends Activity {

    String myURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.le_100_fiabe_activity_main);

        TabHost tabHost = (TabHost) findViewById(R.id.newTabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1");
        tabSpec.setContent(R.id.myTab1);
        tabSpec.setIndicator("Fiabe");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab2");
        tabSpec.setContent(R.id.myTab2);
        tabSpec.setIndicator("Zecchino");
        tabHost.addTab(tabSpec);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity_main, menu);
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        new RetrieveSongsList().execute();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class RetrieveSongsList extends AsyncTask<Void, Void, String> {

        private Exception exception;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://damicolo1.cloudapp.net/policy/Le.100.Fiabe.txt" ); //you can write here any link
                //Open a connection to that URL.//
                URLConnection urlConnection;
                urlConnection = url.openConnection();
                //Define InputStreams to read from the URLConnection.
                InputStream is = urlConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                //Read bytes to the Buffer until there is nothing more to read(-1).
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }
                String s = new String(baf.toByteArray(), "UTF-8");
                Log.d("ImageManager", "String: " + s);
                return  s;

            } catch (Exception e) {
                Log.d("ImageManager", "Error: " + e);
            }
            return "";
        }

        protected void onPostExecute(String list) {
            // TODO: check this.exception
            // TODO: do something with the feed
            myURL = list;
        }
    }
}
