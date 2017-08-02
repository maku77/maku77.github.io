---
title: リレーショナルデータベース (RDB) でツリー構造や階層構造を表現する
created: 2010-08-02
---

フラットなレコードを格納する RDB でも、自分自身のノードの ID と、親ノードの ID を格納すれば、擬似的に階層構造を表現することができます。
ツリー構造を作りたい場合は、自分自身の親は唯一でなければならないので、自分自身のノードを表す `id` フィールドには `UNIQUE` 制約を付けます。

ルートノードを表現するには、例えば、親ノードを表す `parent_id` に `NULL` を格納するというルールにします。

~~~ sql
CREATE TABLE hierarchy_tbl(
    id TEXT UNIQUE NOT NULL,
    parent_id TEXT
);
~~~

上記のテーブルで、例えば以下のようなツリー構造を表現したい場合は、

~~~
root_node
 +- node1
 |   +- node1-1
 |   +- node1-2
 +- node2
     +- node2-1
     +- node2-2
~~~

以下のようにデータを追加します。

~~~ sql
INSERT INTO hierarchy_tbl(id, parent_id) VALUES('root_node', NULL);
INSERT INTO hierarchy_tbl(id, parent_id) VALUES('node1', 'root_node');
INSERT INTO hierarchy_tbl(id, parent_id) VALUES('node2', 'root_node');
INSERT INTO hierarchy_tbl(id, parent_id) VALUES('node1-1', 'node1');
INSERT INTO hierarchy_tbl(id, parent_id) VALUES('node1-2', 'node1');
INSERT INTO hierarchy_tbl(id, parent_id) VALUES('node2-1', 'node2');
INSERT INTO hierarchy_tbl(id, parent_id) VALUES('node2-2', 'node2');
~~~

ツリー構造では、各ノードに親が１つ存在するので、レコードの数はノードの数に等しくなります。

あるのノードの親ノードや、子ノードを調べたいときは、以下のようにすれば OK です。

#### 例: node1-2 の親ノードを調べる。

~~~ sql
SELECT parent_id FROM hierarchy_tbl WHERE id='node1-2';
~~~

#### 例: root_node の子ノードを調べる。

~~~ sql
SELECT id FROM hierarchy_tbl WHERE parent_id='root_node';
~~~

