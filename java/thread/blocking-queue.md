---
title: "BlockingQueue を使ってスレッド間の通信を行う"
date: "2014-06-29"
---

参考: [コレクションクラスのまとめ](../collection/summary.html)

`java.util.concurrent.BlockingQueue` インタフェースを実装したオブジェクトを使うと、スレッド間でデータをやり取りできます。
キューにデータを入れる側で `BlockingQueue#put(E)` を呼び出し、データを取り出す側で `BlockingQueue#take()` を呼び出すだけです。

```
Thread1 ---(put)---> BlockingQueue ---(take)---> Thread2
```

`BlockingQueue` の実装にはいくつかありますが（[リンク](../collection/summary.html)参照）、ここではリンクドリストベースの実装である `LinkedBlockingQueue` を使っています。
下記の例では、`SenderTask` のスレッドが、キューに `Date` オブジェクトを格納し続け、複数の `ReceiverTask` のスレッドがそのオブジェクトを順番に取り出しています。

#### Main.java

```java
package com.example;

import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

// BlockingQueue へメッセージを格納し続けるタスク
class SenderTask implements Runnable {
    private final String name;
    private final BlockingQueue<Date> queue;
    private final PrintStream out;
    public SenderTask(String name, BlockingQueue<Date> queue, PrintStream out) {
        this.name = name;
        this.queue = queue;
        this.out = out;
    }
    @Override
    public void run() {
        try {
            while (true) {
                queue.put(new Date());
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            out.printf("%s interrupted: %s", name, e.getMessage());
        }
    }
}

// BlockingQueue からメッセージを取得し続けるタスク
class ReceiverTask implements Runnable {
    private final String name;
    private final BlockingQueue<Date> queue;
    private final PrintStream out;
    public ReceiverTask(String name, BlockingQueue<Date> queue, PrintStream out) {
        this.name = name;
        this.queue = queue;
        this.out = out;
    }
    @Override
    public void run() {
        try {
            while (true) {
                process(queue.take());
            }
        } catch (InterruptedException e) {
            out.printf("%s interrupted: %s", name, e.getMessage());
        }
    }
    private void process(Date date) {
        out.printf("%s: %tc\n", name, date);
    }
}

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Date> queue = new LinkedBlockingQueue<>(5);

        // 送信スレッドと受信スレッドを開始
        Thread sender = new Thread(new SenderTask("Sender", queue, System.out));
        new Thread(new ReceiverTask("Recv1", queue, System.out)).start();
        new Thread(new ReceiverTask("Recv2", queue, System.out)).start();
        new Thread(new ReceiverTask("Recv3", queue, System.out)).start();
        sender.start();

        // 動かし続ける
        try {
            sender.join();
        } catch (InterruptedException ignore) {}
    }
}
```

#### 実行結果

```
Recv2: Mon Jun 30 15:48:43 JST 2014
Recv1: Mon Jun 30 15:48:44 JST 2014
Recv3: Mon Jun 30 15:48:45 JST 2014
Recv2: Mon Jun 30 15:48:46 JST 2014
Recv1: Mon Jun 30 15:48:47 JST 2014
Recv3: Mon Jun 30 15:48:48 JST 2014
...
```

受信側のタスクは 3 つ並行して動作していますが、`BlockingQueue` から順番にデータを取り出して処理していることが分かります。

