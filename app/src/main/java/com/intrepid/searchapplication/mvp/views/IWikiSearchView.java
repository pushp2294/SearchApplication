package com.intrepid.searchapplication.mvp.views;

import com.intrepid.searchapplication.pojo.Page;

import java.util.List;

public interface IWikiSearchView {

    void showLoading();
    void hideLoading();
    void showWikiListing(List<Page> pageList);
    void showErrorView();
}
