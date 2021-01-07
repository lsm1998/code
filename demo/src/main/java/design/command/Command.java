package design.command;

public interface Command<E>
{
    E resultMethod(E[] target);
}
