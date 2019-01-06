---
title: "PHPUnit を使用してユニットテストを行う"
date: "2012-09-22"
---

PHPUnit は、PHP 言語用の単体テストフレームワークです。


PHPUnit のインストール
----

PEAR を使うと、PHPUnit を簡単にインストールすることができます。

~~~
$ pear config-set auto_discover 1
$ pear install pear.phpunit.de/PHPUnit
~~~

- 参考: [http://www.phpunit.de/manual/current/en/installation.html#installation.pear](http://www.phpunit.de/manual/current/en/installation.html#installation.pear)

インストール作業が終わったら、下記のように実行してみてください。

~~~
$ php -i | grep include_path
~~~

これで表示されるインクルードパスに、`PHPUnit/Framework/TestCase.php` などのファイルが含まれていれば、正常にインストールが完了しています。


PHPUnit の基本的な使い方
----

以下のサンプルでは、内部で数値をカウントするクラス `Counter` を、テストコード `CounterTest.php` でテストしています。

#### Counter.php（テスト対象のクラス）

~~~ php
<?php
class Counter {
    private $count;  // make it private

    public function __construct() {
        $this->count = 0;
    }

    public function getCount() {
        return $this->count;
    }

    public function increment() {
        ++$this->count;
    }
}
~~~

#### CounterTest.php（Counter クラスのテストケース）

~~~ php
<?php
require_once 'Counter.php';

class CounterTest extends PHPUnit_Framework_TestCase {
    public function testConstruct() {
        $cnt = new Counter();
        $this->assertSame($cnt->getCount(), 0);
    }

    public function testIncrement() {
        $cnt = new Counter();
        $cnt->increment();
        $cnt->increment();
        $cnt->increment();
        $this->assertEquals($cnt->getCount(), 3);
    }
}
~~~

テストケースとなるクラス (`CounterTest`) は `PHPUnit_Framework_TestCase` を継承し、`test〜` という名前でテストメソッドを実装していきます。
テストの成功条件は、テストメソッドの中で `assert〜` というメソッドを呼び出すことによって表現します（内部的に `==` で比較する `assertEquals` よりも、`===` で厳密な比較を行う `assertSame` を使用することをオススメします）。

テストを実行するには、`phpunit` コマンドに、テストを実装している PHP ファイル名（テストケース名）を指定して実行します（`.php` は省略可能）。

~~~
$ phpunit CounterTest
PHPUnit 3.7.1 by Sebastian Bergmann.
..
Time: 0 seconds, Memory: 4.50Mb
OK (2 tests, 2 assertions)
~~~


各メソッドで共通の処理（setUp と tearDown）
----

各テストメソッド（`test〜`）の実行前と実行後には、それぞれ `setUp()` と `tearDown()` メソッドが呼び出されるので、各テストに共通の処理をここに記述しておくことができます。

~~~ php
class CounterTest extends PHPUnit_Framework_TestCase {
    private $cnt;

    /* Called before each test. */
    public function setUp() {
        $this->cnt = new Counter();
    }

    /* Called after each test. */
    public function tearDown() {
    }

    ...
}
~~~


コラム: PHPUnit 関連のクラスを require する必要がないのはなぜか？
----

上記のサンプル内で、`CounterTest` クラスは `PHPUnit_Framework_TestCase` を `extends` していますが、`phpunit` コマンドでユニットテストを実行する場合は、何も `require_once` する必要はありません。
`phpunit` コマンドは実際には PHP スクリプトですが、この内部で `PHPUnit/Autoload.php` をロードし、PHPUnit 関連のクラスを使用したときに、自動的に適切な PHP ファイルがロードされるように設定されています。

