---
title: クラスやメソッドの依存関係を調べる (jdeps, cfa)
created: 2016-11-24
---

パッケージ／クラスレベルの依存関係を調べる (jdeps)
----

JDK 8 に付属している [jdeps コマンド](https://docs.oracle.com/javase/jp/8/docs/technotes/tools/unix/jdeps.html) を使用すると、JAR ファイル（.class ファイル）から、パッケージレベル、あるいは、クラスレベルの依存関係を調べることができます。

#### パッケージレベルの依存関係を調べる

```
$ jdeps sample.jar
```

#### クラスレベルの依存関係を調べる

```
$ jdeps -verbose:class sample.jar
```


メソッドレベルの依存関係を調べる (cfa)
----

YaSuenag さんの作成している cfa (Class File Analyzer) というツールを使用すると、.class ファイルを解析して、メソッドレベルの依存関係を調べることができます。

[https://github.com/YaSuenag/cfa/](https://github.com/YaSuenag/cfa/)（[fork しておく](https://github.com/maku77/cfa/)）

実行ファイルは `cfa.jar` です。
Windows であれば、下記のようなバッチファイルを JAR と同じディレクトリにおいて PATH を通しておくと、どのディレクトリからでも `cfa` と入力するだけで実行できて便利です。

#### cfa.cmd

```
@echo off
setlocal
set jar="%~dp0%cfa.jar"
set java=java.exe
if defined JDK_HOME (
    set java=%JDK_HOME%\bin\java.exe
)
%java% -jar %jar% %*
endlocal
```

`cfa.jar` は、JRE ではなく JDK に付属している java コマンド経由で実行する必要があることに注意してください。
実行時に下記のようなエラーが発生する場合は、JRE の java コマンドを参照してしまっている可能性が高いです。

```
Exception in thread "main" java.io.FileNotFoundException: tools.jar does not exist.
```

このような場合は、`JDK_HOME` 環境変数に JDK をインストールしたディレクトリパス（例: `C:\jdk1.8.0_65`）を設定してください。
上記のバッチファイルでは、この環境変数で設定された JDK の java コマンドを参照するようになっています。

下記は使用例です。

#### メソッドレベルの依存関係を調べる（参照しているクラスやフィールドの情報も表示されます）

```
$ cfa sample.jar
```

#### com.example.aaa と com.example.bbb を参照しているメソッドだけを対象とする

```
$ cfa -c com.example.aaa,com.example.bbb sample.jar
```

