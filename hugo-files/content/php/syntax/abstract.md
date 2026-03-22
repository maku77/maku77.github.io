---
title: "PHPメモ: 抽象クラス、抽象メソッドを定義する"
url: "/p/orsb39g/"
date: "2012-10-08"
tags: ["php"]
aliases: [/php/syntax/abstract.html]
---

PHP5 からは、Java と同様の `abstract` キーワードが導入され、抽象クラス、抽象メソッドを表すことができるようになりました。

`abstract` キーワードのついたクラスは、直接インスタンス化することができないため、必ずサブクラスを定義する必要があります。
サブクラスで、`abstract` キーワードのついたメソッドをすべて実装することで、インスタンス化が可能になります。

```php
<?php
abstract class AbstractElement {
    public abstract function getName();
}

class ListElement extends AbstractElement {
    public function getName() {
        return 'ListElement';
    }
}

$e = new AbstractElement();  // Fatal error: Cannot instantiate abstract class
$e = new ListElement();  // OK
```

