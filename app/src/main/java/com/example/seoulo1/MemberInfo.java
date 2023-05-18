package com.example.seoulo1;

public class MemberInfo {
    private String id;
    private String pwd;
    private String nickname;
    private String phoneNum;
    private String status;
    private String search;

    public MemberInfo(String id, String pwd, String nickname,  String phoneNum, String imageURL, String status, String search){
        this.id = id;
        this.pwd = pwd;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.status = status;
        this.search = search;
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


    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }


}