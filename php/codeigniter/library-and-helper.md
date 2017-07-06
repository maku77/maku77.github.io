---
title: CodeIgniter - ライブラリ、ヘルパーの基礎
created: 2012-07-22
---

CodeIgniter のライブラリ、ヘルパーとは？
----

CodeIgniter では、便利なクラス、関数を以下のように呼び分けています。

- ライブラリ (Library) -- 便利なクラスの集まり。普通に Class と呼ぶこともある。
- ヘルパー (Helper) -- 便利の関数の集まり。多くの場合 View の中で使用する。

#### 参考
- [CodeIgniter - Using CodeIgniter Libraries](http://codeigniter.com/user_guide/general/libraries.html)
- [CodeIgniter - Helper Functions](http://codeigniter.com/user_guide/general/helpers.html)


ライブラリ、ヘルパーのロード方法
----

各ライブラリ、ヘルパーを使用するためには、最初にコントローラの中で以下のような感じでロードしておく必要があります。

~~~ php
$this->load->library('email');  // Email クラスのロード
$this->load->library('table');  // HTML Table クラスのロード
$this->load->library('form_validation');  // Form validation クラスのロード
$this->load->helper('date');  // Date ヘルパーのロード
$this->load->helper('form');  // Form ヘルパーのロード
$this->load->helper('url');   // URL ヘルパーのロード
~~~

配列で指定してまとめてロードすることもできます。

~~~ php
$this->load->helper(array('form', 'url'));
~~~

よく使用される `Input` クラスのように、デフォルトでロードされるようになっているライブラリもあります。

各メソッドで毎回上記のようにライブラリ、ヘルパーをロードするが面倒な場合は、コンストラクタの中で以下のようにロードしてしまう方法があります。
これで、各メソッドで最初からライブラリを使用できるようになります。

~~~ php
class My_email_controller extends CI_Controller {
    public function __construct() {
        parent::__construct();  // Don't forget!
        $this->load->library('email');
    }
    //...
}
~~~

アプリケーション全体で、常にロードしておきたいライブラリ、ヘルパーがある場合は、設定ファイル (`autoload.php`) の中で指定することができます。

#### application/config/autoload.php

~~~ php
$autoload['libraries'] = array('database', 'session', 'xmlrpc');
$autoload['helper'] = array('url', 'file');
~~~


ライブラリ、ヘルパーの使い方
----

ライブラリをロードすると、`$this->ライブラリ名->メソッド名` のような形式でメソッドを呼び出せるようになります。
`email` クラスの場合はこんな感じです。

~~~ php
$this->email->send();
~~~

一方、ヘルパーの方は関数の集まりなので、単純に `function()` のような形式で呼び出すことができます。
例えば、URL ヘルパーをロードした場合、以下のようにリンクを生成する `anchor()` 関数を使用できるようになります。

~~~ php
<?= anchor('my_controller/my_action', 'Click Here'); ?>
~~~

