package com.emcy.selector;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.emcy.selector.Constants.DISPLAYED_VALUES;
import static com.emcy.selector.Constants.IS_FROM_ACTIVITY;
import static com.emcy.selector.Constants.IS_MULTIPLE_SELECTION;
import static com.emcy.selector.Constants.IS_OBJECT_SELECTOR;
import static com.emcy.selector.Constants.MODEL_CLASS;
import static com.emcy.selector.Constants.SELECTED_ID;
import static com.emcy.selector.Constants.SELECTED_IDS;
import static com.emcy.selector.Constants.SELECTED_VALUE;
import static com.emcy.selector.Constants.SELECTED_VALUES;
import static com.emcy.selector.Constants.SELECTOR_ATTRS;
import static com.emcy.selector.R.id.clearImage;
import static com.emcy.selector.R.id.clearTv;
import static com.emcy.selector.R.id.doneImage;
import static com.emcy.selector.R.id.doneTv;

/**
 * BottomSheetDialog fragment for selecting objects or values
 */
public class SelectorBottomSheet extends BottomSheetDialogFragment
        implements OnObjectSelectorListener,
        OnValuesSelectorListener, SelectorData.OnSelectorDataResult,
        View.OnClickListener, OnSearchViewListener {

    private BottomSheetBehavior mBehavior;
    private Handler closeHandler, dismissHandler;
    private ObjectSelectorAdapter objectAdapter;
    private ValueSelectorAdapter valuesAdapter;
    private View contentView;
    private SelectorBDFragmentAttributes attributes;
    private boolean isFromActivity;
    private Handler mHandler;
    private boolean isObjectSelector;
    private TextView titleTv;

    @Override
    public void onPause() {
        super.onPause();
        if (closeHandler != null) closeHandler.removeCallbacksAndMessages(null);
        if (dismissHandler != null) dismissHandler.removeCallbacksAndMessages(null);
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        contentView = View.inflate(getContext(), R.layout.layout_fragment_bottom_sheet_selector, null);
        dialog.setContentView(contentView);
        initViewsAndData();
    }

    private void initViewsAndData() {
        boolean isMultiple = false;
        if (getArguments() != null) {
            attributes = getArguments().getParcelable(SELECTOR_ATTRS);
            isMultiple = getArguments().getBoolean(IS_MULTIPLE_SELECTION, false);
        }
        initToolbar(isMultiple);
        initAdapter(isMultiple);
        initSelectorDataGetter();
        initBehavior();
        initSearchView();
        if (attributes != null)
            initFonts();
    }

    private void initFonts() {
        if (getActivity() != null) {
            Helper.setTypeFace(getActivity(), titleTv, attributes.getTitleFont());
            Helper.setTypeFace(getActivity(), (TextView) contentView.findViewById(R.id.clearTv), attributes.getActionButtonsFont());
            Helper.setTypeFace(getActivity(), (TextView) contentView.findViewById(R.id.doneTv), attributes.getActionButtonsFont());
        }
    }

    private void initSearchView() {
        SelectorSearchView selectorSearchView = contentView.findViewById(R.id.selectorSearchView);
        View spacer = contentView.findViewById(R.id.searchViewSpacerBackgroundColor);
        if (attributes != null && attributes.getListItemBackgroundColor() != -1 && getActivity() != null) {
            contentView.findViewById(R.id.recyclerView).setBackgroundColor(getColor(attributes.getListItemBackgroundColor()));
        }
        if (attributes != null && attributes.isEnableSearchView()) {
            selectorSearchView.setVisibility(View.VISIBLE);
            spacer.setVisibility(View.VISIBLE);
            selectorSearchView.initInterface(this);
            selectorSearchView.setAttributes(attributes);
            if (attributes.getSearchSpacerBackgroundColor() != -1 && getActivity() != null) {
                spacer.setBackgroundColor(getColor(attributes.getSearchSpacerBackgroundColor()));
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

    private void initBehavior() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            mBehavior = (BottomSheetBehavior) behavior;
        }
    }

    private void initToolbar(boolean isMultiple) {
        Toolbar toolbar = contentView.findViewById(R.id.toolbar);
        TextView doneTv = contentView.findViewById(R.id.doneTv);
        TextView clearTv = contentView.findViewById(R.id.clearTv);
        ImageView clearImage = contentView.findViewById(R.id.clearImage);
        ImageView doneImage = contentView.findViewById(R.id.doneImage);
        titleTv = contentView.findViewById(R.id.titleTv);
        if (attributes != null && attributes.getToolbarColor() != -1 && getActivity() != null) {
            toolbar.setBackgroundColor(getColor(attributes.getToolbarColor()));
        }
        if (!isMultiple) {
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
        if (attributes.getDoneButtonTextColor() != -1 && getActivity() != null)
            doneTv.setTextColor(getColor(attributes.getDoneButtonTextColor()));
        if (attributes.getDoneButtonTextSize() != -1)
            doneTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimensionPixelSize(attributes.getDoneButtonTextSize()));
    }

    private void setClearButtonAttrs(TextView clearTv) {
        clearTv.setOnClickListener(this);
        if (attributes.getClearButtonText() != -1)
            clearTv.setText(getString(attributes.getClearButtonText()));
        if (attributes.getClearButtonTextColor() != -1 && getActivity() != null)
            clearTv.setTextColor(getColor(attributes.getClearButtonTextColor()));
        if (attributes.getClearButtonTextSize() != -1)
            clearTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimensionPixelSize(attributes.getClearButtonTextSize()));
    }

    private void setToolbarAttrs() {
        if (attributes != null && attributes.getTitleTextColor() != -1 && getActivity() != null)
            titleTv.setTextColor(getColor(attributes.getTitleTextColor()));
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

    @SuppressWarnings("unchecked")
    private void initAdapter(boolean isMultiple) {
        if (getArguments() != null)
            isObjectSelector = getArguments().getBoolean(IS_OBJECT_SELECTOR, false);
        RecyclerView rv = contentView.findViewById(R.id.recyclerView);
        isFromActivity = getArguments().getBoolean(IS_FROM_ACTIVITY, false);
        if (isMultiple) {
            if (isObjectSelector) {
                ArrayList<Long> list = (ArrayList<Long>) getArguments().getSerializable(SELECTED_IDS);
                rv.setAdapter(objectAdapter = new ObjectSelectorAdapter(this, list, attributes, getActivity()));
                onObjectsUpdated(list);
            } else {
                ArrayList<String> values = (ArrayList<String>) getArguments().getSerializable(DISPLAYED_VALUES);
                ArrayList<String> list = (ArrayList<String>) getArguments().getSerializable(SELECTED_VALUES);
                rv.setAdapter(valuesAdapter = new ValueSelectorAdapter(values, this, list, attributes, getActivity()));
                onValuesUpdated(list);
            }
        } else {
            if (isObjectSelector) {
                rv.setAdapter(objectAdapter = new ObjectSelectorAdapter(this, getArguments().getLong(SELECTED_ID, -1), attributes, getActivity()));
            } else {
                ArrayList<String> values = (ArrayList<String>) getArguments().getSerializable(DISPLAYED_VALUES);
                rv.setAdapter(valuesAdapter = new ValueSelectorAdapter(values, this, getArguments().getString(SELECTED_VALUE), attributes, getActivity()));
            }
        }
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(null);
    }

    @SuppressWarnings("unchecked")
    private void initSelectorDataGetter() {
        if (isObjectSelector) {
            try {
                Bundle extras = getArguments();
                if (extras != null) {
                    String classname = extras.getString(MODEL_CLASS);
                    Class<?> clazz = Class.forName(classname);
                    new SelectorData(clazz, this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), getContext().getString(R.string.unknown_class), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }
    }

    private void closeBottomSheet() {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        dismissHandler = new Handler();
        dismissHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 300);
    }

    private void startHandler() {
        closeHandler = new Handler();
        closeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeBottomSheet();
            }
        }, 500);
    }

    @Override
    public void onObjectClick(long id) {
        startHandler();
        try {
            (isFromActivity ? (OnObjectSelectorListener) getActivity() :
                    (OnObjectSelectorListener) getParentFragment()).onObjectClick(id);
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onObjectsSelected(List<Long> selectedIds) {

    }

    @Override
    public void onObjectsUpdated(List<Long> selectedIds) {
        try {
            (isFromActivity ? (OnObjectSelectorListener) getActivity() :
                    (OnObjectSelectorListener) getParentFragment()).onObjectsUpdated(selectedIds);
            if (attributes != null && attributes.getShowSelectedItemCount() && selectedIds != null) {
                setSelectedCount(selectedIds.size());
            }
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onValueClick(String value) {
        startHandler();
        try {
            (isFromActivity ? (OnValuesSelectorListener) getActivity() :
                    (OnValuesSelectorListener) getParentFragment()).onValueClick(value);
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValuesSelected(List<String> selectedValues) {

    }

    @Override
    public void onValuesUpdated(List<String> selectedValues) {
        try {
            (isFromActivity ? (OnValuesSelectorListener) getActivity() :
                    (OnValuesSelectorListener) getParentFragment()).onValuesUpdated(selectedValues);
            if (attributes != null && attributes.getShowSelectedItemCount() && selectedValues != null) {
                setSelectedCount(selectedValues.size());
            }
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(List list) {
        if (objectAdapter != null)
            objectAdapter.refresh(list);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == doneTv || view.getId() == doneImage) {
            donePressed();
        } else if (view.getId() == clearTv || view.getId() == clearImage) {
            clearPressed();
        }
    }

    private void clearPressed() {
        if (isObjectSelector && objectAdapter != null)
            objectAdapter.clearSelection();
        else if (valuesAdapter != null)
            valuesAdapter.clearSelection();
    }

    private void donePressed() {
        startHandler();
        try {
            if (isObjectSelector) {
                (isFromActivity ? (OnObjectSelectorListener) getActivity() :
                        (OnObjectSelectorListener) getParentFragment()).onObjectsSelected(objectAdapter.getSelectedIds());
            } else {
                (isFromActivity ? (OnValuesSelectorListener) getActivity() :
                        (OnValuesSelectorListener) getParentFragment()).onValuesSelected(valuesAdapter.getSelectedValues());
            }
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTextChange(String query) {
        if (isObjectSelector) {
            objectAdapter.getFilterAsync(query);
        } else {
            valuesAdapter.getFilterAsync(query);
        }
    }

    @Override
    public void onTextEmpty() {
        if (isObjectSelector) {
            objectAdapter.onSearchClosed();
        } else {
            valuesAdapter.onSearchClosed();
        }
    }

    private void expandBehaviour() {
        if (attributes != null && attributes.isEnableSearchView()) {
            mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }, 200);
        }
    }

    @Override
    public void onTextFocusChange(boolean focused) {
        if (focused)
            expandBehaviour();
    }

    private void setSelectedCount(int count) {
        if (count != 0) {
            titleTv.setText("" + count + " selected");
        } else {
            setToolbarTitle();
        }
    }

    private int getColor(int colorRes) {
        if (getActivity() != null) {
            try {
                return ContextCompat.getColor(getActivity(), colorRes);
            } catch (Resources.NotFoundException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
}