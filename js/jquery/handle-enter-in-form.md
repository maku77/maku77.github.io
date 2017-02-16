---
title: jQuery でフォーム内の input 要素で Enter キーを押したときのイベントをハンドルする
created: 2013-10-29
---

#### HTML サンプル

~~~ html
<form id="myForm">
  <input type="text" id="memo" size="50"></input>
  <button type="submit" id="btnSubmit">Submit</button>
</form>
~~~

例えば、上記のようなフォームの input 領域で、Enter キーを押したときと、Submit ボタンを押したときのイベントを同じように処理したいのであれば、以下のような jQuery コードでハンドルします。

~~~ javascript
$('#myForm').submit(function(e) {
    e.preventDefault();  // デフォルト挙動をキャンセル
    // 何らかの処理
});
~~~

このように `submit` イベントのハンドラを設定しておけば、ボタン要素がない場合でも Enter キーの入力を捕捉することができます。

一方で、次のようにボタンの `click` イベントをハンドルする方法だと、ブラウザによっては、input 領域で Enter キーを押したときにイベントが発生しないので注意してください。

~~~ javascript
$('#btnSubmit').click(function(e) {
    ...
});
~~~

