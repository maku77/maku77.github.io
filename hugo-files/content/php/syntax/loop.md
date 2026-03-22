---
title: "PHPメモ: PHP のループ構文"
url: "/p/unv93ny/"
date: "2012-01-15"
tags: ["php"]
aliases: [/php/syntax/loop.html]
---

PHP には、C/C++ 言語とほぼ同等のループ構文が用意されています。

while ループ
----

```php
$i = 0;
while ($i < 10) {
    print $i;
    ++$i;
}
```

do while ループ
----

```php
$i = 0;
do {
    print $i;
    ++$i;
} while ($i < 10);
```

for ループ
----

```php
for ($i = 0; $i < 10; ++$i) {
    print $i;
}
```

