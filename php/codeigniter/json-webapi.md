---
title: CodeIgniter - JSON テキストを返す Web API をサクッと作成する
date: "2013-03-02"
---

CodeIgniter のコントローラの実装で、PHP の `json_encode()` 関数を使えば、簡単に JSON 形式のテキストをクライアントに返すことができます。

#### Api コントローラ (application/controllers/api.php)

~~~ php
<?php
class Api extends CI_Controller {
    public function hello($name = '') {
        $arr = array(
            'name' => $name,
            'message' => 'Hello ' . $name
        );
        echo json_encode($arr);
    }
}
~~~

例えば、上記のようなファイルを作成して、`http://localhost/ingniter/index.php/api/hello/Maku` にアクセスすれば、以下のような JSON テキストがレスポンスとして返されます。

~~~ javascript
{"name":"Maku","message":"Hello Maku"}
~~~

