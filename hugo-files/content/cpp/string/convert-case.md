---
title: "C++メモ: ビット演算で小文字と大文字を変換する"
url: "p/c75ks58/"
date: "2011-12-17"
tags: ["cpp"]
aliases: /cpp/string/convert-case.html
---

ASCII コードの `A`～`Z`、`a`〜`z` は以下のような配置になっています。

```
A(01000001) ~ Z(01011010)
a(01100001) ~ z(01111010)
```

つまり、6 番目のビット（10 進数で 32）が 1 ならば小文字、0 ならば大文字になります。
これを利用して、ビット演算で大文字と小文字の変換を行うことができます（32 を足し引きしても同じことができます）。

{{< code lang="cpp" title="例: 全て小文字に変換" >}}
char str[] = "HelloWorld";
int n = strlen(str);
for (int i = 0; i < n; ++i) {
    str[i] |= 0x20;
}
cout << str << endl;
{{< /code >}}

{{< code lang="cpp" title="例: 全て大文字に変換" >}}
for (int i = 0; i < n; ++i) {
    str[i] &= ~0x20;
}
cout << str << endl;
{{< /code >}}

{{< code lang="cpp" title="例: （おまけ）大文字と小文字の反転 (XOR)" >}}
for (int i = 0; i < n; ++i) {
    str[i] ^= 0x20;
}
cout << str << endl;
{{< /code >}}

