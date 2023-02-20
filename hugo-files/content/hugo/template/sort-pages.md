---
title: "Hugo のセクションページでいろいろな方法でページソートする (.ByDate, .ByTitle, .ByWeight)"
url: "p/9gjnqtw/"
date: "2022-05-20"
tags: ["Hugo"]
---

.Pages のデフォルトソート順序
----

Hugo のセクションページのテンプレート内で __`.Pages`__ 変数を参照すると、子セクションや子ページの一覧を取得できますが、その一覧はデフォルトで次のような情報をもとにソートされています（参考: [Lists of Content in Hugo - Order Content](https://gohugo.io/templates/lists/#order-content)）。

1. __`Weight`__
   - ページのフロントマターで `weight: 1` のように書いておくと、ページに重み付けできます。
     より小さな値の `weight` を持っているページの方が先に表示されます。
     `weight` を持たないページ（あるいは `weight: 0` のページ）は、いかなる `weight` を持つページよりも後ろに表示されます。
1. __`Date`__
   - ページのフロントマターで、`date: "2022-05-20"` のように記述しておくと、そのページの作成日として認識されます。
     より新しいページが先に表示されます。
1. __`LinkTitle`__ / __`Title`__
   - ページのタイトルで昇順ソートされます。
     フロントマターに `linkTitle` が指定されていればその値でソートされ、なければ `title` の値でソートされます。
1. __`FilePath`__
   - `.md` ファイルのフルパスで昇順ソートされます。

具体的のどのような実装になっているかは、下記 Hugo ソースコードの `DefaultPageSort` 関数あたりを見ると分かります。

- 参考: [hugo/pages_sort.go](https://github.com/gohugoio/hugo/blob/e164834f0aaaf1c58489fab41f5835b66f16b87c/resources/page/pages_sort.go#L69)


ソート方法をカスタマイズする
----

`.Pages` 変数でページの一覧を取得するときに __`.Pages.ByTitle`__ のように指定すると、ページタイトルでソートされた結果を取得できます。
他にもいろいろな参照方法があります。

| 参照方法 | 意味 |
| ---- | ---- |
| `.Pages.ByWeight` | フロントマターの `weight` の小さい順。ただし、`weight: 0` は指定なしとみなされる |
| `.Pages.ByTitle` | タイトル (`title`) 順 |
| `.Pages.ByLinkTitle` | タイトル (`linkTitle`) 順。`linkTitle` がない場合は `title` を参照する |
| `.Pages.ByDate` | 日付 (`date`) が新しい順 |
| `.Pages.ByPublishDate` | 日付 (`publishdate`) が新しい順 |
| `.Pages.ByExpiryDate` | 日付 (`expirydate`) が新しい順 |
| `.Pages.ByLastmod` | 日付 (`lastmod`) が新しい順 |
| `.Pages.ByLength` | 本文が短い順 |
| `.Pages.ByParam "rating"` | フロントマターの独自フィールドの値でソート（この場合は `rating`） |
| `.Pages.ByParam "author.last_name"` | 上の応用（入れ子になった独自フィールド） |

逆順にしたいときは、後ろに __`.Reverse`__ を付けます。
例えば次のようにすると、__タイトルで降順ソート__ されます。

```go-html-template
.Pages.ByTitle.Reverse
```

セクションテンプレートの中に、次のようなスニペットを記述すれば、各種ソート条件でどのように表示されるかを確認できます。
この例では、ページタイトルで逆順ソートした結果を表示しています。

{{< code lang="go-html-template" title="layouts/_default/section.html（抜粋）" >}}
<ul>
  {{ range .Pages.ByTitle.Reverse }}
  <li><a href="{{ .RelPermalink }}">{{ .Weight }} / {{ .Date.Format "2006-01-02" }} / {{ .LinkTitle }}</a></li>
  {{ end }}
</ul>
{{< /code >}}


応用: 複数の条件でソートする
----

前述の通り、`.Pages` はデフォルトで __Weight → Date → LinkTitle → FilePath__ の優先度でソートされるのですが、これをカスタマイズして __Weight → LinkTitle__ の順でソートしたい場合はちょっとややこしいです。

```go
.Pages.ByWeight.ByTitle
```

としてしまうと、Weight 順にソートされた結果がタイトル順で再度ソートされるだけでうまくいきません（`.ByWeight` の意味がなくなってしまいます）。
段階的にソートするには、まずグループ機能で同じ Weight を持つページを取り出し、そのグループ内でタイトル順ソートする必要があります。
次のパーシャルテンプレートは、渡された `.Pages` を Weight → LinkTitle の順でソートする関数です。

{{< code lang="go-html-template" title="layouts/partials/functions/sort-pages.html" >}}
{{- $pages := . }}
{{- $pagesWithWeight := where $pages "Weight" "<>" 0 }}
{{- $pagesWithoutWeight := where $pages "Weight" "==" 0 }}
{{- $ret := slice }}

{{- range $pagesWithWeight.GroupBy "Weight" }}
  {{- $ret = $ret | append .Pages.ByLinkTitle }}
{{- end }}
{{- $ret = $ret | append $pagesWithoutWeight.ByLinkTitle }}

{{- return $ret }}
{{< /code >}}

あとは、セクションテンプレートなどから次のように使用します。

{{< code lang="go-html-template" title="layouts/_default/section.html（抜粋）" >}}
<ul>
  {{ range partial "functions/sort-pages" .Pages }}
    <li><a href="{{ .RelPermalink }}">{{ .LinkTitle }}</a></li>
  {{ end }}
</ul>
{{< /code >}}

