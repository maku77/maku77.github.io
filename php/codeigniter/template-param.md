---
title: "CodeIgniter - ビューに動的引数（テンプレート引数）を渡す"
date: "2012-05-31"
---

コントローラークラスのメソッドから、`$this->load->view()` でビューを読み込むときに、第 2 引数で連想配列を渡すと、ビューからその値を参照できるようになります。

#### Hello コントローラー (application/controllers/hello.php)

~~~ php
<?php
class Hello extends CI_Controller {
    public function index() {
        $data['param1'] = 'value1';
        $data['param2'] = 'value2';
        $data['param3'] = 'value3';
        $this->load->view('sample', $data);
    }
}
~~~

#### Sample ビュー (application/views/sample.php)

~~~ php
<html>
<head>
    <title>Sample View</title>
</head>
<body>
<ul>
    <li><?= $param1 ?>
    <li><?= $param2 ?>
    <li><?= $param3 ?>
</ul>
</body>
</html>
~~~

ビュー内から値を参照するときは、コントローラで作成した連想配列のキーを変数名にして参照します。
連想配列の値として配列を渡せば、ビューの中でも配列を受け取ることができます。
配列は、以下のように `foreach` を使ってループ処理できます。

#### Hello コントローラー (application/controllers/hello.php)

~~~ php
<?php
class Hello extends CI_Controller {
    public function index() {
        $data['arr'] = array('AAA', 'BBB', 'CCC');
        $this->load->view('sample', $data);
    }
}
~~~

#### Sample ビュー (application/views/sample.php)

~~~ php
<html>
<head>
    <title>Sample View</title>
</head>
<body>
<ul>
    <?php foreach ($arr as $i): ?>
        <li><?= $i ?>
    <?php endforeach; ?>
</ul>
</body>
</html>
~~~

