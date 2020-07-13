package com.intrepid.searchapplication.mvp.presenters;

import com.intrepid.searchapplication.db.WikiDatabaseService;
import com.intrepid.searchapplication.mvp.views.IWikiSearchView;
import com.intrepid.searchapplication.network.WikiSearchApiInterface;
import com.intrepid.searchapplication.pojo.Page;
import com.intrepid.searchapplication.pojo.WikiSearchAPIResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WikiSearchPresenter {

    WikiSearchApiInterface wikiSearchApiInterface;
    IWikiSearchView iWikiSearchView;

    @Inject
    WikiSearchPresenter(WikiSearchApiInterface wikiSearchApiInterface) {
        this.wikiSearchApiInterface = wikiSearchApiInterface;
    }

    public void setiWikiSearchView(IWikiSearchView iWikiSearchView) {
        this.iWikiSearchView = iWikiSearchView;
    }

    public void getQueriedWikiSearchResult(String query) {

        iWikiSearchView.showLoading();
        wikiSearchApiInterface.getWikiSearchResult(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WikiSearchAPIResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(WikiSearchAPIResponse wikiSearchAPIResponse) {
                        if (wikiSearchAPIResponse != null && wikiSearchAPIResponse.getQuery() != null
                                && wikiSearchAPIResponse.getQuery().getPages() != null
                                && !wikiSearchAPIResponse.getQuery().getPages().isEmpty()) {
                            List<Page> pageList = wikiSearchAPIResponse.getQuery().getPages();
                            iWikiSearchView.showWikiListing(pageList);
                            saveWikiPagesToLocal(pageList);
                            return;
                        }
                        iWikiSearchView.showErrorView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iWikiSearchView.showErrorView();
                    }

                    @Override
                    public void onComplete() {
                        iWikiSearchView.hideLoading();
                    }
                });
    }

    private void saveWikiPagesToLocal(List<Page> pageList) {
        WikiDatabaseService.getInstance().addWikiPagesToLocal(pageList);
    }

    public void getQueriedWikiSearchFromLocal(String query) {
        iWikiSearchView.showLoading();
        Observable.just(WikiDatabaseService.getInstance().searchWikiPageFromLocal(query.toLowerCase()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Page>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Page> value) {
                        if(value != null && !value.isEmpty())
                            iWikiSearchView.showWikiListing(value);
                        else
                            iWikiSearchView.showErrorView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iWikiSearchView.showErrorView();
                    }

                    @Override
                    public void onComplete() {
                        iWikiSearchView.hideLoading();
                    }
                });
    }

}