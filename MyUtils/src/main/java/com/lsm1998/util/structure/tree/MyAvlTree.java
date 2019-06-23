package com.lsm1998.util.structure.tree;

import com.lsm1998.util.structure.list.MySimpleArrayList;

/**
 * @作者：刘时明
 * @时间：2019/6/23-14:36
 * @作用：采用递归的方式实现AVL树，原因是循环过于复杂
 */
public class MyAvlTree<K extends Comparable<? super K>, V> implements MyBTree<K, V>
{
    private int size;
    private Node root;

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean insert(K key, V value)
    {
        int temp = size;
        root = insert(root, key, value);
        return temp + 1 == size;
    }

    public Node insert(Node<K, V> node, K key, V value)
    {
        if (node == null)
        {
            size++;
            return new Node(key, value, null, null);
        }
        if (key.compareTo(node.key) > 0)
        {
            node.right = insert(node.right, key, value);
        } else if (key.compareTo(node.key) < 0)
        {
            node.left = insert(node.left, key, value);
        } else
        {
            size--;
            node.value = value;
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balanceFactor = getBalanceFactor(node);
        // LL旋转
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0)
        {
            return rightRotate(node);
        }
        // RR旋转
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0)
        {
            return leftRotate(node);
        }
        // LR旋转
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0)
        {
            return lrRotation(node);
        }
        // RL旋转
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0)
        {
            return rlRotation(node);
        }
        return node;
    }

    private Node rightRotate(Node y)
    {
        Node x = y.left;
        Node t = x.right;
        x.right = y;
        y.left = t;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    private Node leftRotate(Node y)
    {
        Node x = y.right;
        Node t = x.left;
        x.left = y;
        y.right = t;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    private Node lrRotation(Node y)
    {
        y.left = leftRotate(y.left);
        return rightRotate(y);
    }

    private Node rlRotation(Node y)
    {
        y.right = rightRotate(y.right);
        return leftRotate(y);
    }

    @Override
    public boolean remove(K key)
    {
        int temp = size;
        root = remove(root, key);
        return temp - 1 == size;
    }

    private Node remove(Node node, K key)
    {
        if (node == null)
        {
            return null;
        }
        if (node.key.compareTo(key) < 0)
        {
            node.right = remove(node.right, key);
            if (getHeight(node.right) - getHeight(node.left) == 2)
            {
                Node r = node.right;
                if (getHeight(r.left) > getHeight(r.right))
                    node = rlRotation(node);
                else
                    node = rightRotate(node);
            }
        } else if (node.key.compareTo(key) > 0)
        {
            node.left = remove(node.left, key);
            if (getHeight(node.left) - getHeight(node.right) == 2)
            {
                Node l = node.left;
                if (getHeight(l.right) > getHeight(l.left))
                    node = lrRotation(node);
                else
                    node = leftRotate(node);
            }
        } else
        {
            size--;
            if (node.left != null && node.right != null)
            {
                if (getHeight(node.left) > getHeight(node.right))
                {
                    Node temp = getMax(node.left);
                    root.key = temp.key;
                    root.value = temp.value;
                    root.left = remove(root.left, (K) temp.key);
                } else
                {
                    Node temp = getMax(root.right);
                    root.key = temp.key;
                    root.value = temp.value;
                    root.right = remove(root.right, (K) temp.key);
                }
//                Node temp = getMin(node.right);
//                node.key = temp.key;
//                node.value = temp.value;
//                node.right = remove(node.right, (K) node.key);
            } else
            {
                node = node.left == null ? node.right : node.left;
            }
        }
        return node;
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
            postDisPlay(node.left);
        }
        if (node.right != null)
        {
            postDisPlay(node.right);
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
            postDisPlay(node.left);
        }
        printNode(node);
        if (node.right != null)
        {
            postDisPlay(node.right);
        }
    }

    @Override
    public void middleDisPlay()
    {
        if (root != null)
        {
            middleDisPlay(root);
        }
    }

    private void middleDisPlay(Node node)
    {
        if (node.left != null)
        {
            postDisPlay(node.left);
        }
        if (node.right != null)
        {
            postDisPlay(node.right);
        }
        printNode(node);
    }

    @Override
    public boolean contains(K key)
    {
        return contains(root, key) != null;
    }

    private Node contains(Node node, K key)
    {
        if (node == null)
        {
            return null;
        }
        if (node.key.compareTo(key) > 0)
        {
            return contains(node.right, key);
        } else if (node.key.compareTo(key) < 0)
        {
            return contains(node.left, key);
        } else
        {
            return node;
        }
    }

    /**
     * 返回节点高度
     *
     * @param node
     * @return
     */
    private int getHeight(Node node)
    {
        return node == null ? 0 : node.height;
    }

    /**
     * 返回平衡因子
     *
     * @param node
     * @return
     */
    private int getBalanceFactor(Node node)
    {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    public MySimpleArrayList<K> getKeys()
    {
        MySimpleArrayList<K> list = new MySimpleArrayList<>(size);
        if (root != null)
        {
            getKeys(root, list);
        }
        return list;
    }

    private void getKeys(Node node, MySimpleArrayList list)
    {
        if (node.left != null)
        {
            getKeys(node.left, list);
        }
        list.add(node.key);
        if (node.right != null)
        {
            getKeys(node.right, list);
        }
    }

    private class Node<K extends Comparable<? super K>, V>
    {
        int height;
        K key;
        V value;
        Node left;
        Node right;

        public Node(K key, V value, Node left, Node right)
        {
            this.height = 1;
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public V getMax()
    {
        return root == null ? null : (V) getMax(root).value;
    }

    private Node getMax(Node node)
    {
        if (node.right != null)
        {
            return getMax(node.right);
        } else
        {
            return node;
        }
    }

    public V getMin()
    {
        return root == null ? null : (V) getMin(root).value;
    }

    private Node getMin(Node node)
    {
        if (node.left != null)
        {
            return getMin(node.left);
        } else
        {
            return node;
        }
    }

    private void printNode(Node node)
    {
        System.out.println("Key=" + node.key + ",value=" + node.value + ",balanceFactor=" + getBalanceFactor(node));
    }
}
