package greendao;

import greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table MUSIC_MST.
 */
public class MusicMst implements java.io.Serializable {

    private Long id;
    private String name;
    private String nha;
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
    private long musicResultIdDBHR;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient MusicMstDao myDao;

    private MusicResultDBHR musicResultDBHR;
    private Long musicResultDBHR__resolvedKey;


    public MusicMst() {
    }

    public MusicMst(Long id) {
        this.id = id;
    }

    public MusicMst(Long id, String name, String nha, String genre, String artist, Integer bpmFrom, Integer bpmTo, String difficult, Integer notes, Integer scratchNotes, Integer chargeNotes, Integer backSpinScratchNotes, Integer sortNumInDifficult, String mstVersion, java.util.Date insDate, long musicResultIdDBHR) {
        this.id = id;
        this.name = name;
        this.nha = nha;
        this.genre = genre;
        this.artist = artist;
        this.bpmFrom = bpmFrom;
        this.bpmTo = bpmTo;
        this.difficult = difficult;
        this.notes = notes;
        this.scratchNotes = scratchNotes;
        this.chargeNotes = chargeNotes;
        this.backSpinScratchNotes = backSpinScratchNotes;
        this.sortNumInDifficult = sortNumInDifficult;
        this.mstVersion = mstVersion;
        this.insDate = insDate;
        this.musicResultIdDBHR = musicResultIdDBHR;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMusicMstDao() : null;
    }

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

    public java.util.Date getInsDate() {
        return insDate;
    }

    public void setInsDate(java.util.Date insDate) {
        this.insDate = insDate;
    }

    public long getMusicResultIdDBHR() {
        return musicResultIdDBHR;
    }

    public void setMusicResultIdDBHR(long musicResultIdDBHR) {
        this.musicResultIdDBHR = musicResultIdDBHR;
    }

    /** To-one relationship, resolved on first access. */
    public MusicResultDBHR getMusicResultDBHR() {
        long __key = this.musicResultIdDBHR;
        if (musicResultDBHR__resolvedKey == null || !musicResultDBHR__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MusicResultDBHRDao targetDao = daoSession.getMusicResultDBHRDao();
            MusicResultDBHR musicResultDBHRNew = targetDao.load(__key);
            synchronized (this) {
                musicResultDBHR = musicResultDBHRNew;
            	musicResultDBHR__resolvedKey = __key;
            }
        }
        return musicResultDBHR;
    }

    public void setMusicResultDBHR(MusicResultDBHR musicResultDBHR) {
        if (musicResultDBHR == null) {
            throw new DaoException("To-one property 'musicResultIdDBHR' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.musicResultDBHR = musicResultDBHR;
            musicResultIdDBHR = musicResultDBHR.getId();
            musicResultDBHR__resolvedKey = musicResultIdDBHR;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}