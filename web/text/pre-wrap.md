---
title: "pre 要素でもテキストがはみ出さないように折り返す"
date: "2012-10-15"
---

`pre` 要素を使用してソースコードなどを表示しているときに、あまりにも長い行があると、デフォルトでは親要素をはみ出して表示してしまいます。

親要素の幅いっぱいで折り返すようにするには、以下のようにスタイルを設定します。

#### pre 要素で折り返し

~~~ css
pre {
  /* wordwrap settings */
  overflow: auto;
  white-space: pre-wrap;
  word-wrap: break-word;
}
~~~

