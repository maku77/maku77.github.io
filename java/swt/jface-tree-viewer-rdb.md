---
title: 階層構想を表現した RDB のデータを JFace の TreeViewer でツリー表示する
created: 2005-07-26
---

階層構造を持つデータを RDB を使って次のようなテーブルで表現したとします。

~~~ sql
CREATE TABLE node_info (
    node_id CHAR(7) NOT NULL PRIMARY KEY,  -- 自分自身の ID
    parent_id CHAR(7)                      -- 親ノードの ID
    node_name VARCHAR(50),                 -- 付加情報
);

INSERT node_info VALUES('0000001', NULL,      'Title 1');
INSERT node_info VALUES('0000002', '0000001', 'Title 1-1');
INSERT node_info VALUES('0000003', '0000001', 'Title 1-2');
INSERT node_info VALUES('0000004', NULL,      'Title 2');
INSERT node_info VALUES('0000005', '0000004', 'Title 2-1');
INSERT node_info VALUES('0000006', '0000004', 'Title 2-2');
~~~

まず `node_info` テーブルのレコードを格納するための Transfer Object を作成します。
簡単のためにここではフィールドのカプセル化を行いません。

~~~ java
public class NodeInfoTO {
    public String nodeId;
    public String parentId;
    public String nodeName;
}
~~~

次に、データベースから指定したノード ID の子ノード配列 (`NodeInfoTO[]`) を取得するための Data Access Object を作成します。

#### NodeInfoDAO.java

~~~ java
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class NodeInfoDAO {
    /**
     * 指定したノード ID の下の子ノード配列を取得します。
     * null を指定した場合はトップレベルのノード配列を取得します。
     */
    public NodeInfoTO[] searchChildNodeInfo(String parentID) {
        Connection con = null;
        try {
            con = MyConnectionPool.getConnection();
            PreparedStatement st = con.prepareStatement(
                "SELECT * FROM node_info WHERE parent_id = ?");
            if (parentID == null) {
                st.setNull(1, Types.CHAR);
            } else {
                st.setString(1, parentID);
            }
            ResultSet rs = st.executeQuery();
            return createNodeInfoArray(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            safeClose(con);
        }
        return null;
    }

    private void safeClose(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // Do nothing.
            }
        }
    }

    private NodeInfoTO createNodeInfo(ResultSet rs) {
        NodeInfoTO info = new NodeInfoTO();
        info.nodeID = rs.getString("node_id");
        info.parentID = rs.getString("parent_id");
        info.nodeName = rs.getString("node_name");
        return info;
    }

    private NodeInfoTO[] createNodeInfoArray(ResultSet rs) {
        ArrayList<NodeInfoTO> list = new ArrayList<NodeInfoTO>();
        while (rs.next()) {
            list.add(createNodeInfo(rs));
        }
        return list.toArray();
    }
}
~~~

これらのクラスを使ってツリーを構築するためのコードを作成します。

#### Main.java

~~~ java
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;

...
TreeViewer tv = new TreeViewer(tree);
tv.setLabelProvider(new NodeInfoTreeLabelProvider());
tv.setContentProvider(new NodeInfoTreeContentProvider());
tv.setInput(null);  // null を指定してトップレベルから表示
...
~~~

#### NodeInfoTreeLabelProvider.java

~~~ java
import org.eclipse.jface.viewers.LabelProvider;

/**
 * ツリーの各ノードの表示方法を定義した LabelProvider
 */
public class NodeInfoTreeLabelProvider extends LabelProvider {
    public String getText(Object element) {
        return ((NodeInfoTO) element).nodeName;
    }

    public Image getImage(Object element) {
        return null;
    }
}
~~~

#### NodeInfoTreeContentProvider.java

~~~ java
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * ツリーに表示するノードのデータを提供する ContentProvider
 */
public class NodeInfoTreeContentProvider implements ITreeContentProvider {
    public Object[] getChildren(Object element) {
        NodeInfoDAO dao = new NodeInfoDAO();
        return dao.searchChildNodeInfo(((NodeInfoTO) element).parentID);
    }

    // TreeViewer#setInput() で渡される引数を元にトップレベルの
    // ノードを作成するように実装する。
    public Object[] getElements(Object input) {
        NodeInfoDAO dao = new NodeInfoDAO();
        return dao.searchChildNodeInfo((String) input);
    }

    public boolean hasChildren(Object element) {
        Object[] children = getChildren(element);
        return children == null ? false : children.length > 0;
    }

    public Object getParent(Object element) {
        return null;
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput,
            Object newInput) {
    }
}
~~~

