---
title: "foreach でループ処理できるクラスを作成する（Iterator の実装）"
date: "2012-10-01"
---

クラスを実装するときに `Iterator` インタフェースを実装しておくと、そのクラスのオブジェクトは `foreach` でループ処理できるようになります。

#### Iterator インタフェース

~~~
Iterator extends Traversable {
    abstract public mixed current ( void )
    abstract public scalar key ( void )
    abstract public void next ( void )
    abstract public void rewind ( void )
    abstract public boolean valid ( void )
}
~~~

- [PHP: Iterator - Manual](http://www.php.net/manual/en/class.iterator.php) より

下記の `MyIteratable` クラスは、`Interator` インタフェースのメソッドをすべて実装し、内部で保持している配列データを順番に処理できるようにしています。

~~~ php
<?php
class MyIteratable implements Iterator {
    private $_elements = array(100, 200, 300);
    private $_iter_pos = 0;

    /**
     * Iterator::current().
     * Return the current element.
     */
    public function current() {
        return $this->_elements[$this->_iter_pos];
    }

    /**
     * Iterator::key().
     * Return the key of the current element.
     */
    public function key() {
        return $this->_iter_pos;
    }

    /**
     * Iterator#next().
     * Move forward to next element.
     */
    public function next() {
        ++$this->_iter_pos;
    }

    /**
     * Iterator#rewind().
     * Rewind the Iterator to the first element.
     */
    public function rewind() {
        $this->_iter_pos = 0;
    }

    /**
     * Iterator#valid().
     * Checks if current position is valid.
     */
    public function valid() {
        return isset($this->_elements[$this->_iter_pos]);
    }
}
~~~

`MyIteratable` クラスは、`Iterator` インタフェースを実装しているので、以下のように `foreach` でループ処理することができます。

~~~ php
$obj = new MyIteratable();
foreach ($obj as $it) {
    echo $it . "\n";
}
~~~

#### 実行結果

~~~
100
200
300
~~~

