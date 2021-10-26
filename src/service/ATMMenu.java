package service;

import bean.ATM;

import java.util.Scanner;

public class ATMMenu {
    //登录注册欢迎界面
    public int  regAndLoginMenu(Scanner sc){
        System.out.println("--------------------");
        System.out.println("|    1,注册         |");
        System.out.println("|    2,登录         |");
        System.out.println("|    3,退出         |");
        System.out.println("--------------------");
        System.out.println("请输入操作序号：");
        int index = sc.nextInt();
        return index;
    }
    //注册界面
    public ATM regMenu(Scanner sc){

        System.out.println("请输入密码：");
        String  pwd = sc.next();
        System.out.println("请输入名字：");
        String  name = sc.next();
        ATM a = new ATM();
        //此时改对象没有账号
        a.setAtmName(name);
        a.setAtmPwd(pwd);
        a.setAtmMoney(0);
        return a;
    }
    //登录的界面
    public ATM loginMenu(Scanner sc){
        System.out.println("请输入账号：");
        String  code = sc.next();
        System.out.println("请输入密码：");
        String  pwd = sc.next();
        ATM logina = new ATM();
        logina.setAtmCode(code);
        logina.setAtmPwd(pwd);
        return logina;
    }
    //登录成功界面
    public int  welcomeATM(Scanner sc,ATM a){
        System.out.println("--------------------");
        System.out.println("---欢迎"+a.getAtmName()+"使用ATM系统------");
        System.out.println("|    1,存款         |");
        System.out.println("|    2,取款         |");
        System.out.println("|    3,查看         |");
        System.out.println("|    4,转账         |");
        System.out.println("|    5,退出         |");
        System.out.println("--------------------");
        System.out.println("请输入操作序号：");
        int index = sc.nextInt();
        return index;
    }
}
