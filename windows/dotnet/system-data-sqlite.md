---
title: .NET - System.Data.SQLite で SQLite データベースを使用する
date: "2009-03-01"
---

ダウンロード＆ DLL の参照設定
----

[System.Data.SQLite](https://system.data.sqlite.org/) は、SQLite 用の ADO.NET 2.0 provider です。
ADO.NET のインタフェースを利用して SQLite データベースを扱うことができます。

上記のサイトから、

- `SQLite-1.0.60.0-binaries.zip`

などをダウンロードし、展開してできたファイルのうち、

- `bin/System.Data.SQLite.dll`

が実行時に必要な DLL です。
プロジェクトの「参照を追加」から、上記の DLL ファイルを追加すれば使用準備完了です。

`bin/ManagedOnly` ディレクトリ以下に入っている同名の DLL ファイルを使用する場合は、SQLite 本家の DLL が別途必要になります。


はじめての System.Data.SQLite
----

ADO.NET のインタフェースを使用して、SQL クエリの CREATE TABLE、INSERT、SELECT などを実行してみます。

ここでは、ADO.NET のクライアントプログラムが System.Data.SQLite に依存してしまわないように、AbstractFactory パターンを使って ADO.NET 関連オブジェクトの生成部分を `DbFactory` というファクトリインタフェースを使って分離しています。

#### DbFactory.cs

~~~ csharp
using System.Data.Common;csharp

/// <summary>
/// ADO.NET 関連のオブジェクトを作成するためのファクトリインタフェースです。
/// </summary>
public interface DbFactory {
    DbConnection CreateDbConnection();
    DbParameter CreateDbParameter();
}
~~~

#### SQLiteDbFactory.cs

~~~ csharp
using System.Data.Common;
using System.Data.SQLite;

/// <summary>
/// System.Data.SQLite を使用して DbFactory インタフェースを実装します。
/// SQLite に依存する部分はこのクラスにまとめられます。
/// </summary>
public class SQLiteDbFactory : DbFactory {
    private const string DATABASE_FILENAME = "sample.db";

    public DbConnection CreateDbConnection() {
        string conStr = string.Format("Data Source={0}", DATABASE_FILENAME);
        return new SQLiteConnection(conStr);
    }

    public DbParameter CreateDbParameter() {
        return new SQLiteParameter();
    }
}
~~~

#### MainClass.cs

~~~ csharp
using System;
using System.Data.Common;

public class MainClass {
    private static DbFactory m_dbFactory = new SQLiteDbFactory();

    // test_tbl テーブルを作成する
    public void CreateTable(DbConnection con) {
        using (DbCommand cmd = con.CreateCommand()) {
            cmd.CommandText =
                "CREATE TABLE IF NOT EXISTS test_tbl(id INTEGER PRIMARY KEY, title TEXT)";
            cmd.ExecuteNonQuery();
        }
    }

    // test_tbl テーブルにサンプルデータを追加
    public void AddRecord(DbConnection con) {
        using (DbTransaction tran = con.BeginTransaction()) {
            using (DbCommand cmd = con.CreateCommand()) {
                // DbParameter を使って効率的に SQL クエリを生成
                DbParameter param = m_dbFactory.CreateDbParameter();
                cmd.CommandText = "INSERT INTO test_tbl (title) VALUES(?)";
                cmd.Parameters.Add(param);
                for (int i = 0; i < 10; ++i) {
                    param.Value = "Title " + i;
                    cmd.ExecuteNonQuery();
                }
            }
            tran.Commit();
        }
    }

    // test_tbl 内のデータを表示する
    public void ShowRecord(DbConnection con) {
        using (DbCommand cmd = con.CreateCommand()) {
            cmd.CommandText = "SELECT * FROM test_tbl";
            using (DbDataReader reader = cmd.ExecuteReader()) {
                while (reader.Read()) {
                    Console.WriteLine(String.Format("id={0}, title={1}", reader[0], reader[1]));
                }
            }
        }
    }

    // テスト
    static void Main(string[] args) {
        MainClass mc = new MainClass();
        using (DbConnection con = m_dbFactory.CreateDbConnection()) {
            con.Open();
            mc.CreateTable(con);
            mc.AddRecord(con);
            mc.ShowRecord(con);
        }
    }
}
~~~

#### 実行結果

~~~
id=1, title=Title 0
id=2, title=Title 1
id=3, title=Title 2
id=4, title=Title 3
id=5, title=Title 4
id=6, title=Title 5
id=7, title=Title 6
id=8, title=Title 7
id=9, title=Title 8
id=10, title=Title 9
~~~

