---
title: "URL からクエリ文字列を取り出す (window.location.search, URLSearchParams)"
date: "2020-08-22"
---

Web アプリに検索機能を導入するとき、URL の末尾に指定された次のようなクエリ文字列を処理したいことがよくあります。

```
https://example.com/search?q=Hello&type=file
```

Web ブラウザ上の JavaScript から、URL のクエリ文字列部分を取得するには、[window.location.search](https://developer.mozilla.org/ja/docs/Web/API/Location/search) を参照します。

```javascript
console.log(window.location.search);  //=> '?q=Hello&type=file'
```

実際にクエリ文字列を使用するには、キーと値のペアに分割しないといけないのですが、この処理は Web ブラウザが搭載する [URLSearchParams クラス](https://developer.mozilla.org/ja/docs/Web/API/URLSearchParams) を使って行うことができます。

```javascript
const searchParams = new URLSearchParams(window.location.search);
console.log(searchParams.has('q'));  //=> true
console.log(searchParams.get('q'));  //=> 'Hello'
```

`URLSearchParams` オブジェクトを次のように for-of ループで処理すると、すべてのパラメータを順番に取得することができます。

```javascript
const searchParams = new URLSearchParams(window.location.search);
for (const [key, val] of searchParams) {
  console.log(`${key}: ${val}`);
}
```

