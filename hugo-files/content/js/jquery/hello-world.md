---
title: "JavaScriptメモ: jQuery で Hello World"
url: "p/b5shzha/"
date: "2012-11-28"
tags: ["javascript"]
aliases: [/js/jquery/hello-world.html]
---

コードの基本構成
----

jQuery を使ったコードは、以下のような構成で組み立てていきます。

```javascript
$(セレクタ).イベント(() => {
    $(セレクタ).命令().命令().命令();  // 命令はチェーンできる
}
```


Hello World
----

HTML が読み込み終わった時に自動的に実行される処理は、以下のように記述します。

{{< code lang="javascript" title="方法(1)" >}}
$(document).ready(() => {
    // jQuery を使った処理
});
{{< /code >}}

以下のような省略記法も用意されています。

{{< code lang="javascript" title="方法(2)" >}}
$(() => {
    // jQuery を使った処理
});
{{< /code >}}

以下の例では、jQuery のコードから、div 要素にテキストを追加しています。

{{< code lang="html" title="sample.html" >}}
<!DOCTYPE html>
<meta charset="UTF-8">
<title>jQuery Test</title>

<div id="message">Hello</div>

<script src="./jquery-1.8.3.min.js"></script>
<script>
$(() => {
    $('div#message').append('<b>World</b>');
});
</script>
{{< /code >}}
