---
title: "ショートコードの中からフロントマターのパラメータを参照する ($.Page.Params)"
created: 2017-10-02
description: "ショートコードの中で $.Page.Params を参照すると、コンテンツファイルのフロントマターに記述したパラメータにアクセスすることができます。"
---

下記の `hello` ショートコードは、コンテンツファイル (.md) のフロントマターに記述された `title` パラメータと `date` パラメータの値を表示します。

#### /layouts/shortcodes/title-and-date.html

~~~ html
{{ "{{" }} $.Page.Params.title }}<br>
{{ "{{" }} $.Page.Params.date | dateFormat "Mon, 02 Jan 2006" }}
~~~

<div class="note">
時刻は <code>dateFormat</code> 関数に渡して、表示フォーマットを指定しています。
パイプで渡すのではなく、関数の第二パラメータとして
<code>{{ "{{" }} dateFormat "Mon, 02 Jan 2006" $.Page.Params.date }}</code>
のように渡すこともできます。
</div>

Go 言語の時刻の扱いに関しては[こちらを参照](../go/time.html)してください。

作成した `title-and-date` ショートコードは、コンテンツファイルの中からは次のような感じで利用します。

#### /content/posts/page1.md

~~~ md
---
title: "Page1 Title"
date: 2017-10-02
---

{{ "{{" }}< title-and-date >}}
~~~

上記のショートコードの部分は、下記のように展開されます。

~~~ html
Page1 Title<br>
Mon, 02 Oct 2017
~~~

