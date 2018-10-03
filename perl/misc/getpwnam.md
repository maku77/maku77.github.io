---
title: "ユーザー名、グループ名からユーザ ID、グループ ID を取得する (getpwnam, getgrnam)"
date: "2008-04-05"
---

`getpwnam`, `getgrnam` を使用すると、ユーザー名、グループ名をユーザ ID、グループ ID に変換することができます。
存在しない名前を指定すると、これらの関数は `undef` を返します。

~~~ perl
defined(my $user_id = getpwnam 'cathy') or dir 'Unknown user name';
defined(my $group_id = getgrnam 'mysql') or dir 'Unknown group name';

# 使用例
chown $user_id, $group_id, 'sample.txt';
~~~

