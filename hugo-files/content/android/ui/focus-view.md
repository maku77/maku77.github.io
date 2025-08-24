---
title: "Androidメモ: 任意の View をフォーカスする (requestFocus)"
url: "p/fnjj2c7/"
date: "2015-03-14"
tags: ["android"]
aliases: ["/android/ui/focus-view.html"]
---

`View#requestFocus()` によって、任意のビューにフォーカスを当てることができます。
メインスレッドから呼び出す必要があることに注意してください。

```java
// Button mButton;

mButton.post(new Runnable() {
    @Override
    public void run() {
        mButton.requestFocus();
    }
}
```

