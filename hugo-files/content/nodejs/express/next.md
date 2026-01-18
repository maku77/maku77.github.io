---
title: "Node.jsメモ: Express の next() メソッドで次のハンドラへ処理を委譲する"
url: "p/izz3fkg/"
date: "2014-03-23"
tags: ["nodejs"]
aliases: /nodejs/express/next.html
---

Express のルーティングハンドラーを設定する際には、下記のように `app.get()` や `app.post()` メソッドを使用します。

```js
app.get('/path', function(req, res, next) {
    // handler code
});
```

このハンドラ関数の第 3 パラメータ `next` は、Express が自動的に渡してくれる関数で、この関数を呼び出すことで、次に登録されているハンドラへ処理を委譲することができます。
`app.get()` に指定したハンドラの第 3 パラメータの `next` メソッドを呼び出すと、次のハンドラに処理を委譲することができます。
例えば、

```js
app.get('/test', ...);
app.get('/test', ...);
```

のように、同じパスに対して、複数のハンドラを設定しておいて、条件に一致したときに次のハンドラへ処理を委譲させるといったことができます。
`next()` を明示的に呼び出さない場合は、最初に設定したハンドラしか呼び出されません。
下記の例では、`/test/hello` というパスでアクセスした場合にだけ、２番目のハンドラに処理を委譲します。

```js
app.get('/test/:id', function(req, res, next) {
    if (req.params.id === 'hello') {
        next();  // delegate to the second handler
    } else {
        res.send('1st handler');
    }
});

app.get('/test/:id', function(req, res) {
    res.send('2nd handler');
});
```

