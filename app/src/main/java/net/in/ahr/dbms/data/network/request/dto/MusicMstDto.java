package net.in.ahr.dbms.data.network.request.dto;

import java.util.Date;

/**
 * Created by str2653z on 16/05/01.
 */
public class MusicMstDto {

    private Long id;
    private String name;
    private String nha;
    private String version;
    private String genre;
    private String artist;
    private Integer bpmFrom;
    private Integer bpmTo;
    private String difficult;
    private Integer notes;
    private Integer scratchNotes;
    private Integer chargeNotes;
    private Integer backSpinScratchNotes;
    private Integer sortNumInDifficult;
    private String mstVersion;
    private java.util.Date insDate;
    private java.util.Date updDate;
    private long musicResultIdDBHR;
    private MusicResultDBHRDto musicResultDBHR;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getBpmFrom() {
        return bpmFrom;
    }

    public void setBpmFrom(Integer bpmFrom) {
        this.bpmFrom = bpmFrom;
    }

    public Integer getBpmTo() {
        return bpmTo;
    }

    public void setBpmTo(Integer bpmTo) {
        this.bpmTo = bpmTo;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public Integer getNotes() {
        return notes;
    }

    public void setNotes(Integer notes) {
        this.notes = notes;
    }

    public Integer getScratchNotes() {
        return scratchNotes;
    }

    public void setScratchNotes(Integer scratchNotes) {
        this.scratchNotes = scratchNotes;
    }

    public Integer getChargeNotes() {
        return chargeNotes;
    }

    public void setChargeNotes(Integer chargeNotes) {
        this.chargeNotes = chargeNotes;
    }

    public Integer getBackSpinScratchNotes() {
        return backSpinScratchNotes;
    }

    public void setBackSpinScratchNotes(Integer backSpinScratchNotes) {
        this.backSpinScratchNotes = backSpinScratchNotes;
    }

    public Integer getSortNumInDifficult() {
        return sortNumInDifficult;
    }

    public void setSortNumInDifficult(Integer sortNumInDifficult) {
        this.sortNumInDifficult = sortNumInDifficult;
    }

    public String getMstVersion() {
        return mstVersion;
    }

    public void setMstVersion(String mstVersion) {
        this.mstVersion = mstVersion;
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

    public long getMusicResultIdDBHR() {
        return musicResultIdDBHR;
    }

    public void setMusicResultIdDBHR(long musicResultIdDBHR) {
        this.musicResultIdDBHR = musicResultIdDBHR;
    }

    public MusicResultDBHRDto getMusicResultDBHR() {
        return musicResultDBHR;
    }

    public void setMusicResultDBHR(MusicResultDBHRDto musicResultDBHR) {
        this.musicResultDBHR = musicResultDBHR;
    }
}
