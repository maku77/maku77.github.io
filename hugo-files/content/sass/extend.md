---
title: "Sassメモ: エクステンドでスタイルを継承する (@extend)"
url: "p/nkv6w5b/"
date: "2018-12-19"
tags: ["sass"]
description: "@extend ディレクティブを使用すると、他のセレクタに適用されているスタイル定義を継承して使用することができます。"
aliases: ["/sass/extend.html"]
---

Sass (SCSS) の **`@extend`** ディレクティブを使用すると、他のセレクタに適用されているスタイル定義を継承して使用することができます。

`@extend` の基本
----

例えば、`.sample` クラスのスタイル定義の中で下記のように **`@extend`** ディレクティブを使用すると、

```
.sample {
  @extend <セレクタ名>;
}
```

指定したセレクタに設定されているスタイル定義を、`.sample` クラスに継承させることができます。
セレクタ名には、クラス名 (`.foo`) や HTML のタグ名 (`em`) などを引用符で囲まずに指定します。

下記の例では、`.warn` クラスに対して設定したスタイルを、`.error` に継承させています。

{{< code lang="scss" title="入力 (SCSS)" >}}
.warn {
  font-weight: bolder;
  border: solid 3px orange;
  color: orange;
}

.error {
  @extend .warn;
  border-color: red;
  color: red;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.warn, .error {
  font-weight: bolder;
  border: solid 3px orange;
  color: orange;
}

.error {
  border-color: red;
  color: red;
}
{{< /code >}}

これで、`.warn` に適用したスタイルが、`.error` クラスにも適用されるようになります。
`.error` クラスは `.warn` クラスの性質を備えているので、`.error` クラスを使用するときに `.warn` クラスを同時に指定する必要がありません。

```html
<div class="warn">警告メッセージ</div>
<div class="error">エラーメッセージ</div>
```


`@extend` は親クラスのセレクタ構造も継承する
----

`@extend` でスタイルを継承すると、そのスタイルが単純にコピーされたかのように振る舞うだけでなく、特殊化されたスタイル構成も同時に継承されます。
これが、Extend（継承）という名前のディレクティブになっている理由です。

例えば、下記の `.warn` クラスは、装飾用の `.large` クラスと同時に使用することで、追加のスタイルを付加できるようになっています（連結セレクタ `.warn.large` として定義）。
このような構造があるときに、`.error` クラスから `@extend` ディレクティブを使って `.warn` クラスを継承すると、`.error` クラスの方も `.large` クラスと組み合わせて使用できるような構造が生成されます。

{{< code lang="scss" title="入力 (SCSS)" >}}
.warn {
  font-weight: bolder;
  border: solid 3px orange;
  color: orange;
}

.warn.large {
  font-size: large;
}

.error {
  @extend .warn;
  border-color: red;
  color: red;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.warn, .error {
  font-weight: bolder;
  border: solid 3px orange;
  color: orange;
}

.warn.large, .large.error {
  font-size: large;
}

.error {
  border-color: red;
  color: red;
}
{{< /code >}}

結果として、`.large` クラスは、`.warn` クラスだけでなく、`.error` クラスとも同時に使用できるようになります。

{{< code lang="html" title="HTML" >}}
<div class="warn large">大きな警告メッセージ</div>
<div class="error large">大きなエラーメッセージ</div>
{{< /code >}}


`@extend` で継承できるセレクタと継承できないセレクタ
----

`@extend` ディレクティブの構文は、`@extend <セレクタ名>` ですが、この<b>セレクタ名</b>の部分に指定できるセレクタは種類が限られています。
下記に、継承できるセレクタと、継承できないセレクタの種類の一覧を示します。

### 継承できるセレクタ

- <b>型セレクタ（タイプセレクタ）</b> 例: `strong`、`ul`
- <b>ID セレクタ</b> 例: `#main`
- <b>クラスセレクタ</b> 例: `.foo`
- <b>連結セレクタ</b> 例: `.foo.bar`、`p.note`、`#main.bar`
- <b>属性セレクタ</b> 例: `img[src$=".png"]`、`input[type="text"]`、`div[data-foo]`
- <b>疑似クラス</b> 例: `a:hover`、`a:active`、`p:not(.sample)`
- <b>疑似要素</b> 例: `p::first-line`、`blockquote::before`

### 継承できないセレクタ

- <b>子孫セレクタ</b> 例: `p strong`、`.foo .bar`
- <b>子セレクタ</b> 例: `p > strong`、`.foo > .bar`
- <b>隣接セレクタ</b> 例: `h2 + h3`、`.foo + .bar`
- <b>間接セレクタ</b> 例: `h3 ~ h3`、`.foo ~ .bar`

親子構造を示すようなセレクタシーケンスで定義されたルールは継承できないと覚えておけばよいでしょう。


`@extend` 専用のプレースホルダーセレクタ (`%foo`)
----

`@extends` ディレクティブからのみ使用するルールセットを作成するには、プレースホルダーセレクタの仕組みを使用します。
下記のように、**`%`** で始まる名称のセレクタでスタイルを定義しておくと、そのルールセットは `@extend` ディレクティブからの継承専用のルールセットとなります（それ自身のルールセットとしては CSS に出力されません）。

```scss
%foo {
  color: red;
}
```

このプレースホルダーセレクタの仕組みは、`@extend` 専用のスタイルを複数定義した Sass ライブラリを作成するときなどに便利です。
また、部分的に共通のスタイルを適用したいセレクタが多い場合、この仕組みをうまく使用することで SCSS コードの可読性を上げることができます。
プレースホルダーセレクタにいかに分かりやすい名前を付けるかがポイントです。

下記のサンプルでは、ボックスシャドウを設定するためのプレースホルダーセレクタ `%shadow` を定義し、そのスタイルを `button` 要素と `img` 要素に適用しています。

{{< code lang="scss" title="入力 (SCSS)" >}}
%shadow {
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.29);
}

button {
  @extend %shadow;
}

img {
  @extend %shadow;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
button, img {
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.29);
}
{{< /code >}}


`@extend` 対象のセレクタが存在しないときのエラーを抑制する (`!optional`)
----

例えば、下記のように `@extend` で `%shadow` セレクタを継承するように指定したときに、`%shadow` セレクタのルール定義が見つからない場合は、Sass プロセッサはエラーメッセージを表示します。

```scss
button {
  @extend %shadow;
}
```

```
Error: "button" failed to @extend "%shadow".
       The selector "%shadow" was not found.
       Use "@extend %shadow !optional" if the extend should be able to fail.
        on line 2 of input.scss
  Use --trace for backtrace.
```

`@extend` 対象のセレクタが見つからない場合にエラーが発生しないようにするには、**`!optional`** フラグを付けて `@extend` するようにします。

```scss
button {
  @extend %shadow !optional;
}
```

上記の例では、プレースホルダ (`%shadow`) を継承していますが、普通のクラスセレクタ (`.foo`) を狭小する場合も同様に機能します。


`@media` ディレクティブ内での `@extend` の制約
----

メディアクエリを使用して環境別のスタイル定義を行っている場合、`@media` ブロックの中での `@extend` で継承できるセレクタは、同じ `@media` ブロック内で定義されているものに限定されます。

例えば、下記の例では、`@media` ブロック内から、ブロックの外で定義された `.warn` セレクタのスタイルを継承しようとしているのでエラーになります。

{{< code lang="scss" title="間違った例" >}}
.warn {
  font-weight: bolder;
  border: solid 3px orange;
  color: orange;
}

@media print {
  .error {
    @extend .warn;  // エラー！
    border-color: red;
    color: red;
  }
}
{{< /code >}}

```
Error: You may not @extend an outer selector from within @media.
       You may only @extend selectors within the same directive.
```

次のように、同じ `@media` ブロック内で定義されたセレクタであれば `@extend` を使って継承することができます。

```scss
@media print {
  .warn {
    font-weight: bolder;
    border: solid 3px orange;
    color: orange;
  }
  .error {
    @extend .warn;  // OK
    border-color: red;
    color: red;
  }
}
```

これは、メディアクエリの文法による制約です。
将来的な Sass のアップデートにより、`@media` ブロックの外のセレクタも継承できるようになる可能性はあります。
Sass でメディアクエリを使ったルール定義をスマートに行うには、[Mixin の仕組み](/p/awmebxk/)で `@content` ディレクティブを組み合わせて使用する方法がお勧めです。

