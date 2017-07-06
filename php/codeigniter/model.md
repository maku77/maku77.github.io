---
title: CodeIgniter - モデルクラスを使用してデータベースから値を取得する
created: 2012-07-22
---

（下記の記事は、データベースの設定と、データベースの作成、テーブルの作成が終わっていることを前提としています）

以下のサンプルでは、以下のような URL にアクセスしたときに、`books` テーブルの内容をすべて読み出してリスト表示します。

~~~
http://localhost/books/show_all
~~~

#### Books コントローラー (application/controllers/books.php)

~~~ php
<?php
class Books extends CI_Controller {
    public function show_all() {
        $this->load->model('books_model');
        $data['books'] = $this->books->get_all();
        $this->load->view('show_all', $data);
    }
}
~~~

典型的な MVC での設計では、コントローラーがモデルクラスを使ってデータを取得し、描画をビューにまかせます。
上記の例でも、それに従っています。
まず、Books モデルを以下のようにロードしています。

~~~ php
$this->load->model('books_model');
~~~

`Books_model` モデルのロード後は、`$this->books_model->method();` の形式で、`Books_model` クラスで定義されているメソッドを呼び出せるようになります。
ここでは、`Books_model` クラスの `get_all()` メソッドを使ってテーブルの前エントリを配列で取得し、ビュー (`show_all.php`) にそのデータを渡しています。


#### Books_model モデル (application/models/books_model.php)

~~~ php
<?php
class Books_model extends CI_Model {
    public function __construct() {
        parent::__construct();
        $this->load->database();
    }

    public function get_all() {
        $q = $this->db->query('SELECT id, title FROM books');
        return $q->result();
    }
}
~~~

`Books_model` クラスでは、まず最初に、`Database` クラスをロードして、`$this->db->xxxx()` のようなメソッド呼び出しを行えるようにしています。

~~~ php
$this->load->database();
~~~

上記の例では `Database` クラスの `query()` メソッドを使用して、一番原始的な SQL クエリ呼び出しを行っています。


#### show_all ビュー (application/views/show_all.php)

~~~ php
<html>
<body>
  <ul>
    <?php foreach($books as $book) : ?>
      <li><?= $book->id ?> : <?= $book->title ?>
    <?php endforeach; ?>
  </ul>
</body>
</html>
~~~

show_all ビュー (`show_all.php`) では、コントローラーから受け取った `books` 配列の内容を `foreach` ループで列挙しています。

