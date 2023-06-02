---
title: "Hugo に全文検索（インクリメンタルサーチ）の機能を付ける"
url: "p/p4n5m3i/"
date: "2018-09-11"
lastmod: "2023-05-04"
tags: ["Hugo"]
description: "ここでは JavaScript を使ってサイト内の全文検索を実現する方法を示します。全文検索を実現する方法としては、Google カスタム検索を利用する方法もありますが、Google カスタム検索は、インターネット上に公開する Web サイトにしか適用できません。ここで紹介する JavaScript を利用した全文検索は、ローカルで運用する Web サイトでも利用できますし、インクリメンタルサーチも実現することができます。"
changes:
  - 2023-05-04: data.js を出力せず、data 配列を直接 search/index.html に埋め込むよう変更
aliases: /hugo/advanced/full-text-search.html
---

ここでは、Hugo サイトに全文検索の機能を付ける方法を説明します。
全文検索を実現する方法としては、Google カスタム検索を導入する方法もありますが、Google カスタム検索は、インターネット上に公開する Web サイトにしか適用できません。
ここで紹介する JavaScript を利用した全文検索は、ローカルで運用する Web サイトでも利用できますし、インクリメンタルサーチも実現することができます（[実際のサイトの例](https://toushi.maku.blog/search/)）。

{{< image src="img-001.png" title="全文検索＋インクリメンタルサーチの完成イメージ" >}}

大まかに、下記のようなコードを含む HTML ファイルを出力できれば、サイト内の全文検索を実現することができます。

1. 検索用 JavaScript データ (`const data = [...]`)
2. 検索用 HTML フォーム (`<input>`)
3. 検索用 JavaScript 関数 (`function search() {...}`)

ここでは、全文検索のためのページとして `search` という名前のページ (`content/search.md`) を作成することにします（URL としては `http://localhost:1313/search/` という形でアクセスすることになります）。
すでに `search` という名前のページを作成している場合は適宜変更してください。

{{% note title="2023-05-04 追記" %}}
以前の実装では、検索用データ (`search/data.js`) と検索用ページ (`search/index.html`) を分けて出力していましたが、検索用ページに統合するように変更しました。
Hugo がバージョンアップして、出力ファイルに livereload 用の script タグを自動挿入するようになり、正しい `data.js` ファイルとして出力できなくなってしまったからです。
{{% /note %}}


コンテンツファイル (content/search.md) の作成
----

まず、全文検索用のページを出力するためのコンテンツファイルを作成しておきます。

{{< code lang="yaml" title="content/search.md" >}}
---
title: "サイト内全文検索"
layout: search
_build: { list: never }
---
{{< /code >}}

フロントマターの __`layout`__ プロパティで `search` という名前のテンプレートを使うことを指定しておきます。
ついでに、__`_build`__ プロパティで、このページをリスト系ページの一覧に列挙しないようにしておきます（参考: [記事一覧に列挙されないページを作る](/p/4ziyhxe/)）。

本文はすべてテンプレート側で出力するので、Markdown ファイルには上記のようにフロントマターだけ記述しておけば OK です。


テンプレートファイル (layouts/_default/search.html) の作成
----

### 検索用 JavaScript データの出力

全ページの本文を JavaScript から検索できるようするために、検索用のデータ（インデックス）を作成します。
ここでは、HTML ファイルの `<script>` 要素内に、次のようなフォーマットで __`data`__ 変数を出力することにします。

```js
const data = [
  { url:"ページURL1", title:"タイトル1", body:"本文1…" },
  { url:"ページURL2", title:"タイトル2", body:"本文2…" },
  { url:"ページURL3", title:"タイトル3", body:"本文3…" },
  ...
];
```

`data` 変数は、各ページの情報を配列として保持しています。
ユーザーが検索キーワードを入力したときに、この変数内の `body` プロパティの内容を検索するように実装します。
テンプレートファイル内で次のように実装しておけば、全ページの情報を含む `data` 変数のコードを出力することができます。

{{< code lang="go-html-template" title="layouts/_default/search.html（途中経過）" >}}
{{- /* エスケープ処理（改行を空白化、前後の空白削除、連続する空白を集約） */}}
{{- define "escape" }}
  {{- trim (replace . "\n" " ") " " | replaceRE " +" " " -}}
{{- end }}

<script>
// 検索用のインデックスデータ
const data = [
{{- range $index, $page := .Site.Pages }}
  {
    url: {{ $page.RelPermalink }},
    title: {{ $page.Title }},
    date: {{ $page.Date }},
    body: {{ template "escape" (printf "%s %s" $page.Title $page.Plain) }}
  },
{{- end }}
];
</script>
{{< /code >}}

本文内の連続するスペースの集約や、改行文字の削除を行うために、`escape` という部分テンプレートを定義しています（`define` で定義し、`template` で呼び出しています）。

タグの一覧ページ（`.Kind = taxonomy`) や、タグの付いた記事の一覧ページ (`.Kind = term`) を検索対象にしたくないときは、上記の `.Pages` の `range` ループの中に次のような `if` - `end` 条件分岐を追加すればフィルタできます。

