---
title: "AppleScript を実行する"
date: "2018-02-18"
description: "AppleScript を使用すると Mac で実行したい一連の処理をスクリプト化することができます。Windows のバッチファイルのようなものです。"
---

AppleScript を実行するには、`osascript` コマンドを使用します。


ワンライナーで実行する
----

次のようにして、`-e` オプションで実行する処理を直接記述することができます。

#### 例: ダイアログでメッセージを表示する

~~~
$ osascript -e 'display dialog "Hello"'
~~~


スクリプトファイルを実行する
----

長いスクリプトを作成するときは、次のようにスクリプトファイルとして保存しておくのがよいでしょう。

#### sample.osa（スクリプトファイル）

~~~
#!/usr/bin/evn osascript

display dialog "Hello"
~~~

スクリプトファイルを実行するときも、`-e` オプションを使用してファイル名を指定します。

~~~
$ osascript -e sample.osa
~~~

上記のスクリプト例のように一行目にシェバングを記述して、実行権限を付けておけば、そのまま実行ファイルとして実行することができます。

~~~
$ chmod +x sample.osa
$ ./sample.osa
~~~

