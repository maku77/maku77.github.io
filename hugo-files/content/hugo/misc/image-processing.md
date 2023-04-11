---
title: "Hugo で大きな画像ファイルから自動的に小さなサムネイル画像を生成する (Image Processing)"
url: "p/mxhzgwd/"
date: "2020-01-15"
tags: ["Hugo"]
aliases: /hugo/misc/image-processing.html
---

Image Processing 機能とは
----

Hugo の [Image Processing 機能](https://gohugo.io/content-management/image-processing/) を使用すると、[ページにバンドル](/p/9n8p6n4/) した 1 枚の画像ファイルを加工し、サムネイル画像などを自動的に生成することができます。

例えば、デジカメで撮影した写真をウェブサイト上に掲載する場合、直接画像を貼り付けるとサイズが大きいため、小さなサムネイル画像からのリンクを用意することが多いと思います。
このような場合に、Image Processing 機能を使用すれば、サムネイル画像を手動で作成する手間をなくすことができます。


Image Processing の使用例
----

ここでは、下記のように、books セクションページ内に `sample.png` というリソースが含まれているとします。

- `content/`
  - `books/`
    - `_index.md`
    - `sample.png`

Image Processing の機能を使って画像の変換を行うには、[Page Resources のオブジェクト](https://gohugo.io/content-management/page-resources/) を取得する必要があります（`Page` 変数ではないので注意）。
このオブジェクトは、[Page 変数](https://gohugo.io/variables/page/) の次のようなプロパティ、およびメソッドを使って取得することができます。

- __`.Resources`__（バンドルされたすべてのリソースを参照する）
- __`.Resources.ByType`__（指定した種類のリソースだけ取り出す）
  - 例: `$resources := .Resources.ByType "image"`
- __`.Resources.Match`__（ファイル名のパターンに一致するリソースだけ取り出す）
  - 例: `$resources := .Resources.Match "sample.png"`
  - 例: `$resources := .Resources.Match "**.png"`
  - 例: `$resources := .Resources.Match "images/**.png"`

これらのメソッドは Markdown ファイル内から直接呼び出すことはできないので、実際にはショートコードやテンプレートファイルから利用することになります。

例えば、次のようなショートコードを作成しておけば、元の画像ファイルがどのようなサイズであっても、強制的に 150x150 のサイズのサムネイル画像を生成して表示してくれます。

{{< code lang="go-html-template" title="layouts/shortcodes/thumbnail.html" >}}
{{- $src := .Get "src" }}
{{- $imageRes := .Page.Resources.GetMatch $src }}
{{- $imageRes := $imageRes.Fill "150x150" }}
{{- $imageUrl := $imageRes.RelPermalink }}

<img src="{{ $imageUrl }}" alt="{{ $src }}" />
{{< /code >}}

ショートコードから `.Resources` オブジェクトを参照するときは、上記のように `.Page.Resources` としなければいけないことに注意してください。

この `thumbnail` ショートコードは、Markdown ファイル内からは次のように利用します。

{{< code lang="md" title="content/books/_index.md" >}}
---
title: "タイトル"
date: "2020-01-15"
---

{{</* thumbnail src="sample.png" */>}}
{{< /code >}}

ここでは、__`Fill`__ メソッドで生成する画像ファイルのサイズを 150x150 で固定していますが、ショートコードのパラメータで指定できるようにしてもよいでしょう。

ちなみに自動生成されるサムネイル画像の URL は次のような感じのランダムな文字列になるようです。

```
/books/sample_d74hu175c61ccf69bf5f1995b2e27ba4cd_111429_150x150_fill_q75_box_smart1.png
```

Image Processing 用の関数は、`Fill` メソッド以外にも[色々ある](https://gohugo.io/content-management/image-processing/)ので、ざっと目を通しておくとよいでしょう。
下記に、`Resize`、`Fit`、`Fill` メソッドの使用例を示しておきます。

- 幅x高さを 200x150 にする（アスペクト比は考慮されないので、歪んだ画像になるかもしれない）
    - `$img = $resource.Resize "200x150"`
- アスペクト比を保って幅が 200px になるよう拡大縮小する
    - `$img = $resource.Resize "200x"`
- アスペクト比を保って高さが 150px になるよう拡大縮小する
    - `$img = $resource.Resize "x150"`
- アスペクト比を保って幅x高さが大きくても 200x150 に収まるようにする（最終的な画像サイズはどちらか一辺が指定したサイズより小さくなるかもしれない）
    - `$img = $resource.Fit "200x150"`
- アスペクト比を保って幅x高さがちょうど 200x150 になるようにする（アスペクト比が変わる場合、画像の中央当たりだけが見えるような画像になる）
    - `$img = $resource.Fill "200x150"`

他にも、背景色を指定したり、回転させたり、JPEG のクオリティを調整したりと、色々なオプションが用意されています。

Facebook などで Web ページをシェアする場合は、画像ファイルの中央部分しか表示されなかったりしますね（OGP という仕組みが使われる）。
この振舞いは、上記の `Fill` メソッドの動きに似ているので、サムネイル用のショートコードを作成するときは、`Fill` メソッドを使用するのがよいかもしれません。


サムネイル画像をクリックしたときに元の画像ファイルを表示する
----

おまけのショートコードサンプルです。
この `image` ショートコードを使用して画像を表示すると、指定したサイズに縮小した画像を表示しつつ、画像をクリックすることで元の大きいサイズの画像を表示することができます。

{{< code lang="go-html-template" title="layouts/shortcodes/image.html" >}}
{{- $src := .Get "src" }}
{{- $title := .Get "title" }}
{{- $width := .Get "w" }}
{{- $size := printf "%sx" $width }}
{{- $res := .Page.Resources.GetMatch $src }}
{{- $image := $res.Resize $size }}
{{- $imageUrl := $image.RelPermalink }}

<figure>
  <a href="{{ $src }}" target="_blank">
    <img src="{{ $imageUrl }}" alt="{{ $src }}" />
  </a>
  {{- with $title }}
    <figcaption>図: {{ . }}</figcaption>
  {{- end }}
</figure>
{{< /code >}}

Markdown ファイルの中からは次のように呼び出します。

{{< code lang="html" title="content/books/_index.md" >}}
{{</* image w="200" src="sample.jpg" title="タイトル" */>}}
{{< /code >}}

上記のショートコードでは、`figure` タグと `figcaption` を使って、画像のタイトルも表示できるようにしています。
ショートコードのパラメータで `title="タイトル"` のように指定すると、画像の下にタイトルが表示されます。
また、アスペクト比を保ったまま画像を縮小表示することを想定しているため、サイズ指定は横幅だけを指定できるようにしています（ショートコードのパラメータで `w="200"` のように単位なしで指定します）。

### 応用1: w パラメータをオプショナルにしたバージョン

下記はもう少し改良したバージョンの `image` ショートコードで、`w` パラメータが省略された場合に、`src` で指定した画像ファイルをそのまま使用するようにしています。

{{< code lang="go-html-template" title="layouts/shortcodes/image.html" >}}
{{- $src := .Get "src" }}
{{- $title := .Get "title" }}
{{- $width := .Get "w" }}
{{- $imageUrl := $src }}
{{- $res := .Page.Resources.GetMatch $src }}

{{- /* ページまたぎでリンク切れを防ぐおまじない */}}
{{- if $res }}
  {{- $src = $res.RelPermalink }}
  {{- $imageUrl = $res.RelPermalink }}
{{- end }}

{{- if $width }}
  {{- $size := printf "%sx" $width }}
  {{- $image := $res.Resize $size }}
  {{- $imageUrl = $image.RelPermalink }}
{{- end }}

<figure>
  <a href="{{ $src }}" target="_blank">
    <img src="{{ $imageUrl }}" alt="{{ $src }}" />
  </a>
  {{- with $title }}
    <figcaption>図: {{ . }}</figcaption>
  {{- end }}
</figure>
{{< /code >}}

### 応用2: SVG ファイルに対応する

SVG ファイルは Image Processing 機能に対応していないので、同じコードで width 調整しようとすると、次のようなエラーになってしまいます。

```
error calling Resize: *resources.genericResource is not an image
```

下記の `image` ショートコードは、`src` パラメータで指定されたファイルの拡張子が `svg` だった場合に、Image Processing 機能を使わず、`style` 属性を使って横幅を指定するようにしています。

{{< code lang="go-html-template" >}}
{{- $src := .Get "src" }}
{{- $title := .Get "title" }}
{{- $width := .Get "w" }}
{{- $imageUrl := $src }}
{{- $res := .Page.Resources.GetMatch $src }}

{{- /* ページまたぎでリンク切れを防ぐおまじない */}}
{{- if $res }}
  {{- $src = $res.RelPermalink }}
  {{- $imageUrl = $res.RelPermalink }}
{{- end }}

{{- $isSvg := strings.HasSuffix $src "svg" }}
{{- if and $width (not $isSvg) }}
  {{- $size := printf "%sx" $width }}
  {{- $image := $res.Resize $size }}
  {{- $imageUrl = $image.RelPermalink }}
{{- end }}

<figure>
  <a href="{{ $src }}" target="_blank">
    <img {{ if $isSvg }}style="width:{{ $width }}px;"{{ end }} src="{{ $imageUrl }}" alt="{{ $src }}" />
  </a>
  {{- with $title }}
    <figcaption>図: {{ . }}</figcaption>
  {{- end }}
</figure>
{{< /code >}}

