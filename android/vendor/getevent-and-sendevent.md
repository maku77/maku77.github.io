---
title: getevent/sendevent で入力デバイスへの入力情報を取得する／入力を行う
date: "2010-11-15"
---

getevent により入力デバイスへの入力情報を監視する
----

`adb shell` から以下のように実行します。

```
# getevent
add device 1: /dev/input/event0
  name  "Xxx"
```

その後、入力待ち状態になるので、何かキーを入力すると以下のように表示されます。

```
/dev/input/event0: 001 0067 00000001
/dev/input/event0: 001 0067 00000000
/dev/input/event0: 001 006c 00000001
/dev/input/event0: 001 006c 00000000
```

`0067` や `006c` という部分が、key layout ファイルで定義されている値です。例えば、`reference.kl` といったファイルに以下のように定義されていたりします。

```
# Scancode  # Keycode
...
key 103     DPAD_UP       WAKE_DROPPED
key 108     DPAD_DOWN     WAKE_DROPPED
...
```

`103` の 16 進表記が `0067` と一致します。`WAKE_DROPPED` は、ディスプレイ OFF 状態からそのキーで復帰（ディスプレイ ON）することを示し、さらに、そのキーはアプリに届かずに捨てられることを示しています。


sendevent により入力デバイスへの入力を行う
----

`getevent` コマンドで例えば、上キーを押した場合に以下のように表示されたとします。

```
/dev/input/event2: 0001 0067 00000001
/dev/input/event2: 0001 0067 00000000
```

`sendevent` コマンドを使用すると、上記のような入力をコマンドで再現することができます。

```
# sendevent /dev/input/event2 1 103 1
# sendevent /dev/input/event2 1 103 0
```

2 行目のリリースコマンドを送らないと、キーを押したままの状態になってしまいますので注意してください。

