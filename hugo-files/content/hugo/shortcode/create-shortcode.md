---
title: "Hugo で独自のショートコードを作成する"
url: "p/ttfyk5o/"
date: "2017-09-29"
tags: ["Hugo"]
description: "Hugo で独自のショートコードを作成すると、定型の HTML コードを記事内に簡単に埋め込めるようになります。"
aliases: /hugo/shortcode/create-shortcode.html
---

Hugo で独自のショートコードを作成すると、定型の HTML コードを記事内に簡単に埋め込めるようになります。

ショートコード作成の基本
----

ショートコードは、__`layouts/shortcodes`__ ディレクトリ内に __`.html`__ 拡張子のファイルとして作成します。

{{< code lang="html" title="layouts/shortcodes/my-shortcode.html" >}}
This is my first short code.
{{< /code >}}

ファイル名から拡張子を除いたものが、ショートコード名となります。
上記の例の場合、`my-shortcode` というショートコードを作成したことになります。
記事（Markdown ファイル）の中から、下記のように呼び出すと、上記の内容がそこに展開されます。

{{< code lang="md" title="content/page1.md" >}}
---
title: "ページタイトル"
---

{{</* my-shortcode */>}}
{{< /code >}}


ショートコードにパラメータを渡す (.Get)
----

### 単純なパラメータ

ショートコード呼び出し時に、パラメータを渡すことができます。
下記の例では、２つのパラメータ `red`、`32px` を渡しています。

```
{{</* my-shortcode red 32px */>}}
```

渡されたパラメータは、ショートコードの中で __`{{ .Get インデックス番号 }}`__ のように参照することができます。

{{< code lang="html" title="layouts/shortcodes/my-shortcode.html" >}}
<div style="color:{{ .Get 0 }}; font-size:{{ .Get 1 }};">
  This is my first short code.
</div>
{{< /code >}}

### 名前付きパラメータ

上記の例では、参照するパラメータをインデックス番号 (0, 1, 2, ...) で指定していますが、2 つ以上のパラメータを持つショートコードを作成するときは、__名前付きパラメータ__ の仕組みを使うと分かりやすくなります。

```
{{</* my-shortcode color="red" size="32px" */>}}
```

名前付きパラメータとして渡された値を参照するには、__`{{ .Get "パラメータ名" }}`__ のように、インデックス番号の代わりにその名前を引用符で囲んで指定します。

```html
<div style="color:{{ .Get `color` }}; font-size:{{ .Get `size` }};">
  This is my first short code.
</div>
```

上記のように HTML 要素の属性値の中で `.Get` のパラメータ名を記述するときは、ダブルクォーテーション (__`"`__) の代わりにバッククォート (__``` ` ```__) で囲むと、引用符の対応がわかりやすくなります。

### パラメータの有無を調べる

パラメータが渡されたかどうかで処理を分岐するには、`.Get` で取得した値を __`if`__ や __`with`__ で評価します。
次の例では、ショートコードに `class` パラメータが渡されたときに、`span` 要素の `class` 属性の値として出力しています。

```go
<span{{ with .Get "class"}} class="{{.}}"{{ end }}>{{ .Inner }}</span>
```

__`default`__ 関数を組み合わせて使うと、パラメータが指定されなかった場合のデフォルト値を用意しておくことができます。
以下のいずれの書き方も同様に振る舞いますが、1 つ目の書き方が直感的かと思います。

{{< code title="width パラメータのデフォルト値を auto にする" >}}
{{ $width := .Get "width" | default "auto" }}
{{ $width := default "auto" (.Get "width") }}
{{ $width := or (.Get "width") "auto" }}
{{< /code >}}


ショートコードにテキストデータを渡す (.Inner)
----

ショートコードの開始タグと終了タグで任意のテキスト（内部テキスト）を囲むと、ショートコードのテンプレート内からそのテキストを参照することができます。
下記は、`caution` という独自ショートコードにテキストを渡す例です。

{{< code title="content/page1.md" >}}
---
title: "ページタイトル"
---

{{</* caution */>}}
決して、この発射ボタンを押さないでください。
{{</* /caution */>}}
{{< /code >}}

開始タグと終了タグで囲まれた内部テキストは、ショートコードの中から __`{{ .Inner }}`__ で参照することができます。

{{< code lang="html" title="layouts/shortcodes/caution.html" >}}
<div style="color:red;">
  {{ .Inner }}
</div>
{{< /code >}}

内部テキストを最終的に Markdown プロセッサで処理してもらいたい場合は、下記のように __`%`__ を使用した呼び出し方をする必要があります。

```html
---
title: "ページタイトル"
---

{{%/* caution */%}}
決して、この __発射ボタン__ を押さないでください。
{{%/* /caution */%}}
```


テーマ用のショートコード
----

テーマの付属品として配布されるショートコードは、次のようなパスに配置します。

- __`themes/<THEME>/layouts/shortcodes/<SHORTCODE>.html`__

同じ名前のショートコードが、現在の Hugo プロジェクト自身のショートコードディレクトリ (`layouts/shortcodes`) に存在する場合は、そちらが優先して使用されます。

