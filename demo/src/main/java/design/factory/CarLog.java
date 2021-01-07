package design.factory;

public enum CarLog
{
    BWM("宝马"), Benz("奔驰"), Mazda("马自达");

    private final String value;

    CarLog(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
