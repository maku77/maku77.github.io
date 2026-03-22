---
title: "PHPメモ: CakePHP 入門 (6) ヘルパーを使用してリンクを生成する"
url: "/p/bueomu6/"
date: "2012-05-04"
tags: ["php"]
aliases: [/php/cakephp/abc-6.html]
---

`HtmlHelper` クラスを使用すると、様々なリンクを出力することができます。
`HtmlHelper` オブジェクトには、View のテンプレート (`*.ctp`) 内から、

```php
$this->Html
```

のように参照できます。
リンクを生成するときは、`HtmlHelper` の `link()` メソッドを使用します。

```php
$this->Html->link($title, array('controller' => 'tasks', 'action' => 'view', $id)
```

（CakePHP 1.X の頃は $html->link() のような書き方でした）

例えば上記のようにすると、

```html
<a href="/tasks/view/1">Title1</a>
```

のような HTML 要素に変換されます。
これだけだと、あまり便利になったようには見えませんが、`link()` メソッドは、Confirm ダイアログなどを表示する機能なども備えています。

以下のサンプルコードは、タスクの一覧表示のページで、`HtmlHelper#link()` を使った例です。
各タスクのタイトルにリンクを貼り、タスクの詳細ページにジャンプできるようにしています。

{{< code lang="html" title="app/View/Tasks/index.ctp" >}}
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
{{< /code >}}


CakePHP 入門記事一覧
----

- [CakePHP 入門 (1) セットアップ](/p/sq3v2ss/)
- [CakePHP 入門 (2) データベースの設定](/p/kacjhwz/)
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](/p/ijxrod2/)
- [CakePHP 入門 (4) Controller、View、Model を作成する](/p/38ty6s5/)
- [CakePHP 入門 (5) 個別のレコードを表示する](/p/mnwyenz/)
- CakePHP 入門 (6) ヘルパーを使用してリンクを生成する
- [CakePHP 入門 (7) レコードを追加できるようにする](/p/apv3m29/)
- [CakePHP 入門 (8) レコードを編集できるようにする](/p/5qnrd2w/)
- [CakePHP 入門 (9) レコードを削除できるようにする](/p/gyeajmq/)

