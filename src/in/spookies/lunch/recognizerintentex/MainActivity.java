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

// �������͂𕶎��ϊ����ĕ\������A�v��

public class MainActivity extends Activity
{
    private static final int REQUEST = 0;               // ���N�G�X�g�R�[�h

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // ���C�A�E�g�̍쐬
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.rgb(255,255,255));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        // �{�^���̐���
        Button  button;
        button = new Button(this);
        button.setText("�F���J�n");
        setMyLayoutParams(button);
        layout.addView(button);

        // �{�^���C�x���g����
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    // �C���e���g�쐬
                    // ���͂�����������͂���B
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); 
                    // free-form speech recognition.
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    // �\�������镶����
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                                                "�����𕶎��ŏo�͂��܂�");
                    // �C���e���g�J�n
                    startActivityForResult(intent, REQUEST);
                } catch (ActivityNotFoundException e) {
                    // �A�N�e�B�r�e�B��������Ȃ�����
                    Toast.makeText(MainActivity.this,
                        "�A�N�e�B�r�e�B��������܂���ł����B", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    
    // �A�N�e�B�r�e�B�I��
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // �������������C���e���g�ł���Ή�������
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            String speakedString = "";
            
            // ���ʕ����񃊃X�g
            // �����̕�����F�������ꍇ�C�������ďo��
            ArrayList<String> speechToChar = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            
            for (int i = 0; i< speechToChar.size(); i++) {
                speakedString += speechToChar.get(i);
            }

            //�@�������Z�������ꍇ�󔒕����Ńp�f�B���O
            for (int i = (20-speakedString.length()); i>0; i--)
                speakedString += " ";
           
            // �F�����ʂ��_�C�A���O�\��
                showDialog(this, "", speakedString);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // �_�C�A���O�̕\��
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
    
    // ���C�A�E�g�̃p�����[�^��ݒ肷��
    private static void setMyLayoutParams(View view)
    {
        view.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}