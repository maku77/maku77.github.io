---
title: "Hugo で SVG ファイルをインラインで埋め込む svg ショートコードを作成する"
url: "p/kyn8rcv/"
date: "2020-10-20"
tags: ["Hugo"]
aliases: /hugo/shortcode/inline-svg.html
---

Hugo で SVG ファイルを表示するには、Markdown の画像ファイル用構文や、Hugo 組み込みの [figure ショートコード](https://gohugo.io/content-management/shortcodes/#figure) などを使用します。

```
![サンプル画像](sample.svg)

{{</* figure src="sample.svg" caption="サンプル画像" */>}}
```

この方法で SVG ファイルを表示すると、次のような `img` タグで外部ファイルを参照するような HTML が出力されます。

```html
<img src="sample.svg">
```

これは、PNG ファイルや JPG ファイルと同様に外部ファイルを画像として表示する方法なので、SVG ファイル内にテキストが含まれていたとしても、そのテキストを検索することはできません。
SVG ファイルの内容をインラインで `svg` 要素として埋め込むようにすれば、SVG ファイルの内容をブラウザの検索機能などで検索できるようになります。

次の `svg` ショートコードは、指定された SVG ファイルをインラインで HTML に埋め込みます。
ポイントは、Hugo の [readFile 関数](https://gohugo.io/functions/readfile/)でファイルの中身を読み込んで、その場に出力するところです。

{{< code lang="html" title="layouts/shortcodes/svg.html" >}}
{{- $src := .Get "src" }}
{{- $title := .Get "title" }}

{{- /* md ファイルからの相対パスで svg ファイル名を指定できるように */}}
{{- $svgFile := path.Join (path.Dir .Position.Filename) $src }}

<figure class="xImage">
  <a href="{{ $src }}" target="_blank">
    {{ readFile $svgFile | safeHTML }}
  </a>
  {{- with $title }}
  <figcaption>図: {{ . }}</figcaption>
  {{- end }}
</figure>
{{< /code >}}

{{< code title="使用例（Markdown ファイル内）" >}}
{{</* svg src="sample.svg" title="サンプル画像" */>}}
{{< /code >}}

