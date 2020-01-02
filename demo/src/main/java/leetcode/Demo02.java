/*
 * 作者：刘时明
 * 时间：2020/1/1-12:25
 * 作用：
 */
package leetcode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Demo02
{
    static class ListNode
    {
        int val;
        ListNode next;

        ListNode(int x)
        {
            val = x;
        }
    }

    private static ListNode addTwoNumbers(ListNode l1, ListNode l2)
    {
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        while (l1 != null)
        {
            left.insert(0, l1.val);
            l1 = l1.next;
        }
        while (l2 != null)
        {
            right.insert(0, l2.val);
            l2 = l2.next;
        }
        BigInteger sum = new BigDecimal(left.toString()).add(new BigDecimal(right.toString())).toBigInteger();
        LinkedList<Integer> list = new LinkedList<>();
        while (sum.compareTo(new BigInteger(String.valueOf(10))) > 0)
        {
            int temp = sum.mod(new BigInteger(String.valueOf(10))).intValue();
            list.add(temp);
            sum = sum.divide(new BigInteger(String.valueOf(10)));
        }
        if (sum.compareTo(new BigInteger(String.valueOf(10))) == 0)
        {
            list.add(0);
            list.add(1);
        } else
        {
            int temp = sum.mod(new BigInteger(String.valueOf(10))).intValue();
            list.add(temp);
        }
        l1 = new ListNode(list.get(0));
        ListNode temp = null;
        for (int i = 1; i < list.size(); i++)
        {
            if (temp == null)
            {
                temp = new ListNode(list.get(i));
                l1.next = temp;
            } else
            {
                while (temp.next != null)
                {
                    temp = temp.next;
                }
                temp.next = new ListNode(list.get(i));
            }
        }
        return l1;
    }

    public static void main(String[] args)
    {
        ListNode l1 = new ListNode(9);
        //l1.next = new ListNode(8);
        //l1.next.next= new ListNode(9);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(9);
        l2.next.next = new ListNode(9);
        l2.next.next.next = new ListNode(9);
        l2.next.next.next.next = new ListNode(9);
        ListNode result = addTwoNumbers(l1, l2);
        while (result != null)
        {
            System.out.println(result.val);
            result = result.next;
        }
    }
}
