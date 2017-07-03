---
title: PDO - PHP ファイルからの相対パスで SQLite のデータベースファイルを開く
created: 2012-09-02
---

PDO で SQLite3 のデータベースファイルをオープンするときには、絶対パスでファイルパスを指定する必要があります。
PHP ファイルのあるディレクトリからの相対パスでファイルを指定したい場合は、以下のような感じで絶対パスを構成することができます。

~~~
'sqlite:' . __DIR__ . DIRECTORY_SEPARATOR . 'sample.db'   # PHP5.3以降
'sqlite:' . dirname(__FILE__) . DIRECTORY_SEPARATOR . 'sample.db'
~~~

以下のサンプルコードでは、`WordModel` クラスのコンストラクタ内で、`words.db` を相対パス指定で開いています。

~~~ php
<?php
class WordModel {
    const DB_FILE = 'words.db';
    private $pdo;

    public function __construct() {
        try {
            $this->pdo = new PDO($this->getDsn(self::DB_FILE));
        } catch (PDOException $e) {
            die('Error: ' . $e->getMessage());
        }
    }

    public function __destruct() {
        $this->pdo = NULL;
    }

    // Data source name
    private function getDsn($filename) {
        return 'sqlite:' . __DIR__ . DIRECTORY_SEPARATOR . $filename;
    }
}
~~~

