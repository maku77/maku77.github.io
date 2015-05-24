---
title: PHP の if-else 構文
created: 2012-01-15
---

PHP の if 構文は、C/C++ や Java と同じです。

```php
if ($cond1) {
    echo 'AAA';
} else if ($cond2) {
    echo 'BBB';
} else {
    echo 'CCC';
}
```

条件分岐後のステートメントが 1 つだけの場合は、中括弧を省略することができます。
これも、C/C++ や Java と同様です。

```php
if ($cond) echo 'AAA';
else echo 'BBB';
```

