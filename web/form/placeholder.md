---
title: "プレースホルダーで入力フォームに補足説明を表示する（placeholder 属性）"
created: 2017-12-10
description: "入力フォームに placeholder 属性を指定すると、入力例などの補足説明をあらかじめ表示しておくことができます。"
---

下記の例では、テキスト系の入力フォームの `placeholder` 属性を指定して、何も値が入力されていないときに入力例を表示するようにしています。

~~~ html
<form>
  <label>メール: <input type="email" placeholder="例: maku@example.com"></label><br>
  <label>電話番号: <input type="tel" placeholder="例: 11122223333"></label><br>
  <label>URL: <input type="url" placeholder="例: https://example.com/"></label><br>
  <input type="submit" value="送信する">
  <input type="reset" value="リセット">
</form>
~~~

### デモ
<iframe class="maku-htmlDemo" src="placeholder-demo.html"></iframe>
<a target="_blank" href="placeholder-demo.html">デモページを開く</a>


placeholder のテキスト色を変更する
----

プレースホルダーとして表示されるテキストの色は、薄めの色に変更しておくのがよいかもしれません。
CSS でテキストの色を指定することができます。

~~~ css
::-webkit-input-placeholder { color: pink; } /* Chrome, Opera, Safari */
::-moz-placeholder { color: pink; } /* Firefox 19+ */
:-moz-placeholder { color: pink; } /* Firefox 18- */
:-ms-input-placeholder { color: pink; } /* IE 10+ */
~~~

placeholder の内容は入力例に留める
----

プレースホルダーによるテキストが表示されるのは、入力が何も行われていない状況だけに限られます。
ユーザが何らかの文字を入力してしまうと、プレースホルダーのテキストは消えてしまうので、**必ず読んで欲しい情報（項目名や入力条件など）は、常時表示される通常のテキストとして表示すべき**です。
プレースホルダーで表示するテキストは、入力例などの補足程度にしておくのが無難です。

