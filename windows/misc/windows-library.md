---
title: Windows の静的ライブラリと動的ライブラリ
created: 2009-03-26
---

Windows の lib ファイルと dll ファイルに関して
----

### スタティックライブラリ (lib)

* 実装を含むスタティックライブラリの場合と、DLL などのダイナミックライブラリを利用しやすくするためのインポートライブラリの場合がある。DLL を使用するためには必ずしもインポートライブラリは必要ないが、その場合は関数名などをコード内で直接指定する必要がある。
* lib ファイルはツールごとに互換性がない。つまり、VC++ や BCC で使用する lib ファイルは別のものになる。DLL ファイルからそれぞれのツール用にインポートライブラリを生成することができる。
* 複数のオブジェクトファイルをまとめるファイル。
* Unix の共有ライブラリ (libXXX.so) にはインポートライブラリは必要ない。

### ダイナミックライブラリ (dll)

* インポートライブラリはそれぞれのビルドツールごとに互換性がないが、dll ファイルは Windows なら共通で使用できる。


bcc32.exe に付属の tlib.exe で static ライブラリ (lib) を作る
----

ライブラリアン (`tlib.exe`) を使うと、`obj` ファイルを埋め込んだスタティックライブラリを作成できます。

```
# sample.obj を作成
C:\> bcc32 -c sample.cpp

# sample.obj から hoge.lib を作成
C:\> tlib hoge +sample
```


bcc32.exe に付属の implib.exe で任意の DLL のインポートライブラリ (lib) を作る
---

#### 例: sample.dll を使用するための sample.lib を作成

```
C:\> implib sample.lib sample.dll
```


bcc32.exe で static ライブラリ (lib) を使用する
---

static ライブラリを `bcc32` のライブラリパスの通ったディレクトリにコピーし、ビルドするときにファイル名を指定します。

```
C:\> bcc32 sample.cpp hoge.lib
```

cpp ファイルのどこかに以下のような `pragma` 指定を記述しておく方法もあります。

```cpp
#pragma comment(lib, "hoge.lib")
```

