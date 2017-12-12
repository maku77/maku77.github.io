---
title: "入力フォームに自動的にフォーカスする（autofocus 属性）"
created: 2017-12-10
description: "入力フォームの要素に、autofocus 属性を指定しておくと、そのフォームが表示されたときに自動的にフォーカスが当たるようになります。"
---

下記の例では、３番目の `input` 要素に `autofocus` 属性を指定しているので、デフォルトでその要素にフォーカスが当たるようになります。

~~~ html
<form>
  <label>日付: <input type="date"></label><br>
  <label>時間: <input type="time"></label><br>
  <label>メール: <input type="email" autofocus></label><br>
  <label>電話番号: <input type="tel"></label><br>
  <label>URL: <input type="url"></label><br>
  <input type="submit" value="送信する">
  <input type="reset" value="リセット">
</form>
~~~

<div class="note">
<code>autofocus</code> 属性には値を指定する必要はありません。
タグの中に単純に <code>autofocus</code> とだけ記述しておくだけで OK です。
</div>

### デモ
<iframe class="maku-htmlDemo" src="autofocus-demo.html"></iframe>
<a target="_blank" href="autofocus-demo.html">デモページを開く</a>

