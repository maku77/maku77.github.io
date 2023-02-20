---
title: "Hugo でソースコードをハイライト表示する (highlight)"
url: "p/gxk6qat/"
date: "2019-03-17"
tags: ["Hugo"]
aliases: /hugo/shortcode/highlight.html
---

Pygments によるコードハイライト
----

Hugo の設定ファイル `config.toml` の中で、下記のように設定しておくと、[Pygments](http://pygments.org/) によるコードブロックのハイライト機能を有効にすることができます。

```toml
pygmentsCodeFences = true
```

例えば、Java のソースコードであれば、Markdown ファイルの中で、下記のように言語名 (`java`) を指定してコードブロックを記述するだけで、その言語に特化したハイライト表示を行ってくれます。

{{< code lang="md" >}}
```java
public class Main {
    public static void main(String... args) {
        System.out.println("Hello");
    }
}
```
{{< /code >}}

{{< image src="img-001.png" title="ハイライト表示例" >}}


highlight ショートコードによるコードハイライト
----

Hugo の組み込みショートコードとして **`highlight`** が用意されています。
このショートコードを使用すると、`pyghmentsCodeFences` を使う方法よりも高度なコードハイライトを行えます。
例えば、下記のように、行番号を表示したり、特定の行を強調表示することができます。

```
{{</* highlight java "linenos=table, hl_lines=3" */>}}
public class Main {
    public static void main(String... args) {
       System.out.println("Hello");
    }
}
{{</* /highlight */>}}
```

{{< image src="img-002.png" title="ハイライト表示例" >}}

色々なオプションが用意されているので、詳しくは下記の Hugo ドキュメントを参照してください。

- [Hugo - Syntax Highlighting](https://gohugo.io/content-management/syntax-highlighting/)


キャプションを付けられる独自のショートコードを作成する
----

ソースコードの表示をキャプション付きでマークアップする場合は、HTML では下記のように __`figure`__、__`figcaption`__ タグを使って記述します。

```html
<figure>
<figcaption>サンプルコード</figcaption>
<pre><code>
public class Main {
    public static void main(String... args) {
        System.out.println("Hello");
    }
}
</code></pre>
</figure>
```

このようなコードを手書きで毎回記述するのは大変なので、[独自のショートコードを作る](/p/ttfyk5o/) のがオススメです。
ここでは __`code`__ というショートコードを作成し、コードブロックをタイトル付きでハイライト表示できるようにします。
ハイライト機能自体は自力で実装するのは大変なので、Hugo の組み込み関数として用意されている [highlight 関数](https://gohugo.io/functions/highlight/) を利用します（Hugo の組み込みの `highlight` ショートコードも、この `highlight` 関数を使って実装されています）。

{{< code lang="go-html-template" title="layouts/shortcodes/code.html" >}}
{{ $title := .Get "title" }}
{{ $lang := or (.Get "lang") "" }}
<figure class="xCodeBlock">
{{ with $title }}<figcaption class="xCodeBlock_title">{{ . }}</figcaption>{{ end }}
<div class="xCodeBlock_code">{{ highlight (trim .Inner "\r\n") $lang "" }}</div>
</figure>
{{< /code >}}

このショートコードは、Markdown ファイルの中で次のように呼び出します。

```
{{</* code lang="java" title="タイトル" */>}}
public class Main {
    public static void main(String... args) {
        System.out.println("Hello");
    }
}
{{</* /code */>}}
```

{{< image src="img-003.png" title="ハイライト表示例" >}}

ちなみに、上記のようなタイトルラベルを表示するために、次のような CSS 定義を使用しています。

```css
.xCodeBlock_title {
  display: inline-block;
  font-size: smaller;
  border-radius: 0.5em 0.5em 0 0;
  padding: 0 0.5em;
  background: gray;
  color: white;
}
.xCodeBlock_code {
  margin-top: -2px;
}
.xCodeBlock_code pre {
  padding: 0.5em;
  white-space: pre-wrap;
}
```


ハイライトのカラーテーマを変更する
----

Hugo の `highlight` ショートコード、あるいは `highlight` 関数で使用されるカラーテーマは、CSS ファイルを用意することで変更することができます。

まず、`highlight` 機能によって出力される HTML コードが、CSS クラスを使用したものになるように、Hugo の設定ファイル `config.toml` に下記のような行を追加します。

{{< code lang="toml" title="config.toml に追記" >}}
pygmentsUseClasses = true
{{< /code >}}

次に、使用したいカラーテーマの CSS ファイルを生成します。
下記のようなサイトからダウンロードしてしまうのが手っ取り早いです。

- [Pygments Syntax Highlighter CSS Theme Files](https://jwarby.github.io/jekyll-pygments-themes/languages/javascript.html)

Hugo にはシンタックスハイライト用の CSS ファイルを生成する機能がついているので、下記のようにコマンド実行して生成することもできます。
スタイル名は [Pygments style gallery のサイト](https://help.farbox.com/pygments.html)で探してください。

```console
$ hugo gen chromastyles --style=manni > highlight-manni.css
```

あとは、この CSS をすべてのページで読み込むようにすれば OK です。


参考: Hugo の highlight ショートコードの実装
----

- [hugo/tpl/tplimpl/embedded/templates/shortcodes/highlight.html - gohugoio/hugo - GitHub](https://github.com/gohugoio/hugo/blob/9c1e82085eb07d5b4dcdacbe82d5bafd26e08631/tpl/tplimpl/embedded/templates/shortcodes/highlight.html)

```go-html-template
{{ if len .Params | eq 2 }}
  {{ highlight (trim .Inner "\n\r") (.Get 0) (.Get 1) }}
{{ else }}
  {{ highlight (trim .Inner "\n\r") (.Get 0) "" }}
{{ end }}
```

