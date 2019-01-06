---
title: ".svn ディレクトリをまとめて削除する"
date: "2010-04-25"
---

Linux の場合
----

~~~
$ find . -name '.svn' -exec rm -rf {} \;
~~~

コマンドの実行順序の関係で "No such file or directory" といったエラーが大量に出ますが、`.svn` ディレクトリはちゃんと削除されています。


Windows XP の場合
----

~~~
C:\> for /d /r %x in (*.svn) do rmdir /q /s "%x"
~~~

`.svn` ディレクトリが隠しディレクトリになっている場合は、Windows では `FOR` コマンドで列挙されないので、`DIR` コマンドと組み合わせて以下のようにする必要があります。
ただし、この方法は `DIR` コマンドが終わってから `FOR` コマンドに結果が渡されるので、かなり効率が悪くなります。

~~~
C:\> for /f "tokens=*" %x in ('dir /b /s /a:dh *.svn') do rmdir /q /s "%x"
~~~

バッチファイルにするときは、`%x` の部分を `%%x` にするのを忘れないようにしてください。

