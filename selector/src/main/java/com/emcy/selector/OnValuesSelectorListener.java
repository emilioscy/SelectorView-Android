package com.emcy.selector;

import java.util.List;

public interface OnValuesSelectorListener {
    void onValueClick(String value);
    void onValuesSelected(List<String> selectedValues);
    void onValuesUpdated(List<String> selectedValues);
}
