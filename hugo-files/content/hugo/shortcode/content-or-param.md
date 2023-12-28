---
title: "Hugo のショートコードで本文とパラメーターの有無で処理を分岐する"
url: "p/b9jivoi/"
date: "2023-12-28"
tags: ["Hugo"]
---

Hugo のショートコードを呼び出すときに値を渡す方法としては、本文（コンテンツ）として渡す方法と、引数（パラメーター）として渡す方法があります。
次のショートコードは、本文や引数の有無によって処理を分岐する方法を示しています。

{{< code lang="go-html-template" title="layouts/shortcodes/my-shortcode.html" >}}
<ul>
  <li>本文: {{ with .Inner }}{{ . }}{{ else }}（なし）{{ end }}</li>
  <li>引数: {{ with .Get 0 }}{{ . }}{{ else }}（なし）{{ end }}</li>
</ul>
{{< /code >}}

このショートコードは、Markdown ファイル内で次のように呼び出します。

{{< code title="content/page.md（抜粋）" >}}
<h3>本文だけある場合</h3>
{{</* my-shortcode >}}これは本文{{< /my-shortcode */>}}

<h3>引数だけある場合</h3>
{{</* my-shortcode "これは引数" /*/>}}

<h3>本文と引数の両方がある場合</h3>
{{</* my-shortcode "これは引数" >}}これは本文{{< /my-shortcode */>}}
{{< /code >}}

{{< code lang="html" title="出力結果" >}}
<ul>
  <li>本文: これは本文</li>
  <li>引数: （なし）</li>
</ul>

<h3>引数だけある場合</h3>
<ul>
  <li>本文: （なし）</li>
  <li>引数: これは引数</li>
</ul>

<h3>本文と引数の両方がある場合</h3>
<ul>
  <li>本文: これは本文</li>
  <li>引数: これは引数</li>
</ul>
{{< /code >}}

{{% note title="self-closed エラー" %}}
ショートコードの呼び出し時に引数だけを指定する場合は、self-closed というちょっと特殊な書き方をしなければいけないようです（末尾をスラッシュで終わる）。
これは、ショートコードの中で `.Inner`（本文）を参照している場合の仕様のようです。
末尾のスラッシュを省略すると、実際には `.Inner` の値を出力していないのにも関わらず、次のようなエラーが発生してしまいます。

```
shortcode "my-shortcode" must be closed or self-closed
```

この仕様はちょっとわかりにくいので、Hugo 側で変更して欲しいなぁ。。。
{{% /note %}}