```go-html-template
{{- if not (or (eq $page.Kind "taxonomy") (eq $page.Kind "term")) }}
  {
    ...
  },
{{- end }}
```

ここまでできたら、`hugo serve` でローカルサーバーを起動して `http://localhost:1313/search/` にアクセスして、出力されたファイルの内容を確かめてみるとよいでしょう。
ただし、この時点ではブラウザ上での表示は空っぽです。
`data` 変数は `<script>` 要素内に出力されているため、その内容を確認するためには、ブラウザ上で右クリックして「ページのソースを表示」などから確認する必要があります。


### 検索用 HTML フォームと JavaScript 関数の出力

あとは、全体の UI として、ユーザーがキーワードを入力するための `<input>` 要素などを配置し、そのキーワードで `data` 配列の内容を検索するための JavaScript 関数 (`search`) などを追加します。
スタイルシート (CSS) まで含んでいるので若干長いですが、やっていることは単純で、キーワードを含むページを見つけてその概要を出力しているだけです。

{{< code lang="go-html-template" title="layouts/_default/search.html（完成版）" >}}
{{- /* エスケープ処理（改行を空白化、前後の空白削除、連続する空白を集約） */}}
{{- define "escape" }}
  {{- trim (replace . "\n" " ") " " | replaceRE " +" " " -}}
{{- end }}
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>{{ .Page.Title }} | {{ .Site.Title }}</title>
  <style>
    body {
      background: #fafafa;
    }
    input {
      color: deeppink;
      font-size: 1.2em;
      font-weight: bolder;
    }
    input::-webkit-input-placeholder {
      color: pink;
    }
    #result {
      margin: 1em;
    }
    .item_title {
      text-decoration: none;
      color: #36f;
      font-weight: bolder;
    }
    .item_excerpt {
      background: white;
      margin: 0.5em 2em 1em;
      padding: 0.5em;
      border: dashed 1px lightgray;
      font-size: smaller;
    }
    .item_excerpt b {
      background: pink;
    }
  </style>
</head>
<body>

<h1>{{ .Page.Title }}</h1>
<input onkeyup="search(this.value)" size="15" autocomplete="off" autofocus placeholder="検索ワード" />
<span id="inputWord"></span> <span id="resultCount"></span>
<div id="result"></div>

<script>
// 検索用のインデックスデータ
const data = [
{{- range $index, $page := .Site.Pages }}
{{- if not (or (eq $page.Kind "taxonomy") (eq $page.Kind "term")) }}
  {
    url: {{ $page.RelPermalink }},
    title: {{ $page.Title }},
    date: {{ $page.Date }},
    body: {{ template "escape" (printf "%s %s" $page.Title $page.Plain) }}
  },
{{- end }}
{{- end }}
];

function search(query) {
  const result = searchData(query);
  const html = createHtml(result);
  showResult(html);
  showResultCount(result.length, data.length);
}

function searchData(query) {
  // 検索にヒットした情報を下記のような配列として格納していく
  // [データのインデックス, 文字の開始位置, 文字の終了位置]
  const result = [];

  query = query.trim();
  if (query.length < 1) {
    return result;
  }
  const re = new RegExp(query, 'i');
  for (let i = 0; i < data.length; ++i) {
    const pos = data[i].body.search(re);
    if (pos != -1) {
      result.push([i, pos, pos + query.length]);
    }
  }
  return result;
}

function createHtml(result) {
  const htmls = [];
  for (let i = 0; i < result.length; ++i) {
    const dataIndex = result[i][0];
    const startPos = result[i][1];
    const endPos = result[i][2];
    const url = data[dataIndex].url;
    const title = data[dataIndex].title;
    const body = data[dataIndex].body;
    htmls.push(createEntry(url, title, body, startPos, endPos));
  }
  return htmls.join('');
}

function createEntry(url, title, body, startPos, endPos) {
  return '<div class="item">' +
      '<a class="item_title" href="' + url + '">' + title + '</a>' +
      '<div class="item_excerpt">' + excerpt(body, startPos, endPos) + '</div>' +
      '</div>';
}

