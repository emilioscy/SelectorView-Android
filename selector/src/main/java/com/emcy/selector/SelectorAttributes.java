package com.emcy.selector;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Attributes for customise the adapter list item and also the searchView
 */
class SelectorAttributes implements Parcelable {

    @DimenRes
    private int listItemHeight = -1;
    @ColorRes
    private int textNormalColor = -1;
    @ColorRes
    private int textSelectedColor = -1;
    @DimenRes
    private int textSize = -1;
    private float textMarginStartPercent;
    private String textFont = "";
    @ColorRes
    private int listItemBackgroundColor = -1;
    @ColorRes
    private int normalTickColor = -1;
    @DrawableRes
    private int tickDrawable = -1;
    private float tickMarginEndPercent;
    @ColorRes
    private int listItemSeparatorColor = -1;
    @DimenRes
    private int listItemSeparatorHeight = -1;
    private boolean enableSearchView;
    @DrawableRes
    private int searchDrawable = -1;
    @DrawableRes
    private int searchCancelDrawable = -1;
    @ColorRes
    private int searchHintColor = -1;
    @StringRes
    private int searchHintText = -1;
    @ColorRes
    private int searchTextColor = -1;
    @ColorRes
    private int searchBackgroundColor = -1;
    @ColorRes
    private int searchSpacerBackgroundColor = -1;
    @DimenRes
    private int searchCornerRadius = -1;
    private String searchTextFont = "";
    @DimenRes
    private int searchWidth = -1;
    @DimenRes
    private int searchHeight = -1;
    @DimenRes
    private int searchMarginLeft = -1;
    @DimenRes
    private int searchMarginRight = -1;
    @DimenRes
    private int searchMarginTop = -1;
    @DimenRes
    private int searchMarginBottom = -1;

    SelectorAttributes() {
    }

    SelectorAttributes(Parcel in) {
        listItemHeight = in.readInt();
        textNormalColor = in.readInt();
        textSelectedColor = in.readInt();
        textSize = in.readInt();
        textMarginStartPercent = in.readFloat();
        textFont = in.readString();
        listItemBackgroundColor = in.readInt();
        normalTickColor = in.readInt();
        tickDrawable = in.readInt();
        tickMarginEndPercent = in.readFloat();
        listItemSeparatorColor = in.readInt();
        listItemSeparatorHeight = in.readInt();
        enableSearchView = in.readByte() != 0;
        searchDrawable = in.readInt();
        searchCancelDrawable = in.readInt();
        searchHintColor = in.readInt();
        searchHintText = in.readInt();
        searchTextColor = in.readInt();
        searchBackgroundColor = in.readInt();
        searchSpacerBackgroundColor = in.readInt();
        searchCornerRadius = in.readInt();
        searchTextFont = in.readString();
        searchWidth = in.readInt();
        searchHeight = in.readInt();
        searchMarginLeft = in.readInt();
        searchMarginRight = in.readInt();
        searchMarginTop = in.readInt();
        searchMarginBottom = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(listItemHeight);
        parcel.writeInt(textNormalColor);
        parcel.writeInt(textSelectedColor);
        parcel.writeInt(textSize);
        parcel.writeFloat(textMarginStartPercent);
        parcel.writeString(textFont);
        parcel.writeInt(listItemBackgroundColor);
        parcel.writeInt(normalTickColor);
        parcel.writeInt(tickDrawable);
        parcel.writeFloat(tickMarginEndPercent);
        parcel.writeInt(listItemSeparatorColor);
        parcel.writeInt(listItemSeparatorHeight);
        parcel.writeByte((byte) (enableSearchView ? 1 : 0));
        parcel.writeInt(searchDrawable);
        parcel.writeInt(searchCancelDrawable);
        parcel.writeInt(searchHintColor);
        parcel.writeInt(searchHintText);
        parcel.writeInt(searchTextColor);
        parcel.writeInt(searchBackgroundColor);
        parcel.writeInt(searchSpacerBackgroundColor);
        parcel.writeInt(searchCornerRadius);
        parcel.writeString(searchTextFont);
        parcel.writeInt(searchWidth);
        parcel.writeInt(searchHeight);
        parcel.writeInt(searchMarginLeft);
        parcel.writeInt(searchMarginRight);
        parcel.writeInt(searchMarginTop);
        parcel.writeInt(searchMarginBottom);
    }

    public int getSearchWidth() {
        return searchWidth;
    }

    public SelectorAttributes setSearchWidth(int searchWidth) {
        this.searchWidth = searchWidth;
        return this;
    }

    public int getSearchHeight() {
        return searchHeight;
    }

    public SelectorAttributes setSearchHeight(int searchHeight) {
        this.searchHeight = searchHeight;
        return this;
    }

    public SelectorAttributes setSearchMargins(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        this.searchMarginLeft = marginLeft;
        this.searchMarginTop = marginTop;
        this.searchMarginRight = marginRight;
        this.searchMarginBottom = marginBottom;
        return this;
    }

    public float getTextMarginStartPercent() {
        return textMarginStartPercent;
    }

    public SelectorAttributes setTextMarginStartPercent(float textMarginStartPercent) {
        this.textMarginStartPercent = textMarginStartPercent;
        return this;
    }

