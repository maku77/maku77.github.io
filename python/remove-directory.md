---
title: ディレクトリを削除する
created: 2013-05-08
---

`os.rmdir` を使用して、指定したディレクトリを削除することができます。

#### os.rmdir の説明
> rmdir(path, *, dir_fd=None)
>
> Remove a directory.
>
> If dir_fd is not None, it should be a file descriptor open to a directory, and path should be relative; path will then be relative to that directory. dir_fd may not be implemented on your platform. If it is unavailable, using it will raise a NotImplementedError.

下記の例では、カレントディレクトリにある `aaa` ディレクトリを削除しています。

```python
import os

os.rmdir('./aaa')
```

存在しないディレクトリを削除しようとすると、`os.rmdir()` は **FileNotFoundError** を発生させます。

