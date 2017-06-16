---
title: SpeechRecognizer で音声入力を実現する
created: 2014-01-23
---

SpeechRecognizer の基本
----

Android 2.2 Froyo (API Level 8) で追加された SpeechRecognizer を使うと、UI を表示せずにバックグラウンドで音声認識を行えます。
以下のような手順で使用します。

1. `SpeechRecognizer.createSpeechRecognizer()` を使ってインスタンス作成。
2. `SpeechRecognizer#setRecognitionListener()` を使って `RecognitionListner` を登録。
3. `SpeechRecognizer#startListening()` で音声入力の開始。
4. あとは `RecognitionListener` のコールバックを適宜処理。
5. `SpeechRecognizer#cancel()` で音声入力を終了。

#### コード抜粋

~~~ java
// import android.speech.RecognitionListener;
// import android.speech.SpeechRecognizer;

private SpeechRecognizer mRecognizer;
private RecognitionListener mRecognitionListener = new RecognitionListener() {
    //...
};

private void startSpeechRecognition() {
     mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
     mRecognizer.setRecognitionListener(mRecognitionListener);
     mRecognizer.startListening(new Intent());
}
~~~

SpeechRecognizer を利用するには、`RECORD_AUDIO` パーミッションを付加しておく必要があります。
パーミッションが付加されていない場合、`SpeechRecognizer#startListening()` 実行時に、`RecognitionListener#onError(int error)` で、`SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS (9)` が報告されます。

#### AndroidManifest.xml

~~~ xml
<?xml version="1.0" encoding="utf-8"?>
<manifest ...>
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    ...
</manifest>
~~~

RecognitionListener によるイベント処理
----

`SpeechRecognizer#startListening()` を実行すると、以下のようなイベントを捕捉できるようになります。

- `onReadyForSpeech` -- `SpeechRecognizer#startListening()` すると呼び出される
- `onBeginningOfSpeech` -- マイクに向かってしゃべり始めると呼び出される
- `onEndOfSpeech` -- しゃべり終わると呼び出される
- `onResults` -- 音声認識の結果が渡される

`RecognitionListener#onResults()` で渡される Bundle オブジェクトから、音声認識の結果を取得することができます。

~~~ java
private RecognitionListener mRecognitionListener = new RecognitionListener() {
    @Override
    public void onResults(Bundle results) {
        ArrayList<String> values = results.getStringArrayList(
                SpeechRecognizer.RESULTS_RECOGNITION);
        for (String val : values) {
            Log.d(TAG, val);
        }
    }
    ...
};
~~~


音声認識に使用する言語を指定する
----

`SpeechRecognizer#startListening()` を実行するときに指定する Intent をカスタマイズすることで、音声認識に使用する言語を設定することができます。

#### 例: アメリカ英語で音声認識する

~~~ java
// private SpeechRecognizer mRecognizer;
// private RecognitionListener mRecognitionListener = new RecognitionListener() {...}; 

private void startRecognition() {
    mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
    mRecognizer.setRecognitionListener(mRecognitionListener);

    // 英語で音声入力
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    String lang = "en_US";
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, lang);
    intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, lang);
    mRecognizer.startListening(intent);
}
~~~


音声認識で取得する結果の数を設定する
----


SpeechRecognizer での音声認識の結果は、デフォルトでは複数の候補が得られるようになっています。
取得する結果の数を変更するには、以下のように `SpeechRecognizer#startListening()` で渡す Intent でカスタマイズします。

#### 例: 音声認識で得られる結果を 1 つにする

~~~ java
// private SpeechRecognizer mRecognizer;
// private RecognitionListener mRecognitionListener = new RecognitionListener() {...};

private void startRecognition() {
    mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
    mRecognizer.setRecognitionListener(mRecognitionListener);

    // 候補数を 1 つに設定
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
    mRecognizer.startListening(intent);
}
~~~

`EXTRA_MAX_RESULTS` で設定した数が反映されるかどうかは、Android のバージョンにも依存するようです。
いずれにしても、結果を 1 つだけ取得したいのであれば、`RecognitionListener#onResult()` で取得した認識結果の最初だけを取得すればよいでしょう。

