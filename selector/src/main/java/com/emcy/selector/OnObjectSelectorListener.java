package com.emcy.selector;

import java.util.List;

public interface OnObjectSelectorListener {
    void onObjectClick(long id);
    void onObjectsSelected(List<Long> selectedIds);
    void onObjectsUpdated(List<Long> selectedIds);
}
