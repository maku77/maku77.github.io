---
title: "Hugo のページ内に Tex 形式の数式を埋め込めるようにする (MathJax)"
url: "p/dsfzi4n/"
date: "2018-06-21"
lastmod: "2024-05-08"
tags: ["Hugo", "TeX", "数学"]
description: "Hugo のテンプレートで MathJax という Javascript ライブラリを組み込むと、記事内に Tex 形式の数式を埋め込むことができるようになります。"
changes:
  - 2024-05-08: スクロールバー用の CSS について
  - 2023-10-22: MathJax のバージョンを 2 から 3 に更新
aliases: /hugo/settings/math-jax.html
useMath: true
---

Hugo のテンプレートで __MathJax__ という Javascript ライブラリを組み込むと、Markdown 記事内に Tex 形式で数式を埋め込むことができるようになります。
下記は、MathJax による数式表示の例です。

$$
-b\pm \sqrt{b^2 -4ac} \over 2a
$$
<center>図: MathJax による数式表示</center>


MathJax を有効にする
----

MathJax を有効にするのは簡単で、HTML 内で MathJax の Javascript ファイルを読み込むだけです。
次の例では、Hugo の [ベーステンプレート (`baseof.html`)](/p/bbxj5pa/) の `head` 要素内に、MathJax を読み込むための `script` 要素を追加しています。

{{< code lang="go-html-template" title="layouts/_default/baseof.html" hl_lines="6-8" >}}
<!DOCTYPE html>
<html{{ with .Site.LanguageCode }} lang="{{ . }}"{{ end }}>
<head>
  <meta charset="UTF-8">
  ...
  <script type="text/javascript" id="MathJax-script" async
    src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js">
  </script>
</head>
<body>
  ...
</body>
</html>
{{< /code >}}

上記の例では、配信元の CDN として `cdn.jsdeliver.net` を指定していますが、他の CDN で配信されているものを使用することもできます。
詳しくは下記の MathJax のドキュメントを参照してください。

- 参考: [Getting Started with MathJax Components](https://docs.mathjax.org/en/latest/web/start.html)


数式の記述例
----

### ブロック形式 (displayed mathematics)

ブロック形式（独立した段落）の数式を表示するには、Markdown ファイル内に __`$$`__ で囲む形で TeX を記述します（あるいは __`\\[`__ と __`\\]`__ で囲みます）。
次の例では、Sum 記号（Σ）や分数を表示しています。

{{< code title="Markdown 内での記述例" >}}
$$
F(x) = \sum_{n=1}^{N} \frac{1}{N}
$$
{{< /code >}}

$$
F(x) = \sum_{n=1}^{N} \frac{1}{N}
$$
<center>図: 出力結果</center>

### インライン形式 (in-line mathematics)

文章内にインライン形式で数式を埋め込むには、__`\\(`__ と __`\\)`__ で TeX 命令を囲みます。

{{< code title="Markdown 内での記述例" >}}
次の数式は \\(F(x) = \sum_{n=1}^{N} \frac{1}{N}\\) 文章内に埋め込まれます。
{{< /code >}}

表示結果: 次の数式は \\(F(x) = \sum_{n=1}^{N} \frac{1}{N}\\) 文章内に埋め込まれます。

{{% maku-common/reference %}}
- [数学メモ: TeX チートシート｜まくろぐ](https://maku.blog/p/oau2e7h/)
{{% /maku-common/reference %}}


必要なときのみ MathJax を読み込むようにする
----

数式を使わないページで MathJax の JavaScript ファイルを読み込むのは無駄です。
次の例では、記事のフロントマターに __`useMath: true`__ という指定があるときのみ MathJax を有効化しています。

{{< code lang="go-html-template" title="layouts/_default/baseof.html" hl_lines="6 10" >}}
<!DOCTYPE html>
<html{{ with .Site.LanguageCode }} lang="{{ . }}"{{ end }}>
<head>
  <meta charset="UTF-8">
  ...
  {{- if eq .Params.useMath true }}
  <script type="text/javascript" id="MathJax-script" async
    src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js">
  </script>
  {{- end }}
</head>
...
{{< /code >}}

あとは、数式を使用する記事ページで次のようにフロントマターを記述すれば OK です。

{{< code lang="yaml" title="MathJax の有効化" hl_lines="4" >}}
---
title: "ページタイトル"
date: "2023-10-21"
useMath: true
---

オイラーの公式： \\(\mathrm{e}^{\mathrm{i}\theta} = \cos(\theta) + \mathrm{i}\sin(\theta)\\)
{{< /code >}}


（おまけ）水平方向にスクロールできるようにする
----

数式（特にブロック形式）が長くなって親要素の横幅に収まらない場合、デフォルトでは親要素をはみ出して数式が描画されてしまいます。
次のような CSS を設定しておけば、横幅が足りないときに水平方向のスクロールバーを表示してスクロールできるようになります。

```css
/* MathJax の数式が横に長いときにスクロールバーを表示 */
mjx-container {
  overflow-x: auto;
  overflow-y: hidden;
  max-width: 100%;
}
```

