---
title: "Gradle でユニットテストを並列実行する (test.forkEvery, test.maxParallelForks)"
url: "p/pxkr6wv/"
date: "2015-09-24"
tags: ["gradle"]
aliases: ["/gradle/test-parallel.html"]
---

Gradle によるユニットテスト実行は、デフォルトではシーケンシャルに 1 つずつ行われていきます。
テストクラス数が多くなった場合は、並列実行することによって全体のテストにかかる時間を減らすことができます。

**`test.forkEvery`** と **`test.maxParallelForks`** でテスト実行用のプロセスをどう fork するかを制御することができます。

* **`test.forkEvery`** ... 何個のクラスごとにテストプロセスを fork するか（どのくらい積極的に並列化するか）
* **`test.maxParallelForks`** ... 最大何個のテストプロセスを fork するか（最大並列実行数）

下記の例では、「CPU数−1」個まで同時実行することと、クラス 4 つごとにプロセスを fork することを指定しています。
例えば、クラスが 10 個あれば、3 つのプロセスが作成されます（1 つのプロセスで 4 クラスまで担当するから）。

{{< code lang="groovy" title="build.gradle" >}}
test {
    int cpus = Runtime.runtime.availableProcessors()
    maxParallelForks = cpus > 1 ? cpus - 1 : 1
    forkEvery = 4
}
{{< /code >}}

