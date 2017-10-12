---
title: "複数の Hugo サーバーを１つのコマンドプロンプト上で立ち上げる"
created: 2017-10-10
description: "複数のサイトを Hugo で作成しているようなケースでは、ひとつのコマンドプロンプトから同時にサーバー起動できると便利です。"
---

ここでは、下記のようにホームディレクトリ以下に複数の Hugo プロジェクトが存在するとします。

~~~
C:/website/site1
C:/website/site2
C:/website/site3
~~~

各 Hugo プロジェクトに対して Hugo サーバーを起動するには、例えば次のように実行していきます。

#### ポート番号を指定して Hugo サーバーを複数起動する

~~~
C:\> hugo server -p 50001 -s C:/website/site1
C:\> hugo server -p 50002 -s C:/website/site2
C:\> hugo server -p 50003 -s C:/website/site3
~~~

* 参考
  * [カレントディレクトリを気にせずに hugo コマンドを実行する](source-dir.html)
  * [ポート番号を指定して Hugo サーバーを起動する](server-port.html)

ただし、上記のようにすると、１つのサーバープロセスが１つのコマンドプロンプトを占有するため、複数の Hugo サーバーを起動しているときに複数のコマンドプロンプトを開いたままにしなければいけません。

Windows の場合
----

Windows の `start` コマンドを使用すると、バックグラウンドで `hugo` コマンドを実行することができるので、追加でコマンドプロンプトを開くことなしに複数の Hugo サーバーを起動することができます。
例えば、下記のバッチファイルは、３つの Hugo サーバーをバックグラウンドで起動します。

#### start-servers.bat

~~~
@echo off
start /b hugo server -p 50001 -s C:/website/site1
start /b hugo server -p 50002 -s C:/website/site2
start /b hugo server -p 50003 -s C:/website/site3
title Hugo Servers
~~~

<div class="note">
最後の行の <code>title Hugo Servers</code> では、コマンドプロンプトのウィンドウタイトルを Hugo Servers に変更しています。ウィンドウタイトルを変更しておくと、タスクバーを見ただけで何のためのウィンドウなのかを判別しやすくなるのでお勧めです。
</div>

バックグラウンドで動作している Hugo サーバーをまとめて終了するには、例えば、`taskkill` コマンドを使用して、`hugo.exe` によるプロセスをまとめて終了します。

#### stop-servers.bat

~~~
@echo off
taskkill /f /im hugo.exe
~~~

Windows の `start` コマンドや、`taskkill` コマンドの詳細は、[まくまく Windows ノート](/windows/)を参照してください。


Linux の場合
----

Linux や macOS の場合は、下記のように末尾に `&` を付けて `hugo` コマンド実行すれば、簡単に複数のサーバーをバックグラウンドで立ち上げることができます。

#### start-servers.sh

~~~ sh
hugo server -p 50001 -s ~/website/site1 &
hugo server -p 50002 -s ~/website/site2 &
hugo server -p 50003 -s ~/website/site3 &
~~~

Hugo サーバーのプロセスをまとめて終了したいときは、`killall` コマンドで簡単に終了できます。

#### stop-servers.sh

~~~ sh
killall hugo
~~~

