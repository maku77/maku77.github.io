---
title: "ANR の原因を突き止める"
url: "p/be5zj9v/"
date: "2010-08-16"
tags: ["android"]
aliases: [/android/analyze-anr.html]
---

Android のメインスレッドを長期間ブロックするコードがあると、ANR (Application Not Responding) が発生してアプリケーションが強制終了されます。
端末の画面上で Not responding ダイアログが表示されたら `[Force Close]` ボタンを押すと、以下のようなログが出るので、kill されたプロセス ID を確認します。

```
I ActivityManager: Killing process com.example.player (pid=854) at user's request
I Process : Sending signal. PID: 854 SIG: 9
```

この場合のプロセス ID は 854 です。
ANR の詳細は、`/data/anr/traces.txt` に出力されています。
このテキストファイルを見てそのプロセス ID に関するコールスタックを確認すると、具体的にどのメソッドでブロックしてしまっているかがわかります。

```console
$ adb pull /data/anr/traces.txt
$ vim traces.txt
```

例えば、以下のように出力されていれば、`VideoView.java` 258 行目の `MediaPlayer.prepareAsync` 呼び出しでブロックしていることが分かります。

```
----- pid 854 at 2008-10-01 12:03:02 -----
Cmd line: com.example.player

DALVIK THREADS:
"main" prio=5 tid=3 NATIVE
  | group="main" sCount=1 dsCount=0 s=N obj=0xb4c69e50 self=0x804d3f0
  | sysTid=854 nice=0 sched=0/0 cgrp=unknown handle=-1224341824
  at android.media.MediaPlayer.prepareAsync(Native Method)
  at android.widget.VideoView.openVideo(VideoView.java:258)
  at android.widget.VideoView.access$2000(VideoView.java:53)
  at android.widget.VideoView$6.surfaceCreated(VideoView.java:506)
...
```

