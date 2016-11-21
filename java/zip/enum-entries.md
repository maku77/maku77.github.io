---
title: Zip ファイル内の要素（ファイルとディレクトリ）を列挙する
created: 2016-11-21
---

ZipFile クラスを使って Zip ファイルを読み込む
----

`ZipFile` クラスを使用すると、Zip ファイル内の要素をひとつずつ列挙することができます。
列挙中の要素は、`ZipEntry` オブジェクトとして参照することができます。


#### "sample.zip" という Zip ファイルの要素を列挙する

```java
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Main {
    public static void main(String... args) {
        showZipEntries("sample.zip");
    }

    private static void showZipEntries(String filename) {
        try (ZipFile zip = new ZipFile(filename)) {
            showZipEntries(zip);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static void showZipEntries(ZipFile zip) {
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            System.out.println(entry);
        }
    }
}
```

上記のように、`ZipEntry` オブジェクトをそのまま `System.out.println()` に渡すと、その要素の名前（ファイル名 or ディレクトリ名）を出力します（`ZipEntry#getName()` で得られる名前と同様です）。

#### 実行結果

```
$ java Main
dir1/
dir1/dir2/
dir1/dir2/file5.txt
dir1/dir2/file6.txt
dir1/file4.txt
file1.txt
file2.txt
file3.txt
```

Java 1.8 では Stream でループ処理可能
----

Java 1.8 以降は、`Stream<ZipFile>` を返す `ZipFile#stream()` メソッドを使用すると、ループ処理をよりシンプルに記述することができます。

```java
zipFile.stream().forEach(entry -> {
    System.out.println(entry.getName());
});
```

ディレクトリ要素を列挙の対象外にするフィルタ処理も簡潔に記述できます。

```java
zipFile.stream().filter(x -> !x.isDirectory()).forEach(entry -> {
    System.out.println(entry.getName());
});
```

