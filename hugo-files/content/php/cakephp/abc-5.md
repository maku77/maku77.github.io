---
title: "PHPメモ: CakePHP 入門 (5) 個別のレコードを表示する"
url: "/p/mnwyenz/"
date: "2012-05-04"
tags: ["php"]
aliases: [/php/cakephp/abc-5.html]
---

ここでは、

- `http://localhost/tasks/view/1`
- `http://localhost/tasks/view/2`

のようなアドレスで、`tasks` テーブルの中の個別のレコードの内容を表示できるようにします。
URL の構成としては、

```
Controller名: tasks ==> Controller/TasksController.php
    Action名: view ==> View/Tasks/view.ctp
  パラメータ: 1, 2
```

のような感じになっています。
パラメータは、`TasksController.view()` メソッドのパラメータとして渡されます。

{{< code lang="php" title="app/Controller/TasksController.php" >}}
<?php
class TasksController extends AppController {
    ...
    public function view($id) {
        $this->Task->id = $id;
        $this->set('task', $this->Task->read());
    }
}
{{< /code >}}

Controller に `view` メソッドを追加したので、それに対応するテンプレートファイルも作成します。

{{< code lang="html" title="app/View/Tasks/view.ctp" >}}
<h1><?php echo $task['Task']['title'] ?></h1>
<p><?php echo $task['Task']['body'] ?></p>
<p><?php echo $task['Task']['created'] ?></p>
{{< /code >}}

`http://localhost/tasks/view/1` にアクセスして、id=1 のレコードの内容が表示されれば成功です。id=2 の場合も試してみましょう。


CakePHP 入門記事一覧
----

- [CakePHP 入門 (1) セットアップ](/p/sq3v2ss/)
- [CakePHP 入門 (2) データベースの設定](/p/kacjhwz/)
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](/p/ijxrod2/)
- [CakePHP 入門 (4) Controller、View、Model を作成する](/p/38ty6s5/)
- CakePHP 入門 (5) 個別のレコードを表示する
- [CakePHP 入門 (6) ヘルパーを使用してリンクを生成する](/p/bueomu6/)
- [CakePHP 入門 (7) レコードを追加できるようにする](/p/apv3m29/)
- [CakePHP 入門 (8) レコードを編集できるようにする](/p/5qnrd2w/)
- [CakePHP 入門 (9) レコードを削除できるようにする](/p/gyeajmq/)

