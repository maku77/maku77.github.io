---
title: ScrollView を一番下までスクロールさせる
created: 2015-03-14
---

`ScrollView#fullScroll()` を使って、`ScrollView` の一番下に移動することができます。

~~~ java
// final ScrollView scrollView = ((ScrollView) findViewById(R.id.scrollView));
scrollView.post(new Runnable() {
    @Override
    public void run() {
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
});
~~~

メインスレッドから呼ばないといけないことに注意してください。
`fullScroll()` を呼び出した後は、`ScrollView` にフォーカスが当たった状態になります。

