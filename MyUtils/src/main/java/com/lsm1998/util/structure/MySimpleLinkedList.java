package com.lsm1998.util.structure;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间：2019/6/21-23:19
 * @作用：自定义简易单链表
 */
public class MySimpleLinkedList<E> extends MyAbstractSimpleList<E>
{
    private int size;
    private Node<E> head;

    public MySimpleLinkedList()
    {
        head = null;
    }

    public void add(int index, E ele)
    {
        MyAbstractSimpleList.checkBound(size + 1, index);
        if (head == null)
        {
            head = new Node<>(ele, null);
        } else
        {
            if (index == 0)
            {
                linkFirst(ele);
            } else if (index == size)
            {
                linkLast(ele);
            } else
            {
                int count = 0;
                Node<E> temp = head;
                while (++count <= index)
                {
                    temp = temp.next;
                }
                linkAfter(ele, temp);
            }
        }
        size++;
        return;
    }

    private void linkFirst(E ele)
    {
        Node<E> newNode = new Node<>(ele, head);
        head = newNode;
    }

    private void linkLast(E ele)
    {
        Node<E> temp = head;
        while (temp.next != null)
        {
            temp = temp.next;
        }
        temp.next = new Node<>(ele, null);
    }

    private void linkAfter(E ele, Node node)
    {
        Node<E> newNode = new Node<>(ele, null);
        node.next = newNode.next;
        node.next = newNode;
    }

    public void addFirst(E ele)
    {
        add(0, ele);
        return;
    }

    public void addLast(E ele)
    {
        add(size, ele);
        return;
    }

    public void removeFirst()
    {
        remove(0);
    }

    public void removeLast()
    {
        remove(size - 1);
    }

    @Override
    public Iterator<E> iterator()
    {
        // 单链表中不考虑迭代器的实现
        return null;
    }

    @Override
    public void add(E ele)
    {
        addLast(ele);
    }

    @Override
    public boolean contains(Object o)
    {
        Node<E> temp = head;
        if (temp != null)
        {
            for (int i = 0; i < this.size; i++)
            {
                if (this.get(i).equals(o))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o)
    {
        int index = indexOf(o);
        if (index != -1)
        {
            return remove(index);
        }
        return false;
    }

    @Override
    public boolean remove(int index)
    {
        MyAbstractSimpleList.checkBound(size, index);
        if (index == 0)
        {
            unlinkFirst();
        } else if (index == size - 1)
        {
            unlinkLast();
        } else
        {
            int count = 0;
            Node<E> temp = head;
            while (++count <= index)
            {
                temp = temp.next;
            }
            unlinkAfter(temp);
        }
        size--;
        return false;
    }

    /**
     * 删除头节点
     */
    private void unlinkFirst()
    {
        Node<E> temp = head;
        head = temp.next;
    }

    /**
     * 删除尾节点
     */
    private void unlinkLast()
    {
        Node<E> temp = head;
        Node<E> tempNext = head.next;
        while (tempNext != null)
        {
            temp = temp.next;
            tempNext = tempNext.next;
        }
        temp.next = null;
    }

    private void unlinkAfter(Node node)
    {
        node.next = node.next.next;
    }

    @Override
    public void clear()
    {
        head = null;
        size = 0;
    }

    @Override
    public E get(int index)
    {
        MyAbstractSimpleList.checkBound(size, index);
        Node<E> temp = head;
        int count = 0;
        while (++count <= index)
        {
            temp = temp.next;
        }
        return temp.ele;
    }

    @Override
    public E set(int index, E element)
    {
        MyAbstractSimpleList.checkBound(size, index);
        Node<E> temp = head;
        int count = 0;
        while (++count <= index)
        {
            temp = temp.next;
        }
        temp.ele = element;
        return element;
    }

    public int size()
    {
        return size;
    }

    @Override
    public int indexOf(Object o)
    {
        Node<E> temp = head;
        if (temp != null)
        {
            for (int i = 0; i < this.size; i++)
            {
                if (this.get(i).equals(o))
                {
                    return i;
                }
            }
        }
        return -1;
    }

    private class Node<E>
    {
        E ele;
        Node next;

        public Node(E ele, Node next)
        {
            this.ele = ele;
            this.next = next;
        }
    }
}
