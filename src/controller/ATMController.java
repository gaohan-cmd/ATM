package controller;

import bean.ATM;
import service.ATMMenu;
import service.ATMService;
import service.impl.ATMServiceImpl;

import java.util.Scanner;

public class ATMController {
    public static void main(String[] args) {
        ATMMenu atmMenu = new ATMMenu();
        ATMService atmService = new ATMServiceImpl();
        Scanner sc = new Scanner(System.in);
        while (true){
            int index = atmMenu.regAndLoginMenu(sc);
            if (index==1) {
                boolean b = atmService.regATMService(sc);
                if (b) {
                    System.out.println("注册成功");
                }else {
                    System.out.println("注册失败");
                }
            }else if (index==2){
                ATM a = atmService.loginATMService(sc);
                if (a!=null) {
                    atmService.UserService(sc,a);
                }else {
                    System.out.println("账号或密码错误");
                }
            }else {
                break;
            }
        }

    }
}
