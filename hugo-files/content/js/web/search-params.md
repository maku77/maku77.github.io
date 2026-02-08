---
title: "JavaScriptメモ: URL からクエリ文字列を取り出す／辞書オブジェクトからクエリ文字列を生成する (window.location.search, URLSearchParams)"
url: "p/2qhash7/"
date: "2020-08-22"
lastmod: "2021-11-15"
tags: ["javascript"]
aliases: [/js/web/search-params.html]
---

URL からクエリ文字列を取り出す (window.location.search)
----

Web アプリに検索機能を導入するとき、URL の末尾に指定された次のようなクエリ文字列を処理したいことがよくあります。

```
https://example.com/search?aaa=100&bbb=200
```

Web ブラウザ上の JavaScript から、URL のクエリ文字列部分（`?` とその後ろの文字列）を取得するには、[Window.location オブジェクト](https://developer.mozilla.org/ja/docs/Web/API/Window/location) の __`search`__ プロパティを参照します。

```javascript
console.log(window.location.search)  //=> '?aaa=100&bbb=200'
```


クエリ文字列の各パラメーターの値を取得する
----

クエリ文字列をパラメーターごとにキーと値のペアに分割するには、Web ブラウザが搭載する [URLSearchParams クラス](https://developer.mozilla.org/ja/docs/Web/API/URLSearchParams) を使用します。

```javascript
// window.location.search には '?aaa=100&bbb=200' が格納されていると仮定

const searchParams = new URLSearchParams(window.location.search)
console.log(searchParams.has('aaa'))  //=> true
console.log(searchParams.get('aaa'))  //=> '100'
console.log(searchParams.has('bbb'))  //=> true
console.log(searchParams.get('bbb'))  //=> '200'
console.log(searchParams.has('ccc'))  //=> false
console.log(searchParams.get('ccc'))  //=> null
```

`URLSearchParams` オブジェクトを次のように for-of ループで処理すると、すべてのパラメーターを順番に取得することができます。

```javascript
const searchParams = new URLSearchParams(window.location.search)
for (const [key, val] of searchParams) {
  console.log(`${key}: ${val}`)
}
```

{{< code title="実行結果" >}}
aaa: 100
bbb: 200
{{< /code >}}


クエリ文字列を生成する
----

`URLSearchParams` クラスは、キーと値のペアが格納されたオブジェクトからクエリ文字列を生成する機能も備えています。
次のようにコンストラクタにオブジェクトを渡すだけです。

```javascript
const searchParams = new URLSearchParams({
  aaa: 100,
  bbb: 200,
  ccc: 300,
})

console.log(searchParams.toString())  //=> 'aaa=100&bbb=200&ccc=300'
console.log(searchParams.get('aaa'))  //=> '100'
console.log(searchParams.get('bbb'))  //=> '200'
console.log(searchParams.get('ccc'))  //=> '300'
```

ちなみに、`URLSearchParams` インスタンスの `append`、`set`、`delete` メソッドを使うと、パラメーターの追加・上書き・削除を行うことができます。

```javascript
const searchParams = new URLSearchParams()
searchParams.append('aaa', 100)  // 追加
searchParams.append('bbb', 200)  // 追加
searchParams.append('ccc', 300)  // 追加
searchParams.set('bbb', 'XXXX')  // 上書き
searchParams.delete('ccc')       // 削除

console.log(searchParams.toString())  //=> 'aaa=100&bbb=XXXX'
```

