---
title: "バッチファイルのファイル名やディレクトリ名を取得する"
date: "2014-08-04"
lastmod: "2020-01-07"
---

バッチパラメータ置換
----

現在実行中のバッチファイルに関するパス情報は、変数 __`%0`__ で参照することができます。
実際には、ファイル名にスペースが含まれている場合にも正しく処理できるようにするために、`%0` ではなく `%~0` で参照します。

```bat
echo %~0
```

さらに、次の表のように、`%~` と `0` の間に修飾子 (__`d`__, __`p`__, __`n`__, __`x`__) を指定することで、ディレクトリ名や拡張子だけを取り出すことが可能です（組み合わせも可能）。
この機能を「バッチパラメータ置換」と呼びます。

表の実行結果は、`sample.bat` バッチファイルを次のように起動した場合の結果を示しています。

```
C:\Users> maku\sample.bat
```

| 参照方法 | 意味 | 実行結果 |
| ---- | ---- | ---- |
| `%~0` | 指定したファイル名 | `maku\sample.bat` |
| `%~f0` (`%~dpnx0`) | フルパス | `C:\Users\maku\sample.bat` |
| `%~d0` | ドライブ名 | `C:` |
| `%~p0` | ディレクトリ名 | `\Users\maku\` |
| `%~dp0` | ドライブ名 + ディレクトリ名 | `C:\Users\maku\` |
| `%~n0` | ベースネーム | `sample` |
| `%~x0` | 拡張子 | `.bat` |
| `%~nx0` | ベースネーム + 拡張子 | `sample.bat` |

バッチパラメータ置換に関するヘルプは、`call /?` で確認することができます。


使用例
----

例えば、バッチファイルの格納されているディレクトリの絶対パスは、__`%~dp0`__ という変数で取得できるので、次のようにすれば、カレントディレクトリをバッチファイルの置いてあるディレクトリに変更することができます。

```bat
cd /d %~dp0
```

ちなみに、`cd` コマンドの `/d` オプションは、別ドライブのディレクトリに移動するときに必要になるので、必ず付けるようにしておくとよいです。


（おまけ）各変数の内容を調べるバッチファイル (sample.bat)
----

```bat
echo %%~0 ..... %~0
echo %%~f0 .... %~f0
echo %%~d0 .... %~d0
echo %%~p0 .... %~p0
echo %%~dp0 ... %~dp0
echo %%~n0 .... %~n0
echo %%~x0 .... %~x0
echo %%~nx0 ... %~nx0
```