~~~ java
private RecognitionListener mRecognitionListener = new RecognitionListener() {
    @Override
    public void onResults(Bundle results) {
        ArrayList<String> values = results.getStringArrayList(
                SpeechRecognizer.RESULTS_RECOGNITION);
        String val = values.get(0);
        Log.d(TAG, "認識結果: " + val);
    }
    ...
}
~~~


音声認識のエラーコードをテキストに変換する
----

SpeechRecognizer で音声入力をするときにエラーが発生すると、`RecognitionListener#onError()` が呼び出されます。
このとき、引数でエラーコードが渡されるのですが、分かりやすいテキストに変換したいところです。
現時点でテキストに変換する方法は提供されていないようなので、ここでは、適当なユーティリティを作って対応します。

#### SpeechRecognizerUtil.java

~~~ java
package com.example.myfirstapp;

import android.speech.SpeechRecognizer;

public class RecognizerUtil {
     public static String getErrorMessage(int errorCode) {
          switch (errorCode) {
               case SpeechRecognizer.ERROR_AUDIO:
                    return "Audio recording error";
               case SpeechRecognizer.ERROR_CLIENT:
                    return "Other client side errors";
               case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    return "Insufficient permissions";
               case SpeechRecognizer.ERROR_NETWORK:
                    return "Network related errors";
               case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    return "Network operation timed out";
               case SpeechRecognizer.ERROR_NO_MATCH:
                    return "No recognition result matched";
               case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    return "RecognitionService busy";
               case SpeechRecognizer.ERROR_SERVER:
                    return "Server sends error status";
               case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    return "No speech input";
          }
          return "Unknown error";
     }
}
~~~

#### 使用例

~~~ java
private RecognitionListener mRecognitionListener = new RecognitionListener() {
    @Override
    public void onError(int error) {
        Log.e(TAG, "onError: " + RecognizerUtil.getErrorMessage(error));
    }
    ...
}
~~~


音声認識を連続して実行する
----

`SpeechRecognizer#startListening()` で開始した音声入力は、一度入力が完了すると終了します (API Level 19)。
連続して音声入力を行うには、以下のようにするとよいようです。

* `RecognitionListener#onResults()` で結果を取得したら再び `startListening()`
* `RecognitionListener#onError()` で、`ERROR_NO_MATCH` あるいは `ERROR_SPEECH_TIMEOUT` が発生したら再び `startListening()`

二回目以降の `startListening()` を呼び出す前には、一度 SpeechRecognizer を `destroy()` して、再生成しないとうまくいかないことがあるようです（`startListening()` を読んでも実際には何も起こらない）。


#### SpeechRecognizer を連続実行するサンプルコード

~~~ java
package com.example.myfirstapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

public class MainActivity extends Activity {
     private static String TAG = "Sample";
    private SpeechRecognizer mRecognizer;
    private RecognitionListener mRecognitionListener = new RecognitionListener() {
          @Override
          public void onError(int error) {
               if ((error == SpeechRecognizer.ERROR_NO_MATCH) ||
                    (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT)) {
                  startSpeechRecognition();
                  return;
              }
               Log.d(TAG, "Recognition Error: " + error);
          }

          @Override
          public void onResults(Bundle results) {
               ArrayList<String> values = results
                         .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
               String val = values.get(0);
               Log.d(TAG, "認識結果: " + val);
               startSpeechRecognition();
          }

          @Override public void onBeginningOfSpeech() {}
          @Override public void onBufferReceived(byte[] arg0) {}
          @Override public void onEndOfSpeech() {}
          @Override public void onEvent(int arg0, Bundle arg1) {}
          @Override public void onPartialResults(Bundle arg0) {}
          @Override public void onReadyForSpeech(Bundle arg0) {}
          @Override public void onRmsChanged(float arg0) {}
    };

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSpeechRecognition();
    }

    private void startSpeechRecognition() {
         // Need to destroy a recognizer to consecutive recognition?
         if (mRecognizer != null) {
              mRecognizer.destroy();
         }

         // Create a recognizer.
         mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
          mRecognizer.setRecognitionListener(mRecognitionListener);

          // Start recognition.
          Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
          mRecognizer.startListening(intent);
     }
}
~~~

