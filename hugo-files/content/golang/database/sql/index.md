---
title: "Golang で RDB（リレーショナルデーターベース）を扱う (database/sql)"
linkTitle: "RDB（リレーショナルデーターベース）を扱う (database/sql)"
url: "p/kgzfwdt/"
date: "2022-09-17"
tags: ["Golang", "データベース"]
---

Golang の [database/sql パッケージ](https://pkg.go.dev/database/sql) を使用すると、Postgres、MariaDB (MySQL)、SQLite といった RDB 系のデータベースを共通のインタフェースで操作することができます。
`database/sql` を使ってコーディングしておけば、将来的な RDBMS の乗り換えが容易になります。


ドライバーのインストール
----

`database/sql` はデータベース操作用の抽象化レイヤーを提供するだけなので、実際にデータベースに接続するには、それぞれのデータベースごとのドライバーが必要です。

{{< image src="img-001.drawio.svg" >}}

ドライバーは [SQLDrivers の一覧ページ](https://github.com/golang/go/wiki/SQLDrivers) から好きなものを選択します。
例えば、mattn 氏の SQLite 用ドライバーを使う場合は、次のように `go.mod` の依存関係を更新し、

```console
$ go get github.com/mattn/go-sqlite3
```

Go プログラム内で次のようにインポートしておきます（先にインポート文を書いてから `go get .` とする方法もあります）。

{{< code lang="go" hl_lines="3" >}}
import (
	"database/sql"
	_ "github.com/mattn/go-sqlite3"
)
{{< /code >}}

{{% note title="ブランク・インポートが必要" %}}
アプリケーションのコードの中でドライバーが提供する関数を直接参照しない場合は、上記のようにアンダースコアを使ったブランク・インポートを行い、`go mod tidy` 時にこの行が削除されないようにしておく必要があります。
インポート行が削除されてしまうと、`database/sql` パッケージがドライバーを見つけられれず、`unknown driver "sqlite3" (forgotten import?)` といったエラーが発生します。
{{% /note %}}


データベースへの接続 (sql.Open, DB.Ping)
----

データベースに接続（ドライバーをオープン）するには、[sql.Open 関数](https://pkg.go.dev/database/sql#Open) を使用します。

```go
func Open(driverName, dataSourceName string) (*sql.DB, error)
```

第 1 引数には使用するドライバーの名前（例: `mysql`、`sqlite3`）、第 2 引数にはドライバーごとの接続文字列 (DSN: data source name) を指定します。
SQLite3 の場合は、接続文字列はデータベースファイル名なので、とてもシンプルです。

```go
db, err := sql.Open("sqlite3", "./books.db")
```

`sql.Open()` が返す __`*sql.DB`__ インスタンスを使って、データベースの各種操作を行うことになります。

```go
package main

import (
	"database/sql"
	"log"

	_ "github.com/mattn/go-sqlite3"
)

func main() {
	// データベースをオープン
	db, err := sql.Open("sqlite3", "./books.db")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	// ... データベースを操作 ...
}
```

SQLite の場合は接続文字列はシンプル（ファイル名のみ）ですが、MySQL や Postgres などでは複雑な接続文字列を指定する必要があります。
そのため、データベースドライバーによっては、接続文字列を構築するためのユーティリティ関数が提供されていることがあります。

{{< code lang="go" title="MySQL 用の接続文字列を構築する" >}}
// Specify connection properties.
cfg := mysql.Config{
    User:   os.Getenv("DB_USER"),
    Passwd: os.Getenv("DB_PASS"),
    Net:    "tcp",
    Addr:   "127.0.0.1:3306",
    DBName: "sample",
}

// Get a database handle.
db, err = sql.Open("mysql", cfg.FormatDSN())
{{< /code >}}

データベースのオープンに成功したら、__`(*sql.DB) Ping`__ メソッドを実行することで、実際にデーターベースが操作可能な状態になっているかを確認できます（実際のアプリで `Ping` メソッドを呼び出す必要はありません）。

```go
func checkIfDatabaseIsReady(db *sql.DB) {
	if err := db.Ping(); err != nil {
		log.Fatal(err)
	}
	log.Println("Database is ready")
}
```


CRUD 操作 (DB.Query, DB.Exec)
----

データベースからレコードを取得するには __`(*sql.DB) Query`__ 系メソッド、その他の更新操作には __`(*sql.DB) Exec`__ 系メソッドを使用します。

- レコードの取得 (SELECT)
  - `func (db *DB) Query(query string, args ...any) (*Rows, error)`
  - `func (db *DB) QueryContext(ctx context.Context, query string, args ...any) (*Rows, error)`
  - `func (db *DB) QueryRow(query string, args ...any) *Row`
  - `func (db *DB) QueryRowContext(ctx context.Context, query string, args ...any) *Row`
- 更新操作 (CREATE TABLE, ALTER TABLE, DROP TABLE, INSERT, UDPATE, DELETE)
  - `func (db *DB) Exec(query string, args ...any) (Result, error)`
  - `func (db *DB) ExecContext(ctx context.Context, query string, args ...any) (Result, error)`

### CREATE TABLE

次の例では、簡単な `books` テーブルを作成しています。
更新操作なので、__`(*sql.DB) Exec`__ / __`(*sql.DB) ExecContext`__ メソッドを使用します。

```go
func createBooksTable(db *sql.DB) {
	_, err := db.Exec(`CREATE TABLE IF NOT EXISTS books(
		id TEXT PRIMARY KEY NOT NULL,
		title TEXT NOT NULL,
		price INTEGER NOT NULL)`)
	if err != nil {
		log.Fatal(err)
	}
}
```

### INSERT

テーブルにレコードを追加するときも、__`(*sql.DB) Exec`__ / __`(*sql.DB) ExecContext`__ メソッドを使用します。

```go
func insertSampleRecord(db *sql.DB) {
	// INSERT の実行
	query := `INSERT INTO books (id, title, price) VALUES (?, ?, ?)`
	result, err := db.Exec(query, "id-1", "Title 1", 1000)
	if err != nil {
		log.Fatal(err)
	}

	// 挿入されたレコード数を取得
	count, err := result.RowsAffected()
	if err != nil {
		log.Fatal(err)
	}
	log.Printf("%d rows inserted", count)
}
```

同様のクエリで複数のレコードを登録する場合は、__`(*sql.DB) Prepare`__ メソッドで、Prepared statement (`sql.Stmt`) を生成すると便利です。

```go
func insertSampleRecords(db *sql.DB) {
	// Prepared statement を作成する
	stmt, err := db.Prepare("INSERT INTO books (id, title, price) VALUES (?, ?, ?)")
	if err != nil {
		log.Fatal(err)
	}
	defer stmt.Close()

	// 複数のレコードを追加する
	for i := 1; i <= 3; i++ {
		id := fmt.Sprintf("id-%d", i)
		title := fmt.Sprintf("Title %d", i)
		price := 1000 * i
		_, err := stmt.Exec(id, title, price)
		if err != nil {
			log.Fatal(err)
		}
	}
}
```

### SELECT

`SELECT` でレコードを取得するときは、__`(*sql.DB) Query`__ / __`(*sql.DB) QueryContext`__ メソッドを使用します。
戻り値の __`*sql.Rows`__ で取得したレコードを参照できます。

```go
func queryBooks(db *sql.DB) {
	// クエリ実行
	rows, err := db.Query("SELECT id, title, price FROM books")
	if err != nil {
		log.Fatal(err)
	}
	defer rows.Close()

	// レコードを 1 件ずつ取り出す
	for rows.Next() {
		var id string
		var title string
		var price int64
		if err := rows.Scan(&id, &title, &price); err != nil {
			log.Fatal(err)
		}
		log.Printf("%s, %s, %d\n", id, title, price)
	}
	if err := rows.Err(); err != nil {
		log.Fatal(err)
	}
}
```

取得するレコードが 1 件だけだとわかっている場合は、効率のよい __`(*sql.DB) QueryRow`__ / __`(*sql.DB) QueryRowContext`__ メソッドを使用します。
これらのメソッドは、1 件のレコードを参照するための __`*sql.Row`__ を返します。
このメソッドは必ず成功し、エラーが発生することはありません（`Scan` 時のエラーを確認するだけで十分だからです）。

```go
func queryBook(db *sql.DB) {
	bookId := "id-1"
	row := db.QueryRow("SELECT title, price FROM books WHERE id = ?", bookId)
	var title string
	var price int64
	if err := row.Scan(&title, &price); err != nil {
		if err == sql.ErrNoRows {
			log.Fatalf("Book not found (id=%s)\n", bookId)
		}
		log.Fatal(err)
	}
	log.Printf("%s, %d", title, price)
}
```

WHERE 条件に一致するレコードが複数ある場合は、`(*sql.DB) QueryRow` メソッドは最初のレコードのみを返します。
条件に一致するレコードが見つからない場合は、`(*sql.Row) Scan` を呼び出したときに `sql.ErrNoRows` が返されます。


トランザクション処理 (DB.BeginTx)
----

データベースのトランザクションは、複数の更新要求をアトミックに処理するための仕組みです。
複数の更新処理を一括でコミットするか、すべてなかったことにすることができます（ロールバック）。
__`(*sql.DB) BeginTx`__ メソッドを使うとトランザクション処理を実行するための __`*sqlTx`__ インスタンスを取得できます。

```go
func (*sql.DB).BeginTx(ctx context.Context, opts *sql.TxOptions) (*sql.Tx, error)
```

レコードの更新を行うときに、`(*sql.DB) Exec` の代わりに __`(*sql.Tx) Exec`__ を呼び出すことで、その更新処理は 1 つのトランザクション内での処理とみなされます。

```go
func updateRecordsWithTransaction(db *sql.DB) error {
	tx, err := db.BeginTx(context.Background(), nil)
	if err != nil {
		return err
	}
	defer tx.Rollback() // コミットしなかった場合は自動でロールバック

	// 関連する更新処理をトランザクション内で実行する
	if _, err := tx.Exec("...省略..."); err != nil {
		return err
	}
	if _, err := tx.Exec("...省略..."); err != nil {
		return err
	}

	// トランザクション処理をコミット
	if err := tx.Commit(); err != nil {
		return err
	}

	return nil
}
```

一連の処理が終わったあとに、__`(*sql.Tx) Commit`__ メソッドか、__`(*sql.Tx) Rollback`__ メソッドを呼び出す必要があります。
上記のようにトランザクション開始直後に `Rollback` を `defer` 呼び出ししておけば、関数内で `Commit` が呼ばれなかったときに自動でロールバックしてくれます（`Commit` が呼ばれた場合は、`Rollback` は実行されません）。


NULL 値を含むレコードを扱う
----

テーブルスキーマで `NOT NULL` 宣言されていないカラムには、NULL 値が格納されている可能性があります。
NULL 値を含むレコードを `Scan` するときに、バッファーとして `string` や `int64` などのプリミティブな変数を使用するとエラーが発生します。

> Scan error on column index 2, name "price": converting NULL to int64 is unsupported

NULL 値を含む可能性があるレコードを `Scan` する場合は、次のような NULL 値を扱える専用の型を使用します。

- [sql.NullBool](https://pkg.go.dev/database/sql#NullBool)
- [sql.NullFloat64](https://pkg.go.dev/database/sql#NullFloat64)
- [sql.NullInt32](https://pkg.go.dev/database/sql#NullInt32)
- [sql.NullInt64](https://pkg.go.dev/database/sql#NullInt64)
- [sql.NullString](https://pkg.go.dev/database/sql#NullString)
- [sql.NullTime](https://pkg.go.dev/database/sql#NullTime)

これらの型は、値が NULL でないことを調べるための __`Valid`__ プロパティを持っています。
`Valid` が `true` の場合は、各カラムの値を安全に参照できます。

```go
func queryBook(db *sql.DB) error {
	id := "id-1"
	row := db.QueryRow("SELECT title, price FROM books WHERE id = ?", id)

	// NULL 値を考慮した Scan
	var title sql.NullString
	var price sql.NullInt64
	if err := row.Scan(&title, &price); err != nil {
		return err
	}

	// Nullable な title カラムを参照する
	if title.Valid {
		log.Printf("title = %s\n", title.String)
	} else {
		log.Println("title is NULL")
	}

	// Nullable な price カラムを参照する
	if price.Valid {
		log.Printf("price = %d\n", price.Int64)
	} else {
		log.Println("price is NULL")
	}

	return nil
}
```

