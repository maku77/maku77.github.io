---
title: jQuery 本体のロード方法いろいろ
created: 2012-11-28
---

jQuery 本体をダウンロードして使用する場合
----

あらかじめ http://jquery.com/ からダウンロードした jQuery 本体をロードする場合のサンプルです。

#### jQuery テンプレート

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>jQuery sample</title>
</head>
<body>
    ...
    <script src="./jquery-1.8.3.min.js"></script>
    <script>
        // jQuery を使ったコードを記述
    </script>
</body>
</html>
```


CDN (Contents Delivery Network) から jQuery をロードする場合
----

jQuery.com や Google の CDN 上から jQuery をロードするようにしておくと、別サイトのアクセス時にロードした jQuery のキャッシュを利用して高速にページを表示できる可能性があります。

#### jquery.com から jQuery をロード

```html
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script>
    // jQuery を使ったコードを記述
</script>
```

#### Google から jQuery をロード

```html
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script>
    // jQuery を使ったコードを記述
</script>
```

ちなみに、Google は他にもいろいろな JavaScript のライブラリを Host してます。

* 参考: https://developers.google.com/speed/libraries/devguide

例えば、prototype.js も同様に、以下のように使用できます。

#### 例: prototype.js のロード

```html
<script src="//ajax.googleapis.com/ajax/libs/prototype/1.7.1.0/prototype.js"></script>
```

