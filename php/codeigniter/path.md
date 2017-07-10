---
title: 相対パスの扱い方のまとめ
created: 2013-03-02
---

CodeIgniter のツリー構造
----

まずは、CodeIgniter のツリー構成のおさらいから。

~~~
.
├── index.php
├── license.txt
├── application/ （あなたのアプリケーションのコード）
│   ├── ...
│   ├── controllers/
│   └── ...
├── system/ （共通のシステムコード）
│   ├── core/
│   └── ...
└── user_guide/ （マニュアル）
    ├── index.html
    └── ...
~~~

ツリーのルートに、エントリポイントとなる `index.php` があり、ブラウザからのアクセスは `application` ディレクトリ以下のコントローラに処理が委譲されます。
例えば、

~~~ php
http://<IgniterRoot>/ctr1/act1
~~~

という URI 指定は、`application/controllers/ctr1.php` ファイル内に定義した `Ctr1` クラスの `act1` メソッド呼び出しにマッピングされます。
この `application` ディレクトリへのパスは、`index.php` に以下のように設定されているので、自由に変更することが可能です。

#### index.php 抜粋

~~~ php
$application_folder = 'application';
~~~

CodeIngiter 関連のコード内から単純に相対パスを使用すると、基本的には、ブラウザのアドレス欄に表示されている URL に対しての相対パス指定として動作します。
`index.php` が置いてあるルートディレクトリからの相対パスで指定したい場合は、CodeIgniter が提供する API を使用してパスを構築する必要があります（`url` ヘルパーの `base_url()` など）。


View からのアンカータグの出力 - url ヘルパーの anchor()、base_url() 関数
----

View の中から、

~~~ html
<a href="./a.html">a.html</a>
~~~

のように相対パスでリンクを張ると、現在アクセスしている URI を元にした相対パス指定になってしまいます。
CodeIgniter は、コントローラ＋メソッド名＋パラメータの組み合わせで URI を指定するため、この相対パス指定は不安定です。
このような、単純な相対パスでのリンクは使わない方がよいでしょう。

CodeIgniter で作成したサイトのルートからの相対 URI を構築する場合は、`url` ヘルパーの `base_url()` 関数や、`anchor()` 関数を使います。
下記は、`base_url()` を使った相対パス指定の例です。

#### ビュー内で相対リンクを作成

~~~ php
<a href="<?php echo base_url('pages/show'); ?>" ?>pages/show</a>
~~~

上記では、`pages` コントローラの `show` アクションへのリンクを作成しています。

実は、HTML の Anchor タグ（`<a>`）を出力するには、`base_url()` を使うより、`anchor()` 関数を使った方がお手軽です（`anchor()` 関数も `url` ヘルパーに含まれています）。
第一引数にはサイトのルートからの相対パス、第二引数には表示する文字列を指定します。

~~~ php
<?php echo anchor("pages/show/$id", "$title"); ?>
~~~

`url` ヘルパーを使用するときは、コントーラのコンストラクタなどで、以下のようにロードしておく必要があります。

#### コントローラ内で url ヘルパーをロード

~~~ php
class Pages extends CI_Controller {
    public function __construct() {
        parent::__construct();
        //...
        $this->load->helper('url');  // for anchor()
    }
    //...
~~~


View から CSS スタイルシートのための link タグを出力する - html ヘルパーの link_tag() 関数
----

スタイルシートを `index.php` と同じ階層の `css` ディレクトリ内に置くとします。
このディレクトリを参照するときにも、`url` ヘルパーの `base_url()` 関数を利用することができます。

~~~ php
<link href="<?php echo base_url('css/main.css') rel="stylesheet" type="text/css"?>">
~~~

上記のコードは、以下のように絶対パスで記述したのと同様に振る舞います。

~~~ php
<link href="http://＜IgniterRoot＞/css/main.css" rel="stylesheet" type="text/css" />
~~~

アンカー要素を出力するために `anchor()` 関数が用意されていたのと同様に、`link` 要素を出力するために `link_tag()` 関数が用意されています。

~~~ php
<?php echo link_tag('css/main.css'); ?>
~~~

`link_tag()` 関数は、`html` ヘルパーが提供しているので、コントローラ内で以下のようにロードしておく必要があります。

#### コントローラ内で html ヘルパーをロード

~~~ php
$this->load->helper('html');
~~~


View から img タグを出力する - html ヘルパー の img() 関数
----

画像を表示するための `img` タグを出力する場合も、`link` タグの出力とほぼ同様の方法で出力できます。
`img` タグの出力のためには、`html` ヘルパーの `img()` 関数を使用します。

~~~ php
<?php echo img('images/sample.png'); ?>
~~~

`url` ヘルパーの `base_url()` を使って以下のように記述しても同様の結果を得られますが、分かりにくいだけなので、素直に `img()` を使った方がよいでしょう。

~~~ php
<img src="<?php echo base_url('images/sample.png'); ?>">
~~~

