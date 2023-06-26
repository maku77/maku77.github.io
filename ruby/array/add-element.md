---
title: "Add element(要素) to array(配列)"
date: "2011-10-10"
---

Insert(挿入) element to end of array.
----

To insert element to end of array, use `push` method.

```ruby
a = [1, 2, 3]
a.push('A')
a  # => [1, 2, 3, "A"]

a.push('B', 'C')
a  # => [1, 2, 3, "A", "B", "C"]
```

It likes C++, use `<<` to insert element to end of array.

```ruby
a = [1, 2, 3]
a << 'A'
a  # => [1, 2, 3, "A"]

a << 'B' << 'C'
a  # => [1, 2, 3, "A", "B", "C"]
```

If need to insert another array to end of this, use operator `+=`.
An array must be specified on the right side(右辺).

```ruby
a = [1, 2, 3]
a += ['A', 'B', 'C']
a  # => [1, 2, 3, "A", "B", "C"]
```


Insert element to head (先頭) of array
----

To insert element to head of array, use one of the following(いずれか) bellow

* `arr.unshift(...)`
* `arr.insert(0, ...)`
* `arr[0, 0] = ...`

If you want to add multiple values at once, simply separate them with commas
(Note that if you pass an array, one array will be added as an element).

```ruby
a = [1, 2, 3]
a.unshift('A')
a  # => ["A", 1, 2, 3]

a.unshift('B', 'C')
a  # => ["B", "C", "A", 1, 2, 3]

a.unshift(['D', 'E'])
a  # => [["D", "E"], "B", "C", "A", 1, 2, 3]
```


Insert element into array by specify(指定) index
----

To add an element to an array by indexing where to insert the element, use one of the following:

* `arr.insert(index, ...)`
* `arr[index, 0] = ...`

```ruby
a = [1, 2, 3]
a.insert(2, 'A')
a  # => [1, 2, "A", 3]

a.insert(0, 'B')
a  # => ["B", 1, 2, "A", 3]

a.insert(-1, 'C')  # ※
a  # => ["B", 1, 2, "A", 3, "C"]

a.insert(10, 'D')
a  # => ["B", 1, 2, "A", 3, "C", nil, nil, nil, nil, "D"]
```

If specify a negative index for `[]` operator,
than when you specify a negative index with the `insert` method,
the position seems to shift forward by one.

```ruby
a = [1, 2, 3]
a[2, 0] = 'A'
a  # => [1, 2, "A", 3]

a[0, 0] = 'B'
a  # => ["B", 1, 2, "A", 3]

a[-1, 0] = 'C'
a  # => ["B", 1, 2, "A", "C", 3]

a[10, 0] = 'D'
a  # => ["B", 1, 2, "A", "C", 3, nil, nil, nil, nil, "D"]
```

