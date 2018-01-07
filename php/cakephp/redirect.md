---
title: CakePHP - 他のページにリダイレクトする
date: "2012-05-13"
---

コントローラのアクション内の実行を終えたときに、デフォルトのテンプレートで表示を行うのではなく、任意のページにリダイレクトしたい場合は以下のメソッドを使用します。

~~~ php
Controller::redirect(mixed $url, integer $status, boolean $exit)
Controller::flash(string $message, string $url, integer $pause, string $layout)
~~~

- API ドキュメント
  - [Controller::redirect](http://book.cakephp.org/2.0/en/controllers.html#Controller::redirect)
  - [Controller::flash](http://book.cakephp.org/2.0/en/controllers.html#Controller::flash)

単純にリダイレクトしたい場合は `redirect()` を使い、リダイレクト前に何かメッセージを表示したい場合は `flash()` を使います。

#### 使用例

~~~ php
$this->redirect(array('controller' => 'orders', 'action' => 'thanks'));
$this->redirect('/orders/thanks'));
$this->redirect('http://www.example.com');
~~~

