---
title: "Hugo のショートコードの中からフロントマターのパラメータを参照する ($.Page.Params)"
url: "p/t4sdxi3/"
date: "2017-10-02"
description: "Hugo のショートコードの中で $.Page.Params を参照すると、コンテンツファイルのフロントマターに記述したパラメータにアクセスすることができます。"
tags: ["Hugo"]
aliases: /hugo/shortcode/frontmatter-params.html
---

Hugo のショートコードの中で __`$.Page.Params`__ を参照すると、コンテンツファイルのフロントマターに記述したパラメータにアクセスすることができます。
下記の `hello` ショートコードは、コンテンツファイル (.md) のフロントマターに記述された `title` パラメータと `date` パラメータの値を表示します。

{{< code lang="html" title="layouts/shortcodes/title-and-date.html" >}}
{{ $.Page.Params.title }}<br>
{{ $.Page.Params.date | dateFormat "Mon, 02 Jan 2006" }}
{{< /code >}}

{{% note %}}
時刻情報は __`dateFormat`__ 関数に渡すことで、任意のフォーマットで出力することができます。
パイプで渡すのではなく、関数の第 2 パラメータとして次のように渡すこともできます。

```
{{ dateFormat "Mon, 02 Jan 2006" $.Page.Params.date }}
```

Go 言語の時刻の扱いに関しては [こちらを参照](/p/sy58beh/) してください。
{{% /note %}}

作成した `title-and-date` ショートコードは、コンテンツファイルの中から次のように使用します。

{{< code lang="yaml" title="content/page1.md" hl_lines="6" >}}
---
title: "ページタイトル"
date: "2017-10-02"
---

{{</* title-and-date */>}}
{{< /code >}}

上記のショートコードの部分は、HTML ファイル出力時に次のように展開されます。

```html
Page1 Title<br>
Mon, 02 Oct 2017
```

