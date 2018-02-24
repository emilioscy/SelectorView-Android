package com.emcy.selector;

/**
 * The SelectorSearchView interface
 */

interface OnSearchViewListener {
    void onTextChange(String query);
    void onTextEmpty();
    void onTextFocusChange(boolean focused);
}
