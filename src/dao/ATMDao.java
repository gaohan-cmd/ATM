package dao;

import bean.ATM;

import java.sql.Connection;

public interface ATMDao {
    //注册
    public boolean redATM(Connection conn,ATM a);
    //登录
    public ATM loginAtm(Connection conn,String atmCode, String atmPwd);
    //存取款操作
    public boolean updateMoneyDao(Connection conn, ATM a);
    //根据id查询当前对象的余额
    public ATM findById(Connection conn,int atmId);
    //查询转账账户
    public ATM getTransferCode(Connection conn,String otherCode);
    //自动生成卡号
    //查看当前最大的id   id+1  拼接888  就是我下一个用户的账号
    /*888001 888002  888003
    先得到当前最大id
    select max(atm_id) from  atm;
    下一个人是888006*/
    //获取当前最大的id
    public int getMaxId(Connection conn);
}
