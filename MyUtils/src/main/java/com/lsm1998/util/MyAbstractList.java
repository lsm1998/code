package com.lsm1998.util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @作者：刘时明
 * @时间：2019/5/20-8:44
 * @作用：
 */
public abstract class MyAbstractList<E> extends MyAbstractCollection<E> implements MyList<E>
{
    protected MyAbstractList()
    {
    }

    public boolean add(E e)
    {
        add(size(), e);
        return true;
    }

    abstract public E get(int index);

    public E set(int index, E element)
    {
        throw new RuntimeException("不可以直接调用" + this.getClass().getName() + "的set方法");
    }

    public void add(int index, E element)
    {
        throw new RuntimeException("不可以直接调用" + this.getClass().getName() + "的add方法");
    }

    public E remove(int index)
    {
        throw new RuntimeException("不可以直接调用" + this.getClass().getName() + "的remove方法");
    }

    protected transient int modCount = 0;

    protected void removeRange(int fromIndex, int toIndex)
    {
        MyListIterator<E> it = listIterator(fromIndex);
        for (int i = 0, n = toIndex - fromIndex; i < n; i++)
        {
            it.next();
            it.remove();
        }
    }

    public MyListIterator<E> listIterator(final int index)
    {
        rangeCheckForAdd(index);
        return new MyListItr(index);
    }

    public MyListIterator<E> listIterator()
    {
        return listIterator(0);
    }

    private void rangeCheckForAdd(int index)
    {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException("数组越界：" + index);
    }

    @Override
    public int indexOf(Object o)
    {
        MyListIterator<E> it = listIterator();
        if (o == null)
        {
            while (it.hasNext())
                if (it.next() == null)
                    return it.previousIndex();
        } else
        {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return it.previousIndex();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        MyListIterator<E> it = listIterator(size());
        if (o == null)
        {
            while (it.hasPrevious())
                if (it.previous() == null)
                    return it.nextIndex();
        } else
        {
            while (it.hasPrevious())
                if (o.equals(it.previous()))
                    return it.nextIndex();
        }
        return -1;
    }

    private class MyItr implements Iterator<E>
    {
        int cursor = 0;

        int lastRet = -1;

        int expectedModCount = modCount;

        public boolean hasNext()
        {
            return cursor != size();
        }

        public E next()
        {
            checkForComodification();
            try
            {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e)
            {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove()
        {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try
            {
                MyAbstractList.this.remove(lastRet);
                if (lastRet < cursor)
                    cursor--;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e)
            {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification()
        {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class MyListItr extends MyItr implements MyListIterator<E>
    {
        MyListItr(int index)
        {
            cursor = index;
        }

        public boolean hasPrevious()
        {
            return cursor != 0;
        }

        public E previous()
        {
            checkForComodification();
            try
            {
                int i = cursor - 1;
                E previous = get(i);
                lastRet = cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e)
            {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public int nextIndex()
        {
            return cursor;
        }

        public int previousIndex()
        {
            return cursor - 1;
        }

        public void set(E e)
        {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();
            try
            {
                MyAbstractList.this.set(lastRet, e);
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex)
            {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e)
        {
            checkForComodification();
            try
            {
                int i = cursor;
                MyAbstractList.this.add(i, e);
                lastRet = -1;
                cursor = i + 1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex)
            {
                throw new ConcurrentModificationException();
            }
        }
    }

    public MyList<E> subList(int fromIndex, int toIndex)
    {
        return (this instanceof MyRandomAccess ?
                new RandomAccessSubList<>(this, fromIndex, toIndex) :
                new SubList<>(this, fromIndex, toIndex));
    }
}

class RandomAccessSubList<E> extends SubList<E> implements MyRandomAccess
{
    RandomAccessSubList(MyAbstractList<E> list, int fromIndex, int toIndex)
    {
        super(list, fromIndex, toIndex);
    }

    public MyList<E> subList(int fromIndex, int toIndex)
    {
        return new RandomAccessSubList<>(this, fromIndex, toIndex);
    }
}

class SubList<E> extends MyAbstractList<E>
{
    private final MyAbstractList<E> l;
    private final int offset;
    private int size;

    SubList(MyAbstractList<E> list, int fromIndex, int toIndex)
    {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > list.size())
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
        l = list;
        offset = fromIndex;
        size = toIndex - fromIndex;
        this.modCount = l.modCount;
    }

    public E set(int index, E element)
    {
        rangeCheck(index);
        checkForComodification();
        return l.set(index + offset, element);
    }

    public E get(int index)
    {
        rangeCheck(index);
        checkForComodification();
        return l.get(index + offset);
    }

    public int size()
    {
        checkForComodification();
        return size;
    }

    public void add(int index, E element)
    {
        rangeCheckForAdd(index);
        checkForComodification();
        l.add(index + offset, element);
        this.modCount = l.modCount;
        size++;
    }

    public E remove(int index)
    {
        rangeCheck(index);
        checkForComodification();
        E result = l.remove(index + offset);
        this.modCount = l.modCount;
        size--;
        return result;
    }

    protected void removeRange(int fromIndex, int toIndex)
    {
        checkForComodification();
        l.removeRange(fromIndex + offset, toIndex + offset);
        this.modCount = l.modCount;
        size -= (toIndex - fromIndex);
    }

    public boolean addAll(MyCollection<? extends E> c)
    {
        return addAll(size, c);
    }

    public boolean addAll(int index, MyCollection<? extends E> c)
    {
        rangeCheckForAdd(index);
        int cSize = c.size();
        if (cSize == 0)
            return false;

        checkForComodification();
        l.addAll(offset + index, c);
        this.modCount = l.modCount;
        size += cSize;
        return true;
    }

    public Iterator<E> iterator()
    {
        return listIterator();
    }

    public MyListIterator<E> listIterator(final int index)
    {
        checkForComodification();
        rangeCheckForAdd(index);

        return new MyListIterator<E>()
        {
            private final MyListIterator<E> i = l.listIterator(index + offset);

            public boolean hasNext()
            {
                return nextIndex() < size;
            }

            public E next()
            {
                if (hasNext())
                    return i.next();
                else
                    throw new NoSuchElementException();
            }

            public boolean hasPrevious()
            {
                return previousIndex() >= 0;
            }

            public E previous()
            {
                if (hasPrevious())
                    return i.previous();
                else
                    throw new NoSuchElementException();
            }

            public int nextIndex()
            {
                return i.nextIndex() - offset;
            }

            public int previousIndex()
            {
                return i.previousIndex() - offset;
            }

            public void remove()
            {
                i.remove();
                SubList.this.modCount = l.modCount;
                size--;
            }

            public void set(E e)
            {
                i.set(e);
            }

            public void add(E e)
            {
                i.add(e);
                SubList.this.modCount = l.modCount;
                size++;
            }
        };
    }

    public MyList<E> subList(int fromIndex, int toIndex)
    {
        return new SubList<>(this, fromIndex, toIndex);
    }

    private void rangeCheck(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index)
    {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index)
    {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkForComodification()
    {
        if (this.modCount != l.modCount)
            throw new ConcurrentModificationException();
    }
}
