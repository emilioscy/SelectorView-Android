package com.emcy.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom view for showing objects or values.
 * Has it's own attributes for customizing adapter row and searchView.
 */

public class SelectorView extends FrameLayout implements OnObjectSelectorListener, OnValuesSelectorListener, SelectorData.OnSelectorDataResult, OnSearchViewListener {

    private Class tClass;
    private SelectorAttributes attrs;
    private View selectorView;
    private ObjectSelectorAdapter objectAdapter;
    private ValueSelectorAdapter valuesAdapter;
    private OnObjectSelectorListener objectListener;
    private OnValuesSelectorListener valueListener;
    private boolean isMultipleSelection;
    private SelectorSearchView selectorSearchView;
    private boolean isWithValues;

    public SelectorView(@NonNull Context context) {
        super(context);
        init();
    }

    public SelectorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public SelectorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ObjectSelectorTheme, 0, 0);
        this.attrs = new SelectorAttributes();
        try {
            isMultipleSelection = a.getBoolean(R.styleable.ObjectSelectorTheme_isMultipleSelection, false);
            isWithValues = a.getBoolean(R.styleable.ObjectSelectorTheme_withValues, false);
            this.attrs.setEnableSearchView(a.getBoolean(R.styleable.ObjectSelectorTheme_enableSearchView, false));
            if (this.attrs.isEnableSearchView()) {
                this.attrs.setSearchDrawable(a.getResourceId(R.styleable.ObjectSelectorTheme_searchDrawable, R.drawable.ic_search_icon));
                this.attrs.setSearchCancelDrawable(a.getResourceId(R.styleable.ObjectSelectorTheme_searchCancelDrawable, R.drawable.ic_clear_search));
                this.attrs.setSearchHintColor(a.getResourceId(R.styleable.ObjectSelectorTheme_searchHintColor, R.color.colorPrimary));
                this.attrs.setSearchHintText(a.getResourceId(R.styleable.ObjectSelectorTheme_searchHintText, R.string.search_hint));
                this.attrs.setSearchTextColor(a.getResourceId(R.styleable.ObjectSelectorTheme_searchTextColor, R.color.colorPrimary));
                this.attrs.setSearchBackgroundColor(a.getResourceId(R.styleable.ObjectSelectorTheme_searchBackgroundColor, R.color.searchViewColor));
                this.attrs.setSearchSpacerBackgroundColor(a.getResourceId(R.styleable.ObjectSelectorTheme_searchSpacerBackgroundColor, R.color.white));
                this.attrs.setSearchCornerRadius(a.getResourceId(R.styleable.ObjectSelectorTheme_searchCornerRadius, R.dimen.searchViewCornerRadius));
            }
            setClass(a.getResourceId(R.styleable.ObjectSelectorTheme_objectClass, 0));
            this.attrs.setListItemHeight(a.getResourceId(R.styleable.ObjectSelectorTheme_listItemHeight, R.dimen.list_item_height));
            this.attrs.setTextNormalColor(a.getResourceId(R.styleable.ObjectSelectorTheme_textNormalColor, R.color.colorPrimary));
            this.attrs.setTextSelectedColor(a.getResourceId(R.styleable.ObjectSelectorTheme_textSelectedColor, R.color.colorPrimary));
            this.attrs.setTextSize(a.getResourceId(R.styleable.ObjectSelectorTheme_textSize, R.dimen.text_size));
            this.attrs.setTextMarginStartPercent(a.getFloat(R.styleable.ObjectSelectorTheme_textMarginStartPercent, Constants.TEXT_MARGIN_START_PERCENT));
            this.attrs.setListItemBackgroundColor(a.getResourceId(R.styleable.ObjectSelectorTheme_listItemBackgroundColor, R.color.white));
            this.attrs.setNormalTickColor(a.getResourceId(R.styleable.ObjectSelectorTheme_normalTickColor, R.color.black));
            this.attrs.setTickDrawable(a.getResourceId(R.styleable.ObjectSelectorTheme_tickDrawable, R.drawable.ic_done_blue));
            this.attrs.setTickMarginEndPercent(a.getFloat(R.styleable.ObjectSelectorTheme_tickMarginEndPercent, Constants.TICK_MARGIN_END_PERCENT));
            this.attrs.setListItemSeparatorColor(a.getResourceId(R.styleable.ObjectSelectorTheme_listItemSeparatorColor, R.color.colorPrimary));
            this.attrs.setListItemSeparatorHeight(a.getResourceId(R.styleable.ObjectSelectorTheme_listItemSeparatorHeight, R.dimen.separator_height));
        } finally {
            a.recycle();
        }
    }

    private void setClass(int className) {
        if (!isWithValues) {
            if (className == 0) {
                throw new IllegalStateException(getContext().getString(R.string.unknown_class) + " " +
                        getContext().getString(R.string.or_values_true));
            } else {
                try {
                    Class<?> clazz;
                    clazz = Class.forName(getContext().getString(className));
                    tClass = clazz;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (className != 0) {
            throw new IllegalStateException(getContext().getString(R.string.cannot_have_object_class_and_values));
        }
    }

    @SuppressWarnings("unchecked")
    private void init() {
        selectorView = LayoutInflater.from(getContext()).inflate(R.layout.custom_view_selector_layout, null);
        addView(selectorView);
        initAdapter();
        initSearchView();
        if (!isWithValues) {
            try {
                new SelectorData(tClass, this);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), getContext().getString(R.string.unknown_class), Toast.LENGTH_SHORT).show();
                onDetachedFromWindow();
            }
        }
    }

    private void initSearchView() {
        selectorSearchView = selectorView.findViewById(R.id.selectorSearchView);
        View spacer = selectorView.findViewById(R.id.searchViewSpacerBackgroundColor);
        if (attrs != null && attrs.getListItemBackgroundColor() != -1){
            selectorView.findViewById(R.id.recyclerView).setBackgroundColor(ContextCompat.getColor(getContext(), attrs.getListItemBackgroundColor()));
        }
        if (attrs != null && attrs.isEnableSearchView()) {
            selectorSearchView.setVisibility(View.VISIBLE);
            spacer.setVisibility(View.VISIBLE);
            selectorSearchView.initInterface(this);
            selectorSearchView.setAttributes(attrs);
            if (attrs.getSearchSpacerBackgroundColor() != -1) {
                spacer.setBackgroundColor(ContextCompat.getColor(getContext(), attrs.getSearchSpacerBackgroundColor()));
            }
        } else {
            selectorSearchView.setVisibility(View.GONE);
            spacer.setVisibility(View.GONE);
        }
    }

    private void initAdapter() {
        RecyclerView recyclerView = selectorView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (isMultipleSelection) {
            if (isWithValues) {
                recyclerView.setAdapter(valuesAdapter = new ValueSelectorAdapter(new ArrayList<String>(), this, new ArrayList<String>(), attrs, getContext()));
            } else {
                recyclerView.setAdapter(objectAdapter = new ObjectSelectorAdapter(this, null, attrs, getContext()));
            }
        } else {
            if (isWithValues) {
                recyclerView.setAdapter(valuesAdapter = new ValueSelectorAdapter(new ArrayList<String>(), this, "", attrs, getContext()));
            } else {
                recyclerView.setAdapter(objectAdapter = new ObjectSelectorAdapter(this, -1, attrs, getContext()));
            }
        }
        recyclerView.setItemAnimator(null);
    }

    public void setDisplayedValues(List<String> values) {
        if (isWithValues) {
            if (valuesAdapter != null)
                valuesAdapter.refresh(values);
        } else {
            throw new IllegalStateException(getContext().getString(R.string.set_values_true));
        }
    }

    public void setSelectedObject(long id) {
        if (isMultipleSelection) {
            if (objectAdapter != null) {
                List<Long> longs = new ArrayList<>();
                longs.add(id);
                objectAdapter.setSelectedObjects(longs);
            }
        } else {
            if (!isWithValues) {
                if (objectAdapter != null)
                    objectAdapter.setSelectedObject(id);
            } else {
                throw new IllegalStateException(getContext().getString(R.string.set_values_false));
            }
        }
    }

    public void setSelectedObjects(List<Long> selectedIds) {
        if (!isMultipleSelection) {
            throw new IllegalStateException(getContext().getString(R.string.set_multiple_true));
        } else {
            if (!isWithValues) {
                if (objectAdapter != null)
                    objectAdapter.setSelectedObjects(selectedIds);
            } else {
                throw new IllegalStateException(getContext().getString(R.string.set_values_false));
            }
        }
    }

    public void setSelectedValue(String value) {
        if (isMultipleSelection) {
            if (valuesAdapter != null) {
                List<String> strings = new ArrayList<>();
                strings.add(value);
                valuesAdapter.setSelectedValues(strings);
            }
        } else {
            if (isWithValues) {
                if (valuesAdapter != null)
                    valuesAdapter.setSelectedValue(value);
            } else {
                throw new IllegalStateException(getContext().getString(R.string.set_values_true));
            }
        }
    }

    public void setSelectedValues(List<String> values) {
        if (!isMultipleSelection) {
            throw new IllegalStateException(getContext().getString(R.string.set_multiple_true));
        } else {
            if (isWithValues) {
                if (valuesAdapter != null)
                    valuesAdapter.setSelectedValues(values);
            } else {
                throw new IllegalStateException(getContext().getString(R.string.set_values_true));
            }
        }
    }

    public void setOnObjectSelectorListener(OnObjectSelectorListener listener) {
        if (!isWithValues) {
            this.objectListener = listener;
        } else {
            throw new IllegalStateException(getContext().getString(R.string.expected_values_listener));
        }
    }

    public void setOnValueSelectorListener(OnValuesSelectorListener listener) {
        if (isWithValues) {
            this.valueListener = listener;
        } else {
            throw new IllegalStateException(getContext().getString(R.string.expected_object_listener));
        }
    }

    public void clearSelection() {
        if (isMultipleSelection) {
            if (!isWithValues && objectAdapter != null)
                objectAdapter.clearSelection();
            else if (valuesAdapter != null)
                valuesAdapter.clearSelection();
        } else {
            throw new IllegalStateException(getContext().getString(R.string.set_multiple_true));
        }
    }

    public void getSelected() {
        if (isMultipleSelection) {
            if (isWithValues) {
                if (valueListener != null)
                    valueListener.onValuesSelected(valuesAdapter.getSelectedValues());
            } else {
                if (objectListener != null)
                    objectListener.onObjectsSelected(objectAdapter.getSelectedIds());
            }
        } else {
            throw new IllegalStateException(getContext().getString(R.string.set_multiple_true));
        }
    }

    @Override
    public void onObjectClick(long id) {
        if (objectListener != null) objectListener.onObjectClick(id);
    }

    @Override
    public void onObjectsSelected(List<Long> selectedIds) {
        if (objectListener != null) objectListener.onObjectsSelected(selectedIds);
    }

    @Override
    public void onObjectsUpdated(List<Long> selectedIds) {
        if (objectListener != null) objectListener.onObjectsUpdated(selectedIds);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(List list) {
        if (objectAdapter != null) objectAdapter.refresh(list);
    }

    public void setQuery(String query) {
        queryAdapter(query);
    }

    @Override
    public void onTextChange(String query) {
        queryAdapter(query);
    }

    private void queryAdapter(String query) {
        if (!isWithValues && objectAdapter != null) {
            objectAdapter.getFilterAsync(query);
        } else if (valuesAdapter != null) {
            valuesAdapter.getFilterAsync(query);
        }
    }

    @Override
    public void onTextEmpty() {
        if (!isWithValues && objectAdapter != null) {
            objectAdapter.onSearchClosed();
        } else if (valuesAdapter != null) {
            valuesAdapter.onSearchClosed();
        }
    }

    @Override
    public void onTextFocusChange(boolean focused) {

    }

    @Override
    public void onValueClick(String value) {
        if (valueListener != null) valueListener.onValueClick(value);
    }

    @Override
    public void onValuesSelected(List<String> selectedValues) {
        if (valueListener != null) valueListener.onValuesSelected(selectedValues);
    }

    @Override
    public void onValuesUpdated(List<String> selectedValues) {
        if (valueListener != null) valueListener.onValuesUpdated(selectedValues);
    }

}
