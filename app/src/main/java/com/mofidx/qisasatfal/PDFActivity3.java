package com.mofidx.qisasatfal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnLongPressListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PDFActivity3 extends AppCompatActivity {
    Uri pdfUri;
    String pdfurl;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfactivity3);

        pdfView = findViewById(R.id.pdfView);

        String pdfname = getIntent().getStringExtra("FileName");
         pdfurl = getIntent().getStringExtra("FileUrl");

         new RetrivePdfStream().execute(pdfurl);
//        Toast.makeText(this, ""+pdfname+"\n"+pdfurl, Toast.LENGTH_SHORT).show();

//
//        ProgressDialog pd = new ProgressDialog(this);
//        pd.setTitle(pdfname);
//        pd.setMessage("جارٍ فتح القصة ....");





//            pdfUri= Uri.parse(pdfurl);
//            pdfView.fromUri(pdfUri)
//                    .swipeHorizontal(true)
//                    .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
//                    .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
//                    .pageSnap(true) // snap pages to screen boundaries
//                    .pageFling(true) // make a fling change only a single page like ViewPager
//                    .spacing(3)
////                    .nightMode(false) // toggle night mode
//                    .load();










    }

   class RetrivePdfStream extends AsyncTask<String,Void, InputStream> {


       @Override
       protected InputStream doInBackground(String... strings) {

        InputStream inputStream =null;

        try {
            URL url = new URL(strings[0]);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            if (httpsURLConnection.getResponseCode()==200)
            {
                inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());

            }
        } catch (IOException e) {
            return null;
        }
            return inputStream;
       }

       @Override
       protected void onPostExecute(InputStream inputStream) {
           pdfView.fromStream(inputStream)
                   .swipeHorizontal(true)
                   .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                   .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
                   .pageSnap(true) // snap pages to screen boundaries
                   .pageFling(true) // make a fling change only a single page like ViewPager
                   .spacing(3)
//                 .nightMode(false) // toggle night mode
                   .load();
       }
   }



}