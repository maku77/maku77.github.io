---
title: "Windowsメモ: Windows の静的ライブラリ (lib) と動的ライブラリ (dll)"
url: "p/73ibsy6/"
date: "2009-03-26"
tags: ["windows"]
aliases: /windows/misc/windows-library.html
---

Windows の lib ファイルと dll ファイルに関して
----

### スタティックライブラリ (lib)

* lib ファイルには、実装を含むスタティックライブラリの場合と、DLL などのダイナミックライブラリを利用しやすくするためのインポートライブラリの場合があります。DLL を使用するためにインポートライブラリは必ずしも必要ではありませんが、その場合は関数名などをコード内で直接指定する必要があります。
* lib ファイルはツールごとに互換性がありません。つまり、VC++ や BCC で使用する lib ファイルはそれぞれ別のものになります。DLL ファイルからそれぞれのツール用にインポートライブラリを生成することができます。
* lib ファイルは複数のオブジェクトファイルをまとめたファイルです。
* Unix の共有ライブラリ (`libXXX.so`) にはインポートライブラリは必要ありません。

### ダイナミックライブラリ (dll)

* インポートライブラリはビルドツールごとに互換性がありませんが、dll ファイルは Windows 上であれば共通して使用できます。


Borland C++ Compiler (bcc32.exe) でのライブラリ操作
----

**Borland C++ Compiler (bcc32.exe)** は、Embarcadero（旧 Borland）が提供する Windows 向けの C++ コンパイラです。
無償で配布されており、コマンドラインから C/C++ プログラムをコンパイル・リンクできます。
`bcc32.exe` に加えて、スタティックライブラリを作成する `tlib.exe` や、インポートライブラリを生成する `implib.exe` などのツールが付属しています。

### スタティックライブラリ (lib) を作成する (tlib.exe)

ライブラリアン (`tlib.exe`) を使うと、`obj` ファイルを埋め込んだスタティックライブラリを作成できます。

```
# sample.obj を作成
C:\> bcc32 -c sample.cpp

# sample.obj から hoge.lib を作成
C:\> tlib hoge +sample
```

### DLL のインポートライブラリ (lib) を作成する (implib.exe)

`implib.exe` を使うと、任意の DLL に対応するインポートライブラリを生成できます。
異なるビルドツール向けに lib ファイルを用意し直したいときなどに使用します。

{{< code title="例: sample.dll を使用するための sample.lib を作成" >}}
C:\> implib sample.lib sample.dll
{{< /code >}}

### スタティックライブラリ (lib) をリンクする

static ライブラリを `bcc32` のライブラリパスの通ったディレクトリにコピーし、ビルドするときにファイル名を指定します。

```
C:\> bcc32 sample.cpp hoge.lib
```

cpp ファイルのどこかに以下のような `pragma` 指定を記述しておく方法もあります。

```cpp
#pragma comment(lib, "hoge.lib")
```
