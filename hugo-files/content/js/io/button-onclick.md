---
title: "JavaScriptメモ: ボタンを押したときのイベントをハンドルする (onclick)"
url: "p/i5hwe49/"
date: "2012-11-29"
tags: ["javascript"]
aliases: [/js/io/button-onclick.html]
---

下記のサンプルでは、HTML のボタン要素 (button) をクリックしたときに、アラートメッセージを表示しています。

{{< code lang="html" title="実装例 (1) JavaScript コードからハンドラを設定する方法" >}}
<button id="btn1">Click me</button>

<script>
window.onload = () => {
  document.getElementById('btn1').onclick = handleClick;
  function handleClick(message) {
    alert(message);
  }
};
</script>
{{< /code >}}

{{< code lang="html" title="実装例 (2) button 要素の onclick 属性にコードを記述する方法" >}}
<button onclick="handleClick('Pushed')">Click me</button>

<script>
function handleClick(message) {
  alert(message);
}
</script>
{{< /code >}}

#### デモ

<button onclick="handleClick('Pushed')">Click me</button>
<script>
function handleClick(message) {
  alert(message);
}
</script>

