---
title: "CodeIgniter - ユーザのログイン状態を管理する"
date: "2012-08-14"
---


何をやりたいか？
----

ここでは、以下のようなことを実現します。

- ログインフォームの作成
- 入力されたユーザ名、パスワードの形式の検証 (Form Validation)
- 入力されたユーザ名、パスワードが登録済みのものであるかの確認
- 正しいパスワードなら、セッション情報に「ログイン済み」であることを保持

説明をシンプルにするために、

~~~
ユーザ名: "admin"
パスワード: "password"
~~~

という組み合わせだけでログイン可能なログインフォームを作ることにします。
必要に応じてユーザ情報をデータベースで管理するように拡張すれば、ログイン可能なユーザ数をいくらでも増やすことができます。

サンプルコードでは、

- Form Validation ライブラリによる入力データの検証
- Session ライブラリによるログイン情報の保持

などの処理を行っているため、まずはこれらのライブラリの使い方を理解しておく必要があります。

- [CodeIgniter - Form Validation クラスでフォームに入力された値を検査する](./form-validation.html)
- [CodeIgniter - Session クラスでセッションデータを保持する](./session.html)


コードの作成
----

まずは、セッション情報としてログイン状態を管理する `login_session` ライブラリを作成します。

このクラスの `is_logged_in()` メソッドを呼び出すことで、現在接続しているユーザがログイン済みであるかを調べることができます。
コントローラ内や、ビュー内でこのメソッドを使って、ログイン済みの場合だけ行いたい処理を分岐して記述することができます。
ログイン状態を変化させるためには、それぞれ、`login()` メソッド、`logout()` メソッドを呼び出します。

#### login_session ライブラリ (application/libraries/login_session.php)

~~~ php
<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Login_session {
    const LOGGED_IN = 'logged_in';
    const USER_ID = 'user_id';

    public function __construct() {
        $this->CI =& get_instance();
        $this->CI->load->library('session');
    }

    public function is_logged_in() {
        return $this->CI->session->userdata(self::LOGGED_IN);
    }

    public function get_user_id() {
        return $this->CI->session->userdata(self::USER_ID);
    }

    public function login($user_id) {
        $this->CI->session->set_userdata(self::LOGGED_IN, TRUE);
        $this->CI->session->set_userdata(self::USER_ID, $user_id);
    }

    public function logout() {
        //$this->CI->session->unset_userdata(self::LOGGED_IN);
        //$this->CI->session->unset_userdata(self::USER_ID);
        $this->CI->session->sess_destory();
    }
}
~~~

次に、入力されたユーザ名、パスワードが正しいものであるかを調べるための `users_model` モデルを作成します。

このクラスの `check_password($user_id, $password)` が `TRUE` を返せば、ログイン可能であることを示します。
ここでは簡単のため、admin/password の組み合わせだけでログイン可能にしていますが、本来は、データベース内のユーザ情報を参照して、ユーザ名とパスワードの確認をすることを意図しています。

~~~ php
<?php
class Users_model extends CI_Model {
    public function __construct() {
        parent::__construct();
        //$this->load->database();
    }

    public function check_password($user_id, $password) {
        // TODO: These should be in a DB.
        $sha1_pass = sha1($password);
        if ($user_id === 'admin' && $sha1_pass === sha1('password')) {
            return TRUE;
        }
        return FALSE;
    }
}
~~~

次に、入力フォームを表示するための `login_view` ビューを作成します。
このビューは、入力にエラーが見つかった場合の再表示にも使われます。

Form Validation クラスによる、入力フォーマットの検証にエラーが発生した場合は、`validations_errors()` の表示が行われます。
入力フォーマットが正しいけれど、ユーザ名とパスワードの組み合わせが間違っている場合は、コントローラ側で `$login_error` 変数を `TRUE` にセットすることで、"Incorrect password" の表示を行えるようにしています。

