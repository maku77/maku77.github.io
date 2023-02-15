---
title: "複数の Hugo サーバーを 1 つのコマンドプロンプト上で立ち上げる"
url: "p/yg4o9rb/"
date: "2017-10-10"
tags: ["Hugo"]
description: "複数のサイトを Hugo で作成しているケースでは、ひとつのコマンドプロンプトから同時にサーバー起動できると便利です。"
aliases: /hugo/command/multi-server.html
---

Hugo サーバーの複数起動について
----

複数のサイトを Hugo で運用しているケースでは、ひとつのコマンドプロンプトから同時にサーバー起動できると便利です。

ここでは、下記のようにホームディレクトリ以下に複数の Hugo プロジェクトが存在するとします。

- `C:/website/site1`
- `C:/website/site2`
- `C:/website/site3`

各 Hugo プロジェクトに対して Hugo サーバーを起動するには、例えば次のように実行していきます。

{{< code title="ポート番号を指定して Hugo サーバーを複数起動する" >}}
C:\> hugo server -p 50001 -s C:/website/site1
C:\> hugo server -p 50002 -s C:/website/site2
C:\> hugo server -p 50003 -s C:/website/site3
{{< /code >}}

* 参考: [カレントディレクトリを気にせずに hugo コマンドを実行する](/p/wdyk5n7/)
* 参考: [ポート番号を指定して Hugo サーバーを起動する](/p/jj7rcvf/)

ただし、上記のようにすると、1 つのサーバープロセスが 1 つのコマンドプロンプトを占有するため、複数の Hugo サーバーを起動しているときに複数のコマンドプロンプトを開いたままにしなければいけません。


1 つのターミナルで複数の Hugo サーバーを起動する
----

### Windows の場合

Windows の __`start`__ コマンドを使用すると、バックグラウンドで `hugo` コマンドを実行することができるので、追加でコマンドプロンプトを開くことなしに複数の Hugo サーバーを起動することができます。
例えば、下記のバッチファイルは、３つの Hugo サーバーをバックグラウンドで起動します。

{{< code lang="bat" title="start-servers.bat" >}}
@echo off
start /b hugo server -p 50001 -s C:/website/site1
start /b hugo server -p 50002 -s C:/website/site2
start /b hugo server -p 50003 -s C:/website/site3
title Hugo Servers
{{< /code >}}

{{% note %}}
最後の行の __`title Hugo Servers`__ では、コマンドプロンプトのウィンドウタイトルを Hugo Servers に変更しています。
ウィンドウタイトルを変更しておくと、タスクバーを見ただけで何のためのウィンドウなのかを判別しやすくなるのでお勧めです。
{{% /note %}}

バックグラウンドで動作している Hugo サーバーをまとめて終了するには、例えば、__`taskkill`__ コマンドを使用して、`hugo.exe` によるプロセスをまとめて終了します。

{{< code lang="bat" title="stop-servers.bat" >}}
@echo off
taskkill /f /im hugo.exe
{{< /code >}}

Windows の `start` コマンドや、`taskkill` コマンドの詳細は、[まくまく Windows ノート](/windows/)を参照してください。


### Linux の場合

Linux や macOS の場合は、下記のように末尾に __`&`__ を付けて `hugo` コマンド実行すれば、簡単に複数のサーバーをバックグラウンドで立ち上げることができます。

{{< code lang="bash" title="start-servers.sh" >}}
hugo server -p 50001 -s ~/website/site1 &
hugo server -p 50002 -s ~/website/site2 &
hugo server -p 50003 -s ~/website/site3 &
{{< /code >}}

Hugo サーバーのプロセスをまとめて終了したいときは、__`killall`__ コマンドで簡単に終了できます。

{{< code lang="bash" title="stop-servers.sh" >}}
killall hugo
{{< /code >}}

これくらいの規模であれば、シェルスクリプトではなく、関数やエイリアスとして定義してしまうのが手っ取り早いかもしれません。

{{< code lang="bash" title="~/.bash_profile（抜粋）" >}}
function hugo-start {
    hugo server -p 50001 -s ~/website/site1 &
    hugo server -p 50002 -s ~/website/site2 &
    hugo server -p 50003 -s ~/website/site3 &
}

function hugo-stop {
    killall hugo
}
{{< /code >}}

