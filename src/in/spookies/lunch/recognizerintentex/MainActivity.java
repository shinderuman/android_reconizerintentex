package in.spookies.lunch.recognizerintentex;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

// 音声入力を文字変換して表示するアプリ

public class MainActivity extends Activity
{
    private static final int REQUEST = 0;               // リクエストコード

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // レイアウトの作成
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.rgb(255,255,255));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        // ボタンの生成
        Button  button;
        button = new Button(this);
        button.setText("認識開始");
        setMyLayoutParams(button);
        layout.addView(button);

        // ボタンイベント処理
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    // インテント作成
                    // 入力した音声を解析する。
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); 
                    // free-form speech recognition.
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    // 表示させる文字列
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                                                "音声を文字で出力します");
                    // インテント開始
                    startActivityForResult(intent, REQUEST);
                } catch (ActivityNotFoundException e) {
                    // アクティビティが見つからなかった
                    Toast.makeText(MainActivity.this,
                        "アクティビティが見つかりませんでした。", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    
    // アクティビティ終了
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 自分が投げたインテントであれば応答する
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            String speakedString = "";
            
            // 結果文字列リスト
            // 複数の文字を認識した場合，結合して出力
            ArrayList<String> speechToChar = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            
            for (int i = 0; i< speechToChar.size(); i++) {
                speakedString += speechToChar.get(i);
            }

            //　文字が短かった場合空白文字でパディング
            for (int i = (20-speakedString.length()); i>0; i--)
                speakedString += " ";
           
            // 認識結果をダイアログ表示
                showDialog(this, "", speakedString);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // ダイアログの表示
    private static void showDialog(final Activity activity,
                String title, String text)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setTitle(title);
        ad.setMessage(text);
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                        activity.setResult(Activity.RESULT_OK);
                }
        });
        ad.create();
        ad.show();
    }
    
    // レイアウトのパラメータを設定する
    private static void setMyLayoutParams(View view)
    {
        view.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}