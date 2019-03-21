package com.example.daidaijie.syllabusapplication.mealcard;


public class YKTBean {
    /***
     * {
     *     "balance": "4.13"
     * }
     * Response Headers :
     * Cookie →ASP.NET_SessionId=l2do4a14jpfoswxbhd2z4x4h
     * -----------------------------------------
     * {
     *     "detail": [
     *         {
     *             "date": "2019-03-13",
     *             "time": "08:44:01",
     *             "flow": "-4",
     *             "kind": "持卡人消费",
     *             "name": "G座新食堂",
     *             "note": ""
     *         },
     *         {
     *             "date": "2019-03-08",
     *             "time": "09:54:53",
     *             "flow": "-4",
     *             "kind": "持卡人消费",
     *             "name": "G座新食堂",
     *             "note": ""
     *         }
     *     ],
     *     "length": 2
     * }
     */

    private String date;
    private String time;
    private String flow;
    private String kind;
    private String name;
    private String length;
    private String balance;
    private String balance_Cookie;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance_Cookie() {
        return balance_Cookie;
    }

    public void setBalance_Cookie(String balance_Cookie) {
        this.balance_Cookie = balance_Cookie;
    }

}
