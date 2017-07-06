---
title: CodeIgniter - コントローラーを作成する
created: 2012-05-29
---

（CodeIgniter 2.1.0 で確認しています）

コントローラークラスを作成する
----

ここでは、以下のような URL で CodeIgniter アプリケーションにアクセスが発生すると仮定します。

~~~
http://example.com/index.php/hello/world
~~~

ブラウザから上記のようなアドレスでアクセスすると、`Hello` コントローラークラス (`hello.php`) の、`world` メソッドが呼び出されます。
`Hello` クラスは、以下のように `CI_Controller` クラスを継承して作成します。

#### application/controllers/hello.php

~~~ php
<?php
class Hello extends CI_Controller {
    public function world() {
        print "Hello!";
    }
}
~~~

URL の action 名を省略して以下のようにアクセスすると、デフォルトでコントローラクラスの `index` メソッドが呼び出されます。

~~~
http://example.com/index.php/hello
~~~

~~~ php
class Hello extends CI_Controller {
    public function index() {
         print "Index";
    }
    ...
}
~~~

コラム: CakePHP などとは異なり、CodeIgniter の場合は、本当に必要になるまでデータベースや、モデルクラス、ビュークラスなどを用意する必要がありません。この気軽さが人気の一因になっているようです。


URLでパラメータを渡す
----

URL の末尾にスラッシュで区切った文字列を追加すると、その文字列がコントローラーのメソッドに引数として渡されます。

~~~
http://example.com/index.html/hello/world/XYZ
~~~

例えば、上記のようにアクセスすると、`world` メソッドの引数として `XYZ` が渡されます。

#### application/controllers/hello.php

~~~ php
<?php
class Hello extends CI_Controller {
    public function world($name = '') {
        print "Hello " . $name;
    }
}
~~~

パラメータが指定されなかった場合にもエラーにならないようにするために、`world` メソッドの引数には、デフォルト値を指定しています。


コントローラーからビューを分離する
----

レスポンスとなる HTML テキストを出力する場合は、コントローラーの中で `print()` を呼び出すことで直接テキストを出力するのではなく、ビューとして作成した php ファイルを読み込んで出力することができます。

このような構成にすることで、データ処理を行うコードと、見た目を定義するコード（ビュー）を分離することができます。

次の例では、`http://example.com/index.php/hello` のような URL でアクセスすると仮定しています。

#### Hello コントローラ (application/controllers/hello.php)

~~~ php
<?php
class Hello extends CI_Controller {
    public function index() {
        $this->load->view('sample');  // sample.php を読み込んで出力
    }
}
~~~

#### Sample ビュー (application/views/sample.php)

~~~ html
<html>
<head>
    <title>Sample View</title>
</head>
<body>
    <h1>This is a sample view.</h1>
</body>
</html>
~~~

以下のようにして、複数のビューを組み合わせて HTML レスポンスを作成することができます。

~~~ php
$this->load->view('header');  // header.php を読み込んで出力
$this->load->view('body');    // body.php を読み込んで出力
$this->load->view('footer');  // footer.php を読み込んで出力
~~~

