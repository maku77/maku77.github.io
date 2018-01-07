---
title: キーイベント (KeyEvent) を見やすく出力する
date: "2014-07-28"
---

`View` に対するキー入力は、`View#dispatchKeyEvent(KeyEvent event)` などでハンドルすることができます。
このとき渡される `KeyEvent` オブジェクトの詳しい内容は、`toString()` メソッドを使ってテキスト形式で取得することができます。
例えば、下記は `Shift+A` を入力したときに `dispatchKeyEvent()` に渡される `KeyEvent` オブジェクトの内容を `toString()` を利用して出力した例です。

~~~
KeyEvent: KeyEvent { action=ACTION_DOWN, keyCode=KEYCODE_SHIFT_LEFT, scanCode=0, metaState=META_SHIFT_ON|META_SHIFT_LEFT_ON, flags=0x0, repeatCount=0, eventTime=14731488, downTime=14731488, deviceId=-1, source=0x101 }
KeyEvent { action=ACTION_DOWN, keyCode=KEYCODE_A, scanCode=0, metaState=META_SHIFT_ON|META_SHIFT_LEFT_ON, flags=0x0, repeatCount=0, eventTime=14731488, downTime=14731488, deviceId=-1, source=0x101 }
KeyEvent { action=ACTION_UP, keyCode=KEYCODE_A, scanCode=0, metaState=META_SHIFT_ON|META_SHIFT_LEFT_ON, flags=0x0, repeatCount=0, eventTime=14731488, downTime=14731488, deviceId=-1, source=0x101 }
KeyEvent { action=ACTION_UP, keyCode=KEYCODE_SHIFT_LEFT, scanCode=0, metaState=0, flags=0x0, repeatCount=0, eventTime=14731488, downTime=14731488, deviceId=-1, source=0x101 }
~~~

詳しい情報が出てくるのですが、ここまでの情報は必要ないことがほとんどです。
そのような場合、下記のようにシンプルに整形して出力しておくとデバッグなどがはかどります。

~~~
action=down |key=59|scan=0|meta=65|repeat=0
action=down |key=29|scan=0|meta=65|repeat=0
action=up   |key=29|scan=0|meta=65|repeat=0
action=up   |key=59|scan=0|meta=0|repeat=0
~~~

下記は、`KeyEvent` オブジェクトから上記のようなテキスト表現を生成するメソッドです。

~~~ java
public static String prettyKeyEvent(KeyEvent event) {
    StringBuilder sb = new StringBuilder();

    switch (event.getAction()) {
    case KeyEvent.ACTION_DOWN:
        sb.append("action=").append("down ");
        break;
    case KeyEvent.ACTION_UP:
        sb.append("action=").append("up   ");
        break;
    case KeyEvent.ACTION_MULTIPLE:
        sb.append("action=").append("multi");
        break;
    }

    sb.append("|key=").append(event.getKeyCode());
    sb.append("|scan=").append(event.getScanCode());
    sb.append("|meta=").append(event.getMetaState());
    sb.append("|repeat=").append(event.getRepeatCount());

    return sb.toString();
}
~~~

