---
title: "Perlメモ: Perl スクリプトが Windows で実行されているか調べる"
url: "p/7oap2mx/"
date: "2009-12-02"
tags: ["perl"]
aliases: ["/perl/misc/run-on-windows.html"]
---

『Code Reading』より。

```perl
# Heuristic to see if we are running under Windows / DOS or Unix
$windows = (-r 'nul' && !-r '/dev/null');
```
