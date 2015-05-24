---
title: PHP のループ構文
created: 2012-01-15
---

while ループ
====
```php
$i = 0;
while ($i < 10) {
    print $i;
    ++$i;
}
```

do while ループ
====
```php
$i = 0;
do {
    print $i;
    ++$i;
} while ($i < 10);
```

for ループ
====
```php
for ($i = 0; $i < 10; ++$i) {
    print $i;
}
```

