---
title: jQuery で Hello World
date: "2012-11-28"
---

コードの基本構成
----

jQuery を使ったコードは、以下のような構成で組み立てていきます。

```javascript
$(セレクタ).イベント(function() {
    $(セレクタ).命令().命令().命令();  // 命令はチェーンできる
}
```


Hello World
----

HTML が読み込み終わった時に自動的に実行される処理は、以下のように記述します。

#### 方法(1)
```javascript
$(document).ready(function() {
    // jQuery を使った処理
});
```

以下のような省略記法も用意されています。

#### 方法(2)
```javascript
$(function() {
    // jQuery を使った処理
});
```

以下の例では、jQuery のコードから、div 要素にテキストを追加しています。

#### sample.html

```html
<!DOCTYPE html>
<meta charset="UTF-8">
<title>jQuery Test</title>

<div id="message">Hello</div>

<script src="./jquery-1.8.3.min.js"></script>
<script>
$(function() {
    $('div#message').append('<b>World</b>');
});
</script>
```

