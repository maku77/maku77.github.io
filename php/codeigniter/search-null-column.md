---
title: CodeIgniter - ActiveRecord でカラムが NULL のもの、NULL でないものを検索する
date: "2013-01-07"
---

CodeIgniter の Active Record 機能を使用することで、データベース内から、ある条件に一致するレコードを検索することができます。
例えば、下記のようにして値が `NULL` であるものや、`NULL` でないものを検索することできます。

~~~ php
# あるカラムが NULL であるものを検索
$this->db->where('col_name IS NULL', NULL, FALSE);

# あるカラムが NULL でないものを検索
$this->db->where('col_name IS NOT NULL', NULL, FALSE);
~~~

`or_where` メソッドを組み合わせて使用すると、複雑な検索条件を設定することができます。
下記の例では、あるカラムが `NULL` あるいは空文字列であるものを検索しています。

~~~ php
# あるカラムが NULL あるいは空文字列のものを検索
$this->db->where('col_name IS NULL', NULL, FALSE);
$this->db->or_where('col_name', '');
~~~

