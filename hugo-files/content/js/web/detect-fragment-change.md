---
title: "JavaScriptメモ: URL 内のハッシュフラグメントの値を扱う (hashchange, location.hash)"
url: "p/geapn83/"
date: "2015-09-23"
lastmod: "2020-05-23"
tags: ["javascript"]
aliases: [/js/web/detect-fragment-change.html]
---

ハッシュフラグメントの値を取得・設定する
----

`https://example.com/#aaa` という URL でサイトにアクセスした場合に、後ろの `#aaa` の部分を取得するには、 __`location.hash`__ を参照します。
この値には、先頭の `#` が含まれるため、`#` を除いた文字列を取得したい場合は `substring` を合わせて使用します。

```js
const a = location.hash;               // '#aaa'
const b = location.hash.substring(1);  // 'aaa'
```

逆に、ハッシュフラグメントの部分を `#bbb` に変更するには次のようにします。

```js
location.hash = 'bbb';  // 先頭の # は省略可
```

ブラウザの URL 表示は、`https://example.com/#bbb` に変化します。


ハッシュフラグメントに日本語が含まれる場合
----

`https://example.com/#あ` のように、ハッシュフラグメントに日本語が含まれている場合、`location.hash.substring(1)` で取得される値は、`%E3%81%82` といった URI エンコードされた値になってしまいます。
正しく `あ` という日本語を取得するには、次のように __`decodeURI`__ を使って URI デコードします。

```js
const hash = decodeURI(location.hash.substring(1));
```


ハッシュフラグメントの変化イベントをハンドルする
----

URL の後ろの `#aaa` の部分（ハッシュフラグメント）が変化した場合に何か処理をしたい場合は、次のように __`hashchange`__ イベントをハンドルします。

{{< code lang="js" title="バニラ JavaScript の場合" >}}
window.addEventListener('hashchange', () => {
  alert('hash = ' + location.hash);
});
{{< /code >}}

{{< code lang="js" title="jQuery の場合" >}}
$(window).on('hashchange', () => {
  alert('hash = ' + location.hash);
});
{{< /code >}}

このタイミングでページ内のコンテンツを差し替えるようにすれば、画面リロードのない __シングルページアプリケーション (SPA)__ などを作成できます。


ページ表示時にハッシュフラグメントの値を使って何かやる
----

ハッシュフラグメントを含む URL をブラウザで開いたときに、その値を使って HTML 要素を操作したい場合は、 __`DOMContentLoaded`__ イベントをハンドルして処理するのがよいでしょう。

次の例では、ページを表示したタイミングでハッシュフラグメントの値を div 要素内に表示します。

```html
<div id="msg">メッセージ表示領域</div>

<script>
function handleHashFragment() {
  const hash = location.hash.substring(1);
  const elem = document.getElementById('msg');
  elem.innerText = 'ハッシュフラグメントの値: ' + hash;
}

window.addEventListener('DOMContentLoaded', handleHashFragment);
window.addEventListener('hashchange', handleHashFragment);
</script>
```

上記では、ついでにページ表示後にハッシュフラグメントが変化した場合 (`hashchange`) も追従するようにしています。

