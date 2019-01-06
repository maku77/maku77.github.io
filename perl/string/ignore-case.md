---
title: "大文字と小文字を区別しない文字列マッチング"
date: "2008-03-11"
---

`m//` 演算子による正規表現マッチング時に、オプション修飾子 (option modifier) として `/i` を指定すると、大文字と小文字を区別しないマッチングを行うことができます。

```perl
$_ = 'Hello regular expression';
if (/pattern/i) {
    ...
}
```

