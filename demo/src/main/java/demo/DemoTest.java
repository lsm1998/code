/*
 * 作者：刘时明
 * 时间：2019/12/18-21:08
 * 作用：
 */
package demo;

public class DemoTest
{
    public static void main(String[] args)
    {
        String[] str = {"()", "()[]{}", "(]", "([)]", "{[]}"};

        for (String s : str)
        {
            System.out.println(s + "==>" + isOk(s));
        }
    }

    private static boolean isOk(String str)
    {
        // 计数器
        int count = 0;
        // true代表上一次操作是左方向
        boolean flag = true;
        // 连续操作的次数
        int sum = 0;
        // 最后一次
        char last = 0;
        for (int i = 0; i < str.length(); i++)
        {
            int weight = getWeight(str.charAt(i));
            count += weight;
            if (weight > 0)
            {
                if (flag)
                {
                    sum++;
                } else
                {
                    sum = 0;
                }
                flag = true;
                last = str.charAt(i);
            } else
            {
                sum--;
                if (flag)
                {
                    if (getWeight(last) + weight != 0||(sum == 0 && count != 0))
                    {
                        return false;
                    }
                }
                flag = false;
            }
        }
        return true;
    }

    private static int getWeight(char c)
    {
        switch (c)
        {
            case '(':
                return 1;
            case '[':
                return 2;
            case '{':
                return 3;
            case ')':
                return -1;
            case ']':
                return -2;
            case '}':
                return -3;
            default:
                throw new RuntimeException("不支持的字符");
        }
    }
}
