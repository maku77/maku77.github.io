---
title: コメント内で使える特殊キーワード（XXX、TODO など）を理解する
date: "2009-07-29"
---


FALLTHROUGH: 次の case へ処理を継続する
----

`/* FALLTHROUGH */` は `case` の終わりで意図的に `break` しないことを示します。
Lint ツールなどはこのキーワードを検出して、case 漏れ警告を抑制します。

```cpp
switch (...) {
case 1:
    ...
    /* FALLTHROUGH */
case 2:
...
```


NOTREACHED: そこへは到達しない
----

`/* NOTREACHED */` は、関数の終わりなどに達しないことを示します。

```cpp
void hoge() {
    while (...) {
        ...
        if (...) {
            return;
        }
    }
    /* NOTREACHED */
}
```


XXX: 要確認
----

`/* XXX */` は、正しいか分からないが、とりあえず動いていることを示します。

```cpp
// XXX: このコードがあるとなぜか速度が上がる
magic_function(100);
```


FIXME: 修正の必要あり
----

```
// FIXME: 過去の日付を入力しても落ちないように
```


TODO: 将来拡張予定
----

```
// TODO: ヘッダに番組タイトルを表示する
```

