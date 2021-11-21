package com.relyits.rmbs.service;
public interface PageNavigator {
    String buildPageNav(String path, int resultSize, int page, int pageSize, int pageNavTrail);
}
