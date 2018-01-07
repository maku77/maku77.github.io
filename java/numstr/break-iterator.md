---
title: 文字列を単語単位で分割する (BreakIterator)
date: "2014-11-21"
---

`java.text.BreakIterator` クラスを使用すると、指定したテキストを単語単位（あるいは文単位など）で分割することができます。
例えば、

~~~
"テキスト境界を作成し使用します。"
~~~

のようなテキストを次のように分割できます。

~~~
"テキスト"
"境界"
"を"
"作成"
"し"
"使用"
"します"
"。"
~~~

漢字やひらがな、カタカナ、句読点、のような文字の種類ごとに分割されるイメージですね。
下記は `BreakIterator` クラスを使用して単語分割を行うサンプルです。

#### BreakIteratorSample.java

~~~ java
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class BreakIteratorSample {
    private static List<String> breakIntoWords(String text) {
        List<String> words = new ArrayList<>();
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(text);
        for (int start = boundary.first(), end = boundary.next();
                end != BreakIterator.DONE;
                start = end, end = boundary.next()) {
            words.add(text.substring(start, end));
        }
        return words;
    }

    public static void main(String[] args) {
        List<String> words = breakIntoWords("テキスト境界を作成し使用します。");
        System.out.println(words);
    }
}
~~~

