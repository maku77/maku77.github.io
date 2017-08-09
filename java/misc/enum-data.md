---
title: enum の各項目に任意のデータを関連付ける
created: 2011-07-22
---

Java の列挙型は特殊なクラスのように扱うことができるので、以下のようにメンバ変数を持たせれば、各項目に任意の値を関連付けて持たせることができます。

気を付けることは、

- 列挙項目部分の最後の項目は、セミコロン (`;`) で終わること
- コンストラクタは private にすること

です。

~~~ java
public enum Piece {
    EMPTY(' '),
    WHITE_KING('K'),
    WHITE_QUEEN('Q'),
    WHITE_BISHOP('B'),
    WHITE_KNIGHT('N'),
    WHITE_ROOK('R'),
    WHITE_PAWN('P'),
    BLACK_KING('k'),
    BLACK_QUEEN('q'),
    BLACK_BISHOP('b'),
    BLACK_KNIGHT('n'),
    BLACK_ROOK('r'),
    BLACK_PAWN('p');  // セミコロン (;) で終わること

    private char symbol;

    private Piece(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
~~~

また、列挙型の `toString()` メソッドをオーバーライドすることができるため、列挙型オブジェクトをそのまま出力した場合の表示内容を自由にカスタマイズできます。

~~~ java
@Override
public String toString() {
    return Charactor.toString(symbol);
}
~~~

