package cz.jh.phreeqc;

import static cz.jh.phreeqc.Spannables.colorized_phreeqc;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private TextView InputLabel;
    private EditText InputFile;
    Button openInputfile;
    Button openIntInputfile;
    Button saveInputfile;
    Button saveExtInputfile;
    Button RunProgram;
    Button saveOutputfile;
    Button saveExtOutputfile;
    Button Highlight;
    Button Graph;
    Button About;
    Button License;
    private TextView textViewX;
    private TextView outputView;
    private EditText outputView2;
    private static final int READ_FILE6 = 6;
    private Uri documentUri6;
    private static final int READ_FILE26 = 26;
    private Uri documentUri26;
    private static final int CREATE_FILE20 = 20;
    private Uri documentUri20;
    private static final int CREATE_FILE21 = 21;
    private Uri documentUri21;
    Button manual_phreeqc;
    private static final int MY_PERMISSION_REQUEST_STORAGE = 1;
    Button change_size;
    Button PrivacyPolicy;
    private TextView DatabaseLabel;
    private TextView Database;
    private Button openDatabasefile;
    private Button openIntDatabasefile;
    Button searchDatabase;
    Button viewDatabase;
    Button deleteFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DatabaseLabel = (TextView) findViewById(R.id.DatabaseLabel);
        Database = (TextView) findViewById(R.id.Database);
        openDatabasefile = (Button) findViewById(R.id.openDatabasefile);
        openDatabasefile.setOnClickListener(openDatabasefileClick);
        openIntDatabasefile = (Button) findViewById(R.id.openIntDatabasefile);

        InputLabel = (TextView) findViewById(R.id.InputLabel);
        InputFile = (EditText) findViewById(R.id.InputFile);
        // prevent crash in the beginning, when the file is not already unzipped
        File InputTextSize = new File(getFilesDir()+"/InputTextSize.txt");
        if (InputTextSize.exists()) {
            InputFile.setTextSize(Integer.valueOf(exec("cat "+getFilesDir()+"/InputTextSize.txt")).intValue());
        } else {
            InputFile.setTextSize(16);
        }
        // disable - otherwise the text could not be selected
//        InputFile.setMovementMethod(new ScrollingMovementMethod());
        InputFile.addTextChangedListener(new TextWatcher() {
            int startChanged,beforeChanged,countChanged;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startChanged = start;
                beforeChanged = before;
                countChanged = count;
            }
            @Override
            public void afterTextChanged(Editable s) {
                InputFile.removeTextChangedListener(this);
                String text = InputFile.getText().toString();
                // important - not setText() - otherwise the keyboard would be reset after each type
                InputFile.getText().clear();
                InputFile.append(colorized_phreeqc(text));
                // place the cursor at the original position
                InputFile.setSelection(startChanged+countChanged);
                InputFile.addTextChangedListener(this);
            }
        });
        openInputfile = (Button) findViewById(R.id.openInputfile);
        openInputfile.setOnClickListener(openInputfileClick);
        openIntInputfile = (Button) findViewById(R.id.openIntInputfile);
        saveInputfile = (Button) findViewById(R.id.saveInputfile);
        saveInputfile.setOnClickListener(saveInputfileClick);
        saveExtInputfile = (Button) findViewById(R.id.saveExtInputfile);
        saveExtInputfile.setOnClickListener(saveExtInputfileClick);

        RunProgram = (Button) findViewById(R.id.RunProgram);
        RunProgram.setOnClickListener(RunProgramClick);
        searchDatabase = (Button) findViewById(R.id.searchDatabase);
        searchDatabase.setOnClickListener(searchDatabaseClick);
        viewDatabase = (Button) findViewById(R.id.viewDatabase);
        viewDatabase.setOnClickListener(viewDatabaseClick);
        saveOutputfile = (Button) findViewById(R.id.saveOutputfile);
        saveOutputfile.setOnClickListener(saveOutputfileClick);
        saveExtOutputfile = (Button) findViewById(R.id.saveExtOutputfile);
        saveExtOutputfile.setOnClickListener(saveExtOutputfileClick);
        Highlight = (Button) findViewById(R.id.Highlight);
        Highlight.setOnClickListener(HighlightClick);
        About = (Button) findViewById(R.id.About);
        About.setOnClickListener(AboutClick);
        textViewX = (TextView) findViewById(R.id.textViewX);
        outputView = (TextView) findViewById(R.id.outputView);
        outputView2 = (EditText) findViewById(R.id.outputView2);
        // prevent crash in the beginning, when the file is not already unzipped
        File OutputTextSize = new File(getFilesDir()+"/OutputTextSize.txt");
        if (OutputTextSize.exists()) {
            outputView2.setTextSize(Integer.valueOf(exec("cat "+getFilesDir()+"/OutputTextSize.txt")).intValue());
        } else {
            outputView2.setTextSize(12);
        }
