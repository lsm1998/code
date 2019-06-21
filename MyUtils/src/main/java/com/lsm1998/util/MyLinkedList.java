package com.lsm1998.util;

import java.util.*;
import java.util.function.Consumer;

/**
 * @作者：刘时明
 * @时间：2019/6/20-23:32
 * @作用：
 */
public class MyLinkedList<E> extends MyAbstractSequentialList<E> implements MyList<E>, MyDeque<E>, MyCloneable, java.io.Serializable
{
    // 当前链表大小
    transient int size = 0;
    // 头节点
    transient Node<E> first;
    // 尾结点
    transient Node<E> last;

    public MyLinkedList()
    {
    }

    public MyLinkedList(MyCollection<? extends E> c)
    {
        this();
        addAll(c);
    }

    // 插入头节点
    private void linkFirst(E e)
    {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        modCount++;
    }

    // 插入尾结点
    private void linkLast(E e)
    {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }

    // 插入节点在指定节点之前
    void linkBefore(E e, Node<E> node)
    {
        final Node<E> pred = node.prev;
        final Node<E> newNode = new Node<>(pred, e, node);
        node.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        modCount++;
    }

    private E unlinkFirst(Node<E> f)
    {
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null;
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
        modCount++;
        return element;
    }

    private E unlinkLast(Node<E> l)
    {
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null;
        last = prev;
        if (prev == null)
            first = null;
        else
            prev.next = null;
        size--;
        modCount++;
        return element;
    }

