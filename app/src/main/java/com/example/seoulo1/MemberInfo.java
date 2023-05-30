package com.example.seoulo1;

public class MemberInfo {
    private String id;
    private String pwd;
    private String nickname;
    private String phoneNum;

    public MemberInfo(String id, String pwd, String nickname,  String phoneNum){
        this.id = id;
        this.pwd = pwd;
        this.nickname = nickname;
        this.phoneNum = phoneNum;

    }
    public MemberInfo() {

    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }


    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }




}