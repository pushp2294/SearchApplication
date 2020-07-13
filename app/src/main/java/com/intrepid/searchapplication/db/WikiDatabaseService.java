package com.intrepid.searchapplication.db;

import com.intrepid.searchapplication.pojo.Page;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class WikiDatabaseService {

    private static WikiDatabaseService databaseService;
    private final Realm realm;

    private WikiDatabaseService() {
        realm = Realm.getDefaultInstance();
    }

    public static WikiDatabaseService getInstance() {
        if (databaseService == null) {
            databaseService = new WikiDatabaseService();
        }
        return databaseService;
    }

    public void addWikiPagesToLocal(List<Page> pageList) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(pageList);
        realm.commitTransaction();
    }

    public List<Page> searchWikiPageFromLocal(String query) {
        List<Page> pageList = new ArrayList<>();
        realm.beginTransaction();
        RealmResults<Page> pageRealmResults = realm.where(Page.class).findAll();
        for(Page page : realm.copyFromRealm(pageRealmResults)) {
            if(page.getTitle().toLowerCase().contains(query) ||
                    (page.getTerms() != null && page.getTerms().getDescription() != null
                && !page.getTerms().getDescription().isEmpty()
                    && page.getTerms().getDescription().get(0).toLowerCase().contains(query)))
                pageList.add(page);
        }
        realm.commitTransaction();
        return pageList;
    }

    public void closeRealm() {
        realm.close();
    }
}
