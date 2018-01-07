---
title: "入力必須の入力フォームを指定する（required 属性）"
date: "2017-12-10"
description: "入力フォームの要素に、required 属性を指定しておくと、その要素が入力必須であることを示すことができます。"
---

下記のサンプルでは、先頭の２つの要素に `required` 属性を指定し、そのフィールドの入力が必須であることを Web ブラウザに知らせています。
`required` 属性が指定された入力要素の振る舞いがどうなるかは、Web ブラウザの実装によって異なりますが、例えば、マウスカーソルを重ねたときにポップアップメッセージを表示する、必須フィールドの入力を行わずに Submit ボタンを押したときにエラーメッセージを表示する、といった実装が一般的のようです。

~~~ html
<form>
  <label>お名前: <input type="text" required></label>（必須）<br>
  <label>メール: <input type="email" required></label>（必須）<br>
  <label>電話番号: <input type="tel"></label><br>
  <input type="submit" value="送信する">
  <input type="reset" value="リセット">
</form>
~~~

下記のデモで、入力フィールドにマウスカーソルを置いたり、何も入力せずに送信ボタンを押したりしてみてください。

### デモ
<iframe class="maku-htmlDemo" src="required-demo.html"></iframe>
<a target="_blank" href="required-demo.html">デモページを開く</a>


required 属性が指定された要素に「必須」の表示を行う
----

`required` 属性が付加された要素は、CSS の疑似クラス `:required` で選択することができます。
逆に、`required` 属性が付加されていない要素は、疑似クラス `:optional` で選択することができます。
下記のサンプルでは、`required` 属性のついた `input` 要素の背景色を変更しています。

~~~ css
input:required {
  background: pink;
}
~~~

### デモ
<iframe class="maku-htmlDemo" src="required-demo2.html"></iframe>
<a target="_blank" href="required-demo2.html">デモページを開く</a>

<div class="note">
CSS の <code>::before</code> や <code>::after</code> といった疑似要素と組み合わせて使用することで、自動的に <code>required</code> な入力フィールドに「必須」というテキストを付加をしたくなるところですが、置換要素 (replaced elements) である <code>input</code> 要素は、<code>::before</code> や <code>::after</code> などの疑似要素と組み合わせて使用することができません。
素直に <code>label</code> 要素内のテキストに「必須」という文字列を含めてしまうのがよいでしょう。
</div>
