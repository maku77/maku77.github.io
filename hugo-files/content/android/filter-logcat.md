---
title: "Logcat のログ出力をフィルタする"
url: "p/asryvzc/"
date: "2010-04-19"
tags: ["android"]
aliases: [/android/filter-logcat.html]
---

{{% private %}}
- 参考: https://developer.android.com/tools/adb
{{% /private %}}

Android の logcat ログの出力は、出力のレベルでフィルタリングしたり、`Log.d()` メソッドなどの第一引数で指定したタグでフィルタリングできるようになっています。


adb logcat コマンドでのフィルタ方法
----

logcat コマンドによるログのフィルタリングでは、priority として `V/D/I/W/E/F/S` のいずれかを指定できます
例えば、`W` を指定した場合は、Warning レベル以上のログ (`W/E/F/S`) のみが表示されるようになります。

```console
$ adb logcat *:W                # Warning 以上のログだけ表示
$ adb logcat -s Tag1:*          # Tag1 タグのログだけを表示
$ adb logcat *:S Tag1:*         # 同上
$ adb logcat -s Tag1:W Tag2:E   # Tag1 と Tag2 のログだけを表示
$ adb logcat Tag1:S             # Tag1 タグのログを非表示
```

タグ名でログを絞り込みたい場合は、-s オプションで全てタグのログをデフォルトでオフした上で、任意のタグを指定する必要があります。
そうしないと、指定していないタグに関しては、すべてのレベルのログが出てしまいます。

{{< code lang="console" title="絞り込みに失敗する例 (Tag1 に関してしかレベル制御されない）" >}}
$ adb logcat Tag1:W
{{< /code >}}

Linux を使っているなら、`grep` にパイプしまって適当にフィルタリングするのもありです。
正規表現も使えるし、こっちのが実は柔軟性は高いかもしれません。
ただ、このやり方は、ログ受信側でフィルタしているだけなので、Android のシステムがログの出力を抑制しているわけではないことに注意してください。

```console
$ adb logcat | grep HogeHoge
```


おまけ: よく使う方法
----

下記は私がよく使う方法です。まず、`logcat -c` でこれまでのログをクリアしておいて、自分のアプリのタグだけのログ表示に絞り込んでいます。

```console
$ adb logcat -c && adb logcat -s MyApp:*
```


Eclipse の LogCat ウィンドウの場合
----

Warning 以上のレベルのログだけを表示するには、Eclipse の LogCat ウィンドウの "W" のボタンを押します。
タグ名でフィルタするには、LogCat ウィンドウの "+" ボタンを押して、タグ名のフィルタを追加します。

