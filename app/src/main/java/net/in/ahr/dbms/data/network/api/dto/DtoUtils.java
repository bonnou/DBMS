package net.in.ahr.dbms.data.network.api.dto;

import greendao.MusicMst;
import greendao.MusicResultDBHR;

/**
 * Created by str2653z on 16/05/02.
 */
public class DtoUtils {

    public void convertMusicMstFromEntity(MusicMst musicMst, MusicMstDto musicMstDto) {

        if (musicMst == null) {
            // do nothing

        } else {
            musicMstDto.setId(musicMst.getId());
            musicMstDto.setName(musicMst.getName());
            musicMstDto.setNha(musicMst.getNha());
            musicMstDto.setVersion(musicMst.getVersion());
            musicMstDto.setGenre(musicMst.getGenre());
            musicMstDto.setArtist(musicMst.getArtist());
            musicMstDto.setBpmFrom(musicMst.getBpmFrom());
            musicMstDto.setBpmTo(musicMst.getBpmTo());
            musicMstDto.setDifficult(musicMst.getDifficult());
            musicMstDto.setNotes(musicMst.getNotes());
            musicMstDto.setScratchNotes(musicMst.getScratchNotes());
            musicMstDto.setChargeNotes(musicMst.getChargeNotes());
            musicMstDto.setBackSpinScratchNotes(musicMst.getBackSpinScratchNotes());
            musicMstDto.setSortNumInDifficult(musicMst.getSortNumInDifficult());
            musicMstDto.setMstVersion(musicMst.getMstVersion());
            musicMstDto.setInsDate(musicMst.getInsDate());
            musicMstDto.setUpdDate(musicMst.getUpdDate());
            musicMstDto.setMusicResultIdDBHR(musicMst.getMusicResultIdDBHR());

            MusicResultDBHRDto result = new MusicResultDBHRDto();
            convertMusicResultFromEntity(musicMst, musicMst.getMusicResultDBHR(), result);
            musicMstDto.setMusicResultDBHR(result);

        }

    }

    public void convertMusicResultFromEntity(MusicMst music, MusicResultDBHR result, MusicResultDBHRDto resultDto) {

        if (result == null) {
            // do nothing
            resultDto.setId(music.getId());
            resultDto.setName(music.getName());
            resultDto.setNha(music.getNha());

        } else {
            resultDto.setId(result.getId());
            resultDto.setName(result.getName());
            resultDto.setNha(result.getNha());
            resultDto.setClearLamp(result.getClearLamp());
            resultDto.setExScore(result.getExScore());
            resultDto.setBp(result.getBp());
            resultDto.setScoreRank(result.getScoreRank());
            resultDto.setScoreRate(result.getScoreRate());
            resultDto.setMissRate(result.getMissRate());
            resultDto.setTag(result.getTag());
            resultDto.setFav(result.getFav());
            resultDto.setClearLamp_DBR(result.getClearLamp_DBR());
            resultDto.setClearLamp_DBRR(result.getClearLamp_DBRR());
            resultDto.setClearLamp_DBM(result.getClearLamp_DBM());
            resultDto.setClearLamp_DBSR(result.getClearLamp_DBSR());
            resultDto.setClearLamp_DBM_NONAS(result.getClearLamp_DBM_NONAS());
            resultDto.setClearLamp_RH(result.getClearLamp_RH());
            resultDto.setClearLamp_LH(result.getClearLamp_LH());
            resultDto.setMyDifficult(result.getMyDifficult());
            resultDto.setDjPoint(result.getDjPoint());
            resultDto.setClearProgressRate(result.getClearProgressRate());
            resultDto.setLastPlayDate(result.getLastPlayDate());
            resultDto.setLastUpdateDate(result.getLastUpdateDate());
            resultDto.setRemainingGaugeOrDeadNotes(result.getRemainingGaugeOrDeadNotes());
            resultDto.setMemoOther(result.getMemoOther());
            resultDto.setPGreat(result.getPGreat());
            resultDto.setGreat(result.getGreat());
            resultDto.setGood(result.getGood());
            resultDto.setBad(result.getBad());
            resultDto.setPoor(result.getPoor());
            resultDto.setComboBreak(result.getComboBreak());
            resultDto.setInsDate(result.getInsDate());
            resultDto.setUpdDate(result.getUpdDate());

        }

    }

}
