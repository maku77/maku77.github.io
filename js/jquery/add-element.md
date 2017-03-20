---
title: 要素を追加、移動、コピーする (append, prepend)
created: 2013-01-06
---


| Description | target 前記型 | target 後記型 |
| ----------- | ------------- | ------------- |
| target 要素内の末尾に追加 | `$(target).append(content)` | `$(content).appendTo(target)` |
| target 要素内の先頭に追加 | `$(target).prepend(content)` | `$(content).prependTo(target)` |
| target 要素の後ろに追加 | `$(target).after(content)` | `$(content).insertAfter(target)` |
| target 要素の前に追加 | `$(target).before(content)` | `$(content).insertBefore(target)` |
| target 要素を置き換える | `$(target).replaceWith(content)` | `$(content).replaceAll(target)` |

引数 **target** には、要素を追加、移動、コピーする先を指定します。
引数 **content** には、追加、移動、コピーする要素を指定します。
以下のようなものを指定できます。

* テキスト
* jQuery オブジェクト
* Element


要素の追加
----

```javascript
$('#elem').append('<p>Hello</p>');   # elem 要素の末尾（内側）に追加
$('#elem').prepend('★');  # elem 要素の先頭（内側）に追加
$('h2').after('<hr/>');    # すべての h1 要素の直前（外側）に追加
$('h2').before('<hr/>');   # すべての h1 要素の直後（外側）に追加
$('hr').replaceWith('<br/>');   # すべての hr 要素を置換
```

target 前記型のメソッドでは、content として function を渡すことで、現在の要素の内容を参照しながら置換する内容を構築したりできます。

```javascript
// すべての h2 を h3 に置き換える
$('h2').replaceWith(function() {
  return '<h3>' + $(this).html() + '</h3>';
});
```

target 後記型のメソッドでは、先に追加したい要素を作成して、パラメータで追加先を指定します。
HTML テキストから任意の要素を動的に作成することができます。
下記の例では、動的に `<img>` 要素を作成し、各 `h1` 要素の後ろに挿入しています。

```javascript
var attrs = {
    src: 'http://example.com/button.png',
    css: { border: '3px solid red' },
    click: function() { alert(this.src); }
};
var $img = $('<img />', attrs);

$img.insertAfter('h1');    # 各 h1 要素の後ろに <img> を挿入
```


要素の移動とコピー
----

既存の要素を移動させたい場合は、target 後記型のメソッドで、content として既存の要素を選択するセレクタを指定します。

```javascript
$('#elem1').insertAfter('#elem2');    # 要素 elem1 を elem2 の後ろへ移動
```

移動先の target が複数の要素を指す場合は、content がコピーされてそれぞれの位置に挿入されます。
1 つの要素をコピーしたいときは、単純にコピーしようとしても上記のサンプルのように「移動」になってしまうので、以下のように `clone()` で新しい要素を作成してからそれを移動するという手順を踏みます。

```javascript
$('.myClass').clone().insertAfter('#target');
```

jQuery オブジェクトを `clone()` した場合は、デフォルトでは、対象要素のイベントハンドラまではコピーされません。イベントハンドラも含めてコピーするには、`clone()` メソッドのパラメータで true を指定します。

```javascript
$('.myClass').clone(true).insertAfter('#target');
```

