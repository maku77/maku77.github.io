---
title: "Mac のターミナルをコマンドラインから操作する"
date: "2018-02-18"
description: "ソフトウェアの開発作業などで、Mac のターミナルを使うことが多いのであれば、コマンドラインからターミナルを操作する方法を知っておくと便利です。複数の作業用ターミナルを一度に開いたり、タイトルを設定して判別しやすくすることができます。"
---

新しいターミナルを開く
----

~~~
$ open -n /Applications/Utilities/Terminal.app
~~~

<kbd>Command + N</kbd> でも新しいターミナルウィンドウを開くことができます。


新しいタブを開く
----

Terminal 内で新しくタブを開くには、AppleScript を使用します。

~~~
$ osascript -e 'tell application "System Events" to tell process "Terminal" to keystroke "t" using command down'
~~~

<kbd>Command + T</kbd> でも新しいタブを開くことができます。


ターミナルのタイトルを変更する
----

次のようにすると、現在のターミナルの（タブの）タイトルが、「新しいタイトル」に変更されます。

~~~
$ printf "\e]1;新しいタイトル\a"
あるいは
$ echo -e "\033];新しいタイトル\007"
~~~

<kbd>Command + Shift + I</kbd> でダイアログを開いて設定することもできます。

AppleScript からターミナルを操作する方法は[こちらを参照](applescript/terminal.html)してください。

