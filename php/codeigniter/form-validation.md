---
title: "CodeIgniter - Form Validation クラスでフォームに入力された値を検査する"
date: "2012-08-14"
---


Form Validation クラスとは
----

CodeIgniter の Form Validation クラスを使用すると、HTML フォームに入力された値の検査を簡単に行うことができるようになります。

- 参考: [Form Validation : CodeIgniter User Guide](http://codeigniter.com/user_guide/libraries/form_validation.html)


Form Validation クラスを使用する準備
----

Form Validation クラスを使用するには、コントローラーのコンストラクタなどで、以下のようにロードしておく必要があります。

~~~ php
<?php
class Validation_test extends CI_Controller {
    public function __construct() {
        parent::__construct();
        $this->load->library('form_validation');
        $this->load->helper('form');  // form_open, form_close
    }
    //...
}
~~~

検証の対象となるビューには、以下のように `validation_errors()` の戻り値を表示するように記述しておきます。
これで、検証に失敗した場合に、再びこのビューを表示したときに、ここにエラーメッセージが表示されるようになります。

`input` 要素の `value` 属性値に、`set_value()` メソッドで取得した値を格納していますが、このようにしておくことで、検証エラーが発生した後でこのフォームを再表示する場合に、自動的に前回入力した値を表示してくれるようになります（パスワードに関しては、ユーザに再入力させるため、意図的にフィールドをクリアしています）。

#### form_view ビュー (application/views/form_view.php)

~~~ php
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Validation test</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>

<?php echo validation_errors('<div class="error">', '</div>'); ?>

<?php echo form_open('validation_test/'); ?>
    <div>
        <label for="user_id">User ID:</label>
        <input type="text" name="user_id" id="user_id" value="<?php echo set_value('user_id'); ?>" />
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" />
    </div>
    <div>
        <label for="passconf">Password confirmation:</label>
        <input type="password" name="passconf" id="passconf" />
    </div>
    <div>
        <label for="email">E-mail:</label>
        <input type="text" name="email" id="email" value="<?php echo set_value('email'); ?>" />
    </div>
    <div>
        <input type="submit" value="Submit" />
    </div>
<?php echo form_close(); ?>

</body>
</html>
~~~

フォーム入力をハンドルするアクション内では、`$this->form_validation->set_rules()` で各入力の検証ルールを追加し、最後の `run()` メソッドで検証を行います。

#### Validateion_test コントローラー (application/controllers/validation_test.php)

~~~ php
<?php
class Validation_test extends CI_Controller {
    public function index() {
        $this->form_validation->set_rules('user_id', 'User ID',
            'required|min_length[4]|max_length[20]|alpha_numeric');
        $this->form_validation->set_rules('password', 'Password',
            'required|min_length[6]|max_length[20]|matches[passconf]');
        $this->form_validation->set_rules('passconf', 'Password confirmation',
            'required');

        if ($this->form_validation->run()) {
            // Validation succeeded
            // ... Handle input data here ...
            echo "Validation OK!!";
        } else {
            // Validation failed
            $this->load->view('form_view');
        }
    }
}
~~~

`form_validation->set_rules()` の第３引数には、以下に記載されたルールを縦棒 (`|`) で区切って指定できます。
縦棒 (`|`) の前後には空白スペースを入れてはいけません。

- 参考: [Form Validation - Rule Reference](http://codeigniter.com/user_guide/libraries/form_validation.html#rule-reference)
~~~

上記のサンプルコードでは、以下のようなルールを設定しています。

* ユーザID (`user_id`) は、「入力必須」「4文字〜20文字」「英数字、アンダースコア(`_`)、ダッシュ(`-`)で構成」
* パスワード (`password`) は、「入力必須」「6文字〜20文字」「passconf フィールドと同じ内容か確認」
* Eメール (`email`) は、「30文字以内」「正しい e-mail アドレス形式」


Form Validation の注意点（リダイレクトすると validation_errors() が空っぽになる！？）
----

Form Validation クラスの `run()` メソッドを呼び出すと、`validation_errors()` でエラーメッセージを取得できるようになります。
ただし、このエラーメッセージが取得できるのは、`run()` メソッドを呼び出した時の HTTP リクエスト内に限ります。
つまり、以下のようにクライアントに対してリダイレクト要求を出すと、

~~~ php
if ($this->form_validation->run()) {
    ...
} else {
    redirect('/aaa/bbb');
}
~~~

`Aaa` コントローラ内の `bbb` アクションが処理を開始するときには、既に `validation_errors()` でエラーメッセージを取得できなくなっています。

リダイレクト後にエラーメッセージを参照したい場合は、例えば、`session` クラスの `set_flashdata()` を使用して、次回のリクエスト内でエラーメッセージを参照できるようにしておきます。

~~~ php
$this->session->set_flashdata('validation_errors', validation_errors());
redirect('/aaa/bbb');
~~~

面倒くさく感じるかもしれませんが、よく考えてみると、これは、フォームから入力された値を扱う時と同じことで、同一リクエスト内でしか入力データが参照できないのは自然なことです。

結局は、リダイレクトしなくても済むようにコントローラを設計するのが一番楽です。
具体的には、最初にフォームを表示するときにアクセスするときのアクションと、フォームの submit ボタンを押したときにアクセスするアクションを共通にしておきます。
例えば、ここでは `Validation_test` コントローラの `index` アクションを、共通のアクションとします。

~~~ php
class Validation_test extends CI_Controller {
    ...
    public function index() {
        $this->form_validation->set_rules(...);
        $this->form_validation->set_rules(...);
        $this->form_validation->set_rules(...);

        if ($this->form_validation->run()) {
            // Validation succeeded
            $this->load->view('success_view');
        } else {
            // Validation failed
            $this->load->view('form_view');
        }
    }
    //...
~~~

最初にユーザが、`http://your.site/validation_test/` にアクセスした時は、何もまだ入力がないので、当然 `form_validation->run()` は失敗し、`form_view` ビューが表示されます。

#### form_view ビュー (application/views/form_view.php)

~~~ php
...
<?php echo validation_errors('<div class="error">', '</div>'); ?>
<?php echo form_open('validation_test/'); ?>
    ...
<?php echo form_close(); ?>
~~~

`form_view.php` の中では、`validation_errors()` を使ってエラーメッセージを表示しようとしていますが、最初のアクセスではエラーメッセージが空っぽなので何も表示されません。
一方で、submit ボタンを押して、再び `Validation_test` コントローラの `index` アクションが呼び出された後に、validation エラーが発生した場合は、今度は `validation_errors()` にエラーメッセージが格納され、それが画面上に表示されることになります。

