package service;

import bean.ATM;

import java.util.Scanner;

public interface ATMService {
    //注册的功能
    public boolean  regATMService(Scanner sc);
    //自动获取账号的方法
    public String getCode();
    //登录的功能
    public ATM loginATMService(Scanner sc);
    //存款
    public ATM depATM(Scanner sc,ATM a);
    //取款
    public ATM withATM(Scanner sc,ATM a);
    //查询
    public ATM findById(int id);
    //转账
    public ATM transferMoney(Scanner sc,ATM a);
    //业务操作
    public void UserService(Scanner sc, ATM a);
}
