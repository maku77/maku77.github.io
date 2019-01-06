---
title: "CakePHP - 使用するテンプレートファイルを指定する"
date: "2012-05-13"
---

CakePHP で使用されるテンプレートファイルは、デフォルトでは、コントローラ名とアクション名から自動的に決まるようになっています。

~~~ php
class TasksController extends AppController {
    public function view($id) {
        ...
    }
    ...
}
~~~

例えば、上記のような `TasksController` の `view` アクションから使用されるテンプレートファイルは、`app/Views/Tasks/view.ctp` になります。

`Controller::render()` メソッドを使用すると、使用するテンプレートファイルを変更することができます。

~~~ php
class TasksController extends AppController {
    public function view($id) {
        ...
        $this->render('/Foo/bar');
    }
    ...
}
~~~

上記のようにすると、`view` アクションが使用するテンプレートファイルは、`app/Views/Foo/bar.ctp` になります。

階層を付けずに同じコントローラーの中のアクション名を指定することもできます。

~~~ php
$this->render('hoge');
~~~

`TasksController` の中で、上記のようにすると、`app/Views/Tasks/hoge.ctp` が使用されます。