function excerpt(body, startPos, endPos) {
  return [
    body.substring(startPos - 30, startPos),
    '<b>', body.substring(startPos, endPos), '</b>',
    body.substring(endPos, endPos + 200)
  ].join('');
}

function showResult(html) {
  const el = document.getElementById('result');
  el.innerHTML = html;
}

function showResultCount(count, total) {
  const el = document.getElementById('resultCount');
  el.innerHTML = '<b>' + count + '</b> 件見つかりました（' + total + '件中）';
}
</script>

</body>
</html>
{{< /code >}}

これで、`http://localhost:1313/search/` といった URL にアクセスすれば、全文検索のページが表示されるようになります。


（応用）URL のハッシュフラグメントに検索ワードを入れる
----

### やりたいこと

ハッシュフラグメントとは、次のような、URL の末尾についた __`#`__ 以降の文字列のことです。

```
http://localhost:1313/search/#検索ワード
```

ここでは、ハッシュフラグメントに設定された検索ワードを使って全文検索を実行するようにしてみます。
コードは複雑になりますが、このような仕組みにすることで、

- 検索結果のページにリンクを張れるようになる
- 検索結果のページをブックマークできるようになる

といった利点があります（正確には検索結果のページにリンクを張るわけではなく、ページを開いてから検索を実行します）。

### 全文検索のページのコードを修正

元のコードでは、`input` 要素の `onkeyup` 属性を使って `search()` 関数を呼び出すようにしていましたが、次のように、入力した値を URL のハッシュフラグメントにセットするように変更します（2021-06 追記: `location.hash=this.value` としてましたが、これだとキー入力のたびに履歴に追加されてしまうので、`location.replace()` を使うように変更しました）。

```html
<input id="query" onkeyup="location.replace('#' + this.value)"
  size="15" autocomplete="off" autofocus placeholder="検索ワード" />
```

これにより、ユーザーが検索ワードを入力するたびに、ブラウザの URL 欄のハッシュフラグメントにその値が反映されるようになります。

次に、ページの表示時 (`DOMContentLoaded`) と、ハッシュフラグメントの変更時 (`hashchange`) に、ハッシュフラグメントの値を使って `search()` 関数を呼び出すようにします。

```js
// ハッシュフラグメントの値で検索を実行
function searchWithHash() {
  const hash = decodeURI(location.hash.substring(1));
  search(hash);

  // 必要があれば input 要素の値を更新
  const queryElem = document.getElementById('query');
  if (queryElem.value !== hash) {
    queryElem.value = hash;
  }
}

// ハッシュフラグメント付きの URL でページを開いたときに検索
window.addEventListener('DOMContentLoaded', searchWithHash);

// ページ表示後にハッシュフラグメントが変化したら検索
window.addEventListener('hashchange', searchWithHash);
```

これで、ハッシュフラグメント経由での全文検索を実行できるようになります。
検索ワード付きの URL をブラウザで開いたときには、その検索ワードを使って即全文検索が実行されるようになり、その検索ワードが `input` 要素に表示されます。
ページ表示後にユーザーが `input` 要素のテキストを変更すれば、URL のハッシュフラグメントに反映され、その値で再度検索が実行されます。

### 他のページに検索ボックスを配置する

次のような検索フォームを任意のページに配置しておけば、そこから検索ワードを入力して全文検索のページにジャンプできるようになります。

```html
<form>
  <input id="searchKeyword" type="search" size="20">
  <input id="searchButton" type="submit" value="検索">
</form>

<script>
const SEARCH_URL = '/search/';

window.addEventListener('DOMContentLoaded', () => {
  const searchButton = document.getElementById('searchButton');
  const searchKeyword = document.getElementById('searchKeyword');

  // 検索ボタンのクリックで検索ページへジャンプ
  searchButton.addEventListener('click', e => {
    e.preventDefault();  // Prevent default form's behavior
    const query = searchKeyword.value;
    const url = query ? SEARCH_URL + '#' + query : SEARCH_URL;
    location.href = url;
  });
});
</script>
```

`form` 要素のデフォルトアクションではハッシュフラグメントに検索ワードを付加できないので、検索ボタンの `click` イベントをハンドルし、自力でハッシュフラグメントをセットした上でページ遷移を行うようにしています。


参考
---

- [URL 内のハッシュフラグメントの値を扱う (hashchange, location.hash) ｜ まくまくJavaScriptノート](/js/web/detect-fragment-change.html)
- [Hugo で Google カスタム検索を設置して記事を検索できるようにする](/p/n4o7o6m/)

