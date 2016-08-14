package com.eos.eosql.data;

/**
 * Created by jyoung on 16. 8. 14..
 */
public class ContactData {
    private String name, nickname, phonenum;
    private int id, age;

    public ContactData() {
    }

    public ContactData(int id, String name, String nickname, String phonenum, int age) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.phonenum = phonenum;
        this.age = age;
    }

    public ContactData(String name, String nickname, String phonenum, int age) {
        this.name = name;
        this.nickname = nickname;
        this.phonenum = phonenum;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
