---
title: "クラスやメソッドの依存関係を調べる (jdeps, cfa)"
url: "p/mpqhkpv/"
date: "2016-11-24"
tags: ["Java"]
aliases: /java/tools/jdeps.html
---

パッケージ／クラスレベルの依存関係を調べる (jdeps)
----

JDK 8 に付属している [jdeps コマンド](https://docs.oracle.com/javase/jp/8/docs/technotes/tools/unix/jdeps.html) を使用すると、JAR ファイル（.class ファイル）から、パッケージレベル、あるいは、クラスレベルの依存関係を調べることができます。

### パッケージレベルの依存関係を調べる

{{< code lang="console" title="すべての依存を表示（グルーピングして表示）" >}}
$ jdeps app.jar
{{< /code >}}


{{< code lang="console" title="1 行ごとに「依存元 -> 依存先」というフォーマットで出力" >}}
$ jdeps -v app.jar
{{< /code >}}

あるパッケージへの依存のみを抽出したい場合は、`-e` オプションで次のように指定します。

{{< code lang="console" title="com.example.util パッケージへの依存のみを表示" >}}
$ jdeps -e com.example.util.* app.jar
{{< /code >}}

{{% note %}}
特定のパッケージへの依存は、`-p`  オプションでも検索することができますが、
こちらの場合は、完全にパッケージ名が一致するもののみを抽出します。
あるパッケージより下位のパッケージも含めて検索するには、上記のように `-e` オプションで正規表現の形でパッケージ名を指定する必要があります。
{{% /note %}}

### クラスレベルの依存関係を調べる

```console
$ jdeps -verbose:class app.jar
```


メソッドレベルの依存関係を調べる (cfa)
----

YaSuenag さんの作成している `cfa` (Class File Analyzer) というツールを使用すると、`.class` ファイルを解析して、メソッドレベルの依存関係を調べることができます。

[https://github.com/YaSuenag/cfa/](https://github.com/YaSuenag/cfa/)（[fork しておく](https://github.com/maku77/cfa/)）

実行ファイルは `cfa.jar` です。
Windows であれば、下記のようなバッチファイルを JAR と同じディレクトリにおいて PATH を通しておくと、どのディレクトリからでも `cfa` と入力するだけで実行できて便利です。


{{< code lang="bat" title="cfa.cmd" >}}
@echo off
setlocal
set jar="%~dp0%cfa.jar"
set java=java.exe
if defined JDK_HOME (
    set java=%JDK_HOME%\bin\java.exe
)
%java% -jar %jar% %*
endlocal
{{< /code >}}

`cfa.jar` は、JRE ではなく JDK に付属している java コマンド経由で実行する必要があることに注意してください。
実行時に下記のようなエラーが発生する場合は、JRE の java コマンドを参照してしまっている可能性が高いです。

```
Exception in thread "main" java.io.FileNotFoundException: tools.jar does not exist.
```

このような場合は、`JDK_HOME` 環境変数に JDK をインストールしたディレクトリパス（例: `C:\jdk1.8.0_65`）を設定してください。
上記のバッチファイルでは、この環境変数で設定された JDK の java コマンドを参照するようになっています。

下記は `cfa` コマンドの使用例です。


{{< code lang="console" title="メソッドレベルの依存関係を調べる（参照しているクラスやフィールドの情報も表示されます）" >}}
$ cfa app.jar
{{< /code >}}

{{< code lang="console" title="com.example.aaa と com.example.bbb を参照しているメソッドだけを対象とする" >}}
$ cfa -c com.example.aaa,com.example.bbb app.jar
{{< /code >}}

