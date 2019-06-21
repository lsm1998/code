
import com.lsm1998.util.MyLinkedList;

/**
 * @作者：刘时明
 * @时间：2019/5/24-17:10
 * @作用：
 */
public class Test
{
    public static void main(String[] args)
    {
        MyLinkedList<String> list=new MyLinkedList<>();
        list.add("www");
        list.addFirst("www");
        list.addLast("www");
        list.removeFirst();
    }
}
