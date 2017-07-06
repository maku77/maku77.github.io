---
title: CodeIgniter - 独自のユーザライブラリから別のライブラリをロードする
created: 2012-08-13
---

`application/libraries` ディレクトリに作成するユーザライブラリは、`CI_Controller` クラスや、`CI_Model` クラスを継承しないので、他のライブラリやモデルをロードするときに、

~~~ php
$this->load->library('session');
$this->load->model('user');
~~~

といった書き方ができません。

ユーザライブラリの中から他のライブラリをロードするには、まず `get_instance()` で `CodeIgniter` オブジェクトの参照を取得する必要があります。

~~~ php
$CI =& get_instance();
$CI->load->library('session');
$CI->load->model('user');
~~~

以下のサンプルコードでは、コンストラクタで `CodeIgniter` オブジェクトの参照を取得し、メンバ変数 `$this->CI` に保持することで、各メンバメソッドから使用できるようにしています。

#### User_session ライブラリ (application/libraries/user_session.php)

~~~ php
<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class User_session {
    const LOGGED_IN = 'logged_in';

    public function __construct() {
        $this->CI =& get_instance();
        $this->CI->load->library('session');
    }

    public function is_logged_in() {
        return $this->CI->session->userdata(self::LOGGED_IN);
    }

    public function login() {
        $this->CI->session->set_userdata(self::LOGGED_IN, TRUE);
    }

    public function logout() {
        $this->CI->session->set_userdata(self::LOGGED_IN, FALSE);
    }
}
~~~

