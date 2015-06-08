---
title: 音声を再生する
created: 2014-12-07
---

`PlaySound()` を使って WAV ファイルを再生することができます。

* [PlaySound - MQL4 Documentation](http://docs.mql4.com/common/playsound)

#### 使用例
```mql
PlaySound("alert.wav");
```

パラメータでは、MetaTrader の Sounds ディレクトリ内の WAV ファイルを指定できます。

* alert.wav
* alert2.wav
* connect.wav
* disconnect.wav
* email.wav
* expert.wav
* news.wav
* ok.wav
* stops.wav
* tick.wav
* timeout.wav
* wait.wav

音声の再生は非同期に実行されるため、`PlaySound()` の呼び出しがプログラムを停止させることはありません。音声の再生中にもう一度 `PlaySound()` を呼び出すと、現在再生中の音声は停止し、新しく指定した音声の再生が開始されます。例えば、下記のように連続して実行すると、最後に指定した音だけが聞こえます。

```mql
PlaySound("alert.wav");
PlaySound("ok.wav");
```

パラメータに `NULL` を指定すると、現在再生中の音声を停止することができます。

```mql
PlaySound(NULL);
```

次の Script は、1 秒おきに異なる音声を再生していきます。

```mql
void OnStart() {
    string sounds[] = {
        "alert.wav", "alert2.wav", "connect.wav",
        "disconnect.wav", "email.wav", "expert.wav",
        "news.wav", "ok.wav", "stops.wav",
        "tick.wav", "timeout.wav", "wait.wav"
    };

    for(int i = 0; i < ArraySize(sounds); ++i) {
        Print("Sound file: " + sounds[i]);  // Experts タブにファイル名を表示
        PlaySound(sounds[i]);  // 音声を再生
        Sleep(1000);  // 1秒待つ
    }
}
```

