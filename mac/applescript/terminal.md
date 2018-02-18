---
title: "AppleScript でターミナルを操作する"
date: "2018-02-18"
---

新規ターミナルウィンドウを開く
----

次のようにすると、新しいターミナルを起動し、そのターミナルをアクティブにすることができます。

~~~
#!/usr/bin/env osascript

tell application "Terminal"
    do script
    activate
end tell
~~~

上記はスクリプトファイルにする例ですが、次のように、ワンライナーで `osascript` コマンドを実行することもできます。

~~~
$ osascript -e 'tell application "Terminal" to do script'
~~~

新規に開いたターミナル上で任意のシェルコマンドを実行するには、`do script` のパラメータでコマンドを指定します。
下記の例では、単純な `echo` コマンドを実行しています。

~~~
tell application "Terminal"
    do script "echo Hello"
    activate
end tell
~~~

さらに、次のようにすれば、新規ターミナルのタイトルを設定することができます。

~~~
tell application "Terminal"
    do script
    activate

    set currWin to index of first window
    tell window currWin
        set custom title of first tab to "カスタムタイトル"
    end tell
end tell
~~~

ターミナルのテーマを変更することもできます。

~~~
set current settings of window currWin to settings set "Grass"
~~~


新しいタブを開く
----

次の例では、ターミナルに対して <kbd>Command + T</kbd> イベントを送信して、新しいタブを開いています。

~~~
tell application "Terminal"
    my newTab()
end tell

on newTab()
    tell application "System Events" to keystroke "t" using command down
end newTab
~~~

ここでは、`newTab()` という関数を定義して呼び出すようにしています。


新しいタブを開いて、さらに任意のシェルコマンドを実行する
----

次の例では、新しいタブを３つ開き、それぞれのタブの中で `echo` コマンドを実行します。

~~~
-- エントリポイント
on run
    my newTab("echo Hello1")
    my newTab("echo Hello2")
    my newTab("echo Hello3")
end run

-- 新しいタブを開いてコマンドを実行する
on newTab(command)
    -- Open a new tab and wait a little
    tell application "System Events"
        keystroke "t" using command down
        delay 0.5
    end tell

    -- Run the command in the new tab
    tell application "Terminal"
        do script command in front window
    end tell
end newTab
~~~


ターミナル内の同じタブで連続してシェルコマンドを実行する
----

次のようにすると、コマンドを実行したタブを変数に取得することができます。

~~~
set currentTab to do script "コマンド"
~~~

これを利用して、同じタブで複数のコマンドを実行することができます。

~~~
tell application "Terminal"
    set currentTab to do script "echo Hello1"
    delay 0.3
    do script "echo Hello2" in currentTab
    delay 0.3
    do script "echo Hello3" in currentTab
end tell
~~~

コマンドを順序通り実行するには、上記のように `delay` を入れておくのがよいようです。

