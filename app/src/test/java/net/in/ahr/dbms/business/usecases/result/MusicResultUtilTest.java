package net.in.ahr.dbms.business.usecases.result;

import org.junit.Test;

import greendao.MusicMst;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by str2653z on 16/05/15.
 */
public class MusicResultUtilTest {

    @Test
    public void test_retMaxScore() {

        /******************************************************
         * Arrange
         ******************************************************/
        // テスト対象
        MusicResultUtil sut = new MusicResultUtil();

        // 引数準備
        MusicMst music = new MusicMst();
        music.setNotes(100);
        music.setScratchNotes(10);
        music.setChargeNotes(1);

        /******************************************************
         * Act
         ******************************************************/
        int ret = sut.retMaxScore(music);

        /******************************************************
         * Assert
         ******************************************************/
        assertThat(ret).isEqualTo(368);

    }

}