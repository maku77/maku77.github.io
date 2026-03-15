---
title: "Javaメモ: MANIFEST.MF ファイルの内容を取得する"
url: "p/mbmxnx9/"
date: "2011-05-05"
tags: ["java"]
aliases: ["/java/file/manifest.html"]
---

`java.util.jar.JarFile` クラスは、JAR ファイル内に含まれている `MANIFEST.MF` ファイルの情報を読み込む機能を備えています。
下記のサンプルコードでは、`sample.jar` ファイル内の `MANIFEST.MF` のメインアトリビュート情報を読み込んで出力します。

{{< code title="MANIFEST.MF（sample.jar 内の META-INF/MANIFEST.MF）" >}}
Manifest-Version: 1.0
Created-By: 1.6.0_24 (Apple Inc.)
{{< /code >}}

{{< code lang="java" title="サンプルコード (Main.java)" >}}
import java.io.IOException;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class Main {
    public static void main(String[] args) {
        try {
            JarFile jar = new JarFile("sample.jar");
            Manifest manifest = jar.getManifest();
            Attributes attrs = manifest.getMainAttributes();
            for (Entry<Object, Object> e : attrs.entrySet()) {
                System.out.println(e.getKey() + " ==> " + e.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
{{< /code >}}

{{< code title="実行結果" >}}
Manifest-Version ==> 1.0
Created-By ==> 1.6.0_24 (Apple Inc.)
{{< /code >}}

