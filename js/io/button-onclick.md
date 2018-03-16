---
title: "ボタンを押したときのイベントをハンドルする (onclick)"
date: "2012-11-29"
---

下記のサンプルでは、HTML のボタン要素 (button) をクリックしたときに、アラートメッセージを表示しています。

#### 実装例 (1) JavaScript コードからハンドラを設定する方法

~~~ html
<button id="btn1">Click me</button>

<script>
window.onload = function() {
  document.getElementById('btn1').onclick = handleClick;
  function handleClick(message) {
    alert(message);
  }
};
</script>
~~~

#### 実装例 (2) button 要素の onclick 属性にコードを記述する方法

~~~ html
<button onclick="handleClick('Pushed')">Click me</button>

<script>
function handleClick(message) {
  alert(message);
}
</script>
~~~

#### デモ

<button onclick="handleClick('Pushed')">Click me</button>
<script>
function handleClick(message) {
  alert(message);
}
</script>

