---
title: "EasyLanguage: スキップ後の一覧"
date: "2018-12-30"
---

EasyLanguage によるプログラミングでは、コードが自然な英語に近くなるように、**スキップ語**というワードを含めることができるようになっています。
スキップ語は意味を持たないワードとして単純に無視されます。

スキップ語として下記のようなものが定義されています。

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

下記のコードは、上記のようなスキップ語をふんだんに使用した例です。

<pre><code>if <strong>the</strong> Close > <strong>the</strong> High <strong>of</strong> 1 bar ago then Buy <strong>on the</strong> next bar <strong>at the</strong> market</code></pre>

これは下記のように短く書いても同じ意味になります。

<pre><code>if Close > High[1] then Buy next bar market</code></pre>

ちなみに、`High[1]` は `High of 1 bar ago` を短くした表現です。

