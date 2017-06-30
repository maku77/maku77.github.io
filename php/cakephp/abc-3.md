---
title: CakePHP 入門 (3) CakePHP アプリの URL の仕組み
created: 2012-05-03
---

CakePHP で作成したアプリケーションは、以下のような URL でアクセスする規約になっています（Ruby on Rails なども同様の考え方です）。

~~~
http://example.com/＜Controller名＞/＜Action名＞
~~~

Controller は、PHP のクラスとして作成し、Action はそのクラスのメソッドとして追加していきます。例えば、

~~~
http://localhost/tasks/index
~~~

という URL は、自動的に `TasksController` クラスの `index` メソッドを呼び出すという動きをします。
`index` という名前の Action は、デフォルトで省略できるようになっているので、以下のような URL でアクセスすることもできます。

~~~
http://localhost/tasks/
~~~


CakePHP 入門記事一覧
----

- [CakePHP 入門 (1) セットアップ](./abc-1.html)
- [CakePHP 入門 (2) データベースの設定](./abc-2.html)
- CakePHP 入門 (3) CakePHP アプリの URL の仕組み
- [CakePHP 入門 (4) Controller、View、Model を作成する](./abc-4.html)
- [CakePHP 入門 (5) 個別のレコードを表示する](./abc-5.html)
- [CakePHP 入門 (6) ヘルパーを使用してリンクを生成する](./abc-6.html)
- [CakePHP 入門 (7) レコードを追加できるようにする](./abc-7.html)
- [CakePHP 入門 (8) レコードを編集できるようにする](./abc-8.html)
- [CakePHP 入門 (9) レコードを削除できるようにする](./abc-9.html)

