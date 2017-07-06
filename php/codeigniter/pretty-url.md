---
title: CodeIgniter - index.php を省略した URL でアクセスできるようにする
created: 2012-05-30
---

CodeIgniter で Web アプリケーションを作成すると、そのアクセス URL はデフォルトで下記のような感じになります。

~~~
http://example.com/index.php/foo/bar
~~~

この URL の `index.php` という部分を省略し、

~~~
http://example.com/controller/action
~~~

という URL でアクセスできるようにするためには、Apache の URL Rewriting Engine (mod_rewrite) モジュールの機能を利用します。
`.htaccess` ファイルに以下のように作成して、Apache を再起動すれば OK です。

#### ${YourAppRoot}/.htaccess

~~~
RewriteEngine on
RewriteCond $1 !^(index\.php|images|robots\.txt)
RewriteRule ^(.*)$ /index.php/$1 [L]
~~~


### 注意事項

`http://example.com/hoge/controller/action` という感じで、１階層深いアドレスでアクセスするのであれば、RewriteRule の `/index.php/$1` という部分も合わせて `/hoge/index.php/$1` にする必要があります。
つまり、ここには DocumentRoot からの絶対パスを指定します。

さらに、URL ヘルパーの `anchor()` メソッドが生成する URL から、`index.php` を除くために、`config.php` を以下のように修正します。

#### application/config/config.php

~~~
$config['index_page'] = 'index.php';
↓
$config['index_page'] = '';
~~~


### 参考

- [CodeIgniter - CodeIgniter URLs](http://codeigniter.com/user_guide/general/urls.html)

