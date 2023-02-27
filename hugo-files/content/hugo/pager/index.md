---
title: "Hugo のページャー（ページネーター）で複数の記事を切り替えながら表示できるようにする"
url: "p/8ogzaxd/"
date: "2018-06-02"
tags: ["Hugo"]
description: "Hugo には Pagenator という標準のページャー機能が備わっており、比較的簡単にページャー機能を組み込むことができます。"
aliases: /hugo/pager/basic.html
---

Hugo には __Pagenator__ という標準のページャー機能が備わっており、比較的簡単にページャー機能を組み込むことができます（参考: [Pagination｜Hugo](https://gohugo.io/templates/pagination/)）。

ページャーの基本
----

ページャーとは、Google の検索結果のページなどで表示される、下記のようなものです。

{{< image border="true" w="400" src="img-001.png" title="ページャーの例" >}}

あるカテゴリに属する記事のリストを表示するときや、数日分の日記をまとめて表示するようなケースでは、1 ページが長くなりすぎないように、ページャーを導入することを検討するのがよいでしょう。

Hugo でページャーを導入すると、デフォルトでは __`/page/1`__、__`/page/2`__、__`/page/3`__ といった URL で、それぞれのグループ（複数の記事をまとめたページ）を表示するためのページにアクセスできるようになります（`page` というパスは、Hugo のコンフィグファイルの __`PaginatePath`__ で変更できます）。

{{< image src="img-002.svg" title="ページャーによる切り替え" >}}

Hugo のページャー機能は、**ホームページとリスト系ページ（セクションページや、タクソノミーリスト）のみ**で有効です。
つまり、ホームページテンプレート (`layouts/index.html`) や、リストテンプレート (`layouts/_default/list.html`) などに適用することになります。

ホームページテンプレートに適用すれば、それぞれのグループを表示するためのページには、次のようなアドレスでアクセスできるようになります。

- `https://example.com/`__`page/1`__ （`https://example.com/` のエイリアス）
- `https://example.com/`__`page/2`__
- `https://example.com/`__`page/3`__

セクションテンプレートに適用した場合は、次のようなアドレスでアクセスできるようになります。

- `https://example.com/<SECTION>/`__`page/1`__ （`https://example.com/<SECTION>/` のエイリアス）
- `https://example.com/<SECTION>/`__`page/2`__
- `https://example.com/<SECTION>/`__`page/3`__

ここでのポイントは、上記のようなファイル群は、1 つのホームページテンプレート（あるいはセクションテンプレート）から自動的に生成されるということです。
つまり、複数のページとして出力されてはいるものの、ページャー部分以外のコンテンツは同じになります。
後述するように、テンプレート内で __`.Pagenator`__ にアクセスすると、Hugo は自動的にこれらのファイルを出力するようになります。

ちなみに、単独のページを出力するためのシングルページテンプレート (`layouts/_default/single.html`) ではページャー機能は使えないので、代わりに、[ページ切り替え用の「次のページ」、「前のページ」といったリンク](/p/sc9t737/) を表示しておくのがよいでしょう。


ページャーを導入する
----

### 組み込みの pagination テンプレートを使用する

1 グループごとの記事のリストは、__`.Pagenator.Pages`__ で参照することができるようになっているので、これを `range` を使って列挙すれば OK です。
デフォルトでは 10 記事ごとがグルーピングされていますが、このサイズは Hugo のコンフィグファイルの __`Paginate`__ で変更できます。
肝心のページ切り替え部分は、Hugo 組み込みのテンプレート __`_internal/pagination.html`__ を使用して出力することができます。

つまり、ページャー機能を使って、シンプルにグループ内の記事リンクを列挙し、さらに切り替え部分を表示するなら、下記のようなテンプレートを記述すればよいことになります。

{{< code lang="go-html-template" title="layouts/index.html（ホームページテンプレートの抜粋）" >}}
<h3>ページリスト</h3>
<ul>
  {{- range .Paginator.Pages }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{- end }}
</ul>

<h3>切り替え</h3>
{{ template "_internal/pagination.html" . }}
{{< /code >}}

上記のようなテンプレートからは、下記のような URL でアクセス可能な HTML ファイルが出力されます。
ここでは、先頭のグループにアクセスするため、下記のいずれかのアドレスにアクセスしたと考えてください。

- `https://<ドメイン>/`
- `https://<ドメイン>/page/1` （上記のエイリアス）

{{< code lang="html" title="HTML 出力サンプル" >}}
<h3>ページリスト</h3>
<ul>
  <li><a href="/title1/">タイトル1</a>
  <li><a href="/title2/">タイトル2</a>
  <li><a href="/title3/">タイトル3</a>
  <li><a href="/title4/">タイトル4</a>
  <li><a href="/title5/">タイトル5</a>
  <li><a href="/title6/">タイトル6</a>
  <li><a href="/title7/">タイトル7</a>
  <li><a href="/title8/">タイトル8</a>
  <li><a href="/title9/">タイトル9</a>
  <li><a href="/title10/">タイトル10</a>
</ul>

<h3>切り替え</h3>
<ul class="pagination">
  <li><a href="/" aria-label="First"><span aria-hidden="true">&laquo;&laquo;</span></a></li>
  <li class="disabled"><a href="" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
  <li class="active"><a href="/">1</a></li>
  <li><a href="/page/2/">2</a></li>
  <li><a href="/page/3/">3</a></li>
  <li class="disabled"><span aria-hidden="true">&hellip;</span></li>
  <li><a href="/page/8/">8</a></li>
  <li><a href="/page/2/" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
  <li><a href="/page/8/" aria-label="Last"><span aria-hidden="true">&raquo;&raquo;</span></a></li>
</ul>
{{< /code >}}

後半のグループ切り替え部分は、Hugo 組み込みのテンプレート (`_internal/pagination.html`) によって出力されたもので、Bootstrap ベースの構造で出力されています。
Bootstrap を採用していない Web サイトでは、そのままでは思ったような表示にならないので、出力に合わせた CSS を定義してやる必要があります。

- `ul` 要素に設定される __`pagination`__ クラス
- `li` 要素の __`active`__ クラス（現在のページ）と __`disabled`__ クラス（その他のページ）

あたりを考慮した CSS を記述すればよさそうです。
下記は、Bootstrap の `pagination` クラスまわりの定義を抜粋したものです。

```css
.pagination {
  display: inline-block;
  padding-left: 0;
  margin: 20px 0;
  border-radius: 4px;
}
.pagination > li {
  display: inline;
}
.pagination > li > a,
.pagination > li > span {
  position: relative;
  float: left;
  padding: 6px 12px;
  margin-left: -1px;
  line-height: 1.42857143;
  color: #337ab7;
  text-decoration: none;
  background-color: #fff;
  border: 1px solid #ddd;
}
.pagination > li:first-child > a,
.pagination > li:first-child > span {
  margin-left: 0;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
}
.pagination > li:last-child > a,
.pagination > li:last-child > span {
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
}
.pagination > li > a:hover,
.pagination > li > span:hover,
.pagination > li > a:focus,
.pagination > li > span:focus {
  z-index: 2;
  color: #23527c;
  background-color: #eee;
  border-color: #ddd;
}
.pagination > .active > a,
.pagination > .active > span,
.pagination > .active > a:hover,
.pagination > .active > span:hover,
.pagination > .active > a:focus,
.pagination > .active > span:focus {
  z-index: 3;
  color: #fff;
  cursor: default;
  background-color: #337ab7;
  border-color: #337ab7;
}
.pagination > .disabled > span,
.pagination > .disabled > span:hover,
.pagination > .disabled > span:focus,
.pagination > .disabled > a,
.pagination > .disabled > a:hover,
.pagination > .disabled > a:focus {
  color: #777;
  cursor: not-allowed;
  background-color: #fff;
  border-color: #ddd;
}
```

この CSS 定義を使用すると、ページャーの切り替え部分は下記のような表示になります。

{{< image border="true" w="400" src="img-003.png" title="ページャーの表示例" >}}

### 切り替え部分のテンプレートを自作する

ページャーによる切り替え部分は、Hugo の組み込みテンプレート (`_internal/pagination.html`) を使って出力することができました。
この組み込みテンプレートの出力内容が気に入らない場合は、その内容を参考にしながら独自のテンプレートを作成することになるでしょう。
`_internal/pagination.html` の内容は、Hugo の GitHub リポジトリで参照することができます。

- [tpl/tplimpl/embedded/templates/pagination.html -- gohugoio/hugo](https://github.com/gohugoio/hugo/blob/master/tpl/tplimpl/embedded/templates/pagination.html)

