package net.in.ahr.dbms.data.network.request.dto;

import java.util.Date;

/**
 * Created by str2653z on 16/05/01.
 */
public class MusicResultDBHRDto {

    private Long id;

    // online専用
    private String userName;

    private String name;
    private String nha;
    private String clearLamp;
    private Integer exScore;
    private Integer bp;
    private String scoreRank;
    private Double scoreRate;
    private Double missRate;
    private String tag;
    private String fav;
    private String clearLamp_DBR;
    private String clearLamp_DBRR;
    private String clearLamp_DBM;
    private String clearLamp_DBSR;
    private String clearLamp_DBM_NONAS;
    private String clearLamp_RH;
    private String clearLamp_LH;
    private String myDifficult;
    private Integer djPoint;
    private Double clearProgressRate;
    private java.util.Date lastPlayDate;
    private java.util.Date lastUpdateDate;
    private Integer remainingGaugeOrDeadNotes;
    private String memoOther;
    private String pGreat;
    private String great;
    private String good;
    private String bad;
    private String poor;
    private String comboBreak;
    private java.util.Date insDate;
    private java.util.Date updDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNha() {
        return nha;
    }

    public void setNha(String nha) {
        this.nha = nha;
    }

    public String getClearLamp() {
        return clearLamp;
    }

    public void setClearLamp(String clearLamp) {
        this.clearLamp = clearLamp;
    }

    public Integer getExScore() {
        return exScore;
    }

    public void setExScore(Integer exScore) {
        this.exScore = exScore;
    }

    public Integer getBp() {
        return bp;
    }

    public void setBp(Integer bp) {
        this.bp = bp;
    }

    public String getScoreRank() {
        return scoreRank;
    }

    public void setScoreRank(String scoreRank) {
        this.scoreRank = scoreRank;
    }

    public Double getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(Double scoreRate) {
        this.scoreRate = scoreRate;
    }

    public Double getMissRate() {
        return missRate;
    }

    public void setMissRate(Double missRate) {
        this.missRate = missRate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getClearLamp_DBR() {
        return clearLamp_DBR;
    }

    public void setClearLamp_DBR(String clearLamp_DBR) {
        this.clearLamp_DBR = clearLamp_DBR;
    }

    public String getClearLamp_DBRR() {
        return clearLamp_DBRR;
    }

    public void setClearLamp_DBRR(String clearLamp_DBRR) {
        this.clearLamp_DBRR = clearLamp_DBRR;
    }

    public String getClearLamp_DBM() {
        return clearLamp_DBM;
    }

    public void setClearLamp_DBM(String clearLamp_DBM) {
        this.clearLamp_DBM = clearLamp_DBM;
    }

    public String getClearLamp_DBSR() {
        return clearLamp_DBSR;
    }

    public void setClearLamp_DBSR(String clearLamp_DBSR) {
        this.clearLamp_DBSR = clearLamp_DBSR;
    }

    public String getClearLamp_DBM_NONAS() {
        return clearLamp_DBM_NONAS;
    }

    public void setClearLamp_DBM_NONAS(String clearLamp_DBM_NONAS) {
        this.clearLamp_DBM_NONAS = clearLamp_DBM_NONAS;
    }

    public String getClearLamp_RH() {
        return clearLamp_RH;
    }

    public void setClearLamp_RH(String clearLamp_RH) {
        this.clearLamp_RH = clearLamp_RH;
    }

    public String getClearLamp_LH() {
        return clearLamp_LH;
    }

    public void setClearLamp_LH(String clearLamp_LH) {
        this.clearLamp_LH = clearLamp_LH;
    }

    public String getMyDifficult() {
        return myDifficult;
    }

    public void setMyDifficult(String myDifficult) {
        this.myDifficult = myDifficult;
    }

    public Integer getDjPoint() {
        return djPoint;
    }

    public void setDjPoint(Integer djPoint) {
        this.djPoint = djPoint;
    }

    public Double getClearProgressRate() {
        return clearProgressRate;
    }

    public void setClearProgressRate(Double clearProgressRate) {
        this.clearProgressRate = clearProgressRate;
    }

    public Date getLastPlayDate() {
        return lastPlayDate;
    }

    public void setLastPlayDate(Date lastPlayDate) {
        this.lastPlayDate = lastPlayDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getRemainingGaugeOrDeadNotes() {
        return remainingGaugeOrDeadNotes;
    }

    public void setRemainingGaugeOrDeadNotes(Integer remainingGaugeOrDeadNotes) {
        this.remainingGaugeOrDeadNotes = remainingGaugeOrDeadNotes;
    }

    public String getMemoOther() {
        return memoOther;
    }

    public void setMemoOther(String memoOther) {
        this.memoOther = memoOther;
    }

    public String getPGreat() {
        return pGreat;
    }

    public void setPGreat(String pGreat) {
        this.pGreat = pGreat;
    }

    public String getGreat() {
        return great;
    }

    public void setGreat(String great) {
        this.great = great;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public String getPoor() {
        return poor;
    }

    public void setPoor(String poor) {
        this.poor = poor;
    }

    public String getComboBreak() {
        return comboBreak;
    }

    public void setComboBreak(String comboBreak) {
        this.comboBreak = comboBreak;
    }

    public Date getInsDate() {
        return insDate;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}
