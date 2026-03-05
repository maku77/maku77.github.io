---
title: "C++メモ: Usage の出力例"
url: "p/9tr9g8n/"
date: "2009-07-08"
tags: ["cpp"]
aliases: /cpp/misc/usage.html
---

簡単な Usage 文を表示してプログラムを終了させる関数のサンプルです。
プログラムを `exit(1)` で終了させるのがポイント。

```cpp
static void Usage() {
    fprintf(stderr, "Usage: expand [-t tablist] [file ...]\n");
    exit(1);
}
```

### 参考文献

{{< amazon
  itemId="4839956693"
  title="Code Reading ~オープンソースから学ぶソフトウェア開発技法~"
  author="Diomidis Spinellis"
  publisher="マイナビ出版"
  imageUrl="https://m.media-amazon.com/images/I/71prgJ5jy6L._SL1000_.jpg"
>}}

