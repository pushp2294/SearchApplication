package com.intrepid.searchapplication.network;

import com.intrepid.searchapplication.pojo.WikiSearchAPIResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikiSearchApiInterface {

    //@Headers("X-Mashape-Key: fvHlKdY1h3mshGmqwXBB5Wo4DJuyp1M6tUujsnHuIrSLm2BO40")

    @GET("/w/api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpslimit=100")
    Observable<WikiSearchAPIResponse> getWikiSearchResult(@Query("gpssearch") String s);
}
