---
title: "ADB からキー入力やテキスト入力を行う (input text, input keyevent)"
date: "2016-07-14"
---

ADB shell 上で `input` コマンドを使用すると、任意のキー入力やテキスト入力をエミュレートすることができます。

キー入力の例 (input keyevent)
----

`input keyevent` コマンドを使用することで、単一のキー入力をエミュレートできます。

```
$ adb shell input keyevent ENTER         # Enter キー
$ adb shell input keyevent DPAD_CENTER   # D-pad 上の決定キー
$ adb shell input keyevent DPAD_RIGHT    # 右キー
$ adb shell input keyevent POWER         # 電源キー
$ adb shell input keyevent A             # キーボードの A キー
$ adb shell input keyevent TV            # TV キー（Android TV 用）
$ adb shell input keyevent NUMBER_ENTRY  # 10-key キー（Android TV 用）
$ adb shell input keyevent TV_TERRESTRIAL_DIGITAL  # 地デジキー（Android TV 用）
```

パラメータに指定できるキー名は、下記の `KeyEvent` クラスに定義されている `KEYCODE_XXX` という定数の、`KEYCODE_` というプレフィックスを除いたものです。

- [KeyEvent クラスの定数一覧](https://developer.android.com/reference/android/view/KeyEvent.html#constants)


テキスト入力の例 (input text)
----

テキストエリアなどにフォーカスが当たっているときに、連続したキーボード入力を行ってテキスト入力したいときは、`input keyevent` の代わりに `input text` を使用すると便利です。
スペースキーを入力する場合は、`%s` と指定する必要があります。

```
$ adb shell input text ABC%sDEF  # "ABC DEF" というキーボード入力
$ adb shell input text aAbBcCdD  # 大文字と小文字の区別も可能
```

キーボードやリモコン上のキーシーケンスを送り込んでいるだけなので、マルチバイト文字（日本語など）を直接入力することはできないようです。

