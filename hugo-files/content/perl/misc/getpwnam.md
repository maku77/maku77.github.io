---
title: "Perlメモ: ユーザー名、グループ名からユーザ ID、グループ ID を取得する (`getpwnam`, `getgrnam`)"
url: "p/99x2b2e/"
date: "2008-04-05"
tags: ["perl"]
aliases: ["/perl/misc/getpwnam.html"]
---

`getpwnam`, `getgrnam` を使用すると、ユーザー名、グループ名をユーザー ID、グループ ID に変換することができます。
存在しない名前を指定すると、これらの関数は `undef` を返します。

```perl
defined(my $user_id = getpwnam 'cathy') or die 'Unknown user name';
defined(my $group_id = getgrnam 'mysql') or die 'Unknown group name';

# 使用例
chown $user_id, $group_id, 'sample.txt';
```
