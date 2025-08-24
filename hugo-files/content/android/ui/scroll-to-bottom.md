---
title: "Androidメモ: ScrollView を一番下までスクロールさせる (fullScroll)"
url: "p/e6fmtba/"
date: "2015-03-14"
tags: ["android"]
aliases: ["/android/ui/scroll-to-bottom.html"]
---

Android の `ScrollView` を一番下までスクロールさせるには、**`ScrollView#fullScroll()`** メソッドを使用します。

```java
// final ScrollView scrollView = ((ScrollView) findViewById(R.id.scrollView));
scrollView.post(new Runnable() {
    @Override
    public void run() {
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
});
```

`fullScroll()` はメインスレッドから呼び出す必要があるため、`post()` メソッドを使用して UI スレッドで実行されるようにします。
`fullScroll()` を呼び出した後は、その `ScrollView` にフォーカスが当たった状態になります。

