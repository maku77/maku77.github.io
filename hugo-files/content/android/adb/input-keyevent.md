---
title: "ADB からキー入力やテキスト入力を行う (input text, input keyevent)"
url: "p/gorux24/"
date: "2016-07-14"
tags: ["Android"]
aliases: /android/adb/input-keyevent.html
---

ADB shell 上で __`input`__ コマンドを使用すると、任意のキー入力やテキスト入力をエミュレートすることができます。

キー入力の例 (input keyevent)
----

`input keyevent` コマンドを使用することで、単一のキー入力をエミュレートできます。

```console
$ adb shell input keyevent ENTER         # Enter キー
$ adb shell input keyevent DPAD_CENTER   # D-pad 上の決定キー
$ adb shell input keyevent DPAD_RIGHT    # 右キー
$ adb shell input keyevent POWER         # 電源キー
$ adb shell input keyevent A             # キーボードの A キー
$ adb shell input keyevent TV            # TV キー（Android TV 用）
$ adb shell input keyevent NUMBER_ENTRY  # 10-key キー（Android TV 用）
$ adb shell input keyevent TV_TERRESTRIAL_DIGITAL  # 地デジキー（Android TV 用）
```

パラメータに指定できるキー名は、`android.view.KeyEvent` クラスに定義されている `KEYCODE_XXX` という定数の、`KEYCODE_` というプレフィックスを除いたものです（参考: [KeyEvent クラスの定数一覧](https://developer.android.com/reference/android/view/KeyEvent.html#constants)）。

### 長押し

`input keyevent` コマンドの __`--longpress`__ オプションを指定すると、キーの長押しをエミュレートできます。
次の例では、ENTER キーの長押しを再現しています（ちょっとだけ長押しして、すぐ離す、という操作と同じ振る舞いになります）。

```console
$ adb shell input keyevent --longpress ENTER
```

### （応用）sendevent を使う方法

よりローレベルなキー入力を再現したい場合は、__`sendevent`__ コマンドを使った方法があります。
例えば、「キーを 5 秒間押しっぱなしにする」といった細かい動きを再現したいケースでは `input keyevent` は使えないので、`sendevent` を使ったシェルスクリプトなどを作ることになります。

- [getevent/sendevent で入力デバイスへの入力情報を取得する／入力を行う](/p/gufweuc/)


テキスト入力の例 (input text)
----

テキストエリアなどにフォーカスが当たっているときに、連続したキーボード入力を行ってテキスト入力したいときは、`input keyevent` の代わりに __`input text`__ を使用すると便利です。
スペースキーを入力する場合は、`%s` と指定する必要があります。

```console
$ adb shell input text ABC%sDEF  # "ABC DEF" というキーボード入力
$ adb shell input text aAbBcCdD  # 大文字と小文字の区別も可能
```

キーボードやリモコン上のキーシーケンスを送り込んでいるだけなので、マルチバイト文字（日本語など）を直接入力することはできないようです。

