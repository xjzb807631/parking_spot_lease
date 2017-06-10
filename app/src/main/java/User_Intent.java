import java.io.Serializable;

import data.Account;

/**
 * Created by acer on 2017/6/7.
 */

public class User_Intent implements Serializable {

    private String mAccount;
    private String mNickName;
    private String mRealName;
    private String mIdentityNumber;
    private String mWeChat;
    private String mPwd;

    public void setmAccount(String mAccount)
    {
        this.mAccount= mAccount;
    }

    public void setmNickName(String mRealName)
    {
        this.mNickName= mNickName;
    }

    public void setmRealName(String mRealName)
    {
        this.mRealName= mRealName;
    }

    public void setmIdentityNumber(String mIdentityNumber)
    {
        this.mIdentityNumber= mIdentityNumber;
    }

    public void setmWeChat(String mWeChat)
    {
        this.mWeChat= mWeChat;
    }

    public void setmPwd(String mPwd)
    {
        this.mPwd= mPwd;
    }
}
