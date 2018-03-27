package com.emcy.selector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.emcy.selector.Constants.DISPLAYED_VALUES;
import static com.emcy.selector.Constants.IS_MULTIPLE_SELECTION;
import static com.emcy.selector.Constants.IS_OBJECT_SELECTOR;
import static com.emcy.selector.Constants.MODEL_CLASS;
import static com.emcy.selector.Constants.SELECTED_ID;
import static com.emcy.selector.Constants.SELECTED_IDS;
import static com.emcy.selector.Constants.SELECTED_VALUE;
import static com.emcy.selector.Constants.SELECTED_VALUES;

/**
 * Activity for result for selecting objects or values
 */

public class SelectorActivity extends AppCompatActivity implements SelectorData.OnSelectorDataResult, OnObjectSelectorListener, OnValuesSelectorListener, View.OnClickListener, OnSearchViewListener {

    Class targetClass;
    ObjectSelectorAdapter objectAdapter;
    ValueSelectorAdapter valuesAdapter;
    SelectorSearchView selectorSearchView;
    private SelectorActivityAttributes attributes;
    private static final String LAST_QUERY_SEARCH = "last_query_search";
    private static final String LAST_SELECTED_IDS = "last_selected_ids";
    private static final String LAST_SELECTED_VALUES = "last_selected_values";
    private boolean isObjectSelector;
    private Bundle savedInstanceState;
    private AsyncTask<Void, Void, List<String>> getDataPrefsAsync;
    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAttributes();
        setContentView(R.layout.layout_activity_selector);
        initViewAndData();
        if (savedInstanceState != null) {
            restoreLastState(savedInstanceState);
        }
    }

    @SuppressWarnings("unchecked")
    private void restoreLastState(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        String string = savedInstanceState.getString(LAST_QUERY_SEARCH, "");
        if (!string.isEmpty() && selectorSearchView != null)
            selectorSearchView.setQuery(string);
        if (isObjectSelector) {
            ArrayList<Long> list = (ArrayList<Long>) savedInstanceState.getSerializable(LAST_SELECTED_IDS);
            if (objectAdapter != null && list != null) {
                objectAdapter.setSelectedObjects(list);
            }
            onObjectsUpdated(list);
        } else {
            ArrayList<String> list = (ArrayList<String>) savedInstanceState.getSerializable(LAST_SELECTED_VALUES);
            if (valuesAdapter != null && list != null) {
                valuesAdapter.setSelectedValues(list);
            }
            onValuesUpdated(list);
            ArrayList<String> values = (ArrayList<String>) savedInstanceState.getSerializable(DISPLAYED_VALUES);
            if (values != null && values.size() > 0) {
                valuesAdapter.setCoyItems(values);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isObjectSelector && objectAdapter != null)
            objectAdapter.cancelAsync();
        else if (valuesAdapter != null)
            valuesAdapter.cancelAsync();
        if (getDataPrefsAsync != null)
            getDataPrefsAsync.cancel(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!selectorSearchView.getQuery().isEmpty()) {
            outState.putString(LAST_QUERY_SEARCH, selectorSearchView.getQuery());
        }
        if (isObjectSelector) {
            if (getIntent().getBooleanExtra(IS_MULTIPLE_SELECTION, false)) {
                outState.putSerializable(LAST_SELECTED_IDS, (Serializable) objectAdapter.getSelectedIds());
            }
        } else {
            if (valuesAdapter != null && !getIntent().getBooleanExtra(Constants.LARGE_DATA, false))
                outState.putSerializable(DISPLAYED_VALUES, (Serializable) valuesAdapter.getItemsCopy());
            if (getIntent().getBooleanExtra(IS_MULTIPLE_SELECTION, false)) {
                outState.putSerializable(LAST_SELECTED_VALUES, (Serializable) valuesAdapter.getSelectedValues());
            }
        }
    }

    private void initViewAndData() {
        initToolbar();
        initSearchView();
        initAdapter();
        initSelectorGetter();
    }

    private void initSearchView() {
        selectorSearchView = findViewById(R.id.selectorSearchView);
        View spacer = findViewById(R.id.searchViewSpacerBackgroundColor);
        if (attributes != null && attributes.getListItemBackgroundColor() != -1){
            findViewById(R.id.recyclerView).setBackgroundColor(ContextCompat.getColor(this, attributes.getListItemBackgroundColor()));
        }
        if (attributes != null && attributes.isEnableSearchView()) {
            selectorSearchView.setVisibility(View.VISIBLE);
            spacer.setVisibility(View.VISIBLE);
            selectorSearchView.initInterface(this);
            selectorSearchView.setAttributes(attributes);
            if (attributes.getSearchSpacerBackgroundColor() != -1) {
                spacer.setBackgroundColor(ContextCompat.getColor(this, attributes.getSearchSpacerBackgroundColor()));
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    attributes.getSearchWidth() != -1 ? getResources().getDimensionPixelSize(attributes.getSearchWidth()) : RelativeLayout.LayoutParams.MATCH_PARENT,
                    attributes.getSearchHeight() != -1 ? getResources().getDimensionPixelSize(attributes.getSearchHeight()) : getResources().getDimensionPixelSize(R.dimen.searchViewHeight));
            params.setMargins(attributes.getSearchMarginLeft() != -1 ? getResources().getDimensionPixelSize(attributes.getSearchMarginLeft()) : getResources().getDimensionPixelSize(R.dimen.searchMarginLeft),
                    attributes.getSearchMarginTop() != -1 ? getResources().getDimensionPixelSize(attributes.getSearchMarginTop()) : getResources().getDimensionPixelSize(R.dimen.searchMarginTop),
                    attributes.getSearchMarginRight() != -1 ? getResources().getDimensionPixelSize(attributes.getSearchMarginRight()) : getResources().getDimensionPixelSize(R.dimen.searchMarginRight),
                    attributes.getSearchMarginBottom() != -1 ? getResources().getDimensionPixelSize(attributes.getSearchMarginBottom()) : getResources().getDimensionPixelSize(R.dimen.searchMarginBottom));
            params.addRule(RelativeLayout.BELOW, R.id.toolbar);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            selectorSearchView.setLayoutParams(params);
            selectorSearchView.requestLayout();
        } else {
            selectorSearchView.setVisibility(View.GONE);
            spacer.setVisibility(View.GONE);
        }
    }

    private void getAttributes() {
        attributes = getIntent().getParcelableExtra(Constants.SELECTOR_ATTRS);
        if (attributes != null && attributes.getActivityTheme() != -1) {
            setTheme(attributes.getActivityTheme());
        }
    }

    private void initToolbar() {
        setBackButton();
        setActionsButtons(getIntent().getBooleanExtra(IS_MULTIPLE_SELECTION, false));
    }

    private void setActionsButtons(boolean isMultipleSelection) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView doneTv = findViewById(R.id.doneTv);
        TextView clearTv = findViewById(R.id.clearTv);
        ImageView clearImage = findViewById(R.id.clearImageView);
        ImageView doneImage = findViewById(R.id.doneImageView);
        titleTv = findViewById(R.id.titleTv);

        if (attributes != null && attributes.getToolbarColor() != -1){
            toolbar.setBackgroundColor(ContextCompat.getColor(this, attributes.getToolbarColor()));
        }

        if (!isMultipleSelection) {
            doneTv.setVisibility(View.GONE);
            clearTv.setVisibility(View.GONE);
        } else if (attributes != null) {
            if (attributes.getClearDrawable() != -1) {
                clearTv.setVisibility(View.GONE);
                clearImage.setVisibility(View.VISIBLE);
                clearImage.setImageResource(attributes.getClearDrawable());
                clearImage.setOnClickListener(this);
            } else {
                setClearButtonAttrs(clearTv);
            }
            if (attributes.getDoneDrawable() != -1) {
                doneTv.setVisibility(View.GONE);
                doneImage.setVisibility(View.VISIBLE);
                doneImage.setImageResource(attributes.getDoneDrawable());
                doneImage.setOnClickListener(this);
            } else {
                setDoneButtonsAttrs(doneTv);
            }
        } else {
            doneTv.setOnClickListener(this);
            clearTv.setOnClickListener(this);
        }
        setToolbarTitle();
        setToolbarAttrs();
    }

    private void setDoneButtonsAttrs(TextView doneTv) {
        doneTv.setOnClickListener(this);
        if (attributes.getDoneButtonText() != -1)
            doneTv.setText(getString(attributes.getDoneButtonText()));
        if (attributes.getDoneButtonTextColor() != -1)
            doneTv.setTextColor(ContextCompat.getColor(this, attributes.getDoneButtonTextColor()));
        if (attributes.getDoneButtonTextSize() != -1)
            doneTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimensionPixelSize(attributes.getDoneButtonTextSize()));
    }

    private void setClearButtonAttrs(TextView clearTv) {
        clearTv.setOnClickListener(this);
        if (attributes.getClearButtonText() != -1)
            clearTv.setText(getString(attributes.getClearButtonText()));
        if (attributes.getClearButtonTextColor() != -1)
            clearTv.setTextColor(ContextCompat.getColor(this, attributes.getClearButtonTextColor()));
        if (attributes.getClearButtonTextSize() != -1)
            clearTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimensionPixelSize(attributes.getClearButtonTextSize()));
    }

    private void setToolbarAttrs() {
        if (attributes != null && attributes.getTitleTextColor() != -1)
            titleTv.setTextColor(ContextCompat.getColor(this, attributes.getTitleTextColor()));
        if (attributes != null && attributes.getTitleTextSize() != -1)
            titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimensionPixelSize(attributes.getTitleTextSize()));
    }

    private void setToolbarTitle() {
        if (attributes != null && attributes.getTitleText() != -1)
            titleTv.setText(getString(attributes.getTitleText()));
        else
            titleTv.setText(getString(R.string.selector));
    }

    private void setBackButton() {
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_back_home);
        if (attributes != null && attributes.getBackArrowColor() != -1 && upArrow != null)
            upArrow.setColorFilter(ContextCompat.getColor(this, attributes.getBackArrowColor()), PorterDuff.Mode.SRC_ATOP);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setTitle(null);
        }
    }

    private void initAdapter() {
        isObjectSelector = getIntent().getBooleanExtra(IS_OBJECT_SELECTOR, false);
        if (getIntent().getBooleanExtra(Constants.LARGE_DATA, false)) {
            getValuesFromSharedPrefs();
        } else {
            setAdapterData(getIntent().getBooleanExtra(IS_MULTIPLE_SELECTION, false));
        }
    }

    @SuppressWarnings("unchecked")
    private void setAdapterData(boolean isMultiple) {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        if (isMultiple) {
            if (isObjectSelector) {
                ArrayList<Long> list = (ArrayList<Long>) getIntent().getSerializableExtra(SELECTED_IDS);
                rv.setAdapter(objectAdapter = new ObjectSelectorAdapter(this, list, attributes, this));
                onObjectsUpdated(list);
            } else {
                ArrayList<String> values = (ArrayList<String>) getIntent().getSerializableExtra(DISPLAYED_VALUES);
                ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra(SELECTED_VALUES);
                rv.setAdapter(valuesAdapter = new ValueSelectorAdapter(values, this, list, attributes, this));
                onValuesUpdated(list);
            }
        } else {
            if (isObjectSelector) {
                rv.setAdapter(objectAdapter = new ObjectSelectorAdapter(this, getIntent().getLongExtra(SELECTED_ID, -1), attributes, this));
            } else {
                ArrayList<String> values = (ArrayList<String>) getIntent().getSerializableExtra(DISPLAYED_VALUES);
                rv.setAdapter(valuesAdapter = new ValueSelectorAdapter(values, this, getIntent().getStringExtra(SELECTED_VALUE), attributes, this));
            }
        }
        rv.setItemAnimator(null);
    }

    @SuppressWarnings("unchecked")
    private void setAdapterDataFromPrefs(boolean isMultiple, List<String> values) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        if (isMultiple) {
            ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra(SELECTED_VALUES);
            rv.setAdapter(valuesAdapter = new ValueSelectorAdapter(values, this, list, attributes, this));
            onValuesUpdated(values);
        } else {
            rv.setAdapter(valuesAdapter = new ValueSelectorAdapter(values, this, getIntent().getStringExtra(SELECTED_VALUE), attributes, this));
        }
        rv.setItemAnimator(null);
        if (savedInstanceState != null) {
            restoreLastState(savedInstanceState);
        }
    }

    private void getValuesFromSharedPrefs() {

        ProgressBar progressBar = new ProgressBar(this);
        final AlertDialog show = new AlertDialog.Builder(this)
                .setTitle(R.string.please_wait)
                .setCancelable(false)
                .setView(progressBar)
                .show();

        getDataPrefsAsync = new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                String values = getSharedPreferences(Constants.SELECTOR_SHARED_PREFS, Context.MODE_PRIVATE)
                        .getString(DISPLAYED_VALUES, "");
                JSONArray jsonArray;
                List<String> list = new ArrayList<>();
                try {
                    jsonArray = new JSONArray(values);
                    for (int x = 0; x < jsonArray.length(); x++) {
                        list.add(jsonArray.getString(x));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<String> list) {
                super.onPostExecute(list);
                show.cancel();
                setAdapterDataFromPrefs(getIntent().getBooleanExtra(IS_MULTIPLE_SELECTION, false), list);
            }
        }.executeOnExecutor(new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()));
    }

    @SuppressWarnings("unchecked")
    private void initSelectorGetter() {
        if (isObjectSelector) {
            try {
                Bundle extras = getIntent().getExtras();
                String classname = extras.getString(MODEL_CLASS);
                Class<?> clazz;
                clazz = Class.forName(classname);
                targetClass = clazz;
                new SelectorData(clazz, this);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.unknown_class), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onObjectClick(long id) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_ID, id);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onObjectsSelected(List<Long> selectedIds) {
    }

    @Override
    public void onObjectsUpdated(List<Long> selectedIds) {
        if (attributes != null && attributes.getShowSelectedItemCount() && selectedIds != null) {
            setSelectedCount(selectedIds.size());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(List list) {
        if (objectAdapter != null) {
            objectAdapter.refresh(list);
            if (attributes != null && attributes.isEnableSearchView() && selectorSearchView != null && !selectorSearchView.getQuery().isEmpty()) {
                objectAdapter.getFilterAsync(selectorSearchView.getQuery());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == -1) {
            onBackPressed();
        } else if (view.getId() == R.id.doneTv || view.getId() == R.id.doneImageView) {
            handleResult();
        } else if (view.getId() == R.id.clearTv || view.getId() == R.id.clearImageView) {
            clearPressed();
        }
    }

    private void clearPressed() {
        if (isObjectSelector && objectAdapter != null)
            objectAdapter.clearSelection();
        else if (valuesAdapter != null)
            valuesAdapter.clearSelection();
    }

    private void handleResult() {
        Intent intent = new Intent();
        if (isObjectSelector)
            intent.putExtra(SELECTED_IDS, (Serializable) objectAdapter.getSelectedIds());
        else
            intent.putExtra(SELECTED_VALUES, (Serializable) valuesAdapter.getSelectedValues());
        intent.putExtra(IS_MULTIPLE_SELECTION, true);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onTextChange(String query) {
        if (isObjectSelector && objectAdapter != null) {
            objectAdapter.getFilterAsync(query);
        } else if (valuesAdapter != null) {
            valuesAdapter.getFilterAsync(query);
        }
    }

    @Override
    public void onTextEmpty() {
        if (isObjectSelector && objectAdapter != null) {
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
        Intent intent = new Intent();
        intent.putExtra(SELECTED_VALUE, value);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onValuesSelected(List<String> selectedValues) {

    }

    @Override
    public void onValuesUpdated(List<String> selectedValues) {
        if (attributes != null && attributes.getShowSelectedItemCount() && selectedValues != null) {
            setSelectedCount(selectedValues.size());
        }
    }

    private void setSelectedCount(int size) {
        if (size != 0) {
            titleTv.setText("" + size + " selected");
        } else {
            setToolbarTitle();
        }
    }
}