上記のどちらのエラーが発生した場合にも、ユーザ名の再入力を防ぐために、`user_id` フィールドの値は、`set_value()` の返す値を使って初期化しています。

#### login_view ビュー (application/views/login_view.php)

~~~ php
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Log in</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>

<?php echo validation_errors('<div class="error">', '</div>'); ?>

<?php if (isset($login_error)) : ?>
    <div class="error">Incorrect password!</div>
<?php endif; ?>

<?php echo form_open('user/login'); ?>
    <div>
        <label for="user_id">User ID:</label>
        <input type="text" name="user_id" id="user_id" value="<?php echo set_value('user_id'); ?>" />
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" />
    </div>
    <div>
        <input type="submit" value="Submit" />
    </div>
<?php echo form_close(); ?>

</body>
</html>
~~~

最後にこれらを連携させるためのコントローラを見ていきます。

ログインフォームを表示するためには、`http://your.site/user/login` のような URL でアクセスすることにします。
つまり、`User` コントローラの `login` アクションが入り口になります。
`login` アクションでは以下のような二段階のエラーチェックを行います。

1. Form Validation による「ユーザ名」「パスワード」の入力形式のチェック（URL 直指定の場合は Form Validation の `run()` は `FALSE` を返しますが、`validation_errors()` の返すメッセージは空になるという特殊な動きをします。これにより、一種類のビューを、初回アクセス時、エラー発生時の両方で使いまわせるようになっています）
2. users_model によるユーザ名とパスワードの組み合わせチェック

両方のチェックを通過した場合だけ下記のコードが実行され、ユーザがログインした状態になります。

~~~ php
$this->login_session->login($user_id);
~~~

ログインした後は、好きなコントローラ、アクションにリダイレクトして、ログイン後の表示を行うことができます。
リダイレクト後のコントローラ内でも、以下のようにログイン済みであるかのチェックを忘れないようにしてください。
ログインしていない場合は、ログインフォームにリダイレクトするのが親切でしょう。

~~~ php
if ( ! $this->login_session->is_logged_in()) {
    redirect('/user/login');
}
~~~

ユーザ操作（リンクのクリックなど）で意図的にログアウトさせたい場合は、コントローラ内で `login_session` ライブラリの `logout` メソッドを呼び出すか、以下のような URL へのリンクを貼っておきます。

~~~
http://your.site/user/logout
~~~

上記の URL へアクセスすると、結局はコントローラ内で `login_session` クラスの `logout` メソッドを呼び出します。
ログアウト後は、ログインしていない状態でも表示可能なホームページか、ログインフォームにリダイレクトさせましょう。

#### User コントローラー (application/controllers/user.php)

~~~ php
<?php
class User extends CI_Controller {
    public function __construct() {
        parent::__construct();
        $this->load->helper('form');  // form_open, form_close
        $this->load->helper('url');  // redirect
        $this->load->library('form_validation');

        // My libraries
        $this->load->model('users_model');  // check_password
        $this->load->library('login_session');  // login, logout
    }

    public function index() {
        redirect('/user/login');
    }

    public function login() {
        $this->form_validation->set_rules('user_id', 'User ID',
            'required|min_length[4]|max_length[20]|alpha_numeric');
        $this->form_validation->set_rules('password', 'Password',
            'required|min_length[4]|max_length[20]');

        if ($this->form_validation->run()) {
            // Validation succeeded
            $user_id = $this->input->post('user_id');
            $password = $this->input->post('password');
            if ($this->users_model->check_password($user_id, $password)) {
                // Correct password
                $this->login_session->login($user_id);
                echo "Correct password";
                // redirect('/aaa/bbb');
            } else {
                // Incorrect password
                $data['login_error'] = TRUE;
                $this->load->view('login_view', $data);
            }
        } else {
            // Validation failed
            $this->load->view('login_view');
        }
    }

    public function logout() {
        $this->login_session->logout();
        redirect('/user/login');
    }
}
~~~

