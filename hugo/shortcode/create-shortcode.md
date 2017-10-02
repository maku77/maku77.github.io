---
title: "独自のショートコードを作成する"
created: 2017-09-29
description: "独自のショートコードを作成すると、定型の HTML コードを記事内に簡単に埋め込めるようになります。"
---

ショートコード作成の基本
----

ショートコードは、`/layouts/shortcodes/` ディレクトリ内に作成しておきます。
ファイル名から拡張子を除いたものが、ショートコード名となります。

#### layouts/shortcodes/myshortcode.html

~~~ html
This is my first short code.
~~~

記事の中から、下記のように呼び出すと、上記の内容がそこに展開されます。

#### content/page1.md

~~~ html
{{ "{{" }}< myshortcode >}}
~~~


ショートコードにパラメータを渡す
----

ショートコード呼び出し時に、パラメータを渡すことができます。
下記の例では、２つのパラメータ `red`、`32px` を渡しています。

~~~ html
{{ "{{" }}< myshortcode red 32px >}}
~~~

渡されたパラメータは、ショートコードの中から、`{{ "{{" }} .Get インデックス }}` で取得することができます。

#### layouts/shortcodes/myshortcode.html

~~~ html
<div style="color:{{ "{{" }} .Get 0 }}; font-size:{{ "{{" }} .Get 1 }};">
    This is my first short code.
</div>
~~~

上記の例では、参照するパラメータをインデックス番号 (0, 1, 2, ...) で指定していますが、２つ以上のパラメータを持つショートコードを作成するときは、名前付きパラメータの仕組みを使うと分かりやすくなります。

~~~ html
{{ "{{" }}< myshortcode color="red" size="32px" >}}
~~~

名前付きパラメータとして渡された値を参照するには、インデックス番号の代わりにその名前を指定します。

#### layouts/shortcodes/myshortcode.html

~~~ html
<div style="color:{{ "{{" }} .Get `color` }}; font-size:{{ "{{" }} .Get `size` }};">
    This is my first short code.
</div>
~~~

パラメータの名前は**バッククォート (`)** で囲むことに注意してください（ダブルクォーテーションでは囲めません）。


ショートコードにテキストデータを渡す
----

ショートコードの開始タグと終了タグで任意のテキスト（内部テキスト）を囲むと、ショートコードのテンプレート内からそのテキストを参照することができます。
下記は、`caution` という独自ショートコードにテキストを渡す例です。

~~~ html
{{ "{{" }}< caution >}}
この発射ボタンを押さないでください。
{{ "{{" }}< /caution >}}
~~~

開始タグと終了タグで囲まれた内部テキストは、ショートコードの中から `{{ "{{" }} .Inner }}` で参照することができます。

#### layouts/shortcodes/caution.html

~~~ html
<div style="color:red;">
  {{ "{{" }} .Inner }}
</div>
~~~

内部テキストを最終的に Markdown プロセッサで処理してもらいたい場合は、下記のように `%` を使用した呼び出し方をする必要があります。

~~~ html
{{ "{{" }}% caution %}}
この**発射ボタン**を押さないでください。
{{ "{{" }}% /caution %}}
~~~


テーマ用のショートコード
----

ショートコード用の HTML ファイルは、`/layouts/shortcodes/` ディレクトリに配置することで、任意のコンテンツ内から利用できるようになります。
ショートコード用の HTML ファイルをテーマの一部（付属品）として提供したい場合は、下記のディレクトリに配置してください。

~~~
/themes/<THEME>/layouts/shortcodes/<SHORTCODE>.html
~~~

同じ名前のショートコードが `/layouts/shortcodes/` ディレクトリにも存在する場合は、そちらが優先して使用されます。

