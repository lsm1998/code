package com.lsm1998.util.structure.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @作者：刘时明
 * @时间：2019/6/22-11:29
 * @作用：自定义简易双向链表
 */
public class MySimpleDLinedList<E> extends MyAbstractSimpleList<E>
{
    private int size;
    // 头节点
    private Node<E> first;
    // 尾结点
    private Node<E> last;

    public MySimpleDLinedList()
    {
        first = new Node<>(null, null, null);
        last = new Node<>(null, first, null);
        first.next = last;
        size = 0;
    }

    // 根据下标获取一个结点
    private Node<E> getNode(int index, int low, int upper)
    {
        Node<E> p;
        MyAbstractSimpleList.checkBound(low, upper, index);
        // 从首尾两侧寻找至目标结点最近的距离
        if (index > size / 2)
        {
            p = first.next;
            for (int i = 0; i < index; i++)
            {
                p = p.next;
            }
        } else
        {
            p = last;
            for (int i = size; i > index; i--)
            {
                p = p.prev;
            }
        }
        return p;
    }

    private Node<E> getNode(int index)
    {
        return getNode(index, 0, size);
    }

    private void addBefore(Node<E> p, E data)
    {
        Node<E> temp = new Node<>(data, p.prev, p);
        temp.prev.next = temp;
        p.prev = temp;
        size++;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public void add(int index, E ele)
    {
        MyAbstractSimpleList.checkBound(size + 1, index);
        addBefore(getNode(index), ele);
    }

    @Override
    public void add(E ele)
    {
        add(size, ele);
    }

    @Override
    public boolean contains(Object o)
    {
        return indexOf(o) != -1;
    }

    @Override
    public boolean remove(Object o)
    {
        if (size > 0)
        {
            int conut = 0;
            Node<E> temp = first;
            while (temp.next != null)
            {
                temp = temp.next;
                if (temp.ele.equals(o))
                {
                    remove(conut);
                    return true;
                }
                conut++;
            }
        }
        return false;
    }

    @Override
    public boolean remove(int index)
    {
        MyAbstractSimpleList.checkBound(size, index);
        remove(getNode(index));
        return true;
    }

    @Override
    public void clear()
    {
        first = new Node<>(null, null, null);
        last = new Node<>(null, first, null);
        first.next = last;
        size = 0;
    }

    @Override
    public E get(int index)
    {
        return getNode(index).ele;
    }

    @Override
    public E set(int index, E element)
    {
        MyAbstractSimpleList.checkBound(size, index);
        getNode(index).ele = element;
        return element;
    }

    private void remove(Node<E> node)
    {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    @Override
    public int indexOf(Object o)
    {
        if (size > 0)
        {
            Node<E> temp = first;
            int count = 0;
            while (temp.next != null)
            {
                temp = temp.next;
                if (temp.ele.equals(o))
                {
                    return count;
                }
                count++;
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new MyLinkedListIterator();
    }

    private class Node<E>
    {
        E ele;
        Node<E> prev;
        Node<E> next;

        public Node(E ele, Node<E> prev, Node<E> next)
        {
            this.ele = ele;
            this.prev = prev;
            this.next = next;
        }
    }

    private class MyLinkedListIterator implements Iterator<E>
    {
        private int current = 0;

        @Override
        public void remove()
        {
            MySimpleDLinedList.this.remove(--current);
        }

        @Override
        public boolean hasNext()
        {
            return current < size;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return get(current++);
        }
    }
}
