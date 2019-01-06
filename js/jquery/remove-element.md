---
title: "要素を削除する (remove)"
date: "2013-06-20"
---

指定した要素を削除するには、以下の jQuery メソッドを使用します。

* $(selector)**.remove()** -- その要素自体を削除
* $(selector)**.empty()** -- 要素の内側の要素群を削除
* $(selector)**.detach()** -- 一時的に DOM ツリーから取り除く

要素を削除
----

指定した要素そのものを単純に削除する場合は、`remove` メソッドを使用します。

```javascript
$('#elem').remove();    # ID が elem の要素を削除
```

内部を削除
----

指定した要素の、内部に含まれている要素群を削除するには、`empty` メソッドを使用します。

```javascript
$('.secret').empty();   # secret クラスの要素の中身を空にする
```

一時的な削除
----

ある要素を保持する jQuery オブジェクトに対し、**detach()** メソッドを使用すると、一時的に DOM ツリーから取り除くことができます。必要になり次第、再び DOM ツリーに挿入することができます。**detach()** で一時的に取り除いた要素のイベントハンドラも復帰します。

```javascript
var $el = $('#elem');
$el.detach();  // 一時的に削除
$el.insertAfter('#target');  // 復活させる
```

