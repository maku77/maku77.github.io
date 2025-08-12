---
title: "ラベル ─ PIC めもめも"
date: 2002-08-01
url: "p/zm6rg3v/"
tags: ["memo"]
aliases: ["/memo/pic/asm/label.html"]
---

ラベル定義の構文
----

```
<ラベル名>   EQU     <リテラル値>
```

EQU 命令を使うことによってラベルを定義することができます。
指定したラベルは、リテラル (k) として使用することができます。
変数領域が割り当てられるわけではないので、定義する時にアドレスを気にしたりする必要はありません。

{{< code title="例: MAX_VAL を 0xFF として定義" >}}
MAX_VAL  EQU   0xFF    ; MAX_VAL=0xFF
{{< /code >}}

これは、C 言語のプリプロセッサの `#define` のようなものです。

```cpp
#define MAX_VAL 0xFF
``` 

