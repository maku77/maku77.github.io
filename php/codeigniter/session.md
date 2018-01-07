---
title: CodeIgniter - Session クラスでセッションデータを保持する
date: "2012-08-13"
---

Session クラスとは
----

CodeIgniter の `Session` クラスを使用すると、Web ブラウザとサーバ間のセッションデータを簡単に保存することができます。

- 参考: [http://codeigniter.com/user_guide/libraries/sessions.html](http://codeigniter.com/user_guide/libraries/sessions.html)


Session クラスを使用するための準備
----

`Session` クラスを使用するには、`application/config/config.php` に暗号化キーをセットしておく必要があります。

#### application/config/config.php

~~~ php
$config['encryption_key'] = 'B4SgB9IGUZpYcjAKNAnCUICh0ByZpdoJ';
~~~

32 文字のランダムなキーは、例えば以下のように作成できます。

#### create_encryption_key.php

~~~ php
<?php
function create_encryption_key() {
    $chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $num = 62;
    $str = '';
    for ($i = 0; $i < 32; ++$i) {
        $str .= $chars[mt_rand(0, $num-1)];
    }
    return $str;
}
echo create_encryption_key();
~~~


Session クラスを使用する
----

コントローラーのコンストラクタなどで、以下のようにライブラリをロードし、

~~~ php
$this->load->library('session');
~~~

あとは、セッションデータの読み書きを以下のように行うだけです。

~~~ php
$this->set_userdata('key', 'value');  // セッションデータの設定
$this->userdata('key');               // セッションデータの取得
$this->unset_userdata('key');         // セッションデータの削除
~~~


サンプルコード
----

下記の Session コントローラーを配置したうえで、`http://your.site/index.php/session/set/` にアクセスすると、セッションデータの設定、`http://your.site/index.php/session/get/` にアクセスすると、セッションデータの取得が行われます。

#### Session コントローラー (application/controllers/session.php)

~~~ php
<?php
class Session extends CI_Controller {
    public function __construct() {
        parent::__construct();
        $this->load->library('session');
    }

    public function set() {
        $this->session->set_userdata('key', 'ABCDE');
        echo 'Set';
    }

    public function get() {
        echo 'Get: ' . $this->session->userdata('key');
    }
}
~~~

