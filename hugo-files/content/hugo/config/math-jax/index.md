---
title: "Hugo のページ内に Tex 形式の数式を埋め込めるようにする (MathJax)"
url: "p/dsfzi4n/"
date: "2018-06-21"
tags: ["Hugo"]
description: "Hugo のテンプレートで MathJax という Javascript ライブラリを組み込むと、記事内に Tex 形式の数式を埋め込むことができるようになります。"
aliases: /hugo/settings/math-jax.html
---

Hugo のテンプレートで __MathJax__ という Javascript ライブラリを組み込むと、記事内に Tex 形式の数式を埋め込むことができるようになります。

- 参考: [MathJax with Hugo](https://gohugo.io/content-management/formats/#mathjax-with-hugo)


MathJax を有効にする
----

まず、すべてのページで次のようにして MathJax.js を読み込む必要があります。

```html
<script src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
```

フッター用のパーシャルテンプレート（`layouts/partials/footer.html` など）を用意しているのであれば、その中に記述しておくのがよいでしょう。

CDN で提供されている最新の MathJax.js のアドレスは、[cdnjs.com のサイト](https://cdnjs.com/)で `MathJax` と入力するか、[MathJax のサイト](http://docs.mathjax.org/en/latest/configuration.html) で調べることができます。


ページ内に数式を記述する
----

あとは、Markdown ファイル内で __`$$数式$$`__ というフォーマットで記述すれば、きれいな数式が表示されます。

{{< code title="Markdown ファイル内の記述例" >}}
$$F(x) = \sum_{n=1}^{N} \frac{1}{N}$$
{{< /code >}}

{{< image border="true" w="150" src="img-001.png" title="表示例" >}}


インライン形式で数式を記述できるようにする
----

通常の文章の中にインライン形式で数式を埋め込むには、MathJax.js の設定を行っておく必要があります。
MathJax.js を読み込む HTML コードと一緒に、次のようなコードを追加してください。

{{< code lang="html" title="テンプレート抜粋（layouts/partials/footer.html など）" >}}
<script type="text/x-mathjax-config">
MathJax.Hub.Config({
  tex2jax: {
    inlineMath: [['$','$']]
  }
});
</script>
{{< /code >}}

上記のように設定しておくと、文章の中に __`$数式$`__ というフォーマットで、数式をインラインで埋め込めるようになります。

{{< code title="Markdown ファイル内の記述" >}}
次の数式は $F(x) = \sum_{n=1}^{N} \frac{1}{N}$ 文章内に埋め込まれます。
{{< /code >}}

{{< image border="true" src="img-002.png" title="表示例" >}}


