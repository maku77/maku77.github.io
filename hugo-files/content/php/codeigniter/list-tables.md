---
title: "PHPメモ: CodeIgniter - データベースのテーブルを列挙する"
url: "/p/5w7zerw/"
date: "2012-07-14"
tags: ["php"]
aliases: [/php/codeigniter/list-tables.html]
---

`Database` クラスの `list_tables` メソッドを使用すると、接続しているデータベースに含まれているテーブルを列挙することができます。

{{< code lang="php" title="Hello コントローラー (application/controllers/hello.php)" >}}
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
{{< /code >}}

