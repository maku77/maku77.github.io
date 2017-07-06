---
title: CodeIgniter - データベースのテーブルを作成する
created: 2012-07-14
---

ここでは、CodeIgniter のデータベースフォージクラス (dbforge) を使用して、`books` テーブルを作成してみます。

`dbforge->create_table()` を呼び出す前に、`dbforge->add_field()` でテーブルのフィールドを指定しておく必要があります。

#### Hello コントローラー (application/controllers/hello.php)

~~~ php
<?php
class Hello extends CI_Controller {
    public function index() {
        $this->load->dbforge();
        $fields = array(
            'id' => array('type' => 'int', 'auto_increment' => true),
            'title' => array('type' => 'text'),
        );
        $this->dbforge->add_field($fields);
        $this->dbforge->add_key('id', true);  // primary key
        $this->dbforge->create_table('books');
    }
}
~~~

デフォルトでは、すでに `books` テーブルが存在する場合に、以下のコードはエラーになります。

~~~ php
$this->dbforge->create_table('books');
~~~

テーブルが既に存在する場合に何も処理を行わないようにするには、第２パラメータに `TRUE` を設定します（内部的には SQL の `IF NOT EXISTS` で実行されていることになります）。

~~~ php
$this->dbforge->create_table('books', TRUE);
~~~

