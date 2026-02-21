---
title: "SQLメモ: MySQL で各種 SQL コマンドのヘルプを表示する (help)"
url: "p/fdvnytk/"
date: "2012-07-14"
tags: ["sql"]
aliases: /sql/mysql/help.html
---

MySQL に接続後、**`help`** コマンドに続けて SQL 文を入力すると、その SQL 文の説明を表示することができます。

{{< code lang="mysql" title="例: CREATE DATABASE のヘルプを表示" >}}
mysql> help create database
Name: 'CREATE DATABASE'
Description:
Syntax:
CREATE {DATABASE | SCHEMA} [IF NOT EXISTS] db_name
...
{{< /code >}}

`help` コマンドで調べられる項目は、**`help contents`** で確認することができます。

```mysql
mysql> help contents
You asked for help about help category: "Contents"
For more information, type 'help <item>', where <item> is one of the following
categories:
   Account Management
   Administration
   Compound Statements
   Data Definition
   Data Manipulation
   Data Types
   Functions
   Functions and Modifiers for Use with GROUP BY
   Geographic Features
   Language Structure
   Plugins
   Table Maintenance
   Transactions
   User-Defined Functions
   Utility
```

上記の結果から、Account Management カテゴリに関するヘルプも用意されていることがわかるので、さらに以下のようにしてそのヘルプを表示することができます。

```mysql
mysql> help account management
You asked for help about help category: "Account Management"
For more information, type 'help <item>', where <item> is one of the following
topics:
   CREATE USER
   DROP USER
   GRANT
   RENAME USER
   REVOKE
   SET PASSWORD
```
