---
title: CodeIgniter - URL ヘルパーでコントローラへのリンクを挿入する
created: 2012-08-11
---

HTML の中から、特定のコントローラ、アクションへのリンクを貼りたい場合は、URL ヘルパーが提供する `anchor` 関数を使用すると便利です。

~~~ php
<?php echo anchor("pages/show/1", "ページ名"); ?>
~~~

上記のようにすると、サイトの URL を基準にした以下のような Anchor タグが生成されます。

~~~ html
<a href="http://example.com/pages/show/1">ページ名</a>
~~~

`anchor()` 関数を使用するには、コントローラなどで URL ヘルパーをロードしておく必要があります。

~~~ php
<?php
class Pages extends CI_Controller {
    public function __construct() {
        parent::__construct();
        $this->load->helper('url');  // anchor
    }
    ...
~~~

