---
title: CakePHP 入門 (5) 個別のレコードを表示する
date: "2012-05-04"
---

ここでは、

- `http://localhost/tasks/view/1`
- `http://localhost/tasks/view/2`

のようなアドレスで、`tasks` テーブルの中の個別のレコードの内容を表示できるようにします。
URL の構成としては、

~~~
Controller名: tasks ==> Controller/TasksController.php
    Action名: view ==> View/Tasks/view.ctp
  パラメータ: 1, 2
~~~

のような感じになっています。
パラメータは、`TasksController.view()` メソッドのパラメータとして渡されます。

#### app/Controller/TasksController.php

~~~ php
<?php
class TasksController extends AppController {
    ...
    public function view($id) {
        $this->Task->id = $id;
        $this->set('task', $this->Task->read());
    }
}
~~~

Controller に `view` メソッドを追加したので、それに対応するテンプレートファイルも作成します。

#### app/View/Tasks/view.ctp

~~~ php
<h1><?php echo $task['Task']['title'] ?></h1>
<p><?php echo $task['Task']['body'] ?></p>
<p><?php echo $task['Task']['created'] ?></p>
~~~

`http://localhost/tasks/view/1` にアクセスして、id=1 のレコードの内容が表示されれば成功です。id=2 の場合も試してみましょう。


CakePHP 入門記事一覧
----

- [CakePHP 入門 (1) セットアップ](./abc-1.html)
- [CakePHP 入門 (2) データベースの設定](./abc-2.html)
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](./abc-3.html)
- [CakePHP 入門 (4) Controller、View、Model を作成する](./abc-4.html)
- CakePHP 入門 (5) 個別のレコードを表示する
- [CakePHP 入門 (6) ヘルパーを使用してリンクを生成する](./abc-6.html)
- [CakePHP 入門 (7) レコードを追加できるようにする](./abc-7.html)
- [CakePHP 入門 (8) レコードを編集できるようにする](./abc-8.html)
- [CakePHP 入門 (9) レコードを削除できるようにする](./abc-9.html)

