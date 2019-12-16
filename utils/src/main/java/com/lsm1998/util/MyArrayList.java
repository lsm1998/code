package com.lsm1998.util;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * @作者：刘时明
 * @时间：2019/5/20-8:44
 * @作用：
 */
public class MyArrayList<E> extends MyAbstractList<E> implements MyList<E>, MyRandomAccess, Cloneable, Serializable
{
    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;
    // 空实例共享的数组
    private static final Object[] EMPTY_ELEMENT_DATA = {};
    // 空实例共享的数组，用于默认大小的空实例
    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};
    // 存储数据的数组
    transient Object[] elementData;
    // 当前大小
    private int size;

    public MyArrayList(int initialCapacity)
    {
        if (initialCapacity > 0)
        {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0)
        {
            this.elementData = EMPTY_ELEMENT_DATA;
        } else
        {
            throw new RuntimeException("不可以创建容量小于0的数组");
        }
    }

    public MyArrayList()
    {
        this.elementData = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }

    public MyArrayList(MyCollection<? extends E> c)
    {
        elementData = c.toArray();
        if ((size = elementData.length) != 0)
        {
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else
        {
            this.elementData = EMPTY_ELEMENT_DATA;
        }
    }

    public void trimToSize()
    {
        modCount++;
        if (size < elementData.length)
        {
            elementData = (size == 0)
                    ? EMPTY_ELEMENT_DATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    public void ensureCapacity(int minCapacity)
    {
        int minExpand = (elementData != DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA)
                ? 0
                : DEFAULT_CAPACITY;
        if (minCapacity > minExpand)
        {
            ensureExplicitCapacity(minCapacity);
        }
    }

    private static int calculateCapacity(Object[] elementData, int minCapacity)
    {
        if (elementData == DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA)
        {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    private void ensureCapacityInternal(int minCapacity)
    {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity)
    {
        modCount++;
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private void grow(int minCapacity)
    {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity)
    {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    public boolean contains(Object o)
    {
        return indexOf(o) >= 0;
    }

    public int indexOf(Object o)
    {
        if (o == null)
        {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null)
                    return i;
        } else
        {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    public int lastIndexOf(Object o)
    {
        if (o == null)
        {
            for (int i = size - 1; i >= 0; i--)
                if (elementData[i] == null)
                    return i;
        } else
        {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    public Object clone()
    {
        try
        {
            MyArrayList<?> v = (MyArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e)
        {
            throw new RuntimeException("克隆失败：" + e.getMessage());
        }
    }

    public Object[] toArray()
    {
        return Arrays.copyOf(elementData, size);
    }

    public <T> T[] toArray(T[] a)
    {
        if (a.length < size)
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    E elementData(int index)
    {
        return (E) elementData[index];
    }

    @Override
    public E get(int index)
    {
        rangeCheck(index);
        return elementData(index);
    }

    public E set(int index, E element)
    {
        rangeCheck(index);
        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    public boolean add(E e)
    {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    public void add(int index, E element)
    {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1,
                size - index);
        elementData[index] = element;
        size++;
    }

    public E remove(int index)
    {
        rangeCheck(index);
        modCount++;
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    public boolean remove(Object o)
    {
        if (o == null)
        {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null)
                {
                    fastRemove(index);
                    return true;
                }
        } else
        {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index]))
                {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    private void fastRemove(int index)
    {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        elementData[--size] = null;
    }

    public void clear()
    {
        modCount++;
        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0;
    }

    @Override
    public int size()
    {
        return size;
    }

    protected void removeRange(int fromIndex, int toIndex)
    {
        modCount++;
        int numMoved = size - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                numMoved);
        int newSize = size - (toIndex - fromIndex);
        for (int i = newSize; i < size; i++)
        {
            elementData[i] = null;
        }
        size = newSize;
    }

    private void rangeCheck(int index)
    {
        if (index >= size)
            throw new IndexOutOfBoundsException("数组越界：index=" + index);
    }

    private void rangeCheckForAdd(int index)
    {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("数组越界：index=" + index);
    }

    @Override
    public boolean addAll(int index, MyCollection<? extends E> c)
    {
        rangeCheckForAdd(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(elementData, index, elementData, index + numNew,
                    numMoved);
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    public boolean addAll(MyCollection<? extends E> c)
    {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    public MyListIterator<E> listIterator(int index)
    {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index);
        return new MyListItr(index);
    }

    public MyListIterator<E> listIterator()
    {
        return new MyListItr(0);
    }

    public Iterator<E> iterator()
    {
        return new MyItr();
    }

    private class MyItr implements Iterator<E>
    {
        int cursor;
        int lastRet = -1;
        int expectedModCount = modCount;

        MyItr()
        {
        }

        public boolean hasNext()
        {
            return cursor != size;
        }

        public E next()
        {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove()
        {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try
            {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex)
            {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer)
        {
            Objects.requireNonNull(consumer);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i >= size)
            {
                return;
            }
            final Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
            {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount)
            {
                consumer.accept((E) elementData[i++]);
            }
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
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
            super();
            cursor = index;
        }

        public boolean hasPrevious()
        {
            return cursor != 0;
        }

        public int nextIndex()
        {
            return cursor;
        }

        public int previousIndex()
        {
            return cursor - 1;
        }

        public E previous()
        {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e)
        {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try
            {
                MyArrayList.this.set(lastRet, e);
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
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex)
            {
                throw new ConcurrentModificationException();
            }
        }
    }

    public MyList<E> subList(int fromIndex, int toIndex)
    {
        subListRangeCheck(fromIndex, toIndex, size);
        return new MySubList(this, 0, fromIndex, toIndex);
    }

    static void subListRangeCheck(int fromIndex, int toIndex, int size)
    {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
    }

    private class MySubList extends MyAbstractList<E> implements MyRandomAccess
    {
        private final MyAbstractList<E> parent;
        private final int parentOffset;
        private final int offset;
        int size;

        MySubList(MyAbstractList<E> parent,
                  int offset, int fromIndex, int toIndex)
        {
            this.parent = parent;
            this.parentOffset = fromIndex;
            this.offset = offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = MyArrayList.this.modCount;
        }

        public E set(int index, E e)
        {
            rangeCheck(index);
            checkForComodification();
            E oldValue = MyArrayList.this.elementData(offset + index);
            MyArrayList.this.elementData[offset + index] = e;
            return oldValue;
        }

        public E get(int index)
        {
            rangeCheck(index);
            checkForComodification();
            return MyArrayList.this.elementData(offset + index);
        }

        public int size()
        {
            checkForComodification();
            return this.size;
        }

        public void add(int index, E e)
        {
            rangeCheckForAdd(index);
            checkForComodification();
            parent.add(parentOffset + index, e);
            this.modCount = parent.modCount;
            this.size++;
        }

        public E remove(int index)
        {
            rangeCheck(index);
            checkForComodification();
            E result = parent.remove(parentOffset + index);
            this.modCount = parent.modCount;
            this.size--;
            return result;
        }

        protected void removeRange(int fromIndex, int toIndex)
        {
            checkForComodification();
            parent.removeRange(parentOffset + fromIndex,
                    parentOffset + toIndex);
            this.modCount = parent.modCount;
            this.size -= toIndex - fromIndex;
        }

        public boolean addAll(MyCollection<? extends E> c)
        {
            return addAll(this.size, c);
        }

        public boolean addAll(int index, MyCollection<? extends E> c)
        {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize == 0)
                return false;

            checkForComodification();
            parent.addAll(parentOffset + index, c);
            this.modCount = parent.modCount;
            this.size += cSize;
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
            final int offset = this.offset;

            return new MyListIterator<E>()
            {
                int cursor = index;
                int lastRet = -1;
                int expectedModCount = MyArrayList.this.modCount;

                public boolean hasNext()
                {
                    return cursor != MySubList.this.size;
                }

                @SuppressWarnings("unchecked")
                public E next()
                {
                    checkForComodification();
                    int i = cursor;
                    if (i >= MySubList.this.size)
                        throw new NoSuchElementException();
                    Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i + 1;
                    return (E) elementData[offset + (lastRet = i)];
                }

                public boolean hasPrevious()
                {
                    return cursor != 0;
                }

                @SuppressWarnings("unchecked")
                public E previous()
                {
                    checkForComodification();
                    int i = cursor - 1;
                    if (i < 0)
                        throw new NoSuchElementException();
                    Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i;
                    return (E) elementData[offset + (lastRet = i)];
                }

                @SuppressWarnings("unchecked")
                public void forEachRemaining(Consumer<? super E> consumer)
                {
                    Objects.requireNonNull(consumer);
                    final int size = MySubList.this.size;
                    int i = cursor;
                    if (i >= size)
                    {
                        return;
                    }
                    final Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                    {
                        throw new ConcurrentModificationException();
                    }
                    while (i != size && modCount == expectedModCount)
                    {
                        consumer.accept((E) elementData[offset + (i++)]);
                    }
                    lastRet = cursor = i;
                    checkForComodification();
                }

                public int nextIndex()
                {
                    return cursor;
                }

                public int previousIndex()
                {
                    return cursor - 1;
                }

                public void remove()
                {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();
                    try
                    {
                        MySubList.this.remove(lastRet);
                        cursor = lastRet;
                        lastRet = -1;
                        expectedModCount = MyArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex)
                    {
                        throw new ConcurrentModificationException();
                    }
                }

                public void set(E e)
                {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try
                    {
                        MyArrayList.this.set(offset + lastRet, e);
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
                        MySubList.this.add(i, e);
                        cursor = i + 1;
                        lastRet = -1;
                        expectedModCount = MyArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex)
                    {
                        throw new ConcurrentModificationException();
                    }
                }

                final void checkForComodification()
                {
                    if (expectedModCount != MyArrayList.this.modCount)
                        throw new ConcurrentModificationException();
                }
            };
        }

        public MyList<E> subList(int fromIndex, int toIndex)
        {
            subListRangeCheck(fromIndex, toIndex, size);
            return new MySubList(this, offset, fromIndex, toIndex);
        }

        private void rangeCheck(int index)
        {
            if (index < 0 || index >= this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private void rangeCheckForAdd(int index)
        {
            if (index < 0 || index > this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index)
        {
            return "Index: " + index + ", Size: " + this.size;
        }

        private void checkForComodification()
        {
            if (MyArrayList.this.modCount != this.modCount)
                throw new ConcurrentModificationException();
        }

        public Spliterator<E> spliterator()
        {
            checkForComodification();
            return new MyArrayListSpliterator<>(MyArrayList.this, offset,
                    offset + this.size, this.modCount);
        }
    }

    @Override
    public Spliterator<E> spliterator()
    {
        return new MyArrayListSpliterator<>(this, 0, -1, 0);
    }

    static final class MyArrayListSpliterator<E> implements Spliterator<E>
    {
        private final MyArrayList<E> list;
        private int index;
        private int fence;
        private int expectedModCount;

        MyArrayListSpliterator(MyArrayList<E> list, int origin, int fence,
                               int expectedModCount)
        {
            this.list = list;
            this.index = origin;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }

        private int getFence()
        {
            int hi;
            MyArrayList<E> lst;
            if ((hi = fence) < 0)
            {
                if ((lst = list) == null)
                    hi = fence = 0;
                else
                {
                    expectedModCount = lst.modCount;
                    hi = fence = lst.size;
                }
            }
            return hi;
        }

        public MyArrayListSpliterator<E> trySplit()
        {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null :
                    new MyArrayListSpliterator<E>(list, lo, index = mid,
                            expectedModCount);
        }

        public boolean tryAdvance(Consumer<? super E> action)
        {
            if (action == null)
                throw new NullPointerException();
            int hi = getFence(), i = index;
            if (i < hi)
            {
                index = i + 1;
                E e = (E) list.elementData[i];
                action.accept(e);
                if (list.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        public void forEachRemaining(Consumer<? super E> action)
        {
            int i, hi, mc;
            MyArrayList<E> lst;
            Object[] a;
            if (action == null)
                throw new NullPointerException();
            if ((lst = list) != null && (a = lst.elementData) != null)
            {
                if ((hi = fence) < 0)
                {
                    mc = lst.modCount;
                    hi = lst.size;
                } else
                    mc = expectedModCount;
                if ((i = index) >= 0 && (index = hi) <= a.length)
                {
                    for (; i < hi; ++i)
                    {
                        E e = (E) a[i];
                        action.accept(e);
                    }
                    if (lst.modCount == mc)
                        return;
                }
            }
            throw new ConcurrentModificationException();
        }

        public long estimateSize()
        {
            return (long) (getFence() - index);
        }

        public int characteristics()
        {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }
}
