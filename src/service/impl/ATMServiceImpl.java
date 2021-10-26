package service.impl;

import bean.ATM;
import dao.ATMDao;
import dao.impl.ATMDaoImpl;
import service.ATMMenu;
import service.ATMService;
import util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ATMServiceImpl implements ATMService {
    ATMMenu atmMenu = new ATMMenu();
    ATMDao atmDao = new ATMDaoImpl();
    Connection conn = null;
    //注册的功能
    @Override
    public boolean regATMService(Scanner sc) {
        conn = DBUtil.getConn();
        ATM a = atmMenu.regMenu(sc);
        String code = getCode();
        a.setAtmCode(code);
        boolean b = atmDao.redATM(conn,a);
        if(b){
            System.out.println("您的卡号是："+code);
        }
        return b;
    }
    //自动获取账号的方法
    @Override
    public String getCode() {
        conn = DBUtil.getConn();
        String code = "777";
        String maxId = ""+(atmDao.getMaxId(conn)+1);
        //判断补0的个数
        if(maxId.length()==1){
            code += "00"+maxId;
        }else if(maxId.length()==2){
            code += "0"+maxId;
        }else if(maxId.length()==3){
            code +=maxId;
        }
        return code;
    }
    //登录的功能
    @Override
    public ATM loginATMService(Scanner sc) {
        conn = DBUtil.getConn();
        ATM a = atmMenu.loginMenu(sc);
        ATM a1 = atmDao.loginAtm(conn,a.getAtmCode(),a.getAtmPwd());
        return a1;
    }
    //存款
    @Override
    public ATM depATM(Scanner sc, ATM a) {
        ATM user = a;
        System.out.println("请输入存款钱数：");
        double money = 0;
        while (true) {
            money = sc.nextDouble();
            if (money<0) {
                System.out.println("存款必须是正数");
            }else {
                break;
            }
        }
        conn = DBUtil.getConn();
        try {
            conn.setAutoCommit(false);
            user.setAtmMoney(user.getAtmMoney()+money);
            boolean b = atmDao.updateMoneyDao(conn,user);
            if (b) {
                conn.commit();
                return user;
            }else {
                conn.rollback();
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
    //取款
    @Override
    public ATM withATM(Scanner sc, ATM a) {
        ATM user = a;
        System.out.println("请输入取款金额：");
        double money = 0;
        while (true) {
            money = sc.nextDouble();
            if (money<0){
                System.out.println("输入金额必须是正数");
            }else if (money>user.getAtmMoney()){
                System.out.println("余额不足");
            }else{
                break;
            }
        }
        conn = DBUtil.getConn();
        try {
            conn.setAutoCommit(false);
            user.setAtmMoney(user.getAtmMoney()-money);
            boolean b = atmDao.updateMoneyDao(conn,user);
            if (b) {
                conn.commit();
                return user;
            }else {
                conn.rollback();
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
    //查询
    @Override
    public ATM findById(int id) {
        conn = DBUtil.getConn();
        ATM a = atmDao.findById(conn,id);
        System.out.println("-------------------------");
        System.out.println("| 尊敬的"+a.getAtmName()+"  |");
        System.out.println("| 您的当前余额为："+a.getAtmMoney()+"  |");
        System.out.println("--------------------------");
        return a;
    }
    //转账
    @Override
    public ATM transferMoney(Scanner sc,ATM a) {
        //操作对象
        ATM user = a;
        System.out.println("请输入收款账户：");
        ATM transferUser = null;
        while(true){
            String code = sc.next();
            //判断当前账户是否存在  查询数据库
            transferUser= atmDao.getTransferCode(conn,code);
            if(transferUser==null){
                System.out.println("转账账户不存在，请重新输入！！！");
            }else{
                break;
            }
        }
        //开始转账
        System.out.println("请输入转账金额：");
        double transferMoney = 0;
        while(true){
            transferMoney = sc.nextDouble();
            if(transferMoney<0 ){
                System.out.println("转账金额只能是正数，请重新输入");
            }else{
                if(transferMoney> user.getAtmMoney()){
                    System.out.println("余额不足");
                }else{
                    break;
                }
            }
        }
        System.out.println("-----------------------");
        System.out.println("|转账账户为："+transferUser.getAtmCode()+"  |");
        System.out.println("|收款人为："+transferUser.getAtmName()+"  |");
        System.out.println("|转账金额为："+transferMoney+"  |");
        System.out.println("-----------------------");
        System.out.println("请确定信息,确定输入1，取消输入2");
        int  index = sc.nextInt();
        try {
            if(index==1){
                //转账  先扣除A账户的钱
                conn.setAutoCommit(false);
                user.setAtmMoney(user.getAtmMoney()-transferMoney);
                boolean  b = atmDao.updateMoneyDao(conn,user);//A账户
                if(b){
                    //A账户扣钱成功
                    //B账户加钱
                    System.out.println(transferUser.getAtmMoney());
                    transferUser.setAtmMoney(transferUser.getAtmMoney()+transferMoney);
                    boolean b1 = atmDao.updateMoneyDao(conn,transferUser);
                    if(b1){
                        conn.commit();
                        System.out.println("转账成功");
                        return user;
                    }else{
                        //B账户加钱失败
                        //需要把A账户的前还给A账户
                        conn.rollback();
                        System.out.println("B账户没有收到前，但是A账户扣钱成功!!!!");
                        return user;
                    }
                }else{
                    //A账户减钱失败
                    conn.rollback();
                    System.out.println("转账失败！！！");
                    return a;
                }
            }else{
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return a;
    }
    //业务操作
    @Override
    public void UserService(Scanner sc, ATM a) {
        ATM user = a;
        //操作循环开始
        while (true) {
            int index=atmMenu.welcomeATM(sc,user);
            //存款
            if (index==1) {
                user = depATM(sc,user);
            }
            //取款
            if (index==2){
                user = withATM(sc,user);
            }
            //查询
            if (index==3) {
                user = findById(user.getAtmId());
            }
            //转账
            if (index==4){
                user = transferMoney(sc,user);
            }
            //退出
            if (index==5){
                break;
            }
        }
    }
}