//        outputView2.addTextChangedListener(new TextWatcher() {
//            int startChanged,beforeChanged,countChanged;
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                startChanged = start;
//                beforeChanged = before;
//                countChanged = count;
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                outputView2.removeTextChangedListener(this);
//                String text = outputView2.getText().toString();
//                // important - not setText() - otherwise the keyboard would be reset after each type
//                outputView2.getText().clear();
//                outputView2.append(text);
//                // place the cursor at the original position
//                outputView2.setSelection(startChanged+countChanged);
//                outputView2.addTextChangedListener(this);
//            }
//        });

        Graph = (Button) findViewById(R.id.Graph);
        Graph.setOnClickListener(GraphClick);

        deleteFiles = (Button) findViewById(R.id.deleteFiles);
        deleteFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeletePicker.class);
                startActivity(intent);
            }
        });

        PrivacyPolicy = (Button) findViewById(R.id.PrivacyPolicy);
        PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrivacyPolicy.class);
                startActivity(intent);
            }
        });

        change_size = (Button) findViewById(R.id.change_size);
        change_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangeSize.class);
                startActivity(intent);
            }
        });

        License = (Button) findViewById(R.id.License);
        License.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Licenses.class);
                startActivity(intent);
            }
        });

        manual_phreeqc = (Button) findViewById(R.id.manual_phreeqc);
        manual_phreeqc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ManualPhreeqc.class);
                startActivity(intent);
            }
        });

        openIntDatabasefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhreeqcDatabase.class);
                startActivity(intent);
            }
        });

        openIntInputfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhreeqcWork.class);
                startActivity(intent);
            }
        });

        // give the app permissions to access the storage
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_STORAGE);

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_STORAGE);
                }
                ;
            } else {
                // do nothing
            };
        }

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        SharedPreferences.Editor editor1 = wmbPreference.edit();

        if (isFirstRun){

            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.setMessage("Installing PHREEQC. It may take a while.");
            progressDialog.show();
            new Thread() {
                public void run() {
                    copyFromAssetsToInternalStorage("assets.zip");
                    copyFromAssetsToInternalStorage("Database-phreeqc.txt");
                    copyFromAssetsToInternalStorage("Input-phreeqc.txt");
                    copyFromAssetsToInternalStorage("Database-name.txt");
                    copyFromAssetsToInternalStorage("InputTextSize.txt");
                    copyFromAssetsToInternalStorage("OutputTextSize.txt");
//                    copyFromAssetsToInternalStorage("TextSize.txt");
//                    String zipFilePath = getFilesDir()+"/assets.zip";
                    String zipFilePath = getFilesDir()+File.separator+"assets.zip";
//                    String destDir = getFilesDir()+"/" ;
//                    unzipfile( zipFilePath, destDir ) ;
                    try {
//                        unzip(new File(zipFilePath),destDir);
                        unzip(new File(zipFilePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    exec("rm "+getFilesDir()+File.separator+"assets.zip");
//                    unZipFile(getFilesDir()+"/assets.zip");

//                    unzipAssets(MainActivity.this);
//                    copyAsset("assets.zip");
                    exec("mkdir "+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/phreeqc");
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(ErgoInput.getWindowToken(), 0);
//                String command = ErgoInput.getText().toString();
//                    String command = "export HOME="+getFilesDir()+"/ ; cd $HOME ; unzip assets.zip ; rm assets.zip ; chmod -R 755 *";
//                    new RunCommandTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, command);
                    exec("chmod -R 755 "+getFilesDir()+"/");
                    output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                    output4(exec("cat "+getFilesDir()+"/Database-name.txt"));

                    onFinish();
                }
                public void onFinish(){
                    progressDialog.dismiss();
                }
            }.start();
            editor1.putBoolean("FIRSTRUN", false);
            editor1.apply();
        }

    //    restoreGraphFiles();
    }

    public void onStart()
    {
        super.onStart();
    //    restoreGraphFiles();
        output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
        output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
    }





    private View.OnClickListener GraphClick; {

        GraphClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // perform the calculation
                // TODO Auto-generated method stub //
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Please wait...");
                progressDialog.setMessage("PHREEQC calculation is in progress...");
                progressDialog.setCancelable(false);
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialog.show();

                new Thread() {
                    public void run() {
                        // delete the previously created graphs
                        restoreGraphFiles();

                        // run the calculation
                        com.jrummyapps.android.shell.Shell.SH.run("export HOME="+getFilesDir()+" ; cd $HOME ; "+getApplicationInfo().nativeLibraryDir+"/libphreeqc.so Input-phreeqc.txt Input.out Database-phreeqc.txt ; cp Input.out LastExecutionOutput.txt");

                        // prepare the graphs
                        try {
                            // remove the header
                            String Sed = exec("sed -e 1d "+getFilesDir()+"/graph-series1.csv");
                            FileOutputStream fileoutS = openFileOutput("graph-series1.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS = new OutputStreamWriter(fileoutS);
                            outputWriterS.write(Sed);
                            outputWriterS.close();
                            exec("rm "+getFilesDir()+"/graph-series1.csv");
                            exec("mv "+getFilesDir()+"/graph-series1.tmp "+getFilesDir()+"/graph-series1.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series1.csv");
                            FileOutputStream fileoutG = openFileOutput("graph-series1.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG = new OutputStreamWriter(fileoutG);
                            outputWriterG.write(Sort);
                            outputWriterG.close();
                            exec("rm "+getFilesDir()+"/graph-series1.csv");
                            exec("mv "+getFilesDir()+"/graph-series1.tmp "+getFilesDir()+"/graph-series1.csv");
                            // remove blank lines in sorted file
                            String BlankLin = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series1.csv");
                            FileOutputStream fileoutBL = openFileOutput("graph-series1.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL = new OutputStreamWriter(fileoutBL);
                            outputWriterBL.write(BlankLin);
                            outputWriterBL.close();
                            exec("rm "+getFilesDir()+"/graph-series1.csv");
                            exec("mv "+getFilesDir()+"/graph-series1.tmp "+getFilesDir()+"/graph-series1.csv");

                            // remove the header
                            String Sed2 = exec("sed -e 1d "+getFilesDir()+"/graph-series2.csv");
                            FileOutputStream fileoutS2 = openFileOutput("graph-series2.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS2 = new OutputStreamWriter(fileoutS2);
                            outputWriterS2.write(Sed2);
                            outputWriterS2.close();
                            exec("rm "+getFilesDir()+"/graph-series2.csv");
                            exec("mv "+getFilesDir()+"/graph-series2.tmp "+getFilesDir()+"/graph-series2.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort2 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series2.csv");
                            FileOutputStream fileoutG2 = openFileOutput("graph-series2.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG2 = new OutputStreamWriter(fileoutG2);
                            outputWriterG2.write(Sort2);
                            outputWriterG2.close();
                            exec("rm "+getFilesDir()+"/graph-series2.csv");
                            exec("mv "+getFilesDir()+"/graph-series2.tmp "+getFilesDir()+"/graph-series2.csv");
                            // remove blank lines in sorted file
                            String BlankLin2 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series2.csv");
                            FileOutputStream fileoutBL2 = openFileOutput("graph-series2.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL2 = new OutputStreamWriter(fileoutBL2);
                            outputWriterBL2.write(BlankLin2);
                            outputWriterBL2.close();
                            exec("rm "+getFilesDir()+"/graph-series2.csv");
                            exec("mv "+getFilesDir()+"/graph-series2.tmp "+getFilesDir()+"/graph-series2.csv");

                            // remove the header
                            String Sed3 = exec("sed -e 1d "+getFilesDir()+"/graph-series3.csv");
                            FileOutputStream fileoutS3 = openFileOutput("graph-series3.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS3 = new OutputStreamWriter(fileoutS3);
                            outputWriterS3.write(Sed3);
                            outputWriterS3.close();
                            exec("rm "+getFilesDir()+"/graph-series3.csv");
                            exec("mv "+getFilesDir()+"/graph-series3.tmp "+getFilesDir()+"/graph-series3.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort3 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series3.csv");
                            FileOutputStream fileoutG3 = openFileOutput("graph-series3.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG3 = new OutputStreamWriter(fileoutG3);
                            outputWriterG3.write(Sort3);
                            outputWriterG3.close();
                            exec("rm "+getFilesDir()+"/graph-series3.csv");
                            exec("mv "+getFilesDir()+"/graph-series3.tmp "+getFilesDir()+"/graph-series3.csv");
                            // remove blank lines in sorted file
                            String BlankLin3 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series3.csv");
                            FileOutputStream fileoutBL3 = openFileOutput("graph-series3.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL3 = new OutputStreamWriter(fileoutBL3);
                            outputWriterBL3.write(BlankLin3);
                            outputWriterBL3.close();
                            exec("rm "+getFilesDir()+"/graph-series3.csv");
                            exec("mv "+getFilesDir()+"/graph-series3.tmp "+getFilesDir()+"/graph-series3.csv");

                            // remove the header
                            String Sed4 = exec("sed -e 1d "+getFilesDir()+"/graph-series4.csv");
                            FileOutputStream fileoutS4 = openFileOutput("graph-series4.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS4 = new OutputStreamWriter(fileoutS4);
                            outputWriterS4.write(Sed4);
                            outputWriterS4.close();
                            exec("rm "+getFilesDir()+"/graph-series4.csv");
                            exec("mv "+getFilesDir()+"/graph-series4.tmp "+getFilesDir()+"/graph-series4.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort4 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series4.csv");
                            FileOutputStream fileoutG4 = openFileOutput("graph-series4.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG4 = new OutputStreamWriter(fileoutG4);
                            outputWriterG4.write(Sort4);
                            outputWriterG4.close();
                            exec("rm "+getFilesDir()+"/graph-series4.csv");
                            exec("mv "+getFilesDir()+"/graph-series4.tmp "+getFilesDir()+"/graph-series4.csv");
                            // remove blank lines in sorted file
                            String BlankLin4 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series4.csv");
                            FileOutputStream fileoutBL4 = openFileOutput("graph-series4.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL4 = new OutputStreamWriter(fileoutBL4);
                            outputWriterBL4.write(BlankLin4);
                            outputWriterBL4.close();
                            exec("rm "+getFilesDir()+"/graph-series4.csv");
                            exec("mv "+getFilesDir()+"/graph-series4.tmp "+getFilesDir()+"/graph-series4.csv");

                            // remove the header
                            String Sed5 = exec("sed -e 1d "+getFilesDir()+"/graph-series5.csv");
                            FileOutputStream fileoutS5 = openFileOutput("graph-series5.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS5 = new OutputStreamWriter(fileoutS5);
                            outputWriterS5.write(Sed5);
                            outputWriterS5.close();
                            exec("rm "+getFilesDir()+"/graph-series5.csv");
                            exec("mv "+getFilesDir()+"/graph-series5.tmp "+getFilesDir()+"/graph-series5.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort5 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series5.csv");
                            FileOutputStream fileoutG5 = openFileOutput("graph-series5.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG5 = new OutputStreamWriter(fileoutG5);
                            outputWriterG5.write(Sort5);
                            outputWriterG5.close();
                            exec("rm "+getFilesDir()+"/graph-series5.csv");
                            exec("mv "+getFilesDir()+"/graph-series5.tmp "+getFilesDir()+"/graph-series5.csv");
                            // remove blank lines in sorted file
                            String BlankLin5 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series5.csv");
                            FileOutputStream fileoutBL5 = openFileOutput("graph-series5.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL5 = new OutputStreamWriter(fileoutBL5);
                            outputWriterBL5.write(BlankLin5);
                            outputWriterBL5.close();
                            exec("rm "+getFilesDir()+"/graph-series5.csv");
                            exec("mv "+getFilesDir()+"/graph-series5.tmp "+getFilesDir()+"/graph-series5.csv");

                            // remove the header
                            String Sed6 = exec("sed -e 1d "+getFilesDir()+"/graph-series6.csv");
                            FileOutputStream fileoutS6 = openFileOutput("graph-series6.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS6 = new OutputStreamWriter(fileoutS6);
                            outputWriterS6.write(Sed6);
                            outputWriterS6.close();
                            exec("rm "+getFilesDir()+"/graph-series6.csv");
                            exec("mv "+getFilesDir()+"/graph-series6.tmp "+getFilesDir()+"/graph-series6.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort6 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series6.csv");
                            FileOutputStream fileoutG6 = openFileOutput("graph-series6.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG6 = new OutputStreamWriter(fileoutG6);
                            outputWriterG6.write(Sort6);
                            outputWriterG6.close();
                            exec("rm "+getFilesDir()+"/graph-series6.csv");
                            exec("mv "+getFilesDir()+"/graph-series6.tmp "+getFilesDir()+"/graph-series6.csv");
                            // remove blank lines in sorted file
                            String BlankLin6 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series6.csv");
                            FileOutputStream fileoutBL6 = openFileOutput("graph-series6.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL6 = new OutputStreamWriter(fileoutBL6);
                            outputWriterBL6.write(BlankLin6);
                            outputWriterBL6.close();
                            exec("rm "+getFilesDir()+"/graph-series6.csv");
                            exec("mv "+getFilesDir()+"/graph-series6.tmp "+getFilesDir()+"/graph-series6.csv");

                            // remove the header
                            String Sed7 = exec("sed -e 1d "+getFilesDir()+"/graph-series7.csv");
                            FileOutputStream fileoutS7 = openFileOutput("graph-series7.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS7 = new OutputStreamWriter(fileoutS7);
                            outputWriterS7.write(Sed7);
                            outputWriterS7.close();
                            exec("rm "+getFilesDir()+"/graph-series7.csv");
                            exec("mv "+getFilesDir()+"/graph-series7.tmp "+getFilesDir()+"/graph-series7.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort7 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series7.csv");
                            FileOutputStream fileoutG7 = openFileOutput("graph-series7.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG7 = new OutputStreamWriter(fileoutG7);
                            outputWriterG7.write(Sort7);
                            outputWriterG7.close();
                            exec("rm "+getFilesDir()+"/graph-series7.csv");
                            exec("mv "+getFilesDir()+"/graph-series7.tmp "+getFilesDir()+"/graph-series7.csv");
                            // remove blank lines in sorted file
                            String BlankLin7 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series7.csv");
                            FileOutputStream fileoutBL7 = openFileOutput("graph-series7.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL7 = new OutputStreamWriter(fileoutBL7);
                            outputWriterBL7.write(BlankLin7);
                            outputWriterBL7.close();
                            exec("rm "+getFilesDir()+"/graph-series7.csv");
                            exec("mv "+getFilesDir()+"/graph-series7.tmp "+getFilesDir()+"/graph-series7.csv");

                            // remove the header
                            String Sed8 = exec("sed -e 1d "+getFilesDir()+"/graph-series8.csv");
                            FileOutputStream fileoutS8 = openFileOutput("graph-series8.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS8 = new OutputStreamWriter(fileoutS8);
                            outputWriterS8.write(Sed8);
                            outputWriterS8.close();
                            exec("rm "+getFilesDir()+"/graph-series8.csv");
                            exec("mv "+getFilesDir()+"/graph-series8.tmp "+getFilesDir()+"/graph-series8.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort8 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series8.csv");
                            FileOutputStream fileoutG8 = openFileOutput("graph-series8.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG8 = new OutputStreamWriter(fileoutG8);
                            outputWriterG8.write(Sort8);
                            outputWriterG8.close();
                            exec("rm "+getFilesDir()+"/graph-series8.csv");
                            exec("mv "+getFilesDir()+"/graph-series8.tmp "+getFilesDir()+"/graph-series8.csv");
                            // remove blank lines in sorted file
                            String BlankLin8 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series8.csv");
                            FileOutputStream fileoutBL8 = openFileOutput("graph-series8.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL8 = new OutputStreamWriter(fileoutBL8);
                            outputWriterBL8.write(BlankLin8);
                            outputWriterBL8.close();
                            exec("rm "+getFilesDir()+"/graph-series8.csv");
                            exec("mv "+getFilesDir()+"/graph-series8.tmp "+getFilesDir()+"/graph-series8.csv");

                            // remove the header
                            String Sed9 = exec("sed -e 1d "+getFilesDir()+"/graph-series9.csv");
                            FileOutputStream fileoutS9 = openFileOutput("graph-series9.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS9 = new OutputStreamWriter(fileoutS9);
                            outputWriterS9.write(Sed9);
                            outputWriterS9.close();
                            exec("rm "+getFilesDir()+"/graph-series9.csv");
                            exec("mv "+getFilesDir()+"/graph-series9.tmp "+getFilesDir()+"/graph-series9.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort9 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series9.csv");
                            FileOutputStream fileoutG9 = openFileOutput("graph-series9.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG9 = new OutputStreamWriter(fileoutG9);
                            outputWriterG9.write(Sort9);
                            outputWriterG9.close();
                            exec("rm "+getFilesDir()+"/graph-series9.csv");
                            exec("mv "+getFilesDir()+"/graph-series9.tmp "+getFilesDir()+"/graph-series9.csv");
                            // remove blank lines in sorted file
                            String BlankLin9 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series9.csv");
                            FileOutputStream fileoutBL9 = openFileOutput("graph-series9.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL9 = new OutputStreamWriter(fileoutBL9);
                            outputWriterBL9.write(BlankLin9);
                            outputWriterBL9.close();
                            exec("rm "+getFilesDir()+"/graph-series9.csv");
                            exec("mv "+getFilesDir()+"/graph-series9.tmp "+getFilesDir()+"/graph-series9.csv");

                            // remove the header
                            String Sed10 = exec("sed -e 1d "+getFilesDir()+"/graph-series10.csv");
                            FileOutputStream fileoutS10 = openFileOutput("graph-series10.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterS10 = new OutputStreamWriter(fileoutS10);
                            outputWriterS10.write(Sed10);
                            outputWriterS10.close();
                            exec("rm "+getFilesDir()+"/graph-series10.csv");
                            exec("mv "+getFilesDir()+"/graph-series10.tmp "+getFilesDir()+"/graph-series10.csv");
                            // sort file by first column, it must not contain header
                            // sort file by first column, it must not contain header
                            // original command - without extended shell, using Android built in toybox
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // such a command would work in extended shell, not in simple:
                            // String Sort = exec("sort -t',' -k1n,1 "+getFilesDir()+"/graph.csv");
                            // working:
                            String Sort10 = exec("sort -t, -g -k1,1 "+getFilesDir()+"/graph-series10.csv");
                            FileOutputStream fileoutG10 = openFileOutput("graph-series10.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterG10 = new OutputStreamWriter(fileoutG10);
                            outputWriterG10.write(Sort10);
                            outputWriterG10.close();
                            exec("rm "+getFilesDir()+"/graph-series10.csv");
                            exec("mv "+getFilesDir()+"/graph-series10.tmp "+getFilesDir()+"/graph-series10.csv");
                            // remove blank lines in sorted file
                            String BlankLin10 = exec("sed /^[[:space:]]*$/d "+getFilesDir()+"/graph-series10.csv");
                            FileOutputStream fileoutBL10 = openFileOutput("graph-series10.tmp", MODE_PRIVATE);
                            OutputStreamWriter outputWriterBL10 = new OutputStreamWriter(fileoutBL10);
                            outputWriterBL10.write(BlankLin10);
                            outputWriterBL10.close();
                            exec("rm "+getFilesDir()+"/graph-series10.csv");
                            exec("mv "+getFilesDir()+"/graph-series10.tmp "+getFilesDir()+"/graph-series10.csv");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(MainActivity.this, Graph.class);
                        startActivity(intent);

                        onFinish();
                    }
                    public void onFinish() {
                        progressDialog.dismiss();
                    }
                }.start();


            }
        };
    }

    private View.OnClickListener openDatabasefileClick; {

        openDatabasefileClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                read26(getApplicationContext());
                output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
            }
        };
    }

    private View.OnClickListener openInputfileClick; {

        openInputfileClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                read6(getApplicationContext());
                output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
            }
        };
    }

    private View.OnClickListener saveExtInputfileClick; {

        saveExtInputfileClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                write1(getApplicationContext());
                output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
            }
        };
    }

    private View.OnClickListener saveExtOutputfileClick; {

        saveExtOutputfileClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                write2(getApplicationContext());
                output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
            }
        };
    }

    private void read6(Context context6) {
        Intent intent6 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent6.addCategory(Intent.CATEGORY_OPENABLE);
        intent6.setType("text/plain");
        startActivityForResult(intent6, READ_FILE6);
    }

    private void write1(Context context1) {
        Intent intent1 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent1.addCategory(Intent.CATEGORY_OPENABLE);
        intent1.setType("text/plain");
        intent1.putExtra(Intent.EXTRA_TITLE,"MyInputFile");
        startActivityForResult(intent1, CREATE_FILE20);
    }

    private void write2(Context context2) {
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TITLE,"MyOutputFile");
        startActivityForResult(intent2, CREATE_FILE21);
    }

    private void read26(Context context26) {
        Intent intent26 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent26.addCategory(Intent.CATEGORY_OPENABLE);
        intent26.setType("text/plain");
        startActivityForResult(intent26, READ_FILE26);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_FILE6 && data != null) {
            try {
                documentUri6 = data.getData();
                String myData6 = "";
                ParcelFileDescriptor pfd6 = getContentResolver().openFileDescriptor(data.getData(), "r");
                FileInputStream fileInputStream = new FileInputStream(pfd6.getFileDescriptor());
                DataInputStream inp6 = new DataInputStream(fileInputStream);
                BufferedReader br6 = new BufferedReader(new InputStreamReader(inp6));
                String strLine6;
                while ((strLine6 = br6.readLine()) != null) {
                    myData6 = myData6 + strLine6 + "\n";
                }
                inp6.close();

                FileOutputStream fileout6 = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter6 = new OutputStreamWriter(fileout6);
                outputWriter6.write(myData6);
                outputWriter6.close();
                fileInputStream.close();
                pfd6.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "File not read", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == READ_FILE26 && data != null) {
            try {
                documentUri26 = data.getData();
                String myData26 = "";
                ParcelFileDescriptor pfd26 = getContentResolver().openFileDescriptor(data.getData(), "r");
                FileInputStream fileInputStream = new FileInputStream(pfd26.getFileDescriptor());
                DataInputStream inp26 = new DataInputStream(fileInputStream);
                BufferedReader br26 = new BufferedReader(new InputStreamReader(inp26));
                String strLine26;
                while ((strLine26 = br26.readLine()) != null) {
                    myData26 = myData26 + strLine26 + "\n";
                }
                inp26.close();

                FileOutputStream fileout26 = openFileOutput("Database-phreeqc.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter26 = new OutputStreamWriter(fileout26);
                outputWriter26.write(myData26);
                outputWriter26.close();
                fileInputStream.close();
                pfd26.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "File not read", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == CREATE_FILE20 && data != null) {
            // save input file
            Toast.makeText(getApplicationContext(), "File successfully created", Toast.LENGTH_SHORT).show();
            try {
                String fileContents20X = InputFile.getText().toString();
                FileOutputStream fileout20 = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter20 = new OutputStreamWriter(fileout20);
                outputWriter20.write(fileContents20X + "\n");
                outputWriter20.close();

                documentUri20 = data.getData();
                ParcelFileDescriptor pfd20 = getContentResolver().openFileDescriptor(data.getData(), "w");
                FileOutputStream fileOutputStream20 = new FileOutputStream(pfd20.getFileDescriptor());
//                String fileContents20 = InputFile.getText().toString();
                String fileContents20 = exec("cat "+getFilesDir()+"/Input-phreeqc.txt");
                fileOutputStream20.write((fileContents20 + "\n").getBytes());
                fileOutputStream20.close();
                pfd20.close();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "File not written", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == CREATE_FILE21 && data != null) {
            // save output file
            Toast.makeText(getApplicationContext(), "File successfully created", Toast.LENGTH_SHORT).show();
            try {
                documentUri21 = data.getData();
                ParcelFileDescriptor pfd21 = getContentResolver().openFileDescriptor(data.getData(), "w");
                FileOutputStream fileOutputStream21 = new FileOutputStream(pfd21.getFileDescriptor());
                String fileContents21 = outputView2.getText().toString();
                fileOutputStream21.write((fileContents21 + "\n").getBytes());
                fileOutputStream21.close();
                pfd21.close();
                FileOutputStream fileout21 = openFileOutput("Input.out", MODE_PRIVATE);
                OutputStreamWriter outputWriter21 = new OutputStreamWriter(fileout21);
                outputWriter21.write(fileContents21 + "\n");
                outputWriter21.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "File not written", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private View.OnClickListener searchDatabaseClick; {

        searchDatabaseClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alertSearchDatabase();
            }
        };
    }


    public void alertSearchDatabase(){
        // creating the EditText widget programatically
        EditText editText10 = new EditText(MainActivity.this);
        editText10.setTextSize(Integer.valueOf(exec("cat "+getFilesDir()+"/InputTextSize.txt")).intValue());
        editText10.setTypeface(Typeface.MONOSPACE);
        editText10.addTextChangedListener(new TextWatcher() {
            int startChanged,beforeChanged,countChanged;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startChanged = start;
                beforeChanged = before;
                countChanged = count;
            }
            @Override
            public void afterTextChanged(Editable s) {
                editText10.removeTextChangedListener(this);
                String text = editText10.getText().toString();
                // important - not setText() - otherwise the keyboard would be reset after each type
                editText10.getText().clear();
                editText10.append(colorized_phreeqc(text));
                // place the cursor at the original position
                editText10.setSelection(startChanged+countChanged);
                editText10.addTextChangedListener(this);
            }
        });
        // create the AlertDialog as final
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("The result search will be displayed in the output text view.")
                .setTitle("Word search in the selected database")
                .setView(editText10)

                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String SearchedString = editText10.getText().toString();
                        com.jrummyapps.android.shell.Shell.SH.run("cd "+getFilesDir()+"/ ; grep -a -C 3 '"+SearchedString+"' Database-phreeqc.txt > Input.out");
                        output2(exec("cat "+getFilesDir()+"/Input.out"));
                        output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                        output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })
                .create();

        // set the focus change listener of the EditText10
        // this part will make the soft keyboard automatically visible
        editText10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();

    }

    private View.OnClickListener viewDatabaseClick; {

        viewDatabaseClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                com.jrummyapps.android.shell.Shell.SH.run("cd "+getFilesDir()+"/ ; cat Database-phreeqc.txt > Input.out");
                output2(exec("cat "+getFilesDir()+"/Input.out"));
                output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
            }
        };
    }

    private View.OnClickListener saveInputfileClick; {

        saveInputfileClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alertSaveInput();
                output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
            }
        };
    }


    public void alertSaveInput(){
        // creating the EditText widget programatically
        EditText editText10 = new EditText(MainActivity.this);
        editText10.setTextSize(Integer.valueOf(exec("cat "+getFilesDir()+"/InputTextSize.txt")).intValue());
        editText10.setTypeface(Typeface.MONOSPACE);
        editText10.addTextChangedListener(new TextWatcher() {
            int startChanged,beforeChanged,countChanged;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startChanged = start;
                beforeChanged = before;
                countChanged = count;
            }
            @Override
            public void afterTextChanged(Editable s) {
                editText10.removeTextChangedListener(this);
                String text = editText10.getText().toString();
                // important - not setText() - otherwise the keyboard would be reset after each type
                editText10.getText().clear();
                editText10.append(colorized_phreeqc(text));
                // place the cursor at the original position
                editText10.setSelection(startChanged+countChanged);
                editText10.addTextChangedListener(this);
            }
        });
        // create the AlertDialog as final
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("The file will be saved in the folder /data/data/cz.jh.phreeqc/files/phreeqc/work")
                .setTitle("Please write the desired filename (if already present, it will be overwritten)")
                .setView(editText10)

                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String Inputfile = InputFile.getText().toString();
                        String SaveInputName = editText10.getText().toString();
                        try {
                            FileOutputStream fileout = openFileOutput(SaveInputName, MODE_PRIVATE);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                            outputWriter.write(Inputfile+"\n");
                            outputWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        exec("mv "+getFilesDir()+"/"+SaveInputName+" "+getFilesDir()+"/phreeqc/work");
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })
                .create();

        // set the focus change listener of the EditText10
        // this part will make the soft keyboard automatically visible
        editText10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();

    }



    private View.OnClickListener RunProgramClick; {

        RunProgramClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                String Inputfile = InputFile.getText().toString();
                try {
                    FileOutputStream fileout = openFileOutput("Input-phreeqc.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(Inputfile);
                    outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
// perform the calculation
                // TODO Auto-generated method stub //
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Please wait...");
                progressDialog.setMessage("PHREEQC calculation is in progress...");
                progressDialog.setCancelable(false);
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialog.show();

                new Thread() {
                    public void run() {
                        com.jrummyapps.android.shell.Shell.SH.run("export HOME="+getFilesDir()+" ; cd $HOME ; "+getApplicationInfo().nativeLibraryDir+"/libphreeqc.so Input-phreeqc.txt Input.out Database-phreeqc.txt ; cp Input.out LastExecutionOutput.txt");
                        output2(exec("cat "+getFilesDir()+"/Input.out"));
                        output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                        output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
                        onFinish();
                    }
                    public void onFinish() {
                        progressDialog.dismiss();
                    }
                }.start();

            }
        };
    }

















    private View.OnClickListener saveOutputfileClick; {

        saveOutputfileClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                alertSaveOutput();
                output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
            }
        };
    }





    public void alertSaveOutput(){
        // creating the EditText widget programatically
        EditText editText15 = new EditText(MainActivity.this);
        editText15.setTextSize(Integer.valueOf(exec("cat "+getFilesDir()+"/InputTextSize.txt")).intValue());
        editText15.setTypeface(Typeface.MONOSPACE);
        editText15.addTextChangedListener(new TextWatcher() {
            int startChanged,beforeChanged,countChanged;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startChanged = start;
                beforeChanged = before;
                countChanged = count;
            }
            @Override
            public void afterTextChanged(Editable s) {
                editText15.removeTextChangedListener(this);
                String text = editText15.getText().toString();
                // important - not setText() - otherwise the keyboard would be reset after each type
                editText15.getText().clear();
                editText15.append(colorized_phreeqc(text));
                // place the cursor at the original position
                editText15.setSelection(startChanged+countChanged);
                editText15.addTextChangedListener(this);
            }
        });
        // create the AlertDialog as final
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("The file will be saved in the folder /data/data/cz.jh.phreeqc/files/phreeqc/work")
                .setTitle("Please write the desired filename (if already present, it will be overwritten)")
                .setView(editText15)

                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String OutputProtocol = outputView2.getText().toString();
                        String SaveOutputName = editText15.getText().toString();
                        try {
                            FileOutputStream fileout = openFileOutput(SaveOutputName, MODE_PRIVATE);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                            outputWriter.write(OutputProtocol);
                            outputWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        exec("mv "+getFilesDir()+"/"+SaveOutputName+" "+getFilesDir()+"/phreeqc/work");
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })
                .create();

        // set the focus change listener of the EditText10
        // this part will make the soft keyboard automatically visible
        editText15.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        dialog.show();

    }















    private View.OnClickListener HighlightClick; {

        HighlightClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                openhighlightdialog();
            }
        };
    }


    private void openhighlightdialog() {
        // TODO Auto-generated method stub //
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Highlighting numbers is in progress...");
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        progressDialog.show();

        new Thread() {
            public void run() {
                try {
                    outputX(exec("cat "+getFilesDir()+"/LastExecutionOutput.txt"));
                    output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
                    output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
                } catch (Exception e) {
                }

                onFinish();
            }
            public void onFinish() {
                progressDialog.dismiss();
            }
        }.start();
    }


    private View.OnClickListener AboutClick; {

        AboutClick = new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub //
                alertAbout();
            }
        };
    }

    public void alertAbout() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("About the PHREEQC app")
                .setMessage(exec("cat "+getFilesDir()+"/About.txt"))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        output3(exec("cat "+getFilesDir()+"/Input-phreeqc.txt"));
        output4(exec("cat "+getFilesDir()+"/Database-name.txt"));
    //    restoreGraphFiles();
    }

    public void restoreGraphFiles(){
        exec("rm "+getFilesDir()+"/graph-series1.csv");
        exec("rm "+getFilesDir()+"/graph-series2.csv");
        exec("rm "+getFilesDir()+"/graph-series3.csv");
        exec("rm "+getFilesDir()+"/graph-series4.csv");
        exec("rm "+getFilesDir()+"/graph-series5.csv");
        exec("rm "+getFilesDir()+"/graph-series6.csv");
        exec("rm "+getFilesDir()+"/graph-series7.csv");
        exec("rm "+getFilesDir()+"/graph-series8.csv");
        exec("rm "+getFilesDir()+"/graph-series9.csv");
        exec("rm "+getFilesDir()+"/graph-series10.csv");
        exec("touch "+getFilesDir()+"/graph-series1.csv");
        exec("touch "+getFilesDir()+"/graph-series2.csv");
        exec("touch "+getFilesDir()+"/graph-series3.csv");
        exec("touch "+getFilesDir()+"/graph-series4.csv");
        exec("touch "+getFilesDir()+"/graph-series5.csv");
        exec("touch "+getFilesDir()+"/graph-series6.csv");
        exec("touch "+getFilesDir()+"/graph-series7.csv");
        exec("touch "+getFilesDir()+"/graph-series8.csv");
        exec("touch "+getFilesDir()+"/graph-series9.csv");
        exec("touch "+getFilesDir()+"/graph-series10.csv");
    }

    public void output2(final String str2) {
        Runnable proc2 = new Runnable() {
            public void run() {
                outputView2.setText(str2);
            }
        };
        handler.post(proc2);
    }
    // for displaying the output in the second TextView there must be different output3 than output, including the str3/proc3 variables
    public void output3(final String str3) {
        Runnable proc3 = new Runnable() {
            public void run() {
                InputFile.setText(colorized_phreeqc(str3),EditText.BufferType.SPANNABLE);
            }
        };
        handler.post(proc3);
    }
    public void output4(final String str4) {
        Runnable proc4 = new Runnable() {
            public void run() {
                Database.setText(str4);
            }
        };
        handler.post(proc4);
    }
    // for displaying the output in the second TextView there must be different output2 than output, including the str2/proc2 variables
    public void outputX(final String strX) {
        Runnable procX = new Runnable() {
            public void run() {
                outputView2.setText(colorized_phreeqc(strX),EditText.BufferType.SPANNABLE);
            }
        };
        handler.post(procX);
    }

    // Executes UNIX command.
    private String exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[65536];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    protected void copyFromAssetsToInternalStorage(String filename){
        AssetManager assetManager = getAssets();

        try {
            InputStream input = assetManager.open(filename);
            OutputStream output = openFileOutput(filename, Context.MODE_PRIVATE);

            copyFile2(input, output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFile2(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[65536];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public void unzip(File source) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {

            ZipEntry entry = zis.getNextEntry();
            File outPath = new File(getFilesDir()+"");

            while (entry != null) {
                File file = new File(outPath, entry.getName());
                String canonicalPath = file.getCanonicalPath();
                if (canonicalPath.startsWith(outPath.getCanonicalPath() + File.separator)) {
//                    File verifiedFile = new File(canonicalPath,entry.getName());
//                    continue;
                    if (entry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        File parent = file.getParentFile();

                        if (!parent.exists()) {
                            parent.mkdirs();
                        }

                        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {

                            int bufferSize = Math.toIntExact(entry.getSize());
                            byte[] buffer = new byte[bufferSize > 0 ? bufferSize : 1];
                            int location;

                            while ((location = zis.read(buffer)) != -1) {
                                bos.write(buffer, 0, location);
                            }
                        }
                    }
                    entry = zis.getNextEntry();
                } else {
                    throw new IllegalStateException("Unreachable location");
                }
            }
        }
    }

}