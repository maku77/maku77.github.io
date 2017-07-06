---
title: CodeIgniter - データベースのテーブルを列挙する
created: 2012-07-14
---

`Database` クラスの `list_tables` メソッドを使用すると、接続しているデータベースに含まれているテーブルを列挙することができます。

#### Hello コントローラ (application/controllers/hello.php)

~~~ php
<?php
class Hello extends CI_Controller {
    public function index() {
        $this->load->database();

        $tables = $this->db->list_tables();
        foreach ($tables as $t) {
            echo $t;
        }
    }
}
~~~

