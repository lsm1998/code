package com.lsm1998.util.json;

public class ObjectParser
{
    private final String json;
    private int pos = 0;

    public ObjectParser(String json)
    {
        this.json = json;
    }

    public int getPos()
    {
        return pos;
    }

    public Object nextObject()
    {
        if (pos >= json.length())
        {
            throw new JsonException("unexpected string end.");
        }
        char c = getChar();
        switch (c)
        {
            case 't':
            case 'T'://可能是true
                pos++;
                if (checkChars('r', 'u', 'e'))
                {
                    return true;
                }
                throw new JsonException("invalid boolean value");
            case 'f':
            case 'F': //可能是false
                pos++;
                if (checkChars('a', 'l', 's', 'e'))
                {
                    return false;
                }
                throw new JsonException("invalid boolean value");
            case 'n':
            case 'N': //可能是null
                pos++;
                if (checkChars('u', 'l', 'l'))
                {
                    return null;
                }
                throw new JsonException("invalid null value");
        }

        Object number = parseNumber(c);
        if (number != null)
        {
            return number;
        }

        if (c == '\"')
        {
            return parseString(c);
        }

        if (c == '[')
        {
            return parseArray();
        }

        if (c == '{')
        {
            return parseObject();
        }


        throw new JsonException("invalid value : " + c);
    }

    //检查是否有剩余字符
    private void checkPos()
    {
        if (pos >= json.length())
        {
            throw new JsonException("unexpected string end.");
        }
    }


    public static boolean isSpace(char c)
    {
        return c == ' ' || c == '\r' || c == '\n';
    }

    //方法得到当前字符，忽略空格、换行符
    private char getChar()
    {
        char c = json.charAt(pos);
        while (isSpace(c))
        {
            pos++;
            c = getCurrentChar();
        }
        return c;
    }

    //得到当前字符，包括空格、换行符
    private char getCurrentChar()
    {
        checkPos();
        return json.charAt(pos);
    }

    //得到当前字符，包括空格、换行符。将指针指向下一个字符
    private char getCurrentCharNext()
    {
        char c = getCurrentChar();
        pos++;
        checkPos();
        return c;
    }


    //得到当前字符，忽略空格、换行符。将指针指向下一个字符
    private char getCharNext()
    {
        char c = getChar();
        pos++;
        checkPos();
        return c;
    }


    private String parseString(char c)
    {
        //字符串
        pos++;
        StringBuilder sb = new StringBuilder();
        while (true)
        {
            c = getCurrentCharNext();
            if (c == '\\')
            {   //处理转义字符
                char next = getCurrentChar();
                switch (next)
                {
                    case 'r':
                        sb.append('\r');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case 'b':
                        sb.append('\b');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case '\"':
                        sb.append('\"');
                        break;
                    default:
                        throw new JsonException("unknown escape char : " + next);
                }
                pos++;
            } else if (c == '\"')
            {
                break;
            } else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    private Object parseNumber(char c)
    {
        StringBuilder numberBuilder = new StringBuilder();
        boolean containsPoint = false;
        while (c >= '0' && c <= '9' || c == '+' || c == '-' || c == '.')
        {
            if (c == '.')
            {
                containsPoint = true;
            }
            numberBuilder.append(c);
            pos++;
            c = getCurrentChar();
        }
        if (numberBuilder.length() > 0)
        {
            String numberString = numberBuilder.toString();
            try
            {
                if (containsPoint)
                {
                    return Double.valueOf(numberString);
                } else
                {
                    return Integer.valueOf(numberString);
                }
            } catch (NumberFormatException e)
            {
                throw new JsonException("invalid number value:" + numberString);
            }
        }
        return null;
    }


    private JsonArray parseArray()
    {
        pos++;
        checkPos();
        JsonArray jsonArray = new JsonArray();
        boolean flag = false;       //是否需要用逗号分割
        char c = getChar();
        while (c != ']')
        {
            if (flag)
            {
                if (c != ',')
                {
                    throw new JsonException("illegal json array.");
                }
                pos++;
            }
            jsonArray.add(nextObject());
            flag = true;
            if (pos >= json.length())
            {
                throw new JsonException("unexpected string end.");
            }
            c = getChar();
        }
        pos++;
        return jsonArray;
    }

    private JsonObject parseObject()
    {
        JsonObject jsonObject = new JsonObject();
        boolean flag = false;   //是否需要用逗号分割
        char c = getCharNext();
        while (c != '}')
        {
            if (flag)
            {
                if (c != ',')
                {
                    throw new JsonException("illegal json object.");
                }
                pos++;
            }
            Object key = nextObject();
            if (key == null || !(key instanceof String))
            {
                throw new JsonException("illegal json object.key must be a string:" + key);
            }
            c = getCharNext();
            if (c != ':')
            {
                throw new JsonException("illegal json object.");
            }
            Object value = nextObject();
            jsonObject.put((String) key, value);
            flag = true;
            c = getChar();
        }
        pos++;
        return jsonObject;
    }

    private boolean checkChars(char... chars)
    {
        for (char ch : chars)
        {
            char c = getCurrentCharNext();
            if (Character.toLowerCase(ch) != Character.toLowerCase(c))
            {
                return false;
            }
        }
        return true;
    }
}
