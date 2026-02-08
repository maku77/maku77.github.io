---
title: "Javaメモ: 全角文字と半角文字を含んだ文字列を正規化して表記ゆれを吸収する"
url: "p/j3jbsey/"
date: "2014-11-20"
tags: ["java"]
aliases: /java/numstr/normalize-string.html
---

**`java.text.Normalizer`** クラスを使うと、全角文字や半角文字の混在した文字列を一定の規則に基いて正規化することができます。
微妙に表記の異なる文字列同士を比較するような際に使用できます。

{{< code lang="java" title="使用例" >}}
String s = Normalizer.normalize("aａAＡｲイﾊﾟパ1１2２＋+-－．.～~）)}｝", Normalizer.Form.NFKC);
{{< /code >}}

{{< code title="変換結果" >}}
AAAAイイパパ1122++--..~~))}}
{{< /code >}}

大まかには、アルファベットや数字、記号は半角に統一され、半角カタカナは全角に統一されるという動きをします。

2 文字で構成される半角カタカナの「ﾊﾟ」をうまく全角のカタカナ「パ」に変換するには、第2引数 (`form`) に `Normalizer.Form.NFKC` を指定する必要があります。