    public float getTickMarginEndPercent() {
        return tickMarginEndPercent;
    }

    public SelectorAttributes setTickMarginEndPercent(float tickMarginEndPercent) {
        this.tickMarginEndPercent = tickMarginEndPercent;
        return this;
    }

    public int getSearchMarginLeft() {
        return searchMarginLeft;
    }

    public int getSearchMarginRight() {
        return searchMarginRight;
    }

    public int getSearchMarginTop() {
        return searchMarginTop;
    }


    public int getSearchMarginBottom() {
        return searchMarginBottom;
    }

    public int getListItemHeight() {
        return listItemHeight;
    }

    public SelectorAttributes setListItemHeight(int listItemHeight) {
        this.listItemHeight = listItemHeight;
        return this;
    }

    public int getTextNormalColor() {
        return textNormalColor;
    }

    public SelectorAttributes setTextNormalColor(int textNormalColor) {
        this.textNormalColor = textNormalColor;
        return this;
    }

    public int getTextSelectedColor() {
        return textSelectedColor;
    }

    public SelectorAttributes setTextSelectedColor(int textSelectedColor) {
        this.textSelectedColor = textSelectedColor;
        return this;
    }

    public int getTextSize() {
        return textSize;
    }

    public SelectorAttributes setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public int getListItemBackgroundColor() {
        return listItemBackgroundColor;
    }

    public SelectorAttributes setListItemBackgroundColor(int listItemBackgroundColor) {
        this.listItemBackgroundColor = listItemBackgroundColor;
        return this;
    }

    public int getNormalTickColor() {
        return normalTickColor;
    }

    public SelectorAttributes setNormalTickColor(int normalTickColor) {
        this.normalTickColor = normalTickColor;
        return this;
    }

    public int getTickDrawable() {
        return tickDrawable;
    }

    public SelectorAttributes setTickDrawable(int tickDrawable) {
        this.tickDrawable = tickDrawable;
        return this;
    }

    public int getListItemSeparatorColor() {
        return listItemSeparatorColor;
    }

    public SelectorAttributes setListItemSeparatorColor(int listItemSeparatorColor) {
        this.listItemSeparatorColor = listItemSeparatorColor;
        return this;
    }

    public int getListItemSeparatorHeight() {
        return listItemSeparatorHeight;
    }

    public SelectorAttributes setListItemSeparatorHeight(int listItemSeparatorHeight) {
        this.listItemSeparatorHeight = listItemSeparatorHeight;
        return this;
    }

    public boolean isEnableSearchView() {
        return enableSearchView;
    }

    public SelectorAttributes setEnableSearchView(boolean enableSearchView) {
        this.enableSearchView = enableSearchView;
        return this;
    }

    public int getSearchDrawable() {
        return searchDrawable;
    }

    public SelectorAttributes setSearchDrawable(int searchDrawable) {
        this.searchDrawable = searchDrawable;
        return this;
    }

    public int getSearchCancelDrawable() {
        return searchCancelDrawable;
    }

    public SelectorAttributes setSearchCancelDrawable(int searchCancelDrawable) {
        this.searchCancelDrawable = searchCancelDrawable;
        return this;
    }

    public int getSearchHintColor() {
        return searchHintColor;
    }

    public SelectorAttributes setSearchHintColor(int searchHintColor) {
        this.searchHintColor = searchHintColor;
        return this;
    }

    public int getSearchHintText() {
        return searchHintText;
    }

    public SelectorAttributes setSearchHintText(int searchHintText) {
        this.searchHintText = searchHintText;
        return this;
    }

    public int getSearchTextColor() {
        return searchTextColor;
    }

    public SelectorAttributes setSearchTextColor(int searchTextColor) {
        this.searchTextColor = searchTextColor;
        return this;
    }

    public int getSearchBackgroundColor() {
        return searchBackgroundColor;
    }

    public SelectorAttributes setSearchBackgroundColor(int searchBackgroundColor) {
        this.searchBackgroundColor = searchBackgroundColor;
        return this;
    }

    public int getSearchSpacerBackgroundColor() {
        return searchSpacerBackgroundColor;
    }

    public SelectorAttributes setSearchSpacerBackgroundColor(int searchSpacerBackgroundColor) {
        this.searchSpacerBackgroundColor = searchSpacerBackgroundColor;
        return this;
    }

    public int getSearchCornerRadius() {
        return searchCornerRadius;
    }

    public SelectorAttributes setSearchCornerRadius(int searchCornerRadius) {
        this.searchCornerRadius = searchCornerRadius;
        return this;
    }

    public SelectorAttributes setTextFont(String typeFacePath) {
        this.textFont = typeFacePath;
        return this;
    }

    public SelectorAttributes setSearchFont(String typeFacePath) {
        this.searchTextFont = typeFacePath;
        return this;
    }

    public String getTextFont() {
        return  this.textFont;
    }

    public String getSearchFont() {
        return this.searchTextFont;
    }

    public static final Creator<SelectorAttributes> CREATOR = new Creator<SelectorAttributes>() {
        @Override
        public SelectorAttributes createFromParcel(Parcel in) {
            return new SelectorAttributes(in);
        }

        @Override
        public SelectorAttributes[] newArray(int size) {
            return new SelectorAttributes[size];
        }
    };
}
