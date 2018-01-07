---
title: コレクションクラスのまとめ
date: "2014-06-16"
---

Vector と List (ArrayList, LinkedList)
----

Java 1.3 以前のコレクションには Vector がありますが、これは互換性のために残されています。
Vector は ArrayList の synchronized version と説明されることもありますが、現在は API の統一性も考慮された新しい List インタフェースを使用することが推奨されています。
List インタフェースを実装したクラスには、ArrayList（配列ベース）、LinkedList（リンクリストベース）などがあります。
Vector はすべてのメソッドがスレッド排他制御されるように作られていますが、この機構だけでは、そもそもスレッドセーフなプログラミングはできないことが分かっています。
たとえば、2 つのメソッドを呼び出すコードがあるとすると、その呼び出し間は同期されません。
このため、新しいコレクションクラスは、基本的に同期しない作りになっています。


Hashtable と Map (HashMap, ConcurrentHashMap):
----

Vector と同様に、Java 1.3 以前に使用されていたクラスです。
現在は、Map インタフェースを実装した HashMap クラスなどを使うことが推奨されています。
Hashtable はすべてのスレッドが排他制御されるよう作られていますが、これだけではスレッドセーフなコードは書くことはできません。
代わりに HashMap などを使い、明示的に排他制御を行なった方が効率のよいうスレッドセーフコードを作成できます。


CopyOnWriteArrayList (1.5) / CopyOnWriteArraySet (1.5): イテレーション中のリスト操作を許可
----

変更時（add やremove 時）に、内部的にコピーが作成されるようになっている ArrayList 実装です。あるスレッドで一度 Iterator を取得したら、たとえ他のスレッドからの要素の増減があったとしても、その時点での要素を操作できることが保証されます。要素のイテレーション中に、他のスレッドからリストが更新されても問題ない場合に有効な実装です（仮にこのような処理を ArrayList を使っている場合に行うと、Iterator を使っているスレッド側で ConcurrentModificationException が発生します）。
add や remove 操作を頻繁に行うような実装では、非常に重くなってしまうことに注意してください。
同様に Set バージョンとしては、CopyOnWriteArraySet があります。

例えば、CopyOnWriteArrayList#add() メソッドは以下のように Arrays.copyOf() でコピーを作成していることが分かります。

```java
public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}
```

マルチスレッド処理に対応したコレクションクラス
----

Java 1.5 (Tiger) の java.util.concurrent パッケージには、並列処理に対応したコレクションクラスが複数追加されています。これらは、書き込み、読み出し時の同期が必要ありませんが、それぞれロックに関する特性があります。

| タイプ | クラス名 | 特徴 |
| ------ | -------- | ---- |
| `Map<K,V>` | `ConcurrentHashMap<K,V>` | 読み出しは複数スレッドから同時に行える。内部のハッシュテーブルがフラグメント化されて管理されており、書き込み時も別のフラグメントに対する書き込みであれば、複数のスレッドからロックせずに書き込みが行われる（読み書きが速いということ）。 |
| `List<E>`, `RandomAccess` | `CopyOnWriteArrayList<E>` | 要素のイテレーション中でも、別スレッドからの書き込みが許される（書き込み時に新しいリストが内部で生成されるので、イテレーション中のスレッドは、1 つ前のバージョンのスナップショットにアクセスできることが保証される）。書き込み回数に比べ、読み込み回数が非常に多い場合に有効。 |
| `Set<E>` | `CopyOnWriteArraySet<E>` | `CopyOnWriteArrayList` の `Set` バージョン。 |

BlockingQueue インタフェースを実装したクラスを用いると、その Queue オブジェクトを介して、スレッドからスレッドへデータを渡すことができます。送信側のスレッドでキューに put() し、受信側のスレッドでキューから take() します。これも、実装クラスごとに、ロックの特性などが異なります。

| タイプ | クラス名 | 特徴 |
| ------ | -------- | ---- |
| BlockingQueue | `LinkedBlockingQueue` | リンクドリストベースの BlockingQueue 実装。キューは動的に拡張される。 |
| BlockingQueue | `ArrayBlockingQueue` | 配列ベースの BlockingQueue 実装。キューの初期サイズを指定する必要あり。キューが一杯のときに put() しようとすると、別のスレッドが take() するまでブロッキングする。 |
| BlockingQueue | `PriorityBlockingQueue` | java.util.PriorityQueue と同様に、take() 時に最小の要素を取得できる。コンストラクタで Comparator オブジェクトを指定可能。 |
| BlockingQueue | `DelayQueue<E extends Delayed>` | Delayed インタフェースを実装する要素を格納できるキュー。ある遅延時間を経過した後でなければ take() でその要素を取り出せない。 |
| BlockingQueue | `SynchronousQueue` | サイズ 0 のキュー。put() 呼び出しは take() までブロックされ、take() 呼び出しは put() 呼び出しまでブロックされる。書き込みと読み出しが完全に同期されるということ。 |

参考: [BlockingQueue を使ってスレッド間の通信を行う](../thread/blocking-queue.html)

