---
title: "JavaScriptメモ: jQuery 本体のロード方法いろいろ"
url: "p/juq4qnm/"
date: "2012-11-28"
tags: ["javascript"]
aliases: [/js/jquery/load-jquery.html]
---

jQuery 本体をダウンロードして使用する場合
----

あらかじめ http://jquery.com/ からダウンロードした jQuery 本体をロードする場合のサンプルです。

{{< code lang="html" title="jQuery テンプレート" >}}
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
{{< /code >}}


CDN (Contents Delivery Network) から jQuery をロードする場合
----

jQuery.com や Google の CDN 上から jQuery をロードするようにしておくと、別サイトのアクセス時にロードした jQuery のキャッシュを利用して高速にページを表示できる可能性があります。

{{< code lang="html" title="jquery.com から jQuery をロード" >}}
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script>
    // jQuery を使ったコードを記述
</script>
{{< /code >}}

{{< code lang="html" title="Google から jQuery をロード" >}}
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script>
    // jQuery を使ったコードを記述
</script>
{{< /code >}}

ちなみに、Google は他にもいろいろな JavaScript のライブラリをホストしています。

* 参考: https://developers.google.com/speed/libraries/devguide

例えば、prototype.js も同様に、以下のように使用できます。

{{< code lang="html" title="例: prototype.js のロード" >}}
<script src="//ajax.googleapis.com/ajax/libs/prototype/1.7.1.0/prototype.js"></script>
{{< /code >}}
