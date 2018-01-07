---
title: strncpy／strncat／snprintf の安全な使い方
date: "2007-11-16"
---

各関数の概要
----

C89 で追加された `strncpy`、`strncat` 及び、`snprint` 関数では、バッファサイズを指定するパラメータがありますが、ここのサイズ指定を間違えがちなのでメモしておきます。
それぞれの関数のシグネチャと用途は下記のようになっています。

| 関数 | 説明 |
| ---- | ---- |
| `char *strncpy(char *dest, const char *src, size_t n)` | 文字列 src から最大 n バイトを dest にコピーする |
| `char *strncat(char *dest, const char *src, size_t n)` | 文字列 src から最大 n バイトを dest の末尾に追加する |
| `int snprintf(char *str, size_t n, const char *format, ...)` | フォーマットした文字列から最大 n バイトを str に書き込む |


strncpy の使い方
----

`strncpy` の man page を見ると、次のように説明されています。

~~~
char *strncpy(char *dest, const char *src, size_t n);

   If there is no null byte amongh the first n bytes of src,
   the result will not be null terminated.
   In the case where the length of src is less than that of n,
   the remainder of dest will be padded with null bytes.
~~~

- `src` の長さが `n` 以上だったら NULL 終端されないよ～ (>_<)
- `src` の長さが `n` より小さければ NULL 終端してあげるよ～ (^o^)

と言っているので、`src` の長さによらず `dest` を NULL 終端させるには、下記のように最後に `\0` を設定しておく必要があります。

~~~ cpp
strncpy(dst, src, dst_size);
dst[dst_size - 1] = '\0';
~~~


strncat の使い方
----

`strncat` の man page を見ると、次のように説明されています。

~~~
char *strncat(char *dest, const char *src, size_t n);

   Since the result is always terminnated with '\0',
   at most n+1 characters are written.
~~~

引数 `n` で指定するのは、`dest` の末尾に最大何文字付け加えることができるかであって、`dest` のバッファサイズではないことに注意してください。
恐ろしいことに、最大 `n` 文字を加えた後に、末尾に必ず `\0` を加えようとするので、最大で `n+1` byte 分の文字が `dest` に書き込まれます。
つまり、第３引数の値としては、

~~~
'\0' からバッファの末尾までの空きサイズから、'\0' 追加分の 1 を引いた値
~~~

を指定するのが正解で、コードとしては以下のようにしなければいけません。

~~~ cpp
char dst[N] = "hoge";  // ※
strncpy(dst, src, sizeof(dst) - strlen(dst) - 1);
~~~

※上記では、`strlen` でコピー元のサイズを調べているので、`dst` は NULL 終端している必要があります。


snprintf の使い方
----

`snprintf` の man page には下記のように記載されています。

~~~
int snprintf(char *str, size_t size, const char *format, ...);

    The functions snprintf() and vsnprintf() write at most size bytes
    (including the terminating null byte ('\0')) to str.
~~~

フォーマットされたテキストが、`size` バイトを超えてしまうと、`str` に格納されたテキストは NULL 終端されなくなってしまうので、下記のように最後に `\0` を格納するよう実装しておくのが安全です。

~~~ cpp
char buf[N] = "";
snprintf(buf, sizeof(buf), "......", a, b, c);
buf[sizeof(buf) - 1] = '\0';
~~~

`strncpy` と同様ですね。

