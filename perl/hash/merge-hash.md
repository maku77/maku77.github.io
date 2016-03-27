---
title: 2 つのハッシュを合成する
created: 2008-05-21
---

ハッシュ `%new` の内容を `%old` に上書き（マージ）するには次のようにします。

```perl
@old{keys %new} = values %new;
```

次のようにしても同様の結果になりますが、上記の方が効率的です。

```perl
%old = (%old, %new);
```

- 参考: Effective Perl

