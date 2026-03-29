---
title: "TradeStationメモ: スキップ語の一覧"
url: "p/iahrqsi/"
date: "2018-12-30"
tags: ["tradestation"]
aliases: /tradestation/syntax/skip-words.html
---

EasyLanguage では、コードが自然な英語に近くなるように**スキップ語**というワードをコード内に含めることができます。
スキップ語は意味を持たないワードとして単純に無視されます。

スキップ語として定義されているワードは以下の通りです。

- `a`
- `an`
- `at`
- `based`
- `by`
- `does`
- `from`
- `is`
- `of`
- `on`
- `place`
- `than`
- `the`
- `was`

下記のコードは、スキップ語を使って自然な英語に近づけた例です。

<pre><code>if <strong>the</strong> Close > <strong>the</strong> High <strong>of</strong> 1 bar ago then Buy <strong>on the</strong> next bar <strong>at the</strong> market</code></pre>

これは下記のように短く書いても同じ意味になります。

<pre><code>if Close > High[1] then Buy next bar market</code></pre>

なお、`High[1]` は `High of 1 bar ago` を短縮した表現です。
