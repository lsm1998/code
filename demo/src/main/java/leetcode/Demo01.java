/*
 * 作者：刘时明
 * 时间：2020/1/1-12:16
 * 作用：
 */
package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Demo01
{
    private static int[] towSum(int[] nums, int target)
    {
        // key=存储的数值，value=索引
        Map<Integer, Integer> map = new HashMap<>();
        int temp;
        Integer index;
        for (int i = 0; i < nums.length; i++)
        {
            temp = target - nums[i];
            index = map.get(temp);
            if (index != null)
            {
                return new int[]{i, index};
            }else
            {
                map.put(nums[i],i);
            }
        }
        return null;
    }

    public static void main(String[] args)
    {
        System.out.println(Arrays.toString(towSum(new int[]{2,7,11,15},9)));
    }
}
