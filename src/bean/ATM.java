package bean;

import java.util.Date;
import java.util.Objects;

public class ATM {
    private int atmId;//编号
    private String atmCode;//卡号
    private String atmPwd;//密码
    private String atmName;//用户名
    private double atmMoney;//余额
    private Date atmTime;//创建时间

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public String getAtmCode() {
        return atmCode;
    }

    public void setAtmCode(String atmCode) {
        this.atmCode = atmCode;
    }

    public String getAtmPwd() {
        return atmPwd;
    }

    public void setAtmPwd(String atmPwd) {
        this.atmPwd = atmPwd;
    }

    public String getAtmName() {
        return atmName;
    }

    public void setAtmName(String atmName) {
        this.atmName = atmName;
    }

    public double getAtmMoney() {
        return atmMoney;
    }

    public void setAtmMoney(double atmMoney) {
        this.atmMoney = atmMoney;
    }

    public Date getAtmTime() {
        return atmTime;
    }

    public void setAtmTime(Date atmTime) {
        this.atmTime = atmTime;
    }

    @Override
    public String toString() {
        return "ATM{" +
                "atmId=" + atmId +
                ", atmCode='" + atmCode + '\'' +
                ", atmPwd='" + atmPwd + '\'' +
                ", atmName='" + atmName + '\'' +
                ", atmMoney=" + atmMoney +
                ", atmTime=" + atmTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATM atm = (ATM) o;
        return atmId == atm.atmId && Double.compare(atm.atmMoney, atmMoney) == 0 && Objects.equals(atmCode, atm.atmCode) && Objects.equals(atmPwd, atm.atmPwd) && Objects.equals(atmName, atm.atmName) && Objects.equals(atmTime, atm.atmTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atmId, atmCode, atmPwd, atmName, atmMoney, atmTime);
    }

}
