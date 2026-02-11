---
title: "JavaScriptメモ: 使用している Web ブラウザが IE (Internet Explorer) かどうか判別する"
url: "p/d52isdv/"
date: "2020-02-20"
tags: ["javascript"]
aliases: [/js/env/is-ie.html]
---

JavaScript で IE かどうかを判別する
----

### IE 判別関数 (isIe)

[navigator.userAgent](https://developer.mozilla.org/ja/docs/Web/API/NavigatorID/userAgent) プロパティを参照して、ブラウザのユーザーエージェントを調べることで、使用しているブラウザが IE (Internet Explorer) であるかを判別することができます。
IE の場合、ユーザーエージェント名は **`MSIE `** あるいは **`Trident/`** で始まります。

```js
// 使用中のブラウザが IE であるかどうかを判別します。
function isIe() {
  const ua = navigator.userAgent;
  return ua.indexOf('MSIE ') > -1 || ua.indexOf('Trident/') > -1;
}
```

ただし、`navigator.userAgent` プロパティの値はブラウザの設定により書き換えられる可能性があることに注意してください。
まぁ、その場合はユーザーは承知の上で自分で書き換えているはずなので特に問題はないと思いますが。


### 使用例 (1) ダイアログで表示

```js
if (isIe()) {
  alert('It is IE');
} else {
  alert('It is NOT IE');
}
```

### 使用例 (2) body 要素の末尾に警告表示

`body` 要素の末尾にテキストノードを動的に追加し、IE 以外の最新ブラウザを使うことをユーザーに促す方法です。

```js
if (isIe()) {
  const div = document.createElement('div');
  const text = document.createTextNode('IE には対応していません。最新のブラウザを使用してください。');
  div.appendChild(text);
  document.getElementsByTagName('body')[0].appendChild(div);
}
```

### 使用例 (3) 指定した HTML 要素にテキスト表示

エラーメッセージを表示するための HTML 要素をあらかじめ配置しておき、そこに警告メッセージを表示する方法です。

{{< code lang="html" title="html 抜粋" >}}
<div id="ie-error" style="color: red;"></div>
{{< /code >}}

{{< code lang="js" title="js 抜粋" >}}
if (isIe()) {
  document.getElementById('ie-error').innerText =
    'IEには対応していません。最新のブラウザを使用してください。';
}
{{< /code >}}


条件付きコメントで IE かどうかを判別する（IE 10 以前のみ）
----

IE 11 より前のバージョンの IE では、[Conditional comments（条件付きコメント）](https://docs.microsoft.com/en-us/previous-versions/windows/internet-explorer/ie-developer/compatibility/hh801214(v=vs.85))という構文で、IE かどうかを判別して HTML コードを分岐させることができました。

```html
<!--[if IE]>
<img src="ie.png">
<![endif]-->

<!--[if !IE]><!-->
<img src="not-ie.png">
<!--<![endif]-->
```

IE 11 では Conditional comments はサポートされていないので、JavaScript で判定する必要があります。