    E unlink(Node<E> x)
    {
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;
        if (prev == null)
        {
            first = next;
        } else
        {
            prev.next = next;
            x.prev = null;
        }
        if (next == null)
        {
            last = prev;
        } else
        {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        modCount++;
        return element;
    }

    @Override
    public E getFirst()
    {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }

    @Override
    public E getLast()
    {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }

    @Override
    public E removeFirst()
    {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }

    @Override
    public E removeLast()
    {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }

    public void addFirst(E e)
    {
        linkFirst(e);
    }

    public void addLast(E e)
    {
        linkLast(e);
    }

    public boolean contains(Object o)
    {
        return indexOf(o) >= 0;
    }

    public boolean remove(Object o)
    {
        if (o == null)
        {
            for (Node<E> x = first; x != null; x = x.next)
            {
                if (x.item == null)
                {
                    unlink(x);
                    return true;
                }
            }
        } else
        {
            for (Node<E> x = first; x != null; x = x.next)
            {
                if (o.equals(x.item))
                {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    Node<E> node(int index)
    {
        if (index < (size >> 1))
        {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;

        } else
        {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    public int indexOf(Object o)
    {
        int index = 0;
        if (o == null)
        {
            for (Node<E> x = first; x != null; x = x.next)
            {
                if (x.item == null)
                    return index;
                index++;
            }
        } else
        {
            for (Node<E> x = first; x != null; x = x.next)
            {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public boolean offer(E e)
    {
        return add(e);
    }

    public int lastIndexOf(Object o)
    {
        int index = size;
        if (o == null)
        {
            for (Node<E> x = last; x != null; x = x.prev)
            {
                index--;
                if (x.item == null)
                    return index;
            }
        } else
        {
            for (Node<E> x = last; x != null; x = x.prev)
            {
                index--;
                if (o.equals(x.item))
                    return index;
            }
        }
        return -1;
    }

    public E peek()
    {
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }

    public boolean addAll(int index, MyCollection<? extends E> c)
    {
        checkPositionIndex(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;
        Node<E> pred, succ;
        if (index == size)
        {
            succ = null;
            pred = last;
        } else
        {
            succ = node(index);
            pred = succ.prev;
        }
        for (Object o : a)
        {
            E e = (E) o;
            Node<E> newNode = new Node<>(pred, e, null);
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if (succ == null)
        {
            last = pred;
        } else
        {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        modCount++;
        return true;
    }

    private void checkPositionIndex(int index)
    {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isPositionIndex(int index)
    {
        return index >= 0 && index <= size;
    }

    public boolean addAll(MyCollection<? extends E> c)
    {
        return addAll(size, c);
    }

    @Override
    public int size()
    {
        return size;
    }

    public boolean add(E e)
    {
        linkLast(e);
        return true;
    }

    @Override
    public MyListIterator<E> listIterator(int index)
    {
        checkPositionIndex(index);
        return new MyListItr(index);
    }

    @Override
    public E pollLast()
    {
        final Node<E> l = last;
        return (l == null) ? null : unlinkLast(l);
    }

    @Override
    public void push(E e)
    {
        addFirst(e);
    }

    @Override
    public E pollFirst()
    {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }

    @Override
    public E peekLast()
    {
        final Node<E> l = last;
        return (l == null) ? null : l.item;
    }

    @Override
    public boolean offerFirst(E e)
    {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e)
    {
        addLast(e);
        return true;
    }

    @Override
    public E remove()
    {
        return removeFirst();
    }

    @Override
    public E element()
    {
        return getFirst();
    }

    @Override
    public E peekFirst()
    {
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }

    @Override
    public E poll()
    {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }

    @Override
    public E pop()
    {
        return removeFirst();
    }

    @Override
    public boolean removeLastOccurrence(Object o)
    {
        if (o == null)
        {
            for (Node<E> x = last; x != null; x = x.prev)
            {
                if (x.item == null)
                {
                    unlink(x);
                    return true;
                }
            }
        } else
        {
            for (Node<E> x = last; x != null; x = x.prev)
            {
                if (o.equals(x.item))
                {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeFirstOccurrence(Object o)
    {
        return remove(o);
    }

    @Override
    public Iterator<E> descendingIterator()
    {
        return new MyDescendingIterator();
    }

    private MyLinkedList<E> superClone()
    {
        try
        {
            return (MyLinkedList<E>) super.clone();
        } catch (CloneNotSupportedException e)
        {
            throw new InternalError(e);
        }
    }

    public Object clone()
    {
        MyLinkedList<E> clone = superClone();
        clone.first = clone.last = null;
        clone.size = 0;
        clone.modCount = 0;
        for (Node<E> x = first; x != null; x = x.next)
            clone.add(x.item);
        return clone;
    }

    public Object[] toArray()
    {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;
        return result;
    }

    public <T> T[] toArray(T[] a)
    {
        if (a.length < size)
        {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;
        for (Node<E> x = first; x != null; x = x.next)
        {
            result[i++] = x.item;
        }
        if (a.length > size)
        {
            a[size] = null;
        }
        return a;
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException
    {
        s.defaultWriteObject();

        s.writeInt(size);

        for (Node<E> x = first; x != null; x = x.next)
        {
            s.writeObject(x.item);
        }
    }

    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException
    {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in size
        int size = s.readInt();

        // Read in all elements in the proper order.
        for (int i = 0; i < size; i++)
        {
            linkLast((E) s.readObject());
        }
    }

    @Override
    public Spliterator<E> spliterator()
    {
        // Spliterator是针对于并行遍历而设计的迭代器
        return new MyLLSpliterator<>(this, -1, 0);
    }

    private String outOfBoundsMsg(int index)
    {
        return "Index: " + index + ", Size: " + size;
    }

    private static class Node<E>
    {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next)
        {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private class MyListItr implements MyListIterator<E>
    {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        MyListItr(int index)
        {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        public boolean hasNext()
        {
            return nextIndex < size;
        }

        public E next()
        {
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious()
        {
            return nextIndex > 0;
        }

        public E previous()
        {
            checkForComodification();
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = next = (next == null) ? last : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex()
        {
            return nextIndex;
        }

        public int previousIndex()
        {
            return nextIndex - 1;
        }

        public void remove()
        {
            checkForComodification();
            if (lastReturned == null)
                throw new IllegalStateException();

            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
            expectedModCount++;
        }

        public void set(E e)
        {
            if (lastReturned == null)
                throw new IllegalStateException();
            checkForComodification();
            lastReturned.item = e;
        }

        public void add(E e)
        {
            checkForComodification();
            lastReturned = null;
            if (next == null)
                linkLast(e);
            else
                linkBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        public void forEachRemaining(Consumer<? super E> action)
        {
            Objects.requireNonNull(action);
            while (modCount == expectedModCount && nextIndex < size)
            {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
            checkForComodification();
        }

        final void checkForComodification()
        {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class MyDescendingIterator implements Iterator<E>
    {
        private final MyListItr itr = new MyListItr(size());

        public boolean hasNext()
        {
            return itr.hasPrevious();
        }

        public E next()
        {
            return itr.previous();
        }

        public void remove()
        {
            itr.remove();
        }
    }

    static final class MyLLSpliterator<E> implements Spliterator<E>
    {
        // 批处理数组大小增量
        static final int BATCH_UNIT = 1 << 10;
        // 最大批量数组大小
        static final int MAX_BATCH = 1 << 25;
        // null OK unless traversed
        final MyLinkedList<E> list;
        // 当前遍历的节点
        Node<E> current;
        // size estimate; -1 until first needed
        int est;
        // initialized when est set
        int expectedModCount;
        // 拆分的批量大小
        int batch;

        MyLLSpliterator(MyLinkedList<E> list, int est, int expectedModCount)
        {
            this.list = list;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }

        final int getEst()
        {
            int s; // force initialization
            final MyLinkedList<E> lst;
            if ((s = est) < 0)
            {
                if ((lst = list) == null)
                    s = est = 0;
                else
                {
                    expectedModCount = lst.modCount;
                    current = lst.first;
                    s = est = lst.size;
                }
            }
            return s;
        }

        public long estimateSize()
        {
            return (long) getEst();
        }

        // 分割list，返回一个新分割出的spliterator实例
        public Spliterator<E> trySplit()
        {
            Node<E> p;
            int s = getEst();
            if (s > 1 && (p = current) != null)
            {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                Object[] a = new Object[n];
                int j = 0;
                do
                {
                    a[j++] = p.item;
                } while ((p = p.next) != null && j < n);
                current = p;
                batch = j;
                est = s - j;
                return Spliterators.spliterator(a, 0, j, Spliterator.ORDERED);
            }
            return null;
        }

        public void forEachRemaining(Consumer<? super E> action)
        {
            Node<E> p;
            int n;
            if (action == null) throw new NullPointerException();
            if ((n = getEst()) > 0 && (p = current) != null)
            {
                current = null;
                est = 0;
                do
                {
                    E e = p.item;
                    p = p.next;
                    action.accept(e);
                } while (p != null && --n > 0);
            }
            if (list.modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }

        public boolean tryAdvance(Consumer<? super E> action)
        {
            Node<E> p;
            if (action == null) throw new NullPointerException();
            if (getEst() > 0 && (p = current) != null)
            {
                --est;
                E e = p.item;
                current = p.next;
                action.accept(e);
                if (list.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        public int characteristics()
        {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }
}
