---
title: CakePHP - ページのタイトルを設定する
date: "2012-05-13"
---

view のデフォルトのレイアウトは、以下のテンプレートで記述されています。

~~~
app/View/Layouts/default.ctp
~~~

タイトル部分は以下のようになっているので、好きなように変更します。

~~~ php
<title>
    <?php echo $cakeDescription ?>:
    <?php echo $title_for_layout; ?>
</title>
~~~

例えば、以下のような感じで、「ウェブサイト名＋個々のページ名」という表示になるように書き換えます。

~~~ php
<title>My Tasks: <?php echo $title_for_layout ?></title>
~~~

あとは、Controller のアクションで、以下のように `$title_for_layout` 変数にタイトルを設定してやれば OK です。

#### TasksController.php

~~~ php
<?php
class TasksController extends AppController {
    public $components = array('Session');

    public function view($id) {
        // Search task by $id
        $this->Task->id = $id;
        $task = $this->Task->read();

        // Set title and contents
        $this->set('title_for_layout', $task['Task']['title']);
        $this->set('task', $task);
    }
    ...
}
~~~

