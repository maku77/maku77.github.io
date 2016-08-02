---
title: "== と === による文字列比較の違い"
created: 2012-10-22
---

JavaScript における文字列の比較には、`==` あるいは `===` (strict equal) 演算子が使用できます。

```javascript
var str1 = 'ABC';
var str2 = 'AB' + 'C';
print(str1 == str2);   //=> true
print(str1 === str2);  //=> true
```

文字列値 (string) 同士の比較であれば、上記のように差は出ませんが、`==` を使用した比較は、型変換を行ってから比較を行うため、異なる型同士の比較をした場合に差が出てきます。

```javascript
var num = 100;
var str = '100';
print(num == str);   //=> true （型変換してから比較するため一致）
print(num === str);  //=> false（型が異なるので必ず false）
print(num != str);   //=> false
print(num !== str);  //=> true
```

型変換が伴うと、分かりにくい結果になりやすいので、できる限り `===` (strict equal) による比較を行うことをお勧めします。

```javascript
print(123 == 'ABC123');   //=> false
print(123 == '000123');   //=> true !?
print(123 === '000123');  //=> false
print(123 === 'ABC123');  //=> false
```

