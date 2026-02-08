---
title: "JavaScriptメモ: テキストボックスでキー入力したときのイベントをハンドルする"
url: "p/i6x3cm6/"
date: "2012-11-29"
tags: ["javascript"]
aliases: [/js/io/textbox-key-event.html]
---


下記のサンプルでは、テキストボックス内で一文字入力するごとに、すぐ下に同じ文字列を表示します。

```html
<p>
    <input type="text" id="txt1" size="50" />
    <pre id="output">Message will be shown here</pre>
</p>

<script>
window.onload = function() {
  var output = document.getElementById('output');
  document.getElementById('txt1').onkeyup = handleKeyUp;

  function handleKeyUp() {
    output.innerHTML = this.value;
  }
};
</script>
```


#### デモ

<p>
    <input type="text" id="txt1" size="50" />
    <pre id="output">Message will be shown here</pre>
</p>

<script>
window.onload = function() {
  var output = document.getElementById('output');
  document.getElementById('txt1').onkeyup = handleKeyUp;

  function handleKeyUp() {
    output.innerHTML = this.value;
  }
};
</script>

