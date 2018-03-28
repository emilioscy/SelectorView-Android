package com.emcy.selector;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Custom searchView.
 * Can be customize using SelectorAttributes
 * Parent layout : CardView
 */
public class SelectorSearchView extends CardView implements TextWatcher, View.OnClickListener, TextView.OnEditorActionListener, View.OnFocusChangeListener {

    private SearchView searchView;
    private OnSearchViewListener listener;
    private View googleSearchView;
    private CardView layout;

    public SelectorSearchView(@NonNull Context context) {
        super(context);
        init();
    }

    public SelectorSearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectorSearchView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        layout = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.custom_view_search_layout, null);
        addView(layout);
        searchView = layout.findViewById(R.id.emiliosSearchView);
        searchView.setOnClickListener(this);
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setOnEditorActionListener(this);
        googleSearchView = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        try {
            searchEditText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        try {
            searchEditText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.blue));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        searchEditText.addTextChangedListener(this);
        searchEditText.setOnFocusChangeListener(this);
    }

    public void setAttributes(SelectorAttributes attrs) {
        if (attrs != null) {
            try {
                if (attrs.getSearchBackgroundColor() != -1) {
                    layout.setCardBackgroundColor(getColor(attrs.getSearchBackgroundColor()));
                }
                if (attrs.getSearchCornerRadius() != -1)
                    layout.setRadius(getContext().getResources().getDimensionPixelSize(attrs.getSearchCornerRadius()));
                if (attrs.getSearchCancelDrawable() != -1) {
                    ImageView closeButtonImage = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
                    closeButtonImage.setImageDrawable(ContextCompat.getDrawable(getContext(), attrs.getSearchCancelDrawable()));
                }
                if (attrs.getSearchDrawable() != -1) {
                    ImageView searchImage = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
                    searchImage.setImageDrawable(ContextCompat.getDrawable(getContext(), attrs.getSearchDrawable()));
                }
                TextView textView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
                if (attrs.getSearchTextColor() != -1) {
                    textView.setTextColor(getColor(attrs.getSearchTextColor()));
                }
                if (attrs.getSearchHintColor() != -1) {
                    textView.setHintTextColor(getColor(attrs.getSearchHintColor()));
                }
                if (attrs.getSearchHintText() != -1)
                    textView.setHint(getContext().getString(attrs.getSearchHintText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initInterface(OnSearchViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (listener != null) {
            if (charSequence.length() == 0) {
                listener.onTextEmpty();
            } else {
                listener.onTextChange(charSequence.toString());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setQuery(String query) {
        if (query != null && !query.isEmpty()) {
            searchView.setQuery(query, false);
        }
    }

    public String getQuery() {
        return searchView.getQuery().toString();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.emiliosSearchView) {
            googleSearchView.performClick();
            searchView.setIconified(false);
        }
    }

    public void search() {
        String query = getQuery();
        if (query != null && !query.isEmpty()) {
            searchView.setQuery(getQuery(), true);
        }
    }

    public void setQueryAndOpenSearch(String query) {
        setQuery(query);
        searchView.setIconified(false);
        searchView.clearFocus();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            searchView.clearFocus();
            return true;
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        listener.onTextFocusChange(b);
    }

    private int getColor(int colorRes) {
        try {
            return ContextCompat.getColor(getContext(), colorRes);
        } catch (Resources.NotFoundException e) {
            return 0;
        }

    }
}
