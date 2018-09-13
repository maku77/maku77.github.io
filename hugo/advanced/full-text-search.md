---
title: "全文検索（インクリメンタルサーチ）の機能を付ける"
date: "2018-09-11"
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
{{ "{{" }}/* エスケープ処理（改行を空白化、前後の空白削除、連続する空白を集約） */}}
{{ "{{" }} define "escape" }}
  {{ "{{" }}- trim (replace . "\n" " ") " " | replaceRE " +" " " | jsonify -}}
{{ "{{" }} end }}

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

