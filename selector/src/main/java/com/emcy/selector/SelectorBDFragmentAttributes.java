package com.emcy.selector;

import android.os.Parcel;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class SelectorBDFragmentAttributes extends SelectorAttributes {

    @ColorRes
    private int toolbarColor = -1;
    @StringRes
    private int titleText = -1;
    @ColorRes
    private int titleTextColor = -1;
    @DimenRes
    private int titleTextSize = -1;
    @StringRes
    private int doneButtonText = -1;
    @ColorRes
    private int doneButtonTextColor = -1;
    @DimenRes
    private int doneButtonTextSize = -1;
    @DrawableRes
    private int doneDrawable = -1;
    @StringRes
    private int clearButtonText = -1;
    @ColorRes
    private int clearButtonTextColor = -1;
    @DimenRes
    private int clearButtonTextSize = -1;
    @DrawableRes
    private int clearDrawable = -1;
    private boolean showSelectedItemCount;
    private SelectorAttributes selectorAttributes;

    public SelectorBDFragmentAttributes() {
        selectorAttributes = new SelectorAttributes();
    }

    public static SelectorBDFragmentAttributes instance() {
        return new SelectorBDFragmentAttributes();
    }

    public int getDoneDrawable() {
        return doneDrawable;
    }

    public SelectorBDFragmentAttributes setDoneDrawable(int doneDrawable) {
        this.doneDrawable = doneDrawable;
        return this;
    }

    public int getClearDrawable() {
        return clearDrawable;
    }

    public SelectorBDFragmentAttributes setClearDrawable(int clearDrawable) {
        this.clearDrawable = clearDrawable;
        return this;
    }

    public int getToolbarColor() {
        return toolbarColor;
    }

    public SelectorBDFragmentAttributes setToolbarColor(int toolbarColor) {
        this.toolbarColor = toolbarColor;
        return this;
    }

    public int getTitleText() {
        return titleText;
    }

    public SelectorBDFragmentAttributes setTitleText(int titleText) {
        this.titleText = titleText;
        return this;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public SelectorBDFragmentAttributes setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return this;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public SelectorBDFragmentAttributes setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    public int getDoneButtonText() {
        return doneButtonText;
    }

    public SelectorBDFragmentAttributes setDoneButtonText(int doneButtonText) {
        this.doneButtonText = doneButtonText;
        return this;
    }

    public int getDoneButtonTextColor() {
        return doneButtonTextColor;
    }

    public SelectorBDFragmentAttributes setDoneButtonTextColor(int doneButtonTextColor) {
        this.doneButtonTextColor = doneButtonTextColor;
        return this;
    }

    public int getDoneButtonTextSize() {
        return doneButtonTextSize;
    }

    public SelectorBDFragmentAttributes setDoneButtonTextSize(int doneButtonTextSize) {
        this.doneButtonTextSize = doneButtonTextSize;
        return this;
    }

    public int getClearButtonText() {
        return clearButtonText;
    }

    public SelectorBDFragmentAttributes setClearButtonText(int clearButtonText) {
        this.clearButtonText = clearButtonText;
        return this;
    }

    public int getClearButtonTextColor() {
        return clearButtonTextColor;
    }

    public SelectorBDFragmentAttributes setClearButtonTextColor(int clearButtonTextColor) {
        this.clearButtonTextColor = clearButtonTextColor;
        return this;
    }

    public int getClearButtonTextSize() {
        return clearButtonTextSize;
    }

    public boolean getShowSelectedItemCount() {
        return showSelectedItemCount;
    }

    public SelectorBDFragmentAttributes setClearButtonTextSize(int clearButtonTextSize) {
        this.clearButtonTextSize = clearButtonTextSize;
        return this;
    }

    public SelectorBDFragmentAttributes enableSelectedItemsCount() {
        this.showSelectedItemCount = true;
        return this;
    }

    private SelectorBDFragmentAttributes(Parcel in) {
        super(in);
        toolbarColor = in.readInt();
        titleText = in.readInt();
        titleTextColor = in.readInt();
        titleTextSize = in.readInt();
        doneButtonText = in.readInt();
        doneButtonTextColor = in.readInt();
        doneButtonTextSize = in.readInt();
        doneDrawable = in.readInt();
        clearButtonText = in.readInt();
        clearButtonTextColor = in.readInt();
        clearButtonTextSize = in.readInt();
        clearDrawable = in.readInt();
        showSelectedItemCount = in.readByte() != 0;
        selectorAttributes = in.readParcelable(SelectorAttributes.class.getClassLoader());
    }

    public static final Creator<SelectorBDFragmentAttributes> CREATOR = new Creator<SelectorBDFragmentAttributes>() {
        @Override
        public SelectorBDFragmentAttributes createFromParcel(Parcel in) {
            return new SelectorBDFragmentAttributes(in);
        }

        @Override
        public SelectorBDFragmentAttributes[] newArray(int size) {
            return new SelectorBDFragmentAttributes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(toolbarColor);
        parcel.writeInt(titleText);
        parcel.writeInt(titleTextColor);
        parcel.writeInt(titleTextSize);
        parcel.writeInt(doneButtonText);
        parcel.writeInt(doneButtonTextColor);
        parcel.writeInt(doneButtonTextSize);
        parcel.writeInt(doneDrawable);
        parcel.writeInt(clearButtonText);
        parcel.writeInt(clearButtonTextColor);
        parcel.writeInt(clearButtonTextSize);
        parcel.writeInt(clearDrawable);
        parcel.writeByte((byte) (showSelectedItemCount ? 1 : 0));
        parcel.writeParcelable(selectorAttributes, i);
    }

    @Override
    public int getListItemHeight() {
        return selectorAttributes.getListItemHeight();
    }

    @Override
    public SelectorBDFragmentAttributes setListItemHeight(int listItemHeight) {
        selectorAttributes.setListItemHeight(listItemHeight);
        return this;
    }

    @Override
    public int getTextNormalColor() {
        return selectorAttributes.getTextNormalColor();
    }

    @Override
    public SelectorBDFragmentAttributes setTextNormalColor(int textNormalColor) {
        selectorAttributes.setTextNormalColor(textNormalColor);
        return this;
    }

    @Override
    public int getTextSelectedColor() {
        return selectorAttributes.getTextSelectedColor();
    }

    @Override
    public SelectorBDFragmentAttributes setTextSelectedColor(int textSelectedColor) {
        selectorAttributes.setTextSelectedColor(textSelectedColor);
        return this;
    }

    @Override
    public int getTextSize() {
        return selectorAttributes.getTextSize();
    }

    @Override
    public SelectorBDFragmentAttributes setTextSize(int textSize) {
        selectorAttributes.setTextSize(textSize);
        return this;
    }

    @Override
    public int getListItemBackgroundColor() {
        return selectorAttributes.getListItemBackgroundColor();
    }

    @Override
    public SelectorBDFragmentAttributes setListItemBackgroundColor(int listItemBackgroundColor) {
        selectorAttributes.setListItemBackgroundColor(listItemBackgroundColor);
        return this;
    }

    @Override
    public int getNormalTickColor() {
        return selectorAttributes.getNormalTickColor();
    }

    @Override
    public SelectorBDFragmentAttributes setNormalTickColor(int normalTickColor) {
        selectorAttributes.setNormalTickColor(normalTickColor);
        return this;
    }

    @Override
    public int getTickDrawable() {
        return selectorAttributes.getTickDrawable();
    }

    @Override
    public SelectorBDFragmentAttributes setTickDrawable(int tickDrawable) {
        selectorAttributes.setTickDrawable(tickDrawable);
        return this;
    }

    @Override
    public int getListItemSeparatorColor() {
        return selectorAttributes.getListItemSeparatorColor();
    }

    @Override
    public SelectorBDFragmentAttributes setListItemSeparatorColor(int listItemSeparatorColor) {
        selectorAttributes.setListItemSeparatorColor(listItemSeparatorColor);
        return this;
    }

    @Override
    public int getListItemSeparatorHeight() {
        return selectorAttributes.getListItemSeparatorHeight();
    }

    @Override
    public SelectorBDFragmentAttributes setListItemSeparatorHeight(int listItemSeparatorHeight) {
        selectorAttributes.setListItemSeparatorHeight(listItemSeparatorHeight);
        return this;
    }

    @Override
    public boolean isEnableSearchView() {
        return selectorAttributes.isEnableSearchView();
    }

    @Override
    public SelectorBDFragmentAttributes setEnableSearchView(boolean enableSearchView) {
        selectorAttributes.setEnableSearchView(enableSearchView);
        return this;
    }

    @Override
    public int getSearchDrawable() {
        return selectorAttributes.getSearchDrawable();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchDrawable(int searchDrawable) {
        selectorAttributes.setSearchDrawable(searchDrawable);
        return this;
    }

    @Override
    public int getSearchCancelDrawable() {
        return selectorAttributes.getSearchCancelDrawable();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchCancelDrawable(int searchCancelDrawable) {
        selectorAttributes.setSearchCancelDrawable(searchCancelDrawable);
        return this;
    }

    @Override
    public int getSearchHintColor() {
        return selectorAttributes.getSearchHintColor();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchHintColor(int searchHintColor) {
        selectorAttributes.setSearchHintColor(searchHintColor);
        return this;
    }

    @Override
    public int getSearchHintText() {
        return selectorAttributes.getSearchHintText();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchHintText(int searchHintText) {
        selectorAttributes.setSearchHintText(searchHintText);
        return this;
    }

    @Override
    public int getSearchTextColor() {
        return selectorAttributes.getSearchTextColor();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchTextColor(int searchTextColor) {
        selectorAttributes.setSearchTextColor(searchTextColor);
        return this;
    }

    @Override
    public int getSearchBackgroundColor() {
        return selectorAttributes.getSearchBackgroundColor();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchBackgroundColor(int searchBackgroundColor) {
        selectorAttributes.setSearchBackgroundColor(searchBackgroundColor);
        return this;
    }

    @Override
    public int getSearchSpacerBackgroundColor() {
        return selectorAttributes.getSearchSpacerBackgroundColor();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchSpacerBackgroundColor(int searchSpacerBackgroundColor) {
        selectorAttributes.setSearchSpacerBackgroundColor(searchSpacerBackgroundColor);
        return this;
    }

    @Override
    public int getSearchCornerRadius() {
        return selectorAttributes.getSearchCornerRadius();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchCornerRadius(int searchCornerRadius) {
        selectorAttributes.setSearchCornerRadius(searchCornerRadius);
        return this;
    }

    @Override
    public int getSearchWidth() {
        return selectorAttributes.getSearchWidth();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchWidth(int searchWidth) {
        selectorAttributes.setSearchWidth(searchWidth);
        return this;
    }

    @Override
    public int getSearchHeight() {
        return selectorAttributes.getSearchHeight();
    }

    @Override
    public SelectorBDFragmentAttributes setSearchHeight(int searchHeight) {
        selectorAttributes.setSearchHeight(searchHeight);
        return this;
    }

    @Override
    public SelectorBDFragmentAttributes setSearchMargins(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        this.selectorAttributes.setSearchMargins(marginLeft, marginTop, marginRight, marginBottom);
        return this;
    }

    @Override
    public int getSearchMarginLeft() {
        return selectorAttributes.getSearchMarginLeft();
    }


    @Override
    public int getSearchMarginRight() {
        return selectorAttributes.getSearchMarginRight();
    }


    @Override
    public int getSearchMarginTop() {
        return selectorAttributes.getSearchMarginTop();
    }


    @Override
    public int getSearchMarginBottom() {
        return selectorAttributes.getSearchMarginBottom();
    }

    @Override
    public SelectorBDFragmentAttributes setTextMarginStartPercent(float textMarginStartPercent) {
        this.selectorAttributes.setTextMarginStartPercent(textMarginStartPercent);
        return this;
    }

    @Override
    public float getTextMarginStartPercent() {
        return selectorAttributes.getTextMarginStartPercent();
    }

    @Override
    public float getTickMarginEndPercent() {
        return selectorAttributes.getTickMarginEndPercent();
    }

    @Override
    public SelectorBDFragmentAttributes setTickMarginEndPercent(float tickMarginEndPercent) {
        this.selectorAttributes.setTickMarginEndPercent(tickMarginEndPercent);
        return this;
    }
}
