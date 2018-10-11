---
title: "カーネルに渡されたパラメータを調べる (/proc/cmdline)"
date: "2011-02-01"
---

`/proc/cmdline` の内容を出力すると、カーネルへ渡されたコマンドラインパラメータを調べることができます。

```
$ cat /proc/cmdline
BOOT_IMAGE=/boot/vmlinuz-2.6.32-28.generic root=UUID=37c279f8-9c77-78c5-c379-d01e2c480351 ro quiet splash
```

他にも、`/proc` 以下のファイルにアクセスしていろいろな情報を取得することができます。
例えば、`cat /proc/cpuinfo` とすると、CPU の情報を調べることができます。
どのような情報を取得できるか、`man proc` で確認してみてください。

