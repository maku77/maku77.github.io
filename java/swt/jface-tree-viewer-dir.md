---
title: JFace の TreeViewer を使ってディレクトリツリーを表示する
date: "2005-07-24"
---

JFace の TreeViewer で、ディレクトリツリーを表示するサンプルです。
下記のサンプルでは、Windows の C ドライブ以下のディレクトリをツリー表示しています。

#### Main.java

~~~ java
import java.io.File;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
...
TreeViewer tv = new TreeViewer(tree);
tv.setLabelProvider(new FileTreeLabelProvider());
tv.setContentProvider(new FileTreeContentProvider());
tv.setInput(new File("C:"));
...
~~~


#### FileTreeLabelProvider.java

~~~ java
import java.io.File;
import org.eclipse.jface.viewers.LabelProvider;

/**
 * ツリーの各ノードの表示方法を定義した LabelProvider
 */
public class FileTreeLabelProvider extends LabelProvider {
    public String getText(Object element) {
        File f = (File) element;
        if (f.isDirectory()) {
            return f.getName() + '/';
        }
        return f.getName();
    }

    public Image getImage(Object element) {
        return null;
    }
}
~~~


#### FileTreeContentProvider.java

~~~ java
import java.io.File;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * ツリーに File オブジェクトを階層表示するための ContentProvider
 */
public class FileTreeContentProvider implements ITreeContentProvider {
    /**
     * ツリーノードの子ノードの配列を返すように実装します
     * @param element このツリーノードの子ノードを返すように実装します
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object element) {
        return ((File) element).listFiles();
    }

    /**
     * ツリーのルート要素を返すように実装します
     * @param element Viewer#setInput で渡されたオブジェクト
     * @see org.eclipse.jface.viewers.Viewer#setInput(java.lang.Object)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object element) {
        return getChildren(element);
    }

    /**
     * ツリーノードに子ノードがあるなら true を返すように実装します
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        Object[] children = getChildren(element);
        return children == null ? false : children.length > 0;
    }

    /**
     * ツリーノードの親ノードを返すように実装します
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        return ((File) element).getParentFile();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput,
            Object newInput) {
    }
}
~~~

