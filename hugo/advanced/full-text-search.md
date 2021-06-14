---
title: "全文検索（インクリメンタルサーチ）の機能を付ける"
date: "2018-09-11"
lastmod: "2021-06-14"
description: "ここでは JavaScript を使ってサイト内の全文検索を実現する方法を示します。全文検索を実現する方法としては、Google カスタム検索を利用する方法もありますが、Google カスタム検索は、インターネット上に公開する Web サイトにしか適用できません。ここで紹介する JavaScript を利用した全文検索は、ローカルで運用する Web サイトでも利用できますし、インクリメンタルサーチも実現することができます。"
---

<figure>
    <img src="full-text-search.png" />
    <figcaption>全文検索＋インクリメンタルサーチの完成イメージ（<a target="_blank" href="http://memoja.net/search/">実際のサイト</a>）</figcaption>
</figure>

大まかに、下記のようなファイルを出力できれば、サイト内の全文検索を実現することができます。

1. 検索用データ (`search/data.js`)
2. 検索用ページ＋検索処理用JavaScript (`search/index.html`)

ここでは、全文検索のためのページとして `search` という名前のセクションを作成することにします（URL としては http://localhost:1313/search/ という形でアクセスすることになります）。
すでに `search` というセクション名を使用している場合は適宜変更してください。


検索用データ (search/data.js) の作成
----

まず、全ページの本文を JavaScript から検索できるようするために、検索用のデータ（インデックスファイル）を作成します。
ここでは、`search/data.js` という JavaScript ファイルに、下記のようなフォーマットで変数 `data` として出力することにします。

~~~ javascript
var data = [
  { url:"ページURL1", title:"タイトル1", body:"本文1…" },
  { url:"ページURL2", title:"タイトル2", body:"本文2…" },
  { url:"ページURL3", title:"タイトル3", body:"本文3…" },
  ...
];
~~~

`data` 変数は、各ページの情報を配列として保持しています。
検索ページから上記の JavaScript ファイルを読み込んで、`body` プロパティの内容を検索するように実装します。

`data.js` は、次のような Hugo テンプレートを作成しておけば、全てのコンテンツの情報から自動生成することができます。

#### layouts/search/single.html

~~~
{{ "{{" }}- /* エスケープ処理（改行を空白化、前後の空白削除、連続する空白を集約） */}}
{{ "{{" }}- define "escape" }}
  {{ "{{" }}- trim (replace . "\n" " ") " " | replaceRE " +" " " | jsonify -}}
{{ "{{" }}- end }}

var data = [
{{ "{{" }}- range $index, $page := .Site.Pages }}
  {
    url: {{ "{{" }} $page.Permalink | jsonify }},
    title: {{ "{{" }} $page.Title | jsonify }},
    date: {{ "{{" }} $page.Date | jsonify }},
    body: {{ "{{" }} template "escape" (printf "%s %s" $page.Title $page.Plain) }}
  },
{{ "{{" }}- end }}
];
~~~

JavaScript の文字列として含んではいけない文字をエスケープするために、Hugo の `jsonify` 関数を使用しているところがポイントです。
他にも、連続するスペースの集約や、改行文字の削除などを行っています。

タグの一覧ページ（`.Kind = taxonomy`) や、あるタグの一覧ページ (`.Kind = term`) を検索対象にしたくないときは、上記の `.Pages` のループの中に次のような `if - end` 条件分岐を追加してフィルタします。

~~~
{{ "{{" }}- if not (or (eq $page.Kind "taxonomy") (eq $page.Kind "term")) }}
~~~

このテンプレートファイルを作成しただけでは実際の `data.js` ファイルは出力されないので、下記のようなダミーのコンテンツページを作成して、`data.js` が出力されるようにします。
`url` プロパティを使って、強引に拡張子を `.js` にしてしまうのがポイントです。

#### content/search/data.md

~~~
---
url: "search/data.js"
---
~~~

これで、Hugo によって `search/data.js` ファイルが出力されるようになります。
ここまでできたら、http://localhost:1313/search/data.js にアクセスして、出力されたファイルの内容を確かめてみるとよいでしょう。


検索用ページ (search/index.html) の作成
----

最後に、全文検索を実行するためのページのレイアウト (HTML) と JavaScript コードを作成します。
スタイルシート (CSS) まで含んでいるので若干長いですが、やっていることは、上記で作成した `data.js` 内で定義されている `data` 配列の中身を、フォームに入力された文字列で検索しているだけです。

#### layouts/search/list.html

~~~ html
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>{{ "{{" }} .Page.Title }} | {{ "{{" }} .Site.Title }}</title>
  <script src="data.js"></script>
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

<h1>{{ "{{" }} .Page.Title }}</h1>
<input onkeyup="search(this.value)" size="15" autocomplete="off" autofocus placeholder="検索ワード" />
<span id="inputWord"></span> <span id="resultCount"></span>
<div id="result"></div>

<script>
function search(query) {
  var result = searchData(query);
  var html = createHtml(result);
  showResult(html);
  showResultCount(result.length, data.length);
}

function searchData(query) {
  // 検索にヒットした情報を下記のような配列として格納していく
  // [データのインデックス, 文字の開始位置, 文字の終了位置]
  var result = [];

  query = query.trim();
  if (query.length < 1) {
    return result;
  }
  var re = new RegExp(query, 'i');
  for (var i = 0; i < data.length; ++i) {
    var pos = data[i].body.search(re);
    if (pos != -1) {
      result.push([i, pos, pos + query.length]);
    }
  }
  return result;
}

function createHtml(result) {
  var htmls = [];
  for (var i = 0; i < result.length; ++i) {
    var dataIndex = result[i][0];
    var startPos = result[i][1];
    var endPos = result[i][2];
    var url = data[dataIndex].url;
    var title = data[dataIndex].title;
    var body = data[dataIndex].body;
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
  var el = document.getElementById('result');
  el.innerHTML = html;
}

function showResultCount(count, total) {
  var el = document.getElementById('resultCount');
  el.innerHTML = '<b>' + count + '</b> 件見つかりました（' + total + '件中）';
}
</script>

</body>
</html>
~~~

これで、http://localhost:1313/search/ といった URL にアクセスすれば、全文検索のページが表示されるようになります。


応用：URLのハッシュフラグメントに検索ワードを入れる
----

### やりたいこと

ハッシュフラグメントとは、次のような、URL の末尾についた `#` 以降の文字列のことです。

```
http://localhost:1313/search/#検索ワード
```

ここでは、ハッシュフラグメントに設定された検索ワードを使って全文検索を実行するようにしてみます。
コードは複雑になりますが、このような仕組みにすることで、

- 検索結果のページにリンクを張れるようになる
- 検索結果のページをブックマークできるようになる

といった利点があります（正確には検索結果のページにリンクを張るわけではなく、ページを開いてから検索を実行します）。

### 全文検索のページのコードを修正

元のコードでは、`input` 要素の `onkeyup` 属性を使って `search()` 関数を呼び出すようにしていましたが、次のように、入力した値を URL のハッシュフラグメントにセットするように変更します（2021-06追記: `location.hash=this.value` としてましたが、これだとキー入力のたびに履歴に追加されてしまうので、`location.replace()` を使うように変更しました）。

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
- [Google カスタム検索を設置して記事を検索できるようにする](google-custom-search.html)

