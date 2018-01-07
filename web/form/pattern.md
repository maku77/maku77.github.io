---
title: "独自の入力フォーマットを指定する（pattern 属性）"
date: "2017-12-10"
---

`input` 要素の `type` 属性を `text` に設定しておくと、単純なテキスト入力フィールドが表示されますが、下記のように、より具体的な入力データの種類を設定しておくこともできます。

#### input 要素の type 属性の種類

* **number:** 数値（PC では右端にスピンボタンが表示される）。min、max、step 属性の指定が可能。
* **email:** メールアドレス
* **url:** URL
* **tel:** 電話番号
* **date:** 日付（カレンダーから日付を選択できる）
* **time:** 時刻
* **search:** 検索テキスト
* **range:** スライダー（連続値を設定する）

上記のような値を `type` 属性に設定しておくと、それぞれのデータ入力に適した UI を表示してくれたり、入力したテキストのフォーマットをチェックしてくれたりするようになります（振る舞いは Web ブラウザによって多少異なります）。
例えば、`type` 属性を `email` に設定しておくと、入力したテキストに `@` が含まれていない場合に警告メッセージを表示してくれるようになります。

~~~ html
<label>E-mail <input type="email"></label>
~~~

独自の入力フォーマットを指定する（pattern 属性）
----

`input` 要素の `pattern` 属性を使用すると、独自の入力フォーマットを設定することができます。
`pattern` 属性で指定するパターン文字列は、[JavaScript の正規表現](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/RegExp) エンジンで処理されます。
下記の例では、「ユーザ ID」と「日付」のフィールドに、独自の入力フォーマットを設定しています（「メール」のフィールドには `pattern` 属性を指定していないため、`type="email"` 用のデフォルトの入力フォーマットが使用されます）。

#### 例: HTML の form 要素抜粋
~~~ html
<label>
  ユーザーID:
  <input type="text" name="userid" pattern="^[0-9A-Za-z]+$" required>
  （半角英数）※必須
</label><br>
<label>
  メール:
  <input type="email" required>
  ※必須
</label><br>
<label>
  日付:
  <input type="text" pattern="\d\d\d\d-\d\d-\d\d">
  （YYYY-MM-DD）
</label><br>
~~~

<div class="note">
<code>pattern</code> 属性はテキスト系の入力フィールドに対して使用するもののため、<code>type</code> 属性の値が <code>text</code>、<code>search</code>、<code>tel</code>、<code>url</code>、<code>email</code>、<code>password</code> である場合のみ機能します（それ以外のケースでは無視されます）。
</div>

### デモ
<iframe class="maku-htmlDemo" src="pattern-demo.html"></iframe>
<a target="_blank" href="pattern-demo.html">デモページを開く</a>


入力フォーマットを満たしているかを調べる CSS 疑似クラス
----

CSS の疑似クラスである `:valid` と `:invalid` を使用すると、`input` 要素に入力したテキストが、`pattern` 属性で指定したフォーマットに合致しているかどうかに応じてスタイルを適用することができます。
下記の例では、`input` 要素に入力したテキストが、フォーマットを満たしていない場合に背景色がピンクになるように設定しています。

#### CSS

~~~ css
input[type=text]:invalid, input[type=email]:invalid {
  background: pink;
}
~~~

<div class="note">
<code>type</code> 属性が <code>email</code> に設定されている <code>input</code> 要素の場合は、デフォルトで組み込まれているメールアドレス用のフォーマットで入力チェックが行われます。
<code>pattern</code> 属性で独自の入力フォーマットを指定した場合は、そちらを利用して入力チェックが行われます。
</div>

下記の表示サンプルでは、初期状態で「ユーザ ID」と「メール」の入力フィールドが invalid 状態になっていると思います。
これは、それらの `input` 要素に `required` 属性が設定されているからです。
入力が必須とされているフィールドに何も入力されていない場合は、invalid 状態とみなされます。

### デモ
<iframe class="maku-htmlDemo" src="pattern-demo2.html"></iframe>
<a target="_blank" href="pattern-demo2.html">デモページを開く</a>
