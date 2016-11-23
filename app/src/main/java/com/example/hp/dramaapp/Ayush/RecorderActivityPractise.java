package com.example.hp.dramaapp.Ayush;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.dramaapp.R;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Decoder;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.Segment;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class RecorderActivityPractise extends Activity implements
        RecognitionListener {

    int top = -1;

    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String DIGITS_SEARCH = "digits";
    private static final String PHONE_SEARCH = "phones";
    private static final String MENU_SEARCH = "menu";
    Button b, b1;
    Decoder d;
    float totalScore = 0;
    float maxValueOfSentance = -1449;
    float minValueOfSentence = -4000;
    float eachWordMinValue = -1500;
    float averagePercentageEachWord = 0;
    Toast recordingToast;
    int countTheButtonClick = 1;
    TextView nextTextView;
    String[] models /*= {
            "WHAT IS YOUR NAME",
            "NICE TO MEET YOU REKHA",
            "MY NAME IS RAVI",
            "SAME HERE",
            "NO I AM NOT",
            "I AM FROM MUMBAI",
            "WHERE ARE YOU FROM",
            "WHICH TOWN",
            "IS IT CLOSE TO JAIPUR",
            "I HAVE TO GO NOW",
            "GREAT TALKING TO YOU"
    }*/;

    String[] inputSentence /*= {
            "WHAT IS YOUR NAME",
            "NICE TO MEET YOU REKHA",
            "MY NAME IS RAVI",
            "SAME HERE",
            "NO I AM NOT",
            "I AM FROM MUMBAI",
            "WHERE ARE YOU FROM",
            "WHICH TOWN",
            "IS IT CLOSE TO JAIPUR",
            "I HAVE TO GO NOW",
            "GREAT TALKING TO YOU"}*/;
    static int[][] eachInputWordProb /*= {
            {
                    -5691, -1948, -4257, -5510
            },
            {
                    -6669, -1932, -5164, -3389, -7864
            },
            {
                    -3878, -4570, -1980, -10132
            },
            {
                    -7267, -5954
            },
            {
                    -5547, -915, -3615, -6151
            },
            {
                    -1036, -4378, -3937, -11175
            },
            {
                    -5427, -4377, -2547, -5405
            },
            {
                    -4252, -6187
            },
            {
                    -3070, -1355, -6316, -2423, -10013
            },
            {
                    -2261, -4709, -4334, -3996, -6318
            },
            {
                    -7766, -7092, -3014, -4702
            }


    }*/;
    String actor, actorScore, playName;
    int valueFromIntent = 0;
    int wordLengthInputSentence = 0;
    RelativeLayout itemsLayout, recordLayout, loadingLayout;
    ListView listviewItems;
    File newassetDirCopy;
    ProgressBar progressBar;
    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "oh mighty computer";

    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private SpeechRecognizer recognizer;
    private HashMap<String, Integer> captions;
    TextView textViewHtml;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        System.out.print("Hello");
        // Prepare the data for UI

        setContentView(R.layout.recordinglayoutpractise);
        init();
        System.out.println(models.length);
        System.out.println(inputSentence.length);
        eachInputWordProb = new int[8][15];
        for (int i = 0; i < inputSentence.length; i++) {
            String[] strSplit = inputSentence[i].trim().split(" ");
            int j = 0;
            for (String str : strSplit) {
                eachInputWordProb[i][j] = str.length() * -100;
                j++;
            }
        }

        captions = new HashMap<String, Integer>();
        captions.put(FORECAST_SEARCH, R.string.app_name);

        ((TextView) findViewById(R.id.dialoguetext))
                .setText("Wait....");

        itemsOnCreate();
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }
        int permissionCheckStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheckStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }

        runRecognizerSetup();
    }

    ImageButton recordingButton;

    void init() {
        recordingButton = (ImageButton) findViewById(R.id.recordingButton);
        recordingButton.setBackgroundColor(Color.TRANSPARENT);
        progressBar = (ProgressBar) findViewById(R.id.seekBar);
        nextTextView = (TextView) findViewById(R.id.nextButton);
        Bundle bund = getIntent().getExtras();
        actor = bund.getString("Actor");
        actorScore = bund.getString("ProfilePercentage");
        playName = bund.getString("Play");
        System.out.println(bund.getStringArrayList("Dialogues").toString());
        models = bund.getStringArrayList("Dialogues").toArray(new String[bund.getStringArrayList("Dialogues").size()]);
        textViewHtml = ((TextView) findViewById(R.id.dialoguetextOutput));
        inputSentence = bund.getStringArrayList("Dialogues").toArray(new String[bund.getStringArrayList("Dialogues").size()]);
        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueFromIntent++;
                progressBar.setProgress(valueFromIntent * (100 / 8));
                totalScore += (averagePercentageEachWord / wordLengthInputSentence);
                textViewHtml.setVisibility(View.INVISIBLE);
                try {
                    ((TextView) findViewById(R.id.dialoguetext)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_slide_in));
                    ((TextView) findViewById(R.id.dialoguetext)).setText(models[valueFromIntent]);
                    File menuGrammar = new File(newassetDirCopy, "menu" + playName + actor + valueFromIntent + ".gram");
                    recognizer.addGrammarSearch(FORECAST_SEARCH, menuGrammar);
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), )
                    Toast.makeText(getApplicationContext(), totalScore + "", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RecorderActivityPractise.this, ResultActivity.class);
                    intent.putExtra("ProfilePercentage", actorScore);
                    intent.putExtra("DialoguePercent", ((float) (totalScore / 8)) + "");
                    intent.putExtra("Actor", actor);
                    startActivity(intent);
                }
            }
        });

    }

    public void recordingOnCreate() {
        recordingButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wordLengthInputSentence = (inputSentence[valueFromIntent].split(" ")).length;

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    micImageOnClickOperationRecording();


                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    averagePercentageEachWord = 0;
                    micImageOnClickOperationRecording();
                }


                return true;

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void itemsOnCreate() {

        recordingOnCreate();

    }

    public void micImageOnClickOperationRecording() {
        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(
                Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);

        if (countTheButtonClick == 1) {
            // runRecognizerSetup();
            recognizer.startListening(FORECAST_SEARCH, 1000);
            countTheButtonClick = 0;
            //  deleteFile();
            top++;
            if (recognizer == null) {
                Toast.makeText(getApplicationContext(), "Device not Ready-1", Toast.LENGTH_SHORT).show();
                countTheButtonClick = 1;
            } else {

                try {
                    recordingToast.cancel();
                } catch (Exception e) {

                }
                recordingToast = Toast.makeText(getApplicationContext(),
                        "Recording Start", Toast.LENGTH_SHORT);
                recordingToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                recordingToast.show();
            }
        } else {

            //   ((TextView) findViewById(R.id.result_text)).setText(stringtext);

            try {
                recordingToast.cancel();
            } catch (Exception e) {

            }

            recordingToast = Toast.makeText(getApplicationContext(),
                    "Recording Stopped", Toast.LENGTH_SHORT);
            recordingToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            recordingToast.show();

            countTheButtonClick = 1;
            if (recognizer != null) {
                recognizer.stop();
            } else {
                Toast.makeText(getApplicationContext(), "Device not Ready-2", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void startPlaying(String filepath) {

        int minBufferSize = AudioTrack.getMinBufferSize(16000,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, 16000,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, minBufferSize,
                AudioTrack.MODE_STREAM);

        int i = 0;
        byte[] temp = new byte[minBufferSize];

        try {
            FileInputStream fin = new FileInputStream(filepath);
            Log.d("hi", "===== Opening File for Playing : /sdcard/audiofile.pcm ===== ");
            //FileOutputStream fout=new FileOutputStream()
            DataInputStream dis = new DataInputStream(fin);

            track.play();
            while ((i = dis.read(temp, 0, minBufferSize)) > -1) {
                track.write(temp, 0, i);
            }


            Log.d("hi", "===== Playing Audio Completed ===== ");
            track.stop();
            track.release();
            dis.close();
            fin.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void playmp3() {

        startPlaying((Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android/data/com.ayush.languagemodel/files/sync/" + "00000000" + top + ".raw"));

        //set up MediaPlayer
//        MediaPlayer mp = new MediaPlayer();
//
//        try {
//            mp.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Android/data/com.ayush.languagemodel/files/sync/"+"000000000.raw" );
//            mp.prepare();
//            mp.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(RecorderActivityPractise.this);
                    File assetDir = assets.syncAssets();
                    // assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    ((TextView) findViewById(R.id.dialoguetext))
                            .setText("Failed to init recognizer " + result);
                } else {
                    //   switchSearch(KWS_SEARCH);
                    ((TextView) findViewById(R.id.dialoguetext)).setText(models[valueFromIntent]);
                    File menuGrammar = new File(newassetDirCopy, "menu" + playName + actor + valueFromIntent + ".gram");
                    recognizer.addGrammarSearch(FORECAST_SEARCH, menuGrammar);
                    recordingButton.setEnabled(true);

                }
            }
        }.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                runRecognizerSetup();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    // float sum = 0;
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();
        // ((TextView)findViewById(R.id.partical_text)).setText(text+hypothesis.getBestScore());
        // sum = sum + hypothesis.getBestScore();
        if (text.equals(FORECAST_SEARCH))
            switchSearch(FORECAST_SEARCH);
        //else
        //  ((TextView)findViewById(R.id.partical_text)).setText(text+hypothesis.getBestScore());
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
//        ((TextView) findViewById(R.id.result_text)).setText("Not Found");
//        ((TextView) findViewById(R.id.prob_text)).setText("Not Found");
//        ((TextView) findViewById(R.id.partical_text)).setText("Not Found");
//        if ((hypothesis != null)) {
//
//            String text = hypothesis.getHypstr();
//
//            ((TextView) findViewById(R.id.result_text)).setText(text);
//
//            ((TextView) findViewById(R.id.prob_text)).setText("Best Score=" + hypothesis.getBestScore());
//            decoderFunction();
//            String[] split = text.split(" ");
//            int length = split.length;
//            // System.out.print("Length " + length);
////            float evaValue=maxValue*(float)length/4;
////            percentage=(float)(((float)hypothesis.getBestScore()-(-4000))/(float)((evaValue)-(-4000)))*100;
//            ((TextView) findViewById(R.id.partical_text)).setText(evaluatePercetage(hypothesis.getBestScore(), length));
//            // Toast.makeText(getApplicationContext(),hypothesis.getProb()+" "+hypothesis.getBestScore(),Toast.LENGTH_LONG).show();
//            //onBothResult(aListNumbers);


        // }
        if ((hypothesis != null)) {
            System.out.println(hypothesis.getHypstr());
            decoderFunction();


        }
        //online=0;
    }

    void decoderFunction() {
        d = recognizer.getDecoder();
        String valueName[];
        int segmentLength = 0;
        for (Segment seg : d.seg()) {
            System.out.println(seg.getWord());
            System.out.println(seg.getAscore());
            System.out.println(seg.getLscore());
            segmentLength++;
        }

        float bestScoreWithOutSilence = 0;
        float silenceScore = 0;
        int outputSentenceWordCount = 0;
        int extraDetailsValues = 5;

        valueName = new String[segmentLength + extraDetailsValues];
        String[] probValue = new String[segmentLength + extraDetailsValues];

        int[] eachOutputWordProb = new int[d.hyp().getHypstr().split(" ").length];

        segmentLength = extraDetailsValues;

        for (Segment seg : d.seg()) {
            if (!(seg.getWord().contains("<sil>") || seg.getWord().equalsIgnoreCase("(NULL)"))) {
                bestScoreWithOutSilence += (seg.getAscore() + seg.getLscore());
                eachOutputWordProb[outputSentenceWordCount] = (seg.getAscore() + seg.getLscore());
                outputSentenceWordCount++;
            } else {
                silenceScore += (seg.getAscore() + seg.getLscore());
            }

            valueName[segmentLength] = seg.getWord();
            probValue[segmentLength] = (seg.getAscore() + seg.getLscore()) + "";
            segmentLength++;


        }

        eachWordValueDisplay(d.hyp().getHypstr(), eachOutputWordProb);

        valueName[2] = "Score without Sil  ";
        valueName[1] = "The silence Score  ";
        valueName[0] = "Average Score";
        valueName[3] = "Normalize Percentage";
        valueName[4] = "Percentage w/o sil";

        probValue[2] = bestScoreWithOutSilence + "";
        probValue[1] = silenceScore + "";
        probValue[0] = (averagePercentageEachWord / wordLengthInputSentence) + "";
        probValue[3] = normalizePercetage(
                Float.parseFloat(evaluatePercetage((int) bestScoreWithOutSilence, outputSentenceWordCount)),
                outputSentenceWordCount
        );
        System.out.println("Total Score" + totalScore);
        probValue[4] = evaluatePercetage((int) bestScoreWithOutSilence, outputSentenceWordCount);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setVisibility(View.GONE);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PocketSphinxActivity_Grammer_SilenceRemover.this, android.R.layout.simple_list_item_1, s);
//        listView.setAdapter(adapter);

//String[] wordArray=wordColorProbArray(d.hyp().getHypstr(),wordProb);
//        String[] probArray = wordColorWordArray(d.hyp().getHypstr(),wordProb);
        adapterWordsListView customAdapter = new adapterWordsListView(this,
                valueName,
                probValue
        );
        listView.setAdapter(customAdapter);


    }

    void eachWordValueDisplay(String outputSentence, int[] eachOutputWordProb) {
        String[] inputSentenceEachWord = inputSentence[valueFromIntent].replaceAll("[^A-Za-z ]","").split(" ");
        String[] outputSentenceEachWord = outputSentence.split(" ");
        String[] word = new String[outputSentenceEachWord.length];
        String[] percent = new String[outputSentenceEachWord.length];
        String[] myProbValue = new String[outputSentenceEachWord.length];
        String[] alexProvValue = new String[outputSentenceEachWord.length];

        for (int i = 0; i < outputSentenceEachWord.length; i++) {
            for (int j = 0; j < inputSentenceEachWord.length; j++) {
                if (inputSentenceEachWord[j].equalsIgnoreCase(outputSentenceEachWord[i])) {
                    //color[i]=Float.parseFloat(evaluatePercetageWord(wordProb[i], eachWordProb[j]));
                    //color[i]=(splitStringMain[i] + color[i]);
                    word[i] = inputSentenceEachWord[j];
                    percent[i] = Float.parseFloat(evaluatePercetageWord(eachOutputWordProb[i], eachInputWordProb[valueFromIntent][j])) + "";
                    myProbValue[i] = eachOutputWordProb[i] + "";
                    alexProvValue[i] = eachInputWordProb[valueFromIntent][j] + "";
                }
            }
        }
        System.out.println(Arrays.asList(word));
        System.out.println(Arrays.asList(percent));
        ListView listView1 = (ListView) findViewById(R.id.listView1);
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(PocketSphinxActivity_Grammer_SilenceRemover.this, android.R.layout.simple_list_item_1, color);
//        listView1.setAdapter(adapter1);

        try {
            settingTextColor(word, percent);
            adapterWordsListViewColor customAdapter = new adapterWordsListViewColor(this,
                    word,
                    percent,
                    myProbValue,
                    alexProvValue
            );
            listView1.setAdapter(customAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }

        //return color;
    }

    public String evaluatePercetageWord(int obtainScore, int inputMax) {
        float calculatePercentage = 0;

        calculatePercentage = (float) (((float) obtainScore - (inputMax + eachWordMinValue)) / (float) ((inputMax) - (inputMax + eachWordMinValue))) * 100;
        averagePercentageEachWord += calculatePercentage;
        return calculatePercentage + "";
    }

    public String evaluatePercetage(int bestScore, int length) {
        float newPercentage = 0;
        float evaValue_max = maxValueOfSentance * (float) length / wordLengthInputSentence;
        float evaValue_min = minValueOfSentence * (float) length / wordLengthInputSentence;

        newPercentage = (float) (((float) bestScore - (evaValue_min)) / (float) ((evaValue_max) - (evaValue_min))) * 100;

        return newPercentage + "";
    }

    public String normalizePercetage(float percentage, int outputSentenceWordCount) {
        float normalizePercent = (percentage * outputSentenceWordCount) / wordLengthInputSentence;

        return normalizePercent + "";
    }


    @Override
    public void onBeginningOfSpeech() {

    }

    void deleteFile() {

        try {
            for (int i = 0; ; i++) {
                File file = new File(((Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android/data/com.ayush.languagemodel/files/sync/" + "00000000" + i + ".raw")));
                boolean deleted = file.delete();
            }
        } catch (Exception e) {
            //   break;
        }

    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(FORECAST_SEARCH))
            switchSearch(FORECAST_SEARCH);
    }

    private void switchSearch(String searchName) {
        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals(FORECAST_SEARCH))
            recognizer.startListening(searchName);
        else
            recognizer.startListening(searchName, 10000);

//		String caption = getResources().getString(captions.get(searchName));
//		((TextView) findViewById(R.id.caption_text)).setText(caption);
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setKeywordThreshold(1e-40f) // Threshold to tune for keyphrase to balance between false alarms and misses
                .setBoolean("-allphone_ci", true)  // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setSampleRate(16000)
                .getRecognizer();

        recognizer.addListener(this);


        newassetDirCopy = assetsDir;
        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        //    recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);

//        // Create grammar-based search for selection between demos
//        File menuGrammar = new File(assetsDir, "menu" + valueFromIntent + ".gram");
//        recognizer.addGrammarSearch(FORECAST_SEARCH, menuGrammar);

        // Create grammar-based search for digit recognition
        //File digitsGrammar = new File(assetsDir, "digits.gram");
//        recognizer.addGrammarSearch(DIGITS_SEARCH, digitsGrammar);

        // Create language model search
//        File languageModel = new File(assetsDir, "conv.lm.dmp");
//     recognizer.addNgramSearch(FORECAST_SEARCH, languageModel);

        // Phonetic search
//        File phoneticModel = new File(assetsDir, "phones.lm.DMP");
//        recognizer.addAllphoneSearch(FORECAST_SEARCH, phoneticModel);

    }

    @Override
    public void onError(Exception error) {
        ((TextView) findViewById(R.id.caption_text)).setText(error.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch(FORECAST_SEARCH);
    }

    void settingTextColor(String[] word, String[] percent) {
        String colorString = "";
        for (int i = 0; i < percent.length; i++) {
            float valueProb = Float.parseFloat(percent[i]);
            if (valueProb > 85) {

                colorString = colorString + "<font color=#196F3D>" + word[i] + " " + "</font>";
            } else if (valueProb > 60) {
                //txtTitle.setTextColor(Color.HSVToColor(new float[]{(float) 120f, 1f, 1f}));
                colorString = colorString + "<font color=#82E0AA>" + word[i] + " " + "</font>";
            } else if (valueProb > 30) {
                colorString = colorString + "<font color=#F4D03F >" + word[i] + " " + "</font>";
            } else {
                colorString = colorString + "<font color=#C0392B  >" + word[i] + " " + "</font>";
            }
        }
        System.out.println(colorString);

        textViewHtml.setVisibility(View.VISIBLE);
        textViewHtml.setText(Html.fromHtml(colorString));

    }

}

