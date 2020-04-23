package com.lsm1998.structure.tree;

import java.util.function.BiConsumer;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-20 18:59
 **/
public class BinarySearchTree<K extends Comparable<K>,V>
{
    private Node<K,V> root;
    private int size;

    public BinarySearchTree()
    {
        root=null;
        size=0;
    }

    public boolean add(K key,V value)
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
                    temp=temp.left;
                }
            }else if(temp.key.compareTo(key)<0)
            {
                if(temp.right==null)
                {
                    temp.right=new Node<>(temp,null,null,key,value);
                    break;
                }else
                {
                    temp=temp.right;
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

    public boolean find(K key)
    {
        return getNode(key)!=null;
    }

    public V get(K key)
    {
        Node<K,V> temp=getNode(key);
        return temp==null?null:temp.value;
    }

    private Node<K,V> getNode(K key)
    {
        if(root==null)return null;
        Node<K,V> temp=root;
        while (true)
        {
            if(temp.key.compareTo(key)>0)
            {
                if(temp.left==null)
                {
                    break;
                }else
                {
                    temp=temp.left;
                }
            }else if(temp.key.compareTo(key)<0)
            {
                if(temp.right==null)
                {
                    break;
                }else
                {
                    temp=temp.right;
                }
            }else
            {
                return temp;
            }
        }
        return null;
    }

    public boolean remove(K key)
    {
        Node<K,V> target=getNode(key);
        if(target==null) return false;
        if(target.parent==null)
        {
            if(target.left!=null&&target.right!=null)
            {
                Node<K,V> temp=target.left;
                root.key=temp.key;
                root.value=temp.value;
                return remove(temp.key);
            } else
            {
                if(root.left!=null)
                {
                    root=root.left;
                }else
                {
                    root=root.right;
                }
            }
        }else
        {

        }
        return false;
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
            forEach(consumer,node.left);
        }
        consumer.accept(node.key,node.value);
        if(node.right!=null)
        {
            forEach(consumer,node.right);
        }
    }

    static class Node<K,V>
    {
        private Node<K,V> parent;
        private Node<K,V> left;
        private Node<K,V> right;
        private K key;
        private V value;

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
