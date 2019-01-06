---
title: "ハッシュからキーを削除する"
date: "2008-03-04"
---

ハッシュから特定のキー（とその値のペア）を削除するには `delete` 関数を使います。

```perl
delete $hash{'key'};  # ハッシュからキー 'key' とその値を削除
```

キーが最初から存在しない場合は、`delete` は何も行いません。
値として `undef` を代入することとは違うので注意してください。

```perl
$hash{'key'} = undef;  # キー 'key' に対する値が undef になるだけ
```

`delete` を実行後は、`exists($hash{'key'})` の値は必ず偽 (false) になります。

