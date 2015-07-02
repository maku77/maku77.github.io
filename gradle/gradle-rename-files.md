---
title: "Gradle でファイルをリネームする"
created: 2015-06-30
---

Copy 仕様によりファイルをコピーする際に、rename メソッドを使用してファイル名を変更することができます。

#### 例: 拡張子 .txt を .html にリネームしてコピー

```java
task hello(type: Copy) {
    from 'from'
    into 'to'
    rename(/(.+)\.txt$/, '$1.html')
}
```

拡張子として `.txt` を持たないファイルに関しては、元のファイル名のままコピーされます。
上記では `rename` メソッド呼び出しのパラメータとして置換パターンを指定していますが、クロージャの形で置換処理を記述することもできます。

```java
task hello(type: Copy) {
    from 'from'
    into 'to'
    rename { it - ~/\.txt$/ + '.html' }
}
```

上記の例では、それぞれのファイル名に対して、末尾から `.txt` を削除し、`.html` を付加するという処理を行っています。
末尾に `.txt` がないファイルに対しても、`.html` を付加してしまうことに注意してください。

