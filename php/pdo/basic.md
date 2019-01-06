---
title: "PDO を使ってデータベースにアクセスする"
date: "2012-01-15"
---

データベースへ接続する
----

PDO クラス (PHP Data Object) を使用すると、異なるデータベース（SQLite や MySQL など）に、統一されたインタフェースでアクセスできるようになります。
PDO を使ってデータベースへ接続するときには、以下のような形式の DSN (Data Source Name) を使って接続用のパラメータを指定します。

~~~
<db_program_name>:<params>
~~~

例えば、SQLite データベースを使用する場合は、以下のように記述することで接続できます。

~~~ php
$pdo = new PDO('sqlite:sample.db');
~~~

PDO の API ドキュメントには、SQLite のファイルパスは絶対パスで指定すると書いてありますが、実際にはカレントディレクトリからの相対パス指定でも接続できるようです。

データベースへの接続時にパスワードが必要な MySQL などの場合は、PDO のコンストラクタの第２引数、第３引数でユーザ名とパスワードを指定します。

~~~ php
$pdo = new PDO('mysql:host=localhost;dbname=test', 'root', 'pass');
~~~

#### サンプル

~~~ php
try {
    $pdo = new PDO('sqlite:sample.db');
    // ...
    $pdo = NULL;
} catch (PDOException $e) {
    die('Error: ' . $e->getMessage());
}
~~~

上記のように `PDOException` をハンドルしておくと、カレントディレクトリに `sample.db` が存在しないときに、次のようなエラーメッセージが表示されます。

~~~
Error: SQLSTATE[HY000] [14] unable to open database file
~~~

開発中は、あえて例外を捕捉せずに、デフォルトの詳細なエラーメッセージを表示するというの手もあります。

PDO オブジェクトに `NULL` を代入することで、データベースをクローズすることができます。
この処理を明示的に行わない場合、スクリプトが終了した時点でデータベースがクローズされます。


サンプルデータ
----

以降のサンプルコードで使用するデータベースファイルとして、下記のように `sample.db` を作っておきます。

~~~
$ sqlite3 sample.db
sqlite> CREATE TABLE urls(id INTEGER PRIMARY KEY, title TEXT, url TEXT);
sqlite> INSERT INTO urls(title, url) VALUES('Google', 'http://google.com/');
sqlite> INSERT INTO urls(title, url) VALUES('Yahoo', 'http://yahoo.com/');
sqlite> INSERT INTO urls(title, url) VALUES('Bing', 'http://bing.com/');
~~~


単純な SELECT クエリを実行する (query)
----

- 参考
  - PDO クラス: http://www.php.net/manual/en/class.pdo.php
  - PDOStatment クラス: http://www.php.net/manual/en/class.pdostatement.php

単純な SQL 文を実行するには、PDO クラスの `query` メソッドを使用します。

※ WHERE 句で、検索パラメータなどを渡す場合は Prepared statement (`PDO::prepare`) を使用し、SQL インジェクションを防ぐようにします。

~~~
class PDO {
    PDOStatement query(string $statement);
    ...
}
~~~

#### サンプルコード

~~~ php
<?php
try {
    $pdo = new PDO('sqlite:sample.db');
    $stmt = $pdo->query('SELECT id, title, url from urls');
    foreach ($stmt as $row) {
        echo $row[0] . '<br>';  // id
        echo $row[1] . '<br>';  // title
        echo $row[2] . '<br>';  // url
    }
    $pdo = NULL;
} catch (PDOException $e) {
    die('Error: ' . $e->getMessage());
}
~~~

`PDO::query()` で取得した `PDOStatement` オブジェクトを、上記のように `foreach` でイテレートすることによって各レコードの情報を１つずつ取得することができます。
`PDOStatement::fetch()` メソッドを使って、従来の `while` ループで処理することもできます。

~~~ php
while ($row = $stmt->fetch()) {
    echo $row[0] . '<br>';
    echo $row[1] . '<br>';
    echo $row[2] . '<br>';
}
~~~

`PDOStatement::setFetchMode()` で、`PDO::FETCH_ASSOC` を指定しておくと、結果を取得するときに、インデックスではなく、カラム名で参照できるようになります。
速度に特に問題がなければこちらを使っておくと、コードが分かりやすくなります。

~~~ php
$pdo = new PDO('sqlite:sample.db');
$stmt = $pdo->query('SELECT id, title, url from urls');
$stmt->setFetchMode(PDO::FETCH_ASSOC);
foreach ($stmt as $row) {
    echo $row['id'] . '<br>';
    echo $row['title'] . '<br>';
    echo $row['url'] . '<br>';
}
~~~


Prepared Statement で SQL を実行する (prepare)
----

- 参考
  - PHP: Prepared statements and stored procedures - Manual
  - http://php.net/manual/en/pdo.prepared-statements.php

SQL statement を実行するときに、ユーザ入力のパラメータを渡すような場合は、SQL インジェクションの対策を入れておく必要があります。
`PDO::quote()` を使って、各入力をエスケープすることもできますが、`PDO::prepare()` でプレースホルダを指定して Prepared statement を作成することで、このエスケープ作業を自動的に行うことができます。

~~~
class PDO {
    PDOStatement prepare (string $statement, ...)
    ...
}
~~~

`PDO::prepare()` で `PDOStatement` オブジェクトを取得したら、`bindValue()` で各プレースホルダに対して、実際の値を設定してから `execute()` メソッドで実行します。

~~~ php
$stmt = $pdo->prepare('SELECT id, title, url from urls WHERE id = :id');
$stmt->bindValue(':id', 1);
$stmt->execute();
~~~

`execute()` を実行したあとは、`PDO::query()` の場合と同じように `foreach` によって、各レコードを取得できます。
Prepared statement を使用した場合は、`bindValue()`、`execute()` を繰り返し実行することで、
異なるパラメータでの SQL クエリを効率的に実行することができます。

#### サンプルコード

~~~ php
<?php
try {
    $pdo = new PDO('sqlite:sample.db');
    $stmt = $pdo->prepare('SELECT id, title, url from urls WHERE id = :id');
    $stmt->setFetchMode(PDO::FETCH_ASSOC);

    // Bind values and execute the statement
    $stmt->bindValue(':id', 1);
    $stmt->execute();

    foreach ($stmt as $row) {
        echo $row['id'] . '<br>';
        echo $row['title'] . '<br>';
        echo $row['url'] . '<br>';
    }
    $pdo = NULL;
} catch (PDOException $e) {
    die('Error: ' . $e->getMessage());
}
~~~

