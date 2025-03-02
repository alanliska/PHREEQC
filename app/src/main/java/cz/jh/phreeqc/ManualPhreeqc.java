package cz.jh.phreeqc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acutecoder.pdf.OnActionListener;
import com.acutecoder.pdf.PdfScrollBar;
import com.acutecoder.pdf.PdfView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import cz.jh.phreeqc.MainActivity;

public class ManualPhreeqc extends MainActivity {

//    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.manualphreeqc);

        PdfView pdfView = findViewById(R.id.pdfView);
        PdfScrollBar scrollBar = findViewById(R.id.pdfScroll);
        ProgressBar progressBar = findViewById(R.id.pg);

        scrollBar.attachTo(pdfView);
        pdfView.setZoomEnabled(true);
        pdfView.setMaxZoomScale(5); //Maximum Zoom
        pdfView.setPath(new File(getFilesDir()+"/docs/phreeqc_manual.pdf")); //Normal File loaction
        // pdfView.setPath(new TemporaryFile(R.raw.pdf)); //Raw File
        // pdfView.setPath(new TemporaryFile("pdfs/MyPdfFile.pdf")); //Asset File
        pdfView.setQuality(1f); //80%
        pdfView.setModFlingLimit(0);
        pdfView.addOnActionListener(new OnActionListener() {
            @Override
            public void onLoaded() {
                progressBar.setVisibility(View.GONE);
            }
        });
        pdfView.load();
    }

}
