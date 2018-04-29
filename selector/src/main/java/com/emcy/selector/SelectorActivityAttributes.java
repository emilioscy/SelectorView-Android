package com.emcy.selector;

import android.os.Parcel;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

public class SelectorActivityAttributes extends SelectorAttributes {

    @StyleRes
    private int activityTheme = -1;
    @ColorRes
    private int toolbarColor = -1;
    @StringRes
    private int titleText = -1;
    @DimenRes
    private int titleTextSize = -1;
    @ColorRes
    private int titleTextColor = -1;
    private String titleFont = "";
    @StringRes
    private int doneButtonText = -1;
    @DimenRes
    private int doneButtonTextSize = -1;
    @ColorRes
    private int doneButtonTextColor = -1;
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
    @ColorRes
    private int backArrowColor = -1;
    private String actionButtonsFont = "";
    private SelectorAttributes selectorAttributes;
    private boolean showSelectedItemCount;

    public SelectorActivityAttributes() {
        this.selectorAttributes = new SelectorAttributes();
    }

    public static SelectorActivityAttributes instance() {
        return new SelectorActivityAttributes();
    }

    public int getActivityTheme() {
        return activityTheme;
    }

    public SelectorActivityAttributes setActivityTheme(int activityTheme) {
        this.activityTheme = activityTheme;
        return this;
    }

    public int getDoneDrawable() {
        return doneDrawable;
    }

    public SelectorActivityAttributes setDoneDrawable(int doneDrawable) {
        this.doneDrawable = doneDrawable;
        return this;
    }

    public int getClearDrawable() {
        return clearDrawable;
    }

    public SelectorActivityAttributes setClearDrawable(int clearDrawable) {
        this.clearDrawable = clearDrawable;
        return this;
    }

    public int getToolbarColor() {
        return toolbarColor;
    }

    public SelectorActivityAttributes setToolbarColor(int toolbarColor) {
        this.toolbarColor = toolbarColor;
        return this;
    }

    public int getTitleText() {
        return titleText;
    }

    public SelectorActivityAttributes setTitleText(int titleText) {
        this.titleText = titleText;
        return this;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public SelectorActivityAttributes setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public SelectorActivityAttributes setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return this;
    }

    public int getDoneButtonText() {
        return doneButtonText;
    }

    public SelectorActivityAttributes setDoneButtonText(int doneButtonText) {
        this.doneButtonText = doneButtonText;
        return this;
    }

    public int getDoneButtonTextSize() {
        return doneButtonTextSize;
    }

    public SelectorActivityAttributes setDoneButtonTextSize(int doneButtonTextSize) {
        this.doneButtonTextSize = doneButtonTextSize;
        return this;
    }

    public int getDoneButtonTextColor() {
        return doneButtonTextColor;
    }

    public SelectorActivityAttributes setDoneButtonTextColor(int doneButtonTextColor) {
        this.doneButtonTextColor = doneButtonTextColor;
        return this;
    }

    public int getClearButtonText() {
        return clearButtonText;
    }

    public SelectorActivityAttributes setClearButtonText(int clearButtonText) {
        this.clearButtonText = clearButtonText;
        return this;
    }

    public int getClearButtonTextColor() {
        return clearButtonTextColor;
    }

    public SelectorActivityAttributes setClearButtonTextColor(int clearButtonTextColor) {
        this.clearButtonTextColor = clearButtonTextColor;
        return this;
    }

    public int getClearButtonTextSize() {
        return clearButtonTextSize;
    }

    public SelectorActivityAttributes setClearButtonTextSize(int clearButtonTextSize) {
        this.clearButtonTextSize = clearButtonTextSize;
        return this;
    }

    public int getBackArrowColor() {
        return backArrowColor;
    }

    public boolean getShowSelectedItemCount() {
        return showSelectedItemCount;
    }

    public SelectorActivityAttributes setBackArrowColor(int backArrowColor) {
        this.backArrowColor = backArrowColor;
        return this;
    }

    public SelectorActivityAttributes enableSelectedItemsCount() {
        this.showSelectedItemCount = true;
        return this;
    }

    public SelectorActivityAttributes setTitleFont(String typeFacePath) {
        this.titleFont = typeFacePath;
        return this;
    }

    public SelectorActivityAttributes setActionButtonsFont(String typeFacePath) {
        this.actionButtonsFont = typeFacePath;
        return this;
    }

    public String getTitleFont() {
        return this.titleFont;
    }

    public String getActionButtonsFont() {
        return this.actionButtonsFont;
    }

