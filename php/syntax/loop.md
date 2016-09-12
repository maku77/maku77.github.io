---
title: PHP のループ構文
created: 2012-01-15
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

