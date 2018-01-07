---
title: CodeIgniter- HTML ヘルパーでスタイルシート用の link タグを挿入する
date: "2012-08-12"
---

CSS スタイルシートをロードするための `link` タグを出力するには、HTML ヘルパーの提供する `link_tag` 関数を使用すると便利です。

~~~ php
<?php echo link_tag('css/sample.css'); ?>
~~~

上記のコードは以下のように変換されます。

~~~ html
<link href="http://site.com/css/sample.css" rel="stylesheet" type="text/css" />
~~~

CSS ファイルは、application ディレクトリと同じ階層に配置します。

~~~
+-- igniter
    +-- application/
    +-- css/
        +-- sample.css
~~~

さらに、`.css` という拡張子を持つファイルの rewrite ルールを以下のように外しておく必要があります。
これをやっておかないと、CSS ファイルを読み込めません。

#### ${YourAppRoot}/.htaccess

~~~
RewriteEngine on
RewriteCond $1 !^(index\.php|images|.+\.css$|.+\.js$|.+\.png$|.+\.jpg$|robots\.txt)
RewriteRule ^(.*)$ /index.php/$1 [L]
~~~

`link_tag` 関数を使用するには、コントローラなどで HTML ヘルパーをロードしておく必要があります。

~~~ php
<?php
class Pages extends CI_Controller {
    public function __construct() {
        parent::__construct();
        $this->load->helper('html');  // link_tag
    }
    ...
}
~~~

