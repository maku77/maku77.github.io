---
title: "Androidベンダー向けメモ: コンソールからキーの入出力を行う (getevent/sendevent)"
url: "p/gufweuc/"
date: "2010-11-15"
tags: ["Android"]
aliases: /android/vendor/getevent-and-sendevent.html
---

getevent により入力デバイスへの入力情報を監視する
----

`adb shell` で Android デバイスに接続した状態で **`getevent`** コマンドを実行すると、ユーザーの各種入力（主にキー操作）を監視することができます。

```console
$ adb shell getevent
add device 1: /dev/input/event0
  name  "Xxx"
```

上記のように実行すると入力待ち状態になるので、何かキーを入力すると以下のように表示されます。

```
/dev/input/event0: 001 0067 00000001
/dev/input/event0: 001 0067 00000000
/dev/input/event0: 001 006c 00000001
/dev/input/event0: 001 006c 00000000
```

`0067` や `006c` という部分が、key layout ファイルで定義されている値です。例えば、`reference.kl` といったファイルに次のように定義されていたりします。

{{< code title="reference.kl（抜粋）" >}}
# Scancode  # Keycode
...
key 103     DPAD_UP       WAKE_DROPPED
key 108     DPAD_DOWN     WAKE_DROPPED
...
{{< /code >}}

`103` の 16 進表記が `0067` と一致します。
`WAKE_DROPPED` は、ディスプレイ OFF 状態からそのキーで復帰（ディスプレイ ON）することを示し、さらに、そのキーはアプリに届かずに捨てられることを示しています。


sendevent により入力デバイスへの入力を行う
----

__`sendevent`__ コマンドを使用すると、ユーザー入力をコマンドで再現することができます。
まず、再現したいユーザー入力を `getevent` コマンドで調べておきます。
例えば、D-Pad の上キーの入力を再現したい場合は、`getevent` コマンドを実行した状態で実際に上キーを押して、その出力を調べます。

{{< code title="上キーを押したときの getevent の出力" >}}
/dev/input/event2: 0001 0067 00000001
/dev/input/event2: 0001 0067 00000000
{{< /code >}}

この出力を真似して、次のように `sendevent` コマンドを実行すれば、上キーの入力（押して離す）を再現することができます。
引数の数値は、16 進数 → 10 進数変換したものを指定していることに注意してください。

```console
# sendevent /dev/input/event2 1 103 1
# sendevent /dev/input/event2 1 103 0
```

2 行目のリリースコマンドを送らないと、キーを押したままの状態になってしまうので注意してください。

