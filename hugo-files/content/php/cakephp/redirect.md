---
title: "PHPメモ: CakePHP - 他のページにリダイレクトする"
url: "/p/z6cjm8z/"
date: "2012-05-13"
tags: ["php"]
aliases: [/php/cakephp/redirect.html]
---

コントローラのアクション内の実行を終えたときに、デフォルトのテンプレートで表示を行うのではなく、任意のページにリダイレクトしたい場合は以下のメソッドを使用します。

```php
Controller::redirect(mixed $url, integer $status, boolean $exit)
Controller::flash(string $message, string $url, integer $pause, string $layout)
```

- API ドキュメント
  - [Controller::redirect](https://book.cakephp.org/2.0/en/controllers.html#Controller::redirect)
  - [Controller::flash](https://book.cakephp.org/2.0/en/controllers.html#Controller::flash)

単純にリダイレクトしたい場合は `redirect()` を使い、リダイレクト前に何かメッセージを表示したい場合は `flash()` を使います。

{{< code lang="php" title="使用例" >}}
$this->redirect(array('controller' => 'orders', 'action' => 'thanks'));
$this->redirect('/orders/thanks'));
$this->redirect('https://www.example.com');
{{< /code >}}

