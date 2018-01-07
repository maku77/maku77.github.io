---
title: Gradle でユニットテストの結果をコンソールに詳しく表示する
date: "2015-09-24"
---

Gradle でのユニットテストの実行結果が失敗の場合、デフォルトでは下記のようなそっけない表示しかしてくれません。

```
$ gradle test
...
com.example.LargeTest > testAdd FAILED
    java.lang.AssertionError at SampleTest.java:9

2 tests completed, 1 failed
```

build/reports/tests 以下に生成されるテストレポートを見れば、その内容を確認できますが、コンソール上でもう少し詳しい結果を確認したい場合は、-i オプションを指定してログレベルを INFO に設定して test タスクを実行します。

```
$ gradle test -i
...
com.example.SampleTest > testAdd FAILED
    java.lang.AssertionError: expected:<4> but was:<3>
        at org.junit.Assert.fail(Assert.java:88)
        at org.junit.Assert.failNotEquals(Assert.java:834)
        at org.junit.Assert.assertEquals(Assert.java:645)
        at org.junit.Assert.assertEquals(Assert.java:631)
        at com.example.SampleTest.testAdd(SampleTest.java:9)

1 test completed, 1 failed
...
```

ただし、INFO レベルでログを出力すると、余計な出力が多く出てしまって見づらくなります。
build.gradle スクリプトの中で、下記のように exceptionFormat を指定しておくと、上記のような例外部分だけを詳しく表示してくれるようになります（この場合 -i オプションを付けて test タスクを実行する必要はありません）。

```groovy
test {
    testLogging {
        exceptionFormat 'full'
    }
}
```

デフォルトではメソッドごとのテスト経過は表示されませんが、テスト経過をコンソール上で詳しく確認したい場合は、下記のように **events** 設定を行います。

```groovy
test {
    testLogging {
        events 'started', 'skipped', 'passed', 'failed'
    }
}
```

この状態で test タスクを実行すると、それぞれのメソッドごとにテストの経過を確認できるようになります。FAILED や PASSED などのイベント名は実際には分かりやすく色付きで表示されます。

```
$ gradle test
...
com.example.LargeTest > testAdd STARTED
com.example.LargeTest > testAdd FAILED
    java.lang.AssertionError at LargeTest.java:9
com.example.SampleTest > testAdd STARTED
com.example.SampleTest > testAdd PASSED

2 tests completed, 1 failed
```

