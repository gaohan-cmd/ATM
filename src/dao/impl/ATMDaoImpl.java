package dao.impl;

import bean.ATM;
import dao.ATMDao;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ATMDaoImpl implements ATMDao {
    PreparedStatement psta = null;
    ResultSet rs = null;
    //注册
    @Override
    public boolean redATM(Connection conn, ATM a) {
        try {
            String sql="insert into atm(atm_code,atm_pwd,atm_name,atm_money,atm_time) values(?,?,?,?,now())";
            psta = conn.prepareStatement(sql);
            psta.setString(1,a.getAtmCode());
            psta.setString(2,a.getAtmPwd());
            psta.setString(3,a.getAtmName());
            psta.setDouble(4,a.getAtmMoney());
            int num = psta.executeUpdate();
            if(num>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DBUtil.closePsta(psta);
        }
        return false;
    }
    //登录
    @Override
    public ATM loginAtm(Connection conn, String atmCode, String atmPwd) {
        ATM a = null;
        try {
            String sql ="select *  from atm where atm_code=? and atm_pwd=?";
            psta = conn.prepareStatement(sql);
            //拼装sql
            psta.setString(1,atmCode);
            psta.setString(2,atmPwd);
            rs = psta.executeQuery();
            while(rs.next()){
                a=new ATM();
                a.setAtmId(rs.getInt("atm_id"));
                a.setAtmCode(rs.getString("atm_code"));
                a.setAtmPwd(rs.getString("atm_pwd"));
                a.setAtmMoney(rs.getDouble("atm_money"));
                a.setAtmName(rs.getString("atm_name"));
                a.setAtmTime(rs.getDate("atm_time"));
            }
        }   catch (Exception e) {
            e.printStackTrace();
        }finally{
            DBUtil.closeRs(rs);
            DBUtil.closePsta(psta);
        }
        return a;
    }
    //存取款
    @Override
    public boolean updateMoneyDao(Connection conn, ATM a) {
        try{
            String sql = "update atm set atm_money = ? where atm_id=?";
            psta = conn.prepareStatement(sql);
            psta.setDouble(1,a.getAtmMoney());
            psta.setInt(2,a.getAtmId());
            int num = psta.executeUpdate();
            if(num>0){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closePsta(psta);
        }
        return false;
    }
    //根据id查询当前对象的余额
    @Override
    public ATM findById(Connection conn, int atmId) {
        try{
            String sql = "select *  from atm where atm_id=?";
            psta = conn.prepareStatement(sql);
            psta.setInt(1,atmId);
            rs = psta.executeQuery();
            //单挑查询  结果是唯一的
            if(rs.next()){
                ATM a = new  ATM();
                a.setAtmId(rs.getInt("atm_id"));
                a.setAtmCode(rs.getString("atm_code"));
                a.setAtmPwd(rs.getString("atm_pwd"));
                a.setAtmMoney(rs.getDouble("atm_money"));
                a.setAtmName(rs.getString("atm_name"));
                a.setAtmTime(rs.getDate("atm_time"));
                return a;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeRs(rs);
            DBUtil.closePsta(psta);
        }
        return null;
    }
    //查询转账账户
    @Override
    public ATM getTransferCode(Connection conn, String otherCode) {
        try{
            //转账账户我们不需要知道太多信息
            String sql = "select atm_id,atm_code,atm_name,atm_money from atm where atm_code=?";
            psta = conn.prepareStatement(sql);
            psta.setString(1,otherCode);
            rs = psta.executeQuery();
            //单挑查询  结果是唯一的
            if(rs.next()){
                ATM a = new  ATM();
                a.setAtmId(rs.getInt("atm_id"));
                a.setAtmCode(rs.getString("atm_code"));
                a.setAtmName(rs.getString("atm_name"));
                a.setAtmMoney(rs.getDouble("atm_money"));
                return a;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           DBUtil.closeRs(rs);
           DBUtil.closePsta(psta);
        }
        return null;
    }
    //自动生成卡号
    @Override
    public int getMaxId(Connection conn) {
        try{
            String sql = "select max(atm_id) maxId from  atm ";
            psta = conn.prepareStatement(sql);
            rs = psta.executeQuery();
            if(rs.next()){
                //int maxId = rs.getInt(1);
                int maxId = rs.getInt("maxId");
                return maxId;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeRs(rs);
            DBUtil.closePsta(psta);
        }
        return 0;
    }
}