    private SelectorActivityAttributes(Parcel in) {
        activityTheme = in.readInt();
        toolbarColor = in.readInt();
        titleText = in.readInt();
        titleTextSize = in.readInt();
        titleTextColor = in.readInt();
        titleFont = in.readString();
        doneButtonText = in.readInt();
        doneButtonTextSize = in.readInt();
        doneButtonTextColor = in.readInt();
        doneDrawable = in.readInt();
        clearButtonText = in.readInt();
        clearButtonTextColor = in.readInt();
        clearButtonTextSize = in.readInt();
        clearDrawable = in.readInt();
        backArrowColor = in.readInt();
        actionButtonsFont = in.readString();
        showSelectedItemCount = in.readByte() != 0;
        selectorAttributes = in.readParcelable(SelectorAttributes.class.getClassLoader());
    }

    public static final Creator<SelectorActivityAttributes> CREATOR = new Creator<SelectorActivityAttributes>() {
        @Override
        public SelectorActivityAttributes createFromParcel(Parcel in) {
            return new SelectorActivityAttributes(in);
        }

        @Override
        public SelectorActivityAttributes[] newArray(int size) {
            return new SelectorActivityAttributes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(activityTheme);
        parcel.writeInt(toolbarColor);
        parcel.writeInt(titleText);
        parcel.writeInt(titleTextSize);
        parcel.writeInt(titleTextColor);
        parcel.writeString(titleFont);
        parcel.writeInt(doneButtonText);
        parcel.writeInt(doneButtonTextSize);
        parcel.writeInt(doneButtonTextColor);
        parcel.writeInt(doneDrawable);
        parcel.writeInt(clearButtonText);
        parcel.writeInt(clearButtonTextColor);
        parcel.writeInt(clearButtonTextSize);
        parcel.writeInt(clearDrawable);
        parcel.writeInt(backArrowColor);
        parcel.writeString(actionButtonsFont);
        parcel.writeByte((byte) (showSelectedItemCount ? 1 : 0));
        parcel.writeParcelable(selectorAttributes, i);
    }

    @Override
    public int getListItemHeight() {
        return selectorAttributes.getListItemHeight();
    }

    @Override
    public SelectorActivityAttributes setListItemHeight(int listItemHeight) {
        selectorAttributes.setListItemHeight(listItemHeight);
        return this;
    }

    @Override
    public int getTextNormalColor() {
        return selectorAttributes.getTextNormalColor();
    }

    @Override
    public SelectorActivityAttributes setTextNormalColor(int textNormalColor) {
        selectorAttributes.setTextNormalColor(textNormalColor);
        return this;
    }

    @Override
    public int getTextSelectedColor() {
        return selectorAttributes.getTextSelectedColor();
    }

    @Override
    public SelectorActivityAttributes setTextSelectedColor(int textSelectedColor) {
        selectorAttributes.setTextSelectedColor(textSelectedColor);
        return this;
    }

    @Override
    public int getTextSize() {
        return selectorAttributes.getTextSize();
    }

    @Override
    public SelectorActivityAttributes setTextSize(int textSize) {
        selectorAttributes.setTextSize(textSize);
        return this;
    }

    @Override
    public int getListItemBackgroundColor() {
        return selectorAttributes.getListItemBackgroundColor();
    }

    @Override
    public SelectorActivityAttributes setListItemBackgroundColor(int listItemBackgroundColor) {
        selectorAttributes.setListItemBackgroundColor(listItemBackgroundColor);
        return this;
    }

    @Override
    public int getNormalTickColor() {
        return selectorAttributes.getNormalTickColor();
    }

    @Override
    public SelectorActivityAttributes setNormalTickColor(int normalTickColor) {
        selectorAttributes.setNormalTickColor(normalTickColor);
        return this;
    }

    @Override
    public int getTickDrawable() {
        return selectorAttributes.getTickDrawable();
    }

    @Override
    public SelectorActivityAttributes setTickDrawable(int tickDrawable) {
        selectorAttributes.setTickDrawable(tickDrawable);
        return this;
    }

    @Override
    public int getListItemSeparatorColor() {
        return selectorAttributes.getListItemSeparatorColor();
    }

    @Override
    public SelectorActivityAttributes setListItemSeparatorColor(int listItemSeparatorColor) {
        selectorAttributes.setListItemSeparatorColor(listItemSeparatorColor);
        return this;
    }

    @Override
    public int getListItemSeparatorHeight() {
        return selectorAttributes.getListItemSeparatorHeight();
    }

    @Override
    public SelectorActivityAttributes setListItemSeparatorHeight(int listItemSeparatorHeight) {
        selectorAttributes.setListItemSeparatorHeight(listItemSeparatorHeight);
        return this;
    }

    @Override
    public boolean isEnableSearchView() {
        return selectorAttributes.isEnableSearchView();
    }

    @Override
    public SelectorActivityAttributes setEnableSearchView(boolean enableSearchView) {
        selectorAttributes.setEnableSearchView(enableSearchView);
        return this;
    }

    @Override
    public int getSearchDrawable() {
        return selectorAttributes.getSearchDrawable();
    }

    @Override
    public SelectorActivityAttributes setSearchDrawable(int searchDrawable) {
        selectorAttributes.setSearchDrawable(searchDrawable);
        return this;
    }

    @Override
    public int getSearchCancelDrawable() {
        return selectorAttributes.getSearchCancelDrawable();
    }

    @Override
    public SelectorActivityAttributes setSearchCancelDrawable(int searchCancelDrawable) {
        selectorAttributes.setSearchCancelDrawable(searchCancelDrawable);
        return this;
    }

    @Override
    public int getSearchHintColor() {
        return selectorAttributes.getSearchHintColor();
    }

    @Override
    public SelectorActivityAttributes setSearchHintColor(int searchHintColor) {
        selectorAttributes.setSearchHintColor(searchHintColor);
        return this;
    }

    @Override
    public int getSearchHintText() {
        return selectorAttributes.getSearchHintText();
    }

    @Override
    public SelectorActivityAttributes setSearchHintText(int searchHintText) {
        selectorAttributes.setSearchHintText(searchHintText);
        return this;
    }

    @Override
    public int getSearchTextColor() {
        return selectorAttributes.getSearchTextColor();
    }

    @Override
    public SelectorActivityAttributes setSearchTextColor(int searchTextColor) {
        selectorAttributes.setSearchTextColor(searchTextColor);
        return this;
    }

    @Override
    public int getSearchBackgroundColor() {
        return selectorAttributes.getSearchBackgroundColor();
    }

    @Override
    public SelectorActivityAttributes setSearchBackgroundColor(int searchBackgroundColor) {
        selectorAttributes.setSearchBackgroundColor(searchBackgroundColor);
        return this;
    }

    @Override
    public int getSearchSpacerBackgroundColor() {
        return selectorAttributes.getSearchSpacerBackgroundColor();
    }

    @Override
    public SelectorActivityAttributes setSearchSpacerBackgroundColor(int searchSpacerBackgroundColor) {
        selectorAttributes.setSearchSpacerBackgroundColor(searchSpacerBackgroundColor);
        return this;
    }

    @Override
    public int getSearchCornerRadius() {
        return selectorAttributes.getSearchCornerRadius();
    }

    @Override
    public SelectorActivityAttributes setSearchCornerRadius(int searchCornerRadius) {
        selectorAttributes.setSearchCornerRadius(searchCornerRadius);
        return this;
    }

    @Override
    public int getSearchWidth() {
        return selectorAttributes.getSearchWidth();
    }

    @Override
    public SelectorActivityAttributes setSearchWidth(int searchWidth) {
        selectorAttributes.setSearchWidth(searchWidth);
        return this;
    }

    @Override
    public int getSearchHeight() {
        return selectorAttributes.getSearchHeight();
    }

    @Override
    public SelectorActivityAttributes setSearchHeight(int searchHeight) {
        selectorAttributes.setSearchHeight(searchHeight);
        return this;
    }

    @Override
    public SelectorActivityAttributes setSearchMargins(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        this.selectorAttributes.setSearchMargins(marginLeft, marginTop, marginRight, marginBottom);
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
    public SelectorActivityAttributes setTextMarginStartPercent(float textMarginStartPercent) {
        this.selectorAttributes.setTextMarginStartPercent(textMarginStartPercent);
        return this;
    }

    @Override
    public SelectorActivityAttributes setTickMarginEndPercent(float tickMarginEndPercent) {
        this.selectorAttributes.setTickMarginEndPercent(tickMarginEndPercent);
        return this;
    }

    @Override
    public SelectorActivityAttributes setTextFont(String typeFacePath) {
        this.selectorAttributes.setTextFont(typeFacePath);
        return this;
    }

    @Override
    public SelectorActivityAttributes setSearchFont(String typeFacePath) {
        this.selectorAttributes.setSearchFont(typeFacePath);
        return this;
    }

    @Override
    public String getTextFont() {
        return this.selectorAttributes.getTextFont();
    }

    @Override
    public String getSearchFont() {
        return this.selectorAttributes.getSearchFont();
    }
}
