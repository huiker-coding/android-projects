package com.yinliubao.client.functions.login.bean;

/****************************************************************************************************
 * {
 * "terminal_userid","终端用户ID",
 * "user_pwd","登录密码,md5加密",
 * }
 ******************************************************************************************************/
public class ReqLoginVo {
    /**
     * 用户手机号码
     */
    private String terminal_userid;

    /**
     * 用户登录密码
     */
    private String user_pwd;

    public String getTerminal_userid() {
        return terminal_userid;
    }

    public void setTerminal_userid(String terminal_userid) {
        this.terminal_userid = terminal_userid;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

}
