---
title: "Perl スクリプトのエンコーディングを指定する"
date: "2014-04-01"
---

Perl 5.8.1 以降では `encoding` プラグマにより、スクリプトファイルがどんなエンコーディング形式で記述されているか指定することができます。

```perl
#!/usr/bin/env perl
use encoding "Shift_JIS";

print "これで表示という文字が正しく表示されます\n";
```

