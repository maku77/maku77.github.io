---
title: "Rubyメモ: プロファイラを使用してボトルネックを探る"
url: "p/fydda5r/"
date: "2008-05-16"
tags: ["ruby"]
aliases: ["/ruby/other/profiler.html"]
---

`ruby` コマンドを実行するときに、`-rprofile` オプションを指定すると、各関数呼び出しにどれだけ時間がかかっているかのプロファイル情報を表示してくれるようになります。

```
$ ruby -rprofile sample.rb
  %   cumulative   self              self     total
 time   seconds   seconds    calls  ms/call  ms/call  name
100.00     0.02      0.02       17     0.94     0.94  Array#each
  0.00     0.02      0.00        1     0.00     0.00  Fixnum#to_s
  0.00     0.02      0.00       18     0.00     0.00  Thread.current
  0.00     0.02      0.00        6     0.00     0.00  Mutex#lock
  0.00     0.02      0.00        6     0.00     0.00  MonitorMixin#mon_enter
  0.00     0.02      0.00        8     0.00     0.00  Kernel#respond_to_missing?
  0.00     0.02      0.00        6     0.00     0.00  Kernel#respond_to?
  0.00     0.02      0.00        6     0.00     0.00  Gem.suffixes
  0.00     0.02      0.00       24     0.00     0.00  Gem.find_unresolved_default_spec
  ...
```

プロファイルオプションを有効にすると、全体の実行時間は遅くなります。
あくまで、相対的な呼び出し時間の割合を見ることでボトルネックを探すためのものです。
