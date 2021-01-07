package design.command;

import java.util.Arrays;

public class AddCommand implements Command<Integer>
{
    @Override
    public Integer resultMethod(Integer[] target)
    {
        return Arrays.stream(target).mapToInt(e -> e).sum();
    }
}
