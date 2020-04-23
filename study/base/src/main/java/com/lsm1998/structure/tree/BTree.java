package com.lsm1998.structure.tree;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-22 08:52
 **/
public class BTree<K extends Comparable<K>,V>
{
    /**
     * B树中的节点。
     */
    private static class BTreeNode<K, V>
    {
        /**
         * 节点的项，按键非降序存放
         */
        private Entry<K, V>[] entries;
        /**
         * 内节点的子节点
         */
        private BTreeNode<K, V>[] children;
        /**
         * 是否为叶子节点
         */
        private boolean leaf;

        private BTreeNode()
        {
            entries = new Entry[0];
            children = new BTreeNode[0];
            leaf = false;
        }
    }

    private static class Entry<K, V>
    {
        private K key;
        private V value;

        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString()
        {
            return key + ":" + value;
        }
    }
}
