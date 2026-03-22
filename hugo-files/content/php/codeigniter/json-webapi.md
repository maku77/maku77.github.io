---
title: "PHPメモ: CodeIgniter - JSON テキストを返す Web API をサクッと作成する"
url: "/p/wbqitsz/"
date: "2013-03-02"
tags: ["php"]
aliases: [/php/codeigniter/json-webapi.html]
---

CodeIgniter のコントローラの実装で、PHP の `json_encode()` 関数を使えば、簡単に JSON 形式のテキストをクライアントに返すことができます。

{{< code lang="php" title="Api コントローラー (application/controllers/api.php)" >}}
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
{{< /code >}}

例えば、上記のようなファイルを作成して、`http://localhost/igniter/index.php/api/hello/Maku` にアクセスすれば、以下のような JSON テキストがレスポンスとして返されます。

```json
{"name":"Maku","message":"Hello Maku"}
```

