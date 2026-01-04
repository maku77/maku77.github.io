---
title: "Node.jsメモ: jQuery クライアントと Express サーバで JSON データを送受信する例"
url: "p/twrqbe3/"
date: "2013-11-05"
tags: ["nodejs"]
aliases: /nodejs/express/exchange-json.html
---

クライアント (jQuery) からサーバ (Express) への JSON データ送信（GET メソッド）
----

{{< code lang="javascript" title="クライアント（送信側）" >}}
var jsonData = { aaa: 100, bbb: {ccc: 200} };
$.ajax({
    type: 'GET',
    url: 'http://localhost:7000/api',
    data: jsonData,
    contentType: 'application/json',  // 送信側のヘッダ情報
    success: function(data, status, xhr) { alert('OK'); },
    error: function(xhr, status, err) { alert('ERROR'); }
});
{{< /code >}}

{{< code lang="javascript" title="Express サーバ（受信側）" >}}
app.get('/api', function(req, res) {
    var jsonData = req.query;
    console.dir(jsonData);

    res.send('');  // とりあえずレスポンス処理を終わらせる
});
{{< /code >}}


サーバ (Express) からクライアントへの JSON データ送信（GET メソッド）
----

{{< code lang="javascript" title="Express サーバ（送信側）" >}}
app.get('/api', function(req, res) {
    var obj = {aaa:100, bbb:200};
    res.json(obj);
});
{{< /code >}}

`res.send()` あるいは `res.json()` メソッドで、`Array` や `Object` を送信すると、下記のレスポンスヘッダが設定されて JSON データとして送信されます。

```
Content-Type: application/json; charset=utf-8
```

テキスト形式の JSON データを、上記の `Content-Type` で送信したい場合は、`res.send()` ではなく、必ず `res.json()` メソッドを使用する必要があります。
`res.send()` のパラメータにテキストデータを渡すと、デフォルトで `Content-Type` が `text/html` に設定されてしまうからです。

```javascript
app.get('/api', function(req, res) {
    var str = '{"aaa":100, "bbb":200}';
    res.json(str);  // res.send(str) はダメ！
});
```

オブジェクトであっても文字列であっても、JSON データを返したいときは `res.json()` メソッドを使っておけば間違いありません。

`Content-Type` を明示的に指定するのであれば、`res.send()` でも JSON 形式の文字列を送信することができます。

```javascript
app.get('/api', function(req, res) {
    var str = '{"aaa":100, "bbb":200}';
    res.contentType('json');
    res.send(str);
});
```

`Content-Type` ヘッダの設定は、下記のどの方法でも大丈夫です。

```javascript
res.type('json');
res.type('application/json');
res.contentType('json');
res.contentType('application/json');
res.set('Content-Type', 'json');  // これだけはNG
res.set('Content-Type', 'application/json');
```

`res.send()` メソッドと同様に `res.json()` メソッドでもレスポンスコードを指定することができます。

```javascript
res.json(500, { error: 'message' });
```

JSON データを受け取るクライアント側は、例えば下記のように実装します（jQuery を使用した場合）。

{{< code lang="javascript" title="クライアント（受信側）" >}}
$.ajax({
    type: 'GET',
    url: 'http://localhost:7000/api',
    dataType: 'json',  // サーバから取得するデータの型
    success: function(data, status, xhr) {
        alert('OK');
        console.dir(data);
    },
    error: function(xhr, status, err) {
        alert('ERROR');
        console.dir(err);
    }
});
{{< /code >}}

