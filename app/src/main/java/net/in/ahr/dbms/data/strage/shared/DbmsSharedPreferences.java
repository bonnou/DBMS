package net.in.ahr.dbms.data.strage.shared;

import android.content.SharedPreferences;

import net.in.ahr.dbms.others.AppConst;

/**
 * Created by str2653z on 2016/04/10.
 */
public class DbmsSharedPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public DbmsSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public DbmsSharedPreferences edit() {
        editor = sharedPreferences.edit();
        return this;
    }

    public void apply() {
        editor.apply();
        editor = null;
    }

    // 検索条件: 難易度10
    public boolean getSearchConfDiff_10() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_DIFFICULT_10, true);
    }

    public DbmsSharedPreferences putSearchConfDiff_10(boolean searchConfDiff_10) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_DIFFICULT_10, searchConfDiff_10);
        return this;
    }

    // 検索条件: 難易度11
    public boolean getSearchConfDiff_11() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_DIFFICULT_11, true);
    }

    public DbmsSharedPreferences putSearchConfDiff_11(boolean searchConfDiff_11) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_DIFFICULT_11, searchConfDiff_11);
        return this;
    }

    // 検索条件: 1st style
    public boolean getSearchConfVersion_1st() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_1ST, true);
    }


    public DbmsSharedPreferences putSearchConfVersion_1st(boolean searchConfVersion_1st) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_1ST, searchConfVersion_1st);
        return this;
    }

    // 検索条件: substream
    public boolean getSearchConfVersion_sub() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SUB, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_sub(boolean searchConfVersion_sub) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SUB, searchConfVersion_sub);
        return this;
    }

    // 検索条件: 2nd style
    public boolean getSearchConfVersion_2nd() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_2ND, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_2nd(boolean searchConfVersion_2nd) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_2ND, searchConfVersion_2nd);
        return this;
    }

    // 検索条件: 3rd style
    public boolean getSearchConfVersion_3rd() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_3RD, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_3rd(boolean searchConfVersion_3rd) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_3RD, searchConfVersion_3rd);
        return this;
    }

    // 検索条件: 4th style
    public boolean getSearchConfVersion_4th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_4TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_4th(boolean searchConfVersion_4th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_4TH, searchConfVersion_4th);
        return this;
    }

    // 検索条件: 5th style
    public boolean getSearchConfVersion_5th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_5TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_5th(boolean searchConfVersion_5th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_5TH, searchConfVersion_5th);
        return this;
    }

    // 検索条件: 6th style
    public boolean getSearchConfVersion_6th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_6TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_6th(boolean searchConfVersion_6th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_6TH, searchConfVersion_6th);
        return this;
    }

    // 検索条件: 7th style
    public boolean getSearchConfVersion_7th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_7TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_7th(boolean searchConfVersion_7th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_7TH, searchConfVersion_7th);
        return this;
    }

    // 検索条件: 8th style
    public boolean getSearchConfVersion_8th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_8TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_8th(boolean searchConfVersion_8th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_8TH, searchConfVersion_8th);
        return this;
    }

    // 検索条件: 9th style
    public boolean getSearchConfVersion_9th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_9TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_9th(boolean searchConfVersion_9th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_9TH, searchConfVersion_9th);
        return this;
    }

    // 検索条件: 10th style
    public boolean getSearchConfVersion_10th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_10TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_10th(boolean searchConfVersion_10th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_10TH, searchConfVersion_10th);
        return this;
    }

    // 検索条件: IIDX RED
    public boolean getSearchConfVersion_RED() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_RED, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_RED(boolean searchConfVersion_RED) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_RED, searchConfVersion_RED);
        return this;
    }

    // 検索条件: HAPPY SKY
    public boolean getSearchConfVersion_SKY() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SKY, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_SKY(boolean searchConfVersion_SKY) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SKY, searchConfVersion_SKY);
        return this;
    }

    // 検索条件: DistorteD
    public boolean getSearchConfVersion_DD() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_DD, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_DD(boolean searchConfVersion_DD) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_DD, searchConfVersion_DD);
        return this;
    }

    // 検索条件: GOLD
    public boolean getSearchConfVersion_GOLD() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_GOLD, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_GOLD(boolean searchConfVersion_GOLD) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_GOLD, searchConfVersion_GOLD);
        return this;
    }

    // 検索条件: DJ TROOPERS
    public boolean getSearchConfVersion_DJT() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_DJT, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_DJT(boolean searchConfVersion_DJT) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_DJT, searchConfVersion_DJT);
        return this;
    }

    // 検索条件: EMPRESS
    public boolean getSearchConfVersion_EMP() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_EMP, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_EMP(boolean searchConfVersion_EMP) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_EMP, searchConfVersion_EMP);
        return this;
    }

    // 検索条件: SIRIUS
    public boolean getSearchConfVersion_SIR() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SIR, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_SIR(boolean searchConfVersion_SIR) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SIR, searchConfVersion_SIR);
        return this;
    }

    // 検索条件: Resort Anthem
    public boolean getSearchConfVersion_RA() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_RA, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_RA(boolean searchConfVersion_RA) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_RA, searchConfVersion_RA);
        return this;
    }

    // 検索条件: Lincle
    public boolean getSearchConfVersion_LC() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_LC, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_LC(boolean searchConfVersion_LC) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_LC, searchConfVersion_LC);
        return this;
    }

    // 検索条件: tricoro
    public boolean getSearchConfVersion_tri() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_TRI, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_tri(boolean searchConfVersion_tri) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_TRI, searchConfVersion_tri);
        return this;
    }

    // 検索条件: SPADA
    public boolean getSearchConfVersion_SPA() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SPA, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_SPA(boolean searchConfVersion_SPA) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SPA, searchConfVersion_SPA);
        return this;
    }

    // 検索条件: PENDUAL
    public boolean getSearchConfVersion_PEN() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_PEN , true);
    }

    public DbmsSharedPreferences putSearchConfVersion_PEN(boolean searchConfVersion_PEN) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_PEN, searchConfVersion_PEN);
        return this;
    }

    // copula
    public boolean getSearchConfVersion_cop() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_COP, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_cop(boolean searchConfVersion_cop) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_COP, searchConfVersion_cop);
        return this;
    }


}
