---
title: "JavaScriptメモ: プルダウンリストから項目を選択したときのイベントをハンドルする (onchange)"
url: "p/6uju8p3/"
date: "2012-11-28"
tags: ["javascript"]
aliases: [/js/io/pulldown-onchange.html]
---

下記のサンプルでは、プルダウンリストから項目を選択したときに、その項目の `value` 属性の値をすぐ下に表示しています。

```html
<select id="sel1">
  <option value="apple">Apple is red</option>
  <option value="lemon">Lemon is yellow</option>
  <option value="peach">Peach is pink</option>
</select>
<p id="message">Message will be shown here</p>

<script>
function handleChange() {
  document.getElementById('message').innerHTML = this.value;
}
const sel = document.getElementById('sel1');
sel.onchange = handleChange;
sel.onkeyup = handleChange;  // for Firefox
</script>
```

#### デモ

<select id="sel1">
  <option value="apple">Apple is red</option>
  <option value="lemon">Lemon is yellow</option>
  <option value="peach">Peach is pink</option>
</select>
<p id="message">Message will be shown here</p>
<script>
function handleChange() {
  document.getElementById('message').innerHTML = this.value;
}
var sel = document.getElementById('sel1');
sel.onchange = handleChange;
sel.onkeyup = handleChange;  // for Firefox
</script>

