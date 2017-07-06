---
title: CodeIgniter - 現在どんなデータベースを使用しているか調べる
created: 2012-07-14
---

`Database` クラスの `platform` メソッドや、`version` メソッドを使用すると、現在接続しているデータベースの種類やバージョンを調べることができます。

#### Hello コントローラー (application/controllers/hello.php)

~~~ php
<?php
class Hello extends CI_Controller {
    public function index() {
        $this->load->database();
        echo $this->db->platform() . '<BR>';
        echo $this->db->version();
    }
}
~~~

#### 実行結果（http://localhost/hello へアクセスしたときの表示）

~~~
mysql
5.1.44
~~~

