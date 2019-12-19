package com.oo.util;

public interface Cmd
{
    // 好友上线通知
    int CMD_ONLINE = 1000;
    // 离线
    int CMD_OFFLINE = 1001;
    // 群聊
    int CMD_ALL = 1002;
    // 任何人上线
    int CMD_AllONLINE = 1003;
    // 发送信息
    int CMD_SEND = 1004;
    // 发送文件
    int CMD_FILE = 1005;
    // 抖动
    int CMD_SHAKE = 1006;
    // 添加好友
    int CMD_ADDFRI = 1007;
    // 同意添加
    int CMD_ARGEE = 1008;
    // 拒绝添加
    int CMD_REFUSE = 1009;
    // 请求语音
    int CMD_YY = 1010;
    // 删除好友
    int CMD_DELFRIEND = 1011;
    // 接受语音
    int CMD_YYOK = 1012;
    // 拒绝语音
    int CMD_YYON = 1013;
}
