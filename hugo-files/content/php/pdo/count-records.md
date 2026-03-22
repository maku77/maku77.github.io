---
title: "PHPメモ: PDO - SELECT で取得したレコードの数を調べる"
url: "/p/5dv9336/"
date: "2012-08-31"
tags: ["php"]
aliases: [/php/pdo/count-records.html]
---

下記の例では、SQLite の `count` 関数を使用して、`SELECT` クエリで見つかったレコードの件数を調べています。

```php
<?php
try {
    $pdo = new PDO('sqlite:words.db');
    $stmt = $pdo->query('SELECT count(*) FROM words;');
    echo 'Count: ' . $stmt->fetchColumn();
    $pdo = NULL;
} catch (PDOException $e) {
    die('Error: ' . $e->getMessage());
}
```

