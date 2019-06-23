package com.lsm1998.util.structure;

/**
 * @作者：刘时明
 * @时间：2019/6/22-14:53
 * @作用：基于链表实现的二叉树
 */
public class MyLinkedBTree<K extends Comparable<? extends K>, V> implements MyBTree<K, V>
{
    private int size;
    private Node root;

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean insert(K key, V value)
    {
        Node temp = new Node(key, value, null, null);
        size++;
        if (root == null)
        {
            root = temp;
        } else
        {
            return insert(temp);
        }
        return true;
    }

    private boolean insert(Node node)
    {
        Node temp = root;
        while (true)
        {
            if (temp.key.compareTo(node.key) > 0)
            {
                if (temp.left == null)
                {
                    temp.left = node;
                    break;
                } else
                {
                    temp = temp.left;
                }
            } else if (temp.key.compareTo(node.key) < 0)
            {
                if (temp.right == null)
                {
                    temp.right = node;
                    break;
                } else
                {
                    temp = temp.right;
                }
            } else
            {
                temp.value = node.value;
                size--;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean remove(K key)
    {
        if (root != null)
        {
            Node parent = null;
            Node temp = root;
            while (true)
            {
                if (temp.key.compareTo(key) > 0)
                {
                    if (temp.left == null)
                    {
                        break;
                    } else
                    {
                        parent = temp;
                        temp = temp.left;
                    }
                } else if (temp.key.compareTo(key) < 0)
                {
                    if (temp.right == null)
                    {
                        break;
                    } else
                    {
                        parent = temp;
                        temp = temp.right;
                    }
                } else
                {
                    remove(temp, parent);
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    private void remove(Node node, Node parent)
    {
        if (parent == null)
        {
            // 删除根节点
            if (size == 1)
            {
                root = null;
            } else
            {
                root = root.left == null ? root.right : root.left;
            }
        } else
        {
            if (node.left != null && node.right != null)
            {
                Node<K,V> temp = getMin(node.right);
                size++;
                remove(temp.key);
                node.key = temp.key;
                node.value = temp.value;
            } else
            {
                if (parent.key.compareTo(node.key) > 0)
                {
                    parent.left = node.left;
                } else
                {
                    parent.right = node.right;
                }
            }
        }
    }

    @Override
    public void prevDisPlay()
    {
        if (root != null)
        {
            prevDisPlay(root);
        }
    }

    private void prevDisPlay(Node node)
    {
        printNode(node);
        if (node.left != null)
        {
            prevDisPlay(node.left);
        }
        if (node.right != null)
        {
            prevDisPlay(node.right);
        }
    }

    @Override
    public void postDisPlay()
    {
        if (root != null)
        {
            postDisPlay(root);
        }
    }

    private void postDisPlay(Node node)
    {
        if (node.left != null)
        {
            prevDisPlay(node.left);
        }
        printNode(node);
        if (node.right != null)
        {
            prevDisPlay(node.right);
        }
    }

    @Override
    public void middleDisPlay()
    {
        if (root != null)
        {
            postDisPlay(root);
        }
    }

    private void middleDisPlay(Node node)
    {
        if (node.left != null)
        {
            prevDisPlay(node.left);
        }
        if (node.right != null)
        {
            prevDisPlay(node.right);
        }
        printNode(node);
    }

    private void printNode(Node node)
    {
        System.out.println("[K=" + node.key + ",V=" + node.value + "]");
    }

    public V getMax()
    {
        return root == null ? null : (V) getMax(root).value;
    }

    public V getMin()
    {
        return root == null ? null : (V) getMin(root).value;
    }

    private Node<K, V> getMax(Node<K, V> node)
    {
        while (node.right != null)
        {
            node = node.right;
        }
        return node;
    }

    private Node<K, V> getMin(Node<K, V> node)
    {
        while (node.left != null)
        {
            node = node.left;
        }
        return node;
    }

    @Override
    public boolean contains(K key)
    {
        return get(key) != null;
    }

    public Node get(K key)
    {
        if (root != null)
        {
            Node temp = root;
            while (true)
            {
                if (temp.key.compareTo(key) > 0)
                {
                    if (temp.left == null)
                    {
                        return null;
                    } else
                    {
                        temp = temp.left;
                    }
                } else if (temp.key.compareTo(key) < 0)
                {
                    if (temp.right == null)
                    {
                        return null;
                    } else
                    {
                        temp = temp.right;
                    }
                } else
                {
                    return temp;
                }
            }
        }
        return null;
    }

    private class Node<K extends Comparable<? extends K>, V>
    {
        K key;
        V value;
        Node left;
        Node right;

        public Node(K key, V value, Node left, Node right)
        {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
