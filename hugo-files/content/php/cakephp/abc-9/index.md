---
title: "PHPメモ: CakePHP 入門 (9) レコードを削除できるようにする"
url: "/p/gyeajmq/"
date: "2012-05-04"
tags: ["php"]
aliases: [/php/cakephp/abc-9.html]
---

ここでは、指定した ID のレコードを削除できるようにします。
`http://localhost/tasks` にアクセスしたときに、以下のように Delete リンクを表示するようにします。

{{< image border="true" src="img-001.png" >}}

ただし、単純な GET リクエストでレコードを削除できるようにしてしまうと、ブラウザに URL を入力したときや、Web クローラのアクセスだけで削除されてしまうので、削除には必ず POST リクエストを使うように制限します。

以下のように、`TasksController` に `delete` アクションを追加します。

{{< code lang="php" title="app/Controller/TasksController.php" >}}
<?php
class TasksController extends AppController {
    public $components = array('Session');
    ...
    public function delete($id) {
        if ($this->request->is('get')) {
            throw new MethodNotAllowedException();
        }
        if ($this->Task->delete($id)) {
            $this->Session->setFlash('The task has been deleted.');
            $this->redirect(array('action' => 'index'));
        }
    }
}
{{< /code >}}

delete 用のページは作らずに、一覧ページ (index) のリンクから削除できるようにします。
POST リクエストを使うリンクを生成するには、`FormHelper` の `postLink()` を使用します。

```php
$this->Form->postLink('Delete',
    array('action' => 'delete', $id),
    array('confirm' => 'Are you sure?'));
```

{{< code lang="html" title="app/View/Tasks/index.ctp" >}}
<h1>Task List</h1>

<table>
    <tr>
        <th>ID</th><th>Title</th><th></th><th></th><th>Created</th>
    </tr>
    <?php foreach ($tasks as $t):
        $id = $t['Task']['id'];
        $title = $t['Task']['title'];
        $created = $t['Task']['created'];
    ?>
    <tr>
        <td><?php echo $id ?></td>
        <td><?php echo $this->Html->link($title,
            array('controller' => 'tasks', 'action' => 'view', $id)) ?></td>
        <td><?php echo $this->Html->link('Edit',
            array('controller' => 'tasks', 'action' => 'edit', $id)) ?></td>
        <td><?php echo $this->Form->postLink('Delete',
            array('controller' => 'tasks', 'action' => 'delete', $id),
            array('confirm' => 'Are you sure?')) ?></td>
        <td><?php echo $created ?></td>
    </tr>
    <?php endforeach; ?>
</table>
{{< /code >}}


CakePHP 入門記事一覧
----

- [CakePHP 入門 (1) セットアップ](/p/sq3v2ss/)
- [CakePHP 入門 (2) データベースの設定](/p/kacjhwz/)
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](/p/ijxrod2/)
- [CakePHP 入門 (4) Controller、View、Model を作成する](/p/38ty6s5/)
- [CakePHP 入門 (5) 個別のレコードを表示する](/p/mnwyenz/)
- [CakePHP 入門 (6) ヘルパーを使用してリンクを生成する](/p/bueomu6/)
- [CakePHP 入門 (7) レコードを追加できるようにする](/p/apv3m29/)
- [CakePHP 入門 (8) レコードを編集できるようにする](/p/5qnrd2w/)
- CakePHP 入門 (9) レコードを削除できるようにする

