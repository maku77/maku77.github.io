---
title: "Hugo テンプレートで if、with による分岐処理を行う"
created: 2017-12-11
description: "Hugo テンプレートの中で、if アクションや with アクションを使用することで、分岐処理を行うことができます。"
---

if による分岐処理
----

Hugo のテンプレート内で分岐処理を行うには、下記のように if-else を記述します。

~~~
{{ "{{" }} if x }} ... {{ "{{" }} end }}
{{ "{{" }} if x }} ... {{ "{{" }} else }} ... {{ "{{" }} end }}
{{ "{{" }} if x }} ... {{ "{{" }} else if y }} ... {{ "{{" }} end }}
{{ "{{" }} if x }} ... {{ "{{" }} else if y }} ... {{ "{{" }} else }} ... {{ "{{" }} end }}
~~~

`{{ "{{" }}` と `}}` で囲まれた部分は、Go のテンプレートでは**アクション**と呼ばれている部分で、基本的に１つのアクションだけを記述することができます（関数をパイプで結んだりすることはできる）。
`if` による条件文を記述する部分と、`end` でブロックを閉じる部分は、上記のように分けて記述する必要があるため、若干まわりくどくなってしまうケースがあるかもしれませんが、そこは我慢するしかありません。

if の条件文において「偽」とみなされる値は、**空値 (empty value)** です。
空値となるのは下記のような値です。

* `false`
* `0` とみなされる数値（`0.0` も含む）
* サイズ 0 の配列、スライス、マップ
* 空文字列 (`""`)
* `nil` ポインタ

#### 例: 真とみなされる例（T と表示される）

~~~ go
{{ "{{" }} if true  }} T {{ "{{" }} else }} F {{ "{{" }} end }}
{{ "{{" }} if "ABC" }} T {{ "{{" }} else }} F {{ "{{" }} end }}
{{ "{{" }} if 1     }} T {{ "{{" }} else }} F {{ "{{" }} end }}
{{ "{{" }} if -1    }} T {{ "{{" }} else }} F {{ "{{" }} end }}
~~~

#### 例: 偽とみなされる例（F と表示される）

~~~ go
{{ "{{" }} if false }} T {{ "{{" }} else }} F {{ "{{" }} end }}
{{ "{{" }} if ""    }} T {{ "{{" }} else }} F {{ "{{" }} end }}
{{ "{{" }} if 0     }} T {{ "{{" }} else }} F {{ "{{" }} end }}
{{ "{{" }} if 0.0   }} T {{ "{{" }} else }} F {{ "{{" }} end }}
~~~

条件文の部分で、値の比較を行いたい場合は、下記のような `eq` や `ne` といった二値関数 (boolean functions) を使用します。
比較する２つの値は、後ろに続けて記述することに注意してください。

`eq ARG1 ARG2`
: ARG1 ＝ ARG2 のとき （参考: [eq 関数](https://gohugo.io/functions/eq/)）

`ne ARG1 ARG2`
: ARG1 ≠ ARG2 のとき （参考: [ne 関数](https://gohugo.io/functions/ne/)）

`lt ARG1 ARG2`
: ARG1 ＜ ARG2 のとき （参考: [lt 関数](https://gohugo.io/functions/lt/)）

`le ARG1 ARG2`
: ARG1 ≦ ARG2 のとき （参考: [le 関数](https://gohugo.io/functions/le/)）

`gt ARG1 ARG2`
: ARG1 ＞ ARG2 のとき （参考: [gt 関数](https://gohugo.io/functions/gt/)）

`ge ARG1 ARG2`
: ARG1 ≧ ARG2 のとき （参考: [ge 関数](https://gohugo.io/functions/ge/)）


次の例では、数値変数の値が 100 より大きいかを、`gt` 関数を使用して調べています。

#### 例: 変数 $x の値が 100 より大きいか調べる

~~~
{{ "{{" }} $x := 200 }}
{{ "{{" }} if gt $x 100 }}
  x is greater than 100
{{ "{{" }} end }}
~~~

文字列変数の比較にも `eq`（等しい）や `ne`（等しくない）を使用することができます。

#### 例: 変数 $s の値が "hello" かどうか調べる

~~~
{{ "{{" }} $s := "hello" }}
{{ "{{" }} if eq $s "hello" }}
  s is hello
{{ "{{" }} end }}
~~~


with による分岐処理
----

`if` アクションの代わりに、`with` アクションを使用して分岐処理を記述すると、ブロックの中のコンテキスト（ドット `.`）を、条件文で評価した値に置き換えることができます。

~~~
{{ "{{" }} with x }} ... {{ "{{" }} end }}
{{ "{{" }} with x }} ... {{ "{{" }} else }} ... {{ "{{" }} end }}
~~~

使い方は、例を見たほうが早いかもしれません。
次の例では、`with` アクションの条件文で `Maku` という文字列が指定されているため、ブロックの中でドット (`.`) を参照することで、`Maku` という文字列を取得することができます。

#### 例: テンプレート内で with アクションを利用する

~~~ go
{{ "{{" }} with "Maku" }}
  {{ "{{" }} printf "I am %s" . }}
{{ "{{" }} end }}
~~~

#### 実行結果

~~~
I am Maku
~~~

通常、ドット (`.`) はトップレベルのコンテキスト（Hugo のレイアウトファイルの中では通常 `Page` オブジェクト）を表しますが、上記のように `with` アクションを使用することで、コンテキストを切り替えて、ブロック内の記述をシンプルにすることができます。

逆に、`with` ブロックでコンテキストを切り替えた後に、トップレベルのコンテキストを明示的に参照したい場合は、次のように `$` プレフィックスを指定して参照します。

#### 例: with ブロックの中からトップレベルコンテキストを参照する

~~~ go
{{ "{{" }} with "Maku" }}
  {{ "{{" }} printf "Hello, %s. Page title is %s" . $.Title }}
{{ "{{" }} end }}
~~~

#### 実行結果

~~~
Hello, Maku. Page title is SAMPLE_TITLE.
~~~


### よくある例

下記のサンプルは、Web サイトの設定ファイル (`config.toml`) に、サイトの説明文や作者が設定されている場合に、それを meta 要素として出力する例です。

#### テンプレートの記述例

~~~
{{ "{{" }} with .Site.Params.author -}}
  <meta name="author" content="{{ "{{" }} . }}">
{{ "{{" }}- end }}
{{ "{{" }} with .Site.Params.description -}}
  <meta name="description" content="{{ "{{" }} . }}">
{{ "{{" }}- end }}
~~~

#### サイトの設定ファイルの記述例 (config.toml)

~~~
title = "My New Hugo Site"

[params]
  author = "Maku"
  description = "This is my first Hugo site. It's very cool."
~~~

#### 出力結果

~~~ html
<meta name="author" content="Maku">
<meta name="description" content="This is my first Hugo site. It&#39;s very cool.">
~~~

クォーテーションマークが、出力箇所のコンテキストに応じて、自動的にエスケープ処理されてますね。クール！

