---
title: Swing - JTextField のテキストを右寄せで表示する
created: 2011-02-06
---

JTextField によるテキスト表示のアライメントを設定するには下記のメソッドを使用します。

~~~
void JTextField#setHorizontalAlignment(int alignment)
~~~

`alignment` パラメータには次のいずれかの値を設定できます。

- `JTextField.LEFT` -- 左寄せ
- `JTextField.CENTER` -- 中央寄せ
- `JTextField.RIGHT` -- 右寄せ
- `JTextField.LEADING` (default) -- 左寄せ（ただし Right to Left な言語では右寄せ）
- `JTextField.TRAILING` -- 右寄せ（ただし Right to Left な言語では左寄せ）

