package com.lsm1998.structure.list;

import java.util.*;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-20 18:06
 **/
public abstract class MyAbstractList<E> implements MyList<E>
{
    protected transient int modCount = 0;

    protected MyAbstractList(){}

    @Override
    public boolean isEmpty()
    {
        return this.size()==0;
    }

    @Override
    public int indexOf(Object o)
    {
        MyListIterator<E> it = this.listIterator();
        if (o == null)
        {
            while(it.hasNext())
            {
                if (it.next() == null)
                {
                    return it.previousIndex();
                }
            }
        } else {
            while(it.hasNext())
            {
                if (o.equals(it.next()))
                {
                    return it.previousIndex();
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        MyListIterator<E> it = this.listIterator(this.size());
        if (o == null)
        {
            while(it.hasPrevious())
            {
                if (it.previous() == null) {
                    return it.nextIndex();
                }
            }
        } else {
            while(it.hasPrevious())
            {
                if (o.equals(it.previous())) {
                    return it.nextIndex();
                }
            }
        }
        return -1;
    }

    @Override
    public int hashCode()
    {
        int hashCode = 1;
        Object e;
        for(Iterator<E> var2 = this.iterator(); var2.hasNext(); hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode()))
        {
            e = var2.next();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MyList)) {
            return false;
        } else {
            MyListIterator<E> e1 = this.listIterator();
            MyListIterator<E> e2 = ((MyList<E>)o).listIterator();
            while(true)
            {
                if (e1.hasNext() && e2.hasNext())
                {
                    E o1 = e1.next();
                    Object o2 = e2.next();
                    if (o1 == null) {
                        if (o2 == null) {
                            continue;
                        }
                    } else if (o1.equals(o2))
                    {
                        continue;
                    }
                    return false;
                }
                return !e1.hasNext() && !e2.hasNext();
            }
        }
    }

    static void subListRangeCheck(int fromIndex, int toIndex, int size)
    {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        } else if (toIndex > size) {
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        } else if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
    }

    public MyListIterator<E> listIterator()
    {
        return this.listIterator(0);
    }

    public MyListIterator<E> listIterator(int index)
    {
        this.rangeCheckForAdd(index);
        return new MyAbstractList<E>.ListItr(index);
    }

    protected void removeRange(int fromIndex, int toIndex)
    {
        MyListIterator<E> it =this.listIterator(fromIndex);
        int i = 0;
        for(int n = toIndex - fromIndex; i < n; ++i)
        {
            it.next();
            it.remove();
        }
    }

    private void rangeCheckForAdd(int index)
    {
        if (index < 0 || index > this.size())
        {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index)
    {
        return "Index: " + index + ", Size: " + this.size();
    }

    private class ListItr extends MyAbstractList<E>.Itr implements MyListIterator<E> {
        ListItr(int index)
        {
            super();
            this.cursor = index;
        }

        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        public E previous() {
            this.checkForComodification();

            try {
                int i = this.cursor - 1;
                E previous = MyAbstractList.this.get(i);
                this.lastRet = this.cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException var3) {
                this.checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            return this.cursor;
        }

        public int previousIndex() {
            return this.cursor - 1;
        }

        public void set(E e) {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            } else {
                this.checkForComodification();
                try
                {
                    MyAbstractList.this.set(this.lastRet, e);
                    this.expectedModCount = MyAbstractList.this.modCount;
                } catch (IndexOutOfBoundsException var3) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        public void add(E e) {
            this.checkForComodification();

            try {
                int i = this.cursor;
                MyAbstractList.this.add(i, e);
                this.lastRet = -1;
                this.cursor = i + 1;
                this.expectedModCount = MyAbstractList.this.modCount;
            } catch (IndexOutOfBoundsException var3) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class Itr implements Iterator<E> {
        int cursor = 0;
        int lastRet = -1;
        int expectedModCount;

        private Itr()
        {
            this.expectedModCount = MyAbstractList.this.modCount;
        }

        public boolean hasNext()
        {
            return this.cursor != MyAbstractList.this.size();
        }

        public E next()
        {
            this.checkForComodification();

            try {
                int i = this.cursor;
                E next = MyAbstractList.this.get(i);
                this.lastRet = i;
                this.cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException var3) {
                this.checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove()
        {
            if (this.lastRet < 0)
            {
                throw new IllegalStateException();
            } else
                {
                this.checkForComodification();

                try {
                    MyAbstractList.this.remove(this.lastRet);
                    if (this.lastRet < this.cursor) {
                        --this.cursor;
                    }

                    this.lastRet = -1;
                    this.expectedModCount = MyAbstractList.this.modCount;
                } catch (IndexOutOfBoundsException var2) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        final void checkForComodification()
        {
            if (MyAbstractList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
