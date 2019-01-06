---
title: "CodeIgniter - 別のコントローラー、アクションへリダイレクトする"
date: "2012-08-13"
---

例えば、フォームから入力されたデータをデータベースに格納するアクションを定義した場合、そのアクションの最後で、今追加したデータを表示するためのアクションへリダイレクトしたくなります。
このような場合は、URL ヘルパーの提供する `redirect()` 関数を使用します。

コントローラーの中で、以下のように `redirect()` メソッドを呼び出すと、クライアント（ブラウザ）に対してリダイレクト要求が送られます。

~~~ php
redirect('/controller名/action名/parameter');
~~~

以下のサンプルコードでは、`do_create` アクションで入力データを処理した後で、`show` アクションへリダイレクトしています。

~~~ php
class Pages extends CI_Controller {
    public function __construct() {
        parent::__construct();
        $this->load->helper('url');  // redirect
        $this->load->model('pages_model');
    }

    public function do_create() {
        $id = $this->_create_id();
        $data = array(
            'id' => $id,
            'title' => $this->input->post('title'),
            'body' => $this->input->post('body'),
        );
        $this->pages_model->insert($data);
        redirect('/pages/show/' . $id);
    }
    ...
~~~

`redirect()` を使わずに、以下のような感じで自分自身のコントローラのアクションを直接呼び出した場合は、サーバ内部で処理が完結するため、ブラウザの URL 表示が変化しません。

~~~
$this->show($id);
~~~

