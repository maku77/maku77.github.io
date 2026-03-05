---
title: "C++メモ: default ラベルの綴りを間違えてもエラーにならない"
url: "p/5yzkdby/"
date: "2005-07-26"
tags: ["cpp"]
aliases: /cpp/typo-of-default.html
---

C/C++ では次のように `default` ラベルの綴りを間違えても普通にコンパイルが通ってしまいます。

```cpp
switch (n) {
case 1:
    break;
defalt:       // "default:" の間違い
    break;
}
```

これは綴りを間違えたラベルが単なるジャンプ先のラベルとしてみなされるからです。
例えば、次のようなコーディングがされる可能性があるため、コンパイラはエラーを出さないのです。

```cpp
switch (n) {
case 1:
    foo();
    break;
DEFAULT_PROCESS:  // ここには色んな場所から飛んでくる可能性がある
    bar();
    break;
}

if (hoge) {
    goto DEFAULT_PROCESS;  // goto ジャンプで switch の中へ
}
```

