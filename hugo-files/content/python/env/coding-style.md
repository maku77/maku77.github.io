---
title: "Python Coding Style"
url: "p/pyk3j2h/"
date: "2007-02-06"
lastmod: "2022-02-17"
tags: ["Python"]
aliases: /python/coding-style.html
---

Coding style of python is, PEP8 and PEP 257.

- [PEP 8 -- Style Guide for Python Code](https://www.python.org/dev/peps/pep-0008/)
- [PEP 257 -- Docstring Conventions](https://www.python.org/dev/peps/pep-0257/)

Trang này tập trung chủ yếu vào các nội dung được đề cập ở trên, tuy nhiên khi áp dụng phong cách lập trình cho dự án Python thực tế, chúng tôi khuyên bạn nên sử dụng các [công cụ định dạng Python như Black](https://maku.blog/p/4oybku6/).
Mặc dù có một số điểm khác biệt so với PEP 8 và PEP 257, Black sẽ bắt buộc áp dụng phong cách hợp lý.

Naming conventions(命名規則)
----

### Uppercase/Lowercase

- Package name: lowercase（例: `mypackage`）
- Class name: uppercase（例: `MyClass`）
- Function hoặc public method: lowercase（例: `my_public_method`）
- Protected methods là lowercase với 1 dấu gạch dưới ở đầu（例: `_my_protected_method`）
- Private method là lowercase với 2 dấu gạch dưới ở đầu（例: `__my_private_method`）
- Constant: all uppercase（例: `MY_CONSTANT`）

Về cấu trúc của Python's name mangling, name bắt đầu bằng hai dấu gạch dưới sẽ không thể truy cập từ bên ngoài lớp hoặc từ một lớp con (You can actually access it with the name `_ClassName__method`).
Với tính năng này, họ khuyến nghị đặt tên private methods với 2 dấu gạch dưới ở đầu.


### About the use of underscores in the global scope(領域)

Với các lớp hoặc hàm được định nghĩa ở cấp độ cao nhất, những thứ không được công khai ra bên ngoài mô-đun (tệp) sẽ có tiền tố là dấu gạch dưới.
Bằng cách này, bạn có thể ngăn chặn việc nhập tự động trong hình thức `from M import * `.

```python
# Function which is used in module
def _parse_timestamp_with_tzinfo(value, tzinfo):
    """Parse timestamp with pluggable tzinfo options."""
    ...省略...

# Class which is used in module
class _RetriesExceededError(Exception):
    """Internal exception used when the number of retries are exceeded."""
    pass
```

Module name (file) and package name (directory) is only downcase.
The module name may include underscores for word separation if necessary, but underscores should not be included in package names (directories).

- Module names may include underscores (OK: `user.py`, `user_info.py`)
- Package names should not include underscores (OK: `game/`, NG: `game_db/`)

Regarding the handling of these underscores, it is described in the section [PEP 0008 (Style Guide for Python Code)](https://www.python.org/dev/peps/pep-0008/#package-and-module-names) :

> Modules should have short, all-lowercase names. Underscores can be used in the module name if it improves readability.
>
> Python packages should also have short, all-lowercase names, although the use of underscores is discouraged.


### Another Naming Convension

- The independent Exception class is created by inheriting from Exception and adding the suffix __`Error`__ to it (for example: `HogeHogeError`).
- The first argument name for instance methods is __`self`__, and for class methods, it is __`cls`__.
- If you want to use an attribute name that conflicts with a reserved word, instead of using an awkward abbreviation, it is recommended to add an underscore as a suffix. However, when it comes to the name `class`, it is preferable to use `cls` instead of `class`_ .


Indent/Space
----

- インデントは __スペース 4 文字__。タブは使用しない。
- Indent is __4 spaces__. Not use tab.
  - If you specify `-t` as a command-line argument in Python, it will display a warning when spaces and tabs are mixed (`-tt` option will raise an error).
- The maximum line length is 79 characters (including line breaks). However, for documentation (docstrings) and comment lines, the maximum length is 72 characters.
- In default parameters, do not include spaces before or after the `=` sign.
  ```python
  def complex(real, imag=0.0):
      return magic(r=real, i=imag)
  ```
- When breaking a long line, the indentation of the next line should align with the opening parenthesis of the previous line. If breaking after an operator, the line should be indented to align with the operator.

  ```python
  if (width == 0 and height == 0 and
      color == 'red' and emphasis == 'strong' or
      highlight > 100):
  ```

- Trong class, các methods nên được phân tách bằng một dòng trống. Định nghĩa lớp và phương thức ở mức đỉnh cần được phân tách bằng hai dòng trống.
  - Các quy tắc này được tuân thủ đúng trong mã nguồn của các dự án OSS nổi tiếng như [botocore](https://github.com/boto/botocore/tree/develop/botocore), nhưng có vẻ như quy tắc không đính kèm "s" ở động từ số ít thứ ba trong phần đầu của nhận xét tài liệu không được tuân thủ một cách rộng rãi.
- Không chèn khoảng trắng trước và sau dấu ngoặc mở `(` và `[` .
- Không chèn khoảng trắng trước dấu hai chấm `:` và dấu chấm phẩy `;`.
- Trước và sau các toán tử số học, hãy chèn khoảng trắng.
- Khi phép gán kéo dài qua nhiều dòng, không cần căn chỉnh vị trí của dấu `=` bằng khoảng trắng.


Định dạng encoding
----

- Từ Python 3.0, UTF-8 được khuyến nghị là định dạng mã hóa cho các chuỗi.
- Nếu bạn sử dụng định dạng mã hóa khác ASCII trong các chuỗi literal ngoại trừ các comment và docstring, bạn cần sử dụng ký tự escape `\x`, `\u`, `\U`.


Import
----

- `import` được khai báo theo thứ tự sau đây và __phân tách các phần bằng dòng trống__.
  1. Thư viện tiêu chuẩn
  2. Thư viện bên thứ ba
  3. Thư viện cục bộ
  ```python
  import random
  import os
  import socket

  import dateutil.parser
  from dateutil.tz import tzutc

  import foo
  import foo.bar
  ```
- `import` được thực hiện bằng cách tách thành từng dòng như sau.
  ```python
  import os
  import sys
  ```
- Tuy nhiên, dạng `from -- import --` như sau có thể được viết trên cùng một dòng.
  ```python
  from subprocess import Popen, PIPE
  ```
- Khi phần `import` trên trải dài qua nhiều dòng, có thể bao bọc bằng dấu ngoặc như sau.
  ```python
  from botocore.compat import (
      json, quote, zip_longest, urlsplit, urlunsplit, OrderedDict,
      six, urlparse, get_tzinfo_options, get_md5, MD5_AVAILABLE,
      HAS_CRT
  )
  ```


Comment
----

- Comment nên được viết dưới dạng câu, với chữ cái đầu tiên viết hoa. Câu ngắn có thể bỏ qua dấu chấm cuối, nhưng thông thường không nên bỏ qua dấu chấm.
- Comment nên được viết bằng tiếng Anh.
- Đoạn văn trong comment dạng khối nên được phân tách bằng một dòng chứa một dấu `#`.
- Dấu `#` trong nhận xét nằm sau câu lệnh nên có ít nhất 2 khoảng trắng.

Các nhận xét tài liệu cho các module (file), class, function, và các thành phần khác nên được viết theo định dạng __docstring__. Vui lòng tham khảo bài viết dưới đây để biết thêm thông tin.

- [Viết tài liệu bằng Docstring](/p/y2biqz7/)

