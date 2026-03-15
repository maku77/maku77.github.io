---
title: "Javaメモ: Java でランダムな UUID を生成する (java.util.UUID)"
url: "p/uwatzo2/"
date: "2010-08-29"
tags: ["java"]
aliases: ["/java/misc/uuid.html"]
---

**`java.util.UUID`** クラスを使用すると、ユニークな ID を表現するための UUID を生成することができます。
Java 1.6 の `java.util.UUID` クラスでは、UUID version 4 が実装されており、乱数に基づいた UUID が生成されます。

{{< code lang="java" title="Sample.java" >}}
import java.util.UUID;

public class Sample {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
    }
}
{{< /code >}}

{{< code title="実行例" >}}
D:\> java Sample
ede66c43-9b9d-4222-93ed-5f11c96e08e2
{{< /code >}}

ちなみに、Windows API では GUID（UUID version 1 の実装）と呼ばれている実装が使われています。

