---
title: "Pythonメモ: 数値と文字（文字列）を変換する (chr, ord, int, hex, oct, bin)"
url: "p/35h86i9/"
date: "2013-04-10"
tags: ["python"]
aliases: ["/python/numstr/convert-number-and-string.html"]
---

## アスキーコードと文字の相互変換

{{< code lang="python" title="アスキーコード → 文字 (chr)" >}}
>>> chr(65)
'A'
>>> chr(10)
'\n'
{{< /code >}}

{{< code lang="python" title="文字 → アスキーコード (ord)" >}}
>>> ord('A')
65
>>> ord('\n')
10
{{< /code >}}

`ord()` 関数には、長さが 1 文字の文字列しか渡せないことに注意してください。
2 文字以上の文字列を渡すと、`TypeError` が発生します。


## 数値 (int) と n 進数表記の文字列の相互変換

### n 進数表記の文字列 → int

{{< code lang="python" title="16 進数文字列から int へ" >}}
int('10', 16)  #=> 16
int('ff', 16)  #=> 255
int('0xFF', 16)  #=> 255
{{< /code >}}

{{< code lang="python" title="8 進数文字列から int へ" >}}
int('10', 8)  #=> 8
int('100', 8)  #=> 64
{{< /code >}}

{{< code lang="python" title="2 進数文字列から int へ" >}}
int('100', 2)  #=> 4
int('00001111', 2)  #=>15
{{< /code >}}

#### 文字列のプレフィックスから判断

整数リテラルのプレフィックス（`0x`、`0o`、`0b`）から基数を判断して `int` へ変換するには、第二引数の基数パラメータ (`base`) に 0 を指定します。

```python
int('100', 0)  #=> 100（10進数と判断）
int('0x100', 0)  #=> 256（16進数と判断）
int('0o100', 0)  #=> 64（8進数と判断）
int('0b100', 0)  #=> 4（2進数と判断）
```

### int → n 進数表記の文字列

{{< code lang="python" title="int から 16 進数文字列へ" >}}
hex(10)  #=> '0xa'
hex(16)  #=> '0x10'
hex(255)  #=> '0xff'
{{< /code >}}

{{< code lang="python" title="int から 8 進数文字列へ" >}}
oct(8)  #=> '0o10'
oct(64)  #=> '0o100'
{{< /code >}}

{{< code lang="python" title="int から 2 進数文字列へ" >}}
bin(4)  #=> '0b100'
bin(15)  #=> '0b1111'
{{< /code >}}

`hex()`、`oct()`、`bin()` はそれぞれ、Python のリテラル表記での文字列を返すので、プレフィックスとして `0x`、`0o`、`0b` の付いた文字列が返されます。
これらのプレフィックスを取るには以下のようにスライスすればよいでしょう。

```python
hex(65535)[2:]  #=> 'ffff'
```

さらに、表示上の最小桁数が決まっていて、0 パディングしたい場合は、`zfill()` メソッドが使えます。

```python
hex(65535)[2:].zfill(8)  #=> '0000ffff'
```

任意の基数の文字列へ変換するには、以下のような関数が使えます。

```python
def int2str(n, base):
    if not 2 <= base <= 36:
        raise ValueError('base must be between 2 and 36')

    table = '0123456789abcdefghijklmnopqrstuvwxyz'
    buf = []
    while True:
        n, r = divmod(n, base)
        buf.append(table[r])
        if n == 0: break
    return ''.join(reversed(buf))

# Test
print(int2str(65530, 16))  #=> 'fffa'
print(int2str(64, 8))  #=> '100'
print(int2str(255, 2))  #=> '11111111'
```
