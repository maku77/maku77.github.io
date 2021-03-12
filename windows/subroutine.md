---
title: "バッチファイルでサブルーチンを定義する、別のバッチファイルを呼び出す (call, setlocal)"
date: "2015-02-09"
lastmod: "2021-03-12"
---

バッチファイルにおけるサブルーチンの定義
----

バッチファイルで関数は定義できませんが、__`call`__ でラベルにジャンプすることで、サブルーチンとして実行できます。

```bat
call :ラベル名
```

`goto` でジャンプしてしまうと、ジャンプ先のコマンド群を実行した後に戻ってきてくれませんが、`call` でジャンプすれば、呼び出し位置に戻ってきてくれます。
下記の例では、関数もどきとして、`MyFunc1`、`MyFunc2` を定義し、呼び出しています。

#### hello.cmd

```bat
@echo off

REM === メインシーケンス
call :MyFunc1
call :MyFunc2
exit /b

REM === 関数 1
:MyFunc1
echo Hello, I am MyFunc1
exit /b

REM === 関数 2
:MyFunc2
echo Hello, I am MyFunc2
exit /b
```

#### 実行結果

```
C:\> hello
Hello, I am MyFunc1
Hello, I am MyFunc2
```

それぞれのシーケンス（メイン、関数1、関数2）の最後で、`exit /b` を実行しなければいけないことに注意してください。
そうしないと、それぞれのシーケンスで、バッチファイルの最後まで実行してしまいます（その場合も `call` 呼び出し位置に戻ってきます）。


サブルーチンに引数を渡す (%1, %2, %3)
----

サブルーチンに渡されたパラメータは、__`%1`__、__`%2`__ などで取得することができます。

#### sample.cmd

```bat
@echo off && setlocal

REM === メインシーケンス
call :MyFunc aaa
call :MyFunc aaa bbb ccc
call :MyFunc "aaa"
call :MyFunc "aaa bbb ccc"
exit /b

REM === 関数定義
:MyFunc
echo %1
exit /b
```

#### 実行結果:

```
C:\> sample
aaa
aaa
"aaa"
"aaa bbb ccc"
```

パラメータ内にスペースや特殊な記号を含む場合は、ダブルクォーテーション `"` で囲めばよいのですが、`%1` で取得した結果にダブルクォーテーションが含まれてしまいます（上記サンプルの `"aaa bbb ccc"` という出力結果）。
ダブルクォーテーションを削除した結果を取得したい場合は、以下のように __`%~1`__（チルダ付き）で参照します。

```bat
REM === 関数定義
:MyFunc
echo %~1
exit /b
```

#### 実行結果:

```
C:\> sample
aaa
aaa
aaa
aaa bbb ccc
```


サブルーチン内でローカル変数を定義する (setlocal)
----

バッチファイル内で変数を定義するときに、あらかじめ __`SETLOCAL`__ を呼び出しておくと、変数のスコープがサブルーチンやバッチファイル内に限定されます。
つまり、`SET 変数名=値` で定義した変数が、ローカル変数のように振る舞うようになります（デフォルトではグローバル変数のように振る舞います）。

`SETLOCAL` した後で `ENDLOCAL` を呼び出すと、`SETLOCAL` を呼び出したときの変数定義状態に戻すことができます。
ただ、サブルーチンから戻る際は自動的に `ENDLOCAL` されるようになっているので、実際にはサブルーチンの先頭（ラベル名の直後やバッチファイルの先頭）で `SETLOCAL` しておくだけで十分です。

#### sample.cmd

```bat
@echo off && setlocal

set price=100
echo %price%
call :MyFunc
echo %price%
exit /b

:MyFunc
setlocal
set price=200
echo %price%
exit /b
```

#### 実行結果

```
100
200
100
```


バッチファイルから別のバッチファイルを呼び出す
----

ちなみに、`call` コマンドでは、別ファイルのバッチファイルを呼び出すこともできます。
サブルーチンの呼び出しでは、先頭にコロン `:` を付けますが、この場合は代わりにバッチファイル名を指定します。

```bat
call %~dp0SubModule.cmd    （拡張子 .cmd は省略可能）
```

先頭の `%~dp0` は、バッチファイルの含まれているディレクトリを表すおまじないです（参考: [バッチファイルのファイル名やディレクトリ名を取得する](file/batch-dir.html)）。
これを入れることにより、バッチファイルのあるディレクトリ以外から実行した場合に、正しく別のバッチファイルを参照できるようになります。
ちなみに、呼び出されるバッチファイル側は `exit /b` で終了する必要はありません。

### 別のバッチファイルを呼び出すサンプル

次の例では、バッチファイル `Main` から、バッチファイル `SubModule` を呼び出しています。
`SubModule` の処理が終わると、`Main` の続きから処理が再開されます。

#### Main.cmd（呼び出す側）

```bat
@echo off && setlocal

set my_name=Main
echo I am %my_name%
call %~dp0SubModule
echo I am %my_name%
```

#### SubModule.cmd（呼び出される側）

```bat
@echo off && setlocal

set my_name=SubModule
echo I am %my_name%
```

#### 実行結果

```
C:\> Main
I am Main
I am SubModule
I am Main
```

### 別のバッチファイルに引数を渡す

`call` で別のバッチファイルを呼び出すときに渡した引数は、__`%1`__、__`%2`__ のように参照することができます。
実際には、前述したとおり、ダブルクォーテーションを外した値を取得するために、__`%~1%`__、__`%~2%`__ のようなチルダ付きで参照します。

#### main.cmd

```bat
@echo off && setlocal

call %~dp0hello Maku
call %~dp0hello Ponyo
call %~dp0hello "Chiko Rita"
```

#### hello.cmd

```bat
@echo off && setlocal

echo Hello, %~1%
```

#### 実行結果

```
C:\> main
Hello, Maku
Hello, Ponyo
Hello, Chiko Rita
```
