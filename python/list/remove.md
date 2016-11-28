---
title: リストから特定の値を持つ要素を削除する (list.remove)]
created: 2014-04-30
---

リストから特定の値を１つ削除する
----

`list#remove(value)` は、リストから `value` に一致する最初の要素を削除します（元のリスト自体が変更されます）。

```python
a = [10, 20, 30, 20, 10]
a.remove(20)  #=> [10, 30, 20, 10]
```

リスト内に指定した値が存在しない場合は `ValueError` が発生するため、削除する要素の存在が保証されていない場合は、下記のようにエラー処理を記述しておく必要があります。

```python
try:
    a.remove(20)
except ValueError:
    pass
```

リストから特定の値をすべて削除する
----

リストから特定の値を全て削除するには、例えば以下のようにします（効率悪そう ^^;）。

```python
def remove_specified_values(arr, value):
    while value in arr:
        arr.remove(value)

a = [10, 20, 30, 20, 10]
remove_specified_values(a, 20)
print(a)  #=> [10, 30, 10]
```

あるいは、以下のようにリスト内包表記を使用して、新しいリストを作成してしまうこともできます。

```python
a = [10, 20, 30, 20, 10]
b = [x for x in a if x != 20]
print(b)  #=> [10, 30, 10]
```

