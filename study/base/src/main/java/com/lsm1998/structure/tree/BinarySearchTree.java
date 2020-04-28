package com.lsm1998.structure.tree;

import java.util.function.BiConsumer;

/**
 * @program: code
 * @description: 二分搜索树
 * @author: lsm
 * @create: 2020-04-20 18:59
 **/
public class BinarySearchTree<K extends Comparable<K>,V> extends AbstractTree<K,V>
{
    private Node<K,V> root;
    private int size;

    public BinarySearchTree()
    {
        root=null;
        size=0;
    }

    @Override
    protected Tree.Node<K, V> getRoot()
    {
        return root;
    }

    @Override
    public boolean put(K key, V value)
    {
        size++;
        if(root==null)
        {
            root=new Node<>(null,null,null,key,value);
            return true;
        }
        Node<K,V> temp=root;
        while (true)
        {
            if(temp.key.compareTo(key)>0)
            {
                if(temp.left==null)
                {
                    temp.left=new Node<>(temp,null,null,key,value);
                    break;
                }else
                {
                    temp=(Node<K,V>)temp.left;
                }
            }else if(temp.key.compareTo(key)<0)
            {
                if(temp.right==null)
                {
                    temp.right=new Node<>(temp,null,null,key,value);
                    break;
                }else
                {
                    temp=(Node<K,V>)temp.right;
                }
            }else
            {
                size--;
                temp.value=value;
                break;
            }
        }
        return false;
    }

    @Override
    public boolean find(K key)
    {
        return getNode(key)!=null;
    }

    @Override
    public V get(K key)
    {
        Node<K,V> temp=(Node<K,V>)this.getNode(key);
        return temp==null?null:temp.value;
    }


    @Override
    public boolean remove(K key)
    {
        Node<K,V> target=(Node<K,V>)getNode(key);
        if(target==null) return false;
        size--;
        return remove(target.parent,target);
    }

    private boolean remove(Node<K,V> parent,Node<K,V> node)
    {
        if(node.left!=null&&node.right!=null)
        {
            Node<K,V> temp=(Node<K,V>)node.left;
            node.key=temp.key;
            node.value=temp.value;
            return remove(node,temp);
        } else {
            if(parent==null)
            {
                root=root.left==null?(Node<K,V>)root.right:(Node<K,V>)root.left;
                if(root!=null)
                {
                    root.parent=null;
                }
                return true;
            }
            if(parent.left==node)
            {
                parent.left=node.left;
            }else
            {
                parent.right=node.right;
            }
            deleteQuote(node);
        }
        return false;
    }

    private void deleteQuote(Node<K,V> node)
    {
        node.left=null;
        node.right=null;
        node.parent=null;
    }

    public void forEach(BiConsumer<K,V> consumer)
    {
        if(root==null)return;
        forEach(consumer,root);
    }

    public int size()
    {
        return size;
    }

    private void forEach(BiConsumer<K,V> consumer,Node<K,V> node)
    {
        if(node.left!=null)
        {
            forEach(consumer,(Node<K,V>)node.left);
        }
        consumer.accept(node.key,node.value);
        if(node.right!=null)
        {
            forEach(consumer,(Node<K,V>)node.right);
        }
    }

    static class Node<K,V> extends Tree.Node<K,V>
    {
        private Node<K,V> parent;

        public Node(Node<K, V> parent, Node<K, V> left, Node<K, V> right, K key, V value)
        {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.key = key;
            this.value = value;
        }
    }
}
