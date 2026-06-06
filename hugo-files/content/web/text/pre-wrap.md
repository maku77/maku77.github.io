---
title: "HTML/CSSメモ: pre 要素でもテキストがはみ出さないように折り返す"
url: "p/hexkm9d/"
date: "2012-10-15"
tags: ["css"]
aliases: /web/text/pre-wrap.html
---

`pre` 要素を使用してソースコードなどを表示しているときに、あまりにも長い行があると、デフォルトでは親要素をはみ出して表示してしまいます。

親要素の幅いっぱいで折り返すようにするには、以下のようにスタイルを設定します。

{{< code lang="css" title="pre 要素で折り返し" >}}
pre {
  overflow: auto;
  white-space: pre-wrap;
  overflow-wrap: break-word;
}
{{< /code >}}

