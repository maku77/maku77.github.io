---
title: "Pythonメモ: 2進数、8進数、16進数の数値リテラル"
url: "p/3bkr3ka/"
date: "2009-11-18"
tags: ["python"]
aliases: ["/python/numstr/num-literal.html"]
---

数値のプレフィックスとして、`0b`, `0o`, `0x` を付けることにより、十進表記以外で数値を表すことができます。

{{< code lang="python" title="2 進数" >}}
>>> 0b0111
7
{{< /code >}}

{{< code lang="python" title="8 進数" >}}
>>> 0o777
511
{{< /code >}}

{{< code lang="python" title="16 進数" >}}
>>> 0xFFFF
65535
{{< /code >}}

Python 3.0 以前は 8 進数を以下のように表現していましたが、Python 3.0 以降は SyntaxError になるので、`0o` を使用する必要があります。

{{< code lang="python" title="8 進数（旧式）" >}}
>>> 0777
511
{{< /code >}}
