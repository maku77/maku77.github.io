---
title: CakePHP 入門 (6) ヘルパーを使用してリンクを生成する
created: 2012-05-04
---

`HtmlHelper` クラスを使用すると、様々なリンクを出力することができます。
`HtmlHelper` オブジェクトには、View のテンプレート (`*.ctp`) 内から、

~~~
$this->Html
~~~

のように参照できます。
リンクを生成するときは、`HtmlHelper` の `link()` メソッドを使用します。

~~~
$this->Html->link($title, array('controller' => 'tasks', 'action' => 'view', $id)
~~~

（CakePHP 1.X の頃は $html->link() のような書き方でした）

例えば上記のようにすると、

~~~ html
<a href="/tasks/view/1">Title1</a>
~~~

のような HTML 要素に変換されます。
これだけだと、あまり便利になったようには見えませんが、`link()` メソッドは、Confirm ダイアログなどを表示する機能なども備えています。

以下のサンプルコードは、タスクの一覧表示のページで、`HtmlHelper#link()` を使った例です。
各タスクのタイトルにリンクを貼り、タスクの詳細ページにジャンプできるようにしています。

#### app/View/Tasks/index.ctp

~~~ php
<h1>Task List</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Created</th>
    </tr>
    <?php foreach ($tasks as $t): ?>
    <tr>
        <td><?php echo $t['Task']['id'] ?></td>
        <td><?php echo $this->Html->link($t['Task']['title'],
            array('controller' => 'tasks', 'action' => 'view', $t['Task']['id'])) ?></td>
        <td><?php echo $t['Task']['created'] ?></td>
    </tr>
    <?php endforeach; ?>
</table>
~~~


CakePHP 入門記事一覧
----

- [CakePHP 入門 (1) セットアップ](./abc-1.html)
- [CakePHP 入門 (2) データベースの設定](./abc-2.html)
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](./abc-3.html)
- [CakePHP 入門 (4) Controller、View、Model を作成する](./abc-4.html)
- [CakePHP 入門 (5) 個別のレコードを表示する](./abc-5.html)
- CakePHP 入門 (6) ヘルパーを使用してリンクを生成する
- [CakePHP 入門 (7) レコードを追加できるようにする](./abc-7.html)
- [CakePHP 入門 (8) レコードを編集できるようにする](./abc-8.html)
- [CakePHP 入門 (9) レコードを削除できるようにする](./abc-9.html)

