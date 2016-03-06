---
title: Perl スクリプトが Windows で実行されているか調べる
created: 2009-12-02
---

『Code Reading』より。

```perl
# Heuristic to see if we are running under Windows / DOS or Unix
$windows = (-r 'nul' && !-r '/dev/null');
```

