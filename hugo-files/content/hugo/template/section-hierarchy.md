---
title: "Hugo でセクションの階層構造を取得する (.CurrentSection、.Parent、.Sections)"
url: "p/8vrj4ui/"
date: "2017-12-05"
tags: ["Hugo"]
description: "Hugo のテンプレートファイル内から参照できる .Parent や .Sections といった Page 変数を参照すると、親セクションの情報や、子セクションの情報を取得することができます。"
aliases: /hugo/layout/section-hierarchy.html
---

Hugo のテンプレートファイル内から参照できる __`.Parent`__ や __`.Sections`__ といった Page 変数を参照すると、親セクションの情報や、子セクションの情報を取得することができます。
セクション系の情報を取得するための [Page 変数](https://gohugo.io/variables/page/) には下記のようなものがあります。

`.CurrentSection`
: 自分自身が所属するセクションの情報。自分自身がセクションページであったり、ホームページである場合は、自分自身のページの情報。型は `Page` オブジェクト。通常の記事ページ、ホームページ、セクションページ以外では `nil` になります（例えば、タクソノミーリストのページでは `nil` になります）。

`.Parent`
: 自分自身がセクションページの場合、親セクションの情報。自分自身が通常ページの場合、自分が所属するセクションの情報。型は `Page` オブジェクト。

`.Sections`
: 自分自身のセクションが含む子セクションの配列（ホームページテンプレート、あるいは、セクションテンプレートのみで意味を持つ）。型は `Page` オブジェクトの配列。


レイアウトファイル内で、これらのセクション情報を参照することで、__階層構造に応じたナビゲーション用リンクを自動で生成__ したりすることができるようになります。
ここでは、下記のような階層構造のコンテンツを用意して、それぞれのページで参照できるセクション情報がどのように変化するかを見てみましょう。

```
contents/
  +-- _index.md (home page)
  +-- dir1/
  |    +-- _index.md (section page)
  |    +-- page.md   (single page)
  |    +-- dir1-1/
  |    |    +-- _index.md (section page)
  |    |    +-- page.md   (single page)
  |    +-- dir1-2/
  |         +-- _index.md (section page)
  |         +-- page.md   (single page)
  +-- dir2/
  |    +-- _index.md (section page)
  +-- dir3/
       +-- _index.md (section page)
```


.CurrentSection（自分が所属するセクション）
----

テンプレートファイル内で __`.CurrentSection`__ を参照すると、自分自身のページが所属するセクション (同一階層の `_index.md`）の `Page` オブジェクトを取得することができます。
テンプレートファイル（レイアウトファイル）内で、`.CurrentSection` の値を出力して、どのような値が格納されているかを調べてみましょう。
ここでは、次のような３種類のテンプレートファイルを用意することにします。

- Home page template: `layouts/index.html`
- Section page template: `layouts/_default/section.html`
- Single page template: `layouts/_default/single.html`

{{< code lang="go-html-template" title="テンプレートファイル内の記述（上記 3 ファイルとも共通）" >}}
<pre>
{{ with .CurrentSection }}
  .CurrentSection = {{ . }}
  .CurrentSection.File.Path = {{ .File.Path }}
  .CurrentSection.Title = {{ .Title }}
  .CurrentSection.Kind = {{ .Kind }}
  .CurrentSection.Section = {{ .Section }}
{{ end }}
</pre>
{{< /code >}}

`.CurrentSections` は通常ページ、ホームページ、セクションページ以外のテンプレートから参照すると `nil` になります。
つまり、`layout/_default/taxonomy.html` の中で参照すると必ず `nil` になるので、__タクソノミーリストテンプレートの中では参照してはいけません__。
また、`layouts/_default/list.html` の中で参照した場合でも、それがタクソノミーリストテンプレートとして使用されたときに `.CurrentSections` は `nil` になります。
`layouts/_default/list.html` の中で `.CurrentSection` の値を使用するときは、上記のように `with` ブロックや `if` ブロックで `nil` チェックしてから参照すると安全です。

- 参考: [hugo/site_sections.go at master · gohugoio/hugo · GitHub](https://github.com/gohugoio/hugo/blob/master/hugolib/site_sections.go#L47)
  > CurrentSection returns the page's current section or the page itself if home or a section.
  > Note that this will return nil for pages that is not regular, home or section pages.

### ホームページ (/index.html) にアクセスしたとき

```ini
.CurrentSection = Page("タイトル (/index.html)")
.CurrentSection.File.Path = _index.md
.CurrentSection.Title = タイトル (/index.html)
.CurrentSection.Kind = home
.CurrentSection.Section =
```

ホームページ (`/index.md`) にアクセスしたときの `.CurrentSection` 変数の値は、自分自身のページ（ホームページ）を表す `Page` オブジェクトになります。
つまり、ホームページテンプレート (`/layouts/index.html`) の中では、`.CurrenctSection.Title` と `.Title` の値は同じになります。
ホームページはルート階層のコンテンツなので、どのセクションにも存在しておらず、セクション名 (`.Section`) は空っぽになっています。

{{% note %}}
`.Section` は `.CurrentSection` と混同しがちですが、まったく違うものであることに注意してください。
`.Section` は自分自身のページが配置されている一階層目のディレクトリ名（`String` 型）を表し、`.CurrentSection` は自分自身のページが所属するセクション（同一階層の `_index.md`）の `Page` オブジェクトを表しています。
{{% /note %}}

### ルート階層の通常ページ (/page.html) にアクセスしたとき

```ini
.CurrentSection = Page("タイトル (/index.html)")
.CurrentSection.File.Path = _index.md
.CurrentSection.Title = タイトル (/index.html)
.CurrentSection.Kind = home
.CurrentSection.Section =
```

ルート階層に置いた通常ページ（シングルページ）にアクセスしたときの `.CurrentSection` 変数の値は、ホームページ（ルート階層の `_index.md`）の `Page` オブジェクトになります。
上記の出力結果を見ると、ホームページ (`/index.html`) にアクセスしたときと同じ結果になっていることが分かります。

### 1 階層目のセクションページ (/dir1/index.html) にアクセスしたとき

```ini
.CurrentSection = Page("タイトル (/dir1/index.html)")
.CurrentSection.File.Path = dir1/_index.md
.CurrentSection.Title = タイトル (/dir1/index.html)
.CurrentSection.Kind = section
.CurrentSection.Section = dir1
```

セクションページ（ディレクトリ内の `_index.md`）にアクセスしたときの `.CurrentSection` 変数の値は、自分自身のページ（セクション）を表す Page オブジェクトになります。
つまり、セクションページテンプレート (`/layouts/_default/section.html`) の中では、`.CurrentSection.Title` と `.Title` の値は同じになります。


### 1 階層目のセクションに所属する通常ページ (/dir1/page.html) にアクセスしたとき

```ini
.CurrentSection = Page("タイトル (/dir1/index.html)")
.CurrentSection.File.Path = dir1/_index.md
.CurrentSection.Title = タイトル (/dir1/index.html)
.CurrentSection.Kind = section
.CurrentSection.Section = dir1
```

あるセクションに所属する通常ページ（シングルページ）にアクセスしたときの `.CurrentSection` 変数の値は、同じ階層の `_index.md`（つまり、所属するセクションのセクションページ）を表す `Page` オブジェクトになります。

### 2 階層目のセクションページ (/dir1/dir1-1/index.html) にアクセスしたとき

```ini
.CurrentSection = Page("タイトル (/dir1/dir1-1/index.html)")
.CurrentSection.File.Path = dir1/dir1-1/_index.md
.CurrentSection.Title = タイトル (/dir1/dir1-1/index.html)
.CurrentSection.Kind = section
.CurrentSection.Section = dir1
```

2 階層目のセクションページにアクセスしたときの `.CurrentSection` 変数の値も同様に、自分自身のページ（2 階層目のセクション）を表す `Page` オブジェクトになります。
深い階層のセクションであっても、`.Section` 変数の値は、1 階層目のディレクトリ名になることに注意してください。

### 2 階層目のセクションに所属する通常ページ (/dir1/dir1-1/page.html) にアクセスしたとき

```ini
.CurrentSection = Page("タイトル (/dir1/dir1-1/index.html)")
.CurrentSection.File.Path = dir1/dir1-1/_index.md
.CurrentSection.Title = タイトル (/dir1/dir1-1/index.html)
.CurrentSection.Kind = section
.CurrentSection.Section = dir1
```

2 階層目のセクションに所属する通常ページ（シングルページ）にアクセスした場合も、これまでと同じ考え方ですね。
`.CurrentSection` の値は、同じ階層にある `_index.md` から生成されるセクションページの `Page` オブジェクトになります。


.Parent（親セクション）
----

テンプレートファイル（レイアウトファイル）内から、__`.Parent`__ を参照すると、親セクションのセクションページを表す `Page` オブジェクトを取得することができます。
親セクションというのは、自分自身がセクションページの場合は 1 階層上のセクションページ、自分自身が通常ページ（シングルページ）の場合は同一階層のセクションページを表します。
各種テンプレートの内容を次のように変更して、それぞれのページにアクセスしたときにどのような値が表示されるかを確認してみましょう。

{{< code lang="go-html-template" hl_lines="2" >}}
<pre>
{{ with .Parent }}
  .Parent = {{ . }}
  .Parent.File.Path = {{ .File.Path }}
  .Parent.Title = {{ .Title }}
  .Parent.Kind = {{ .Kind }}
  .Parent.Section = {{ .Section }}
{{ end }}
</pre>
{{< /code >}}

### ホームページ (/index.html) にアクセスしたとき

ホームページは、最上位のセクションを表すページであるため、ホームページテンプレートからは親セクションを取得することはできません。
`.Parent` を参照したときの値は __`nil`__ になるため、上記テンプレートでは何も表示されません。

{{% note %}}
正確には、ホームページはセクションページには分類されませんが、`.Parent` で参照する親セクションを考えるときには、通常のセクションと同様に考えたほうが分かりやすいです。
{{% /note %}}

### ルート階層の通常ページ (/page.html) にアクセスしたとき

```ini
.Parent = Page("タイトル (/index.html)")
.Parent.File.Path = _index.md
.Parent.Title = タイトル (/index.html)
.Parent.Kind = home
.Parent.Section =
```

ルート階層に配置された通常ページのテンプレート（シングルページテンプレート）から `.Parent` を参照すると、ホームページを表す `Page` オブジェクトを取得することができます。

### 1 階層目のセクションページ (/dir1/index.html) にアクセスしたとき

```ini
.Parent = Page("タイトル (/index.html)")
.Parent.File.Path = _index.md
.Parent.Title = タイトル (/index.html)
.Parent.Kind = home
.Parent.Section =
```

1 階層目のセクションのセクションページから `.Parent` を参照すると、ホームページを表す `Page` オブジェクトを取得できます。

### 1 階層目のセクションに所属する通常ページ (/dir1/page.html) にアクセスしたとき

```ini
.Parent = Page("タイトル (/dir1/index.html)")
.Parent.File.Path = dir1/_index.md
.Parent.Title = タイトル (/dir1/index.html)
.Parent.Kind = section
.Parent.Section = dir1
```

1 階層目のセクションに属する通常ページのテンプレート（シングルページテンプレート）から `.Parent` を参照すると、そのページが所属するセクションのセクションページを表す `Page` オブジェクトを取得することができます。

### 2 層目のセクションページ (/dir1/dir1-1/index.html) にアクセスしたとき

```ini
.Parent = Page("タイトル (/dir1/index.html)")
.Parent.File.Path = dir1/_index.md
.Parent.Title = タイトル (/dir1/index.html)
.Parent.Kind = section
.Parent.Section = dir1
```

2 階層目のセクションのセクションページから `.Parent` を参照すると、1 階層上のセクションのセクションページを表す `Page` オブジェクトを取得することができます。

### 2 階層目のセクションに所属する通常ページ (/dir1/dir1-1/page.html) にアクセスしたとき

```ini
.Parent = Page("タイトル (/dir1/dir1-1/index.html)")
.Parent.File.Path = dir1/dir1-1/_index.md
.Parent.Title = タイトル (/dir1/dir1-1/index.html)
.Parent.Kind = section
.Parent.Section = dir1
```

2 階層目のセクションに属する通常ページのテンプレート（シングルページテンプレート）から `.Parent` を参照すると、自分自身が所属するセクション（2 階層目のセクション）のセクションページを表す `Page` オブジェクトを取得することができます。


.Sections（子セクションの配列）
----

テンプレートファイル（レイアウトファイル）の中から、__`.Sections`__ を参照すると、そのセクションの下位セクション（子セクション）の配列を取得することができます。
`.Sections` に子セクションの値が格納されるのは、ホームページテンプレート、あるいは、セクションテンプレートから参照したときです（シングルページテンプレートから `.Sections` を参照すると、空配列になります）。

ここでは、各種テンプレートファイルに下記のように記述し、出力される値を確かめてみましょう。

```go-html-template
<ul>
  {{ range .Sections }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ul>
```

### ホームページ (/index.html) にアクセスしたとき

```html
<ul>
    <li><a href="/dir1/">タイトル (/dir1/index.html)</a>
    <li><a href="/dir2/">タイトル (/dir2/index.html)</a>
    <li><a href="/dir3/">タイトル (/dir3/index.html)</a>
</ul>
```

最上位には、`dir1/`、`dir2/`、`dir3/` というセクション（ディレクトリ）が配置されているため、ホームページテンプレートから `.Sections` を参照すると、それらのセクションページを表す `Page` オブジェクトの配列を取得することができます。

### ルート階層の通常ページ (/page.html) にアクセスしたとき

通常ページのテンプレート（シングルページテンプレート）からは、子セクションの値を取得できません。
`Sections` の値はサイズ０の配列になります。

```html
<ul>
</ul>
```

### 1 階層目のセクションページ (/dir1/index.html) にアクセスしたとき

```html
<ul>
    <li><a href="/dir1/dir1-1/">タイトル (/dir1/dir1-1/index.html)</a>
    <li><a href="/dir1/dir1-2/">タイトル (/dir1/dir1-2/index.html)</a>
</ul>
```

1 階層目のセクション (`/dir1`) には、2 階層目のセクション (`dir1-1`、`dir1-2`) が存在するため、`.Sections` を参照すると、それらの下位セクションのセクションページを表す `Page` オブジェクトの配列を取得することができます。

### 1 階層目のセクションに所属する通常ページ (/dir1/page.html) にアクセスしたとき

通常ページのテンプレート（シングルページテンプレート）からは、子セクションの値を取得できません。
`Sections` の値はサイズ 0 の配列になります。

```html
<ul>
</ul>
```

### 2 階層目のセクションページ (/dir1/dir1-1/index.html) にアクセスしたとき

2 階層目のセクション (`/dir1/dir1-1`) には、下位セクションが存在しないため、`.Sections` はサイズ 0 の配列になります。

```html
<ul>
</ul>
```

### 2 階層目のセクションに所属する通常ページ (/dir1/dir1-1/page.html) にアクセスしたとき

通常ページのテンプレート（シングルページテンプレート）からは、子セクションの値を取得できません。
`Sections` の値はサイズ 0 の配列になります。

```html
<ul>
</ul>
```


応用例（兄弟セクションの一覧）
----

シングルページテンプレート内で次のように記述すれば、そのページが所属するセクションの子セクションの一覧（つまり、兄弟セクションの一覧）を表示することができます。

```go-html-template
<ul>
  {{ range .CurrentSection.Sections }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ul>
```

