---
title: "URL 内のハッシュフラグメントの変化を検出する (hashchange)"
date: "2015-09-23"
---

```
http://example.com/#aaa
```

という URL でサイトにアクセスした場合に、後ろの `#aaa` の部分が変化した場合のイベント通知を受けるには以下のようにします。

```javascript
window.addEventListener('hashchange', function() {
  var hash = location.hash;  // '#aaa'
  alert(hash.substring(1));  // 'aaa'
}, false);
```

jQuery を使用する場合は、下記のように記述できます。

```
$(function() {
  $(window).on('hashchange', function() {
    var hash = location.hash;  // '#aaa'
    alert(hash.substring(1));  // 'aaa'
  });
});
```

このタイミングでページ内のコンテンツを差し替えるようにすれば、画面リロードのないシングルページアプリケーションを作成できます。
逆に、ハッシュフラグメントの部分を `#bbb` に変更するには以下のようにします。

```javascript
location.hash = 'bbb';
```

