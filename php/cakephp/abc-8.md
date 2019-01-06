---
title: "CakePHP 入門 (8) レコードを編集できるようにする"
date: "2012-05-04"
---

ここでは、以下のように ID を指定して、レコードを編集できるようにしてみます。

~~~
http://localhost/tasks/edit/1
http://localhost/tasks/edit/2
http://localhost/tasks/edit/3
~~~

`TasksController` に `edit` アクションを追加し、それに対応する `edit.ctp` テンプレートを追加します。

#### app/Controller/TasksController.php

~~~ php
<?php
class TasksController extends AppController {
    public $components = array('Session');
    ...
    public function edit($id = null) {
        if ($this->request->is('get')) {
            $this->Task->id = $id;
            $this->request->data = $this->Task->read();
        } else {
            if ($this->Task->save($this->request->data)) {
                $this->Session->setFlash('Your post has been updated.');
                $this->redirect(array('action' => 'index'));
            } else {
                $this->Session->setFlash('Unable to update your post.');
            }
        }
    }
}
~~~

#### app/View/Tasks/edit.ctp

~~~ php
<h1>Edit Task</h1>
<?php
    echo $this->Form->create('Task');
    echo $this->Form->input('id', array('type' => 'hidden'));
    echo $this->Form->input('title', array('rows' => '1'));
    echo $this->Form->input('body', array('rows' => '3'));
    echo $this->Form->end('Save Task');
?>
~~~

ちなみに、edit ページへのリンクは、例えば以下のようにして生成することができます。

~~~ php
$this->Html->link('Edit', array('action' => 'edit', $post['Task']['id']));
~~~


CakePHP 入門記事一覧
----

- [CakePHP 入門 (1) セットアップ](./abc-1.html)
- [CakePHP 入門 (2) データベースの設定](./abc-2.html)
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](./abc-3.html)
- [CakePHP 入門 (4) Controller、View、Model を作成する](./abc-4.html)
- [CakePHP 入門 (5) 個別のレコードを表示する](./abc-5.html)
- [CakePHP 入門 (6) ヘルパーを使用してリンクを生成する](./abc-6.html)
- [CakePHP 入門 (7) レコードを追加できるようにする](./abc-7.html)
- CakePHP 入門 (8) レコードを編集できるようにする
- [CakePHP 入門 (9) レコードを削除できるようにする](./abc-9.html)

