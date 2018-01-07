---
title: 任意の View をフォーカスする
date: "2015-03-14"
---

`View#requestFocus()` によって、任意のビューにフォーカスを当てることができます。
メインスレッドから呼び出す必要があることに注意してください。

~~~ java
// Button mButton;

mButton.post(new Runnable() {
    @Override
    public void run() {
        mButton.requestFocus();
    }
}
~~~

