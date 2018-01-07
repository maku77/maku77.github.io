---
title: jQuery の ajax/get/post メソッドが動作しない
date: "2013-10-29"
---

dataType 間違いでコールバックが呼ばれないケース
----

Ajax 系のコールバックが呼び出されない場合は、コールバックで受け取るデータ形式を示す、`dataType` の指定が間違っている可能性があります。
POST の際に間違いがちですが、`dataType` に指定するタイプは、クライアントが送るデータのタイプではなく、サーバが返すデータのタイプです。

~~~ javascript
$.ajax({
    type: 'POST',
    url: '/memos/create',
    data: {body: 'Memo1'},
    success: function(data) { alert('Created!'); },
    dataType: 'text'  // サーバが返すデータのタイプを指定
});

// 下記も同様
$.post('/memos/create', {body: 'Memo1'},
        function() { alert('Created!'); }, 'text');
~~~


click イベントによるイベントと重なってしまうケース
----

ボタンの click イベントをトリガにして Ajax 通信を発生させる場合、デフォルトの click イベント処理と重複してうまく動かないことがあります。
これを解決するには、デフォルトのイベントをキャンセルして Ajax 通信を行います。

~~~ javascript
$('#btn').click(function(e) {
    e.preventDefault();    // デフォルトの挙動をキャンセル
    $.ajax(...);
});
~~~


GET/POST/PUT/DELETE メッセージがキャッシュされてしまっているケース
----

ブラウザにより異なりますが、特に IE や Firefox で、HTTP リクエストがキャッシュされてしまい、Ajax リクエストが実行されないことがあります。
これを防ぐには、`$.ajax()` のオプションとして、`cache: false` を指定します。

~~~ javascript
$.ajax({
    type: 'DELETE',
    url: '/memos/' + encodeURI(id),
    cache: false,    // リクエストをキャッシュしない
    success: function(result) {
        updateMemos();
    }
});
~~~

