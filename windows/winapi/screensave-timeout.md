---
title: "Windows API でスクリーンセーバーの起動時間を取得／設定する"
date: "2010-09-06"
---

SystemParametersInfo API
----

スクリーンセーバーの起動時間を取得／設定するには、**`SystemParametersInfo`** API を使用します。

- [参考: SystemParametersInfo 関数 - MSDN](https://msdn.microsoft.com/ja-jp/library/cc429946.aspx)

~~~ cpp
BOOL SystemParametersInfo(
    UINT uiAction,  // 取得または設定するべきシステムパラメータ
    UINT uiParam,   // 実施するべき操作によって異なる
    PVOID pvParam,  // 実施するべき操作によって異なる
    UINT fWinIni    // ユーザープロファイルの更新オプション
);
~~~

`uiAction` パラメータに下記の値を指定することでスクリーンセーバーの起動時間に関しての操作を行えます。

- <code>SPI_<b>GET</b>SCREENSAVETIMEOUT</code>: タイムアウト時間を取得する
- <code>SPI_<b>SET</b>SCREENSAVETIMEOUT</code>: タイムアウト時間を設定する

#### 例: スクリーンセーバー起動までの待ち時間（秒数）を取得する

~~~ cpp
int seconds;
::SystemParametersInfo(SPI_GETSCREENSAVETIMEOUT, 0, &seconds, 0);
~~~

#### 例: スクリーンセーバー起動までの待ち時間を 10 分に設定する

~~~ cpp
::SystemParametersInfo(SPI_SETSCREENSAVETIMEOUT, 10*60, NULL, 0);
~~~


サンプルプログラム (setsaver.exe)
----

以下のプログラム `setsaver.exe` は、コマンドラインから「スクリーンセーバ起動までの待ち時間（秒数）」を設定するツールです。
パラメータなしで起動すると、現在の設定（秒数）を表示します。

#### 使用例: 現在のタイムアウト設定を表示

~~~
D:> setsaver.exe
Usage: setsaver.exe [seconds]
Current timeout is 1800 seconds
~~~

#### 使用例: タイムアウト設定を変更

~~~
D:> setsaver.exe 300
Set screensaver timeout: 300 seconds
~~~

### ソースコード＆実行ファイル:

- 実行ファイル: [screensave-timeout-setsaver.zip](screensave-timeout-setsaver.zip)
- ソースコード: [screensave-timeout-setsaver.cpp](screensave-timeout-setsaver.cpp)

Windows Vista (32bit)、Windows 7 (64bit) で動作確認済みです。

Windows 起動時に自動で実行したければ、`Startup` ディレクトリにショートカットを作成して、プロパティからタイムアウト時間をパラメータ指定しておけば OK。

#### MinGW でのビルド方法

```
C:\> g++ -o setsaver.exe -static -luser32 setsaver.cpp
```

