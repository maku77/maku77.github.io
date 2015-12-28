---
title: ヘルプを表示する
created: 2014-12-09
---

ヘルプドキュメントのトップページを表示する
----

```r
help.start()
```

関数のヘルプを表示する
----

```r
help(function)
?function
```

指定した関数のシグネチャを表示する
----

```r
args(function)
```

指定した関数に使用例がある場合、それを実行する
----

```r
example(function)  # 例: example(mean)
```

構文のヘルプを表示する
----

```r
?"for"
help("for")
```

キーワードでヘルプを検索する
----

```r
??keyword
help.search("keyword")
```

Web 上で検索する
----

```r
RSiteSearch("keyword")
```
