package com.emcy.selector;

/**
 * This class has to be extented by the user for using ObjectSelector.
 * It has two abstract methods:
 *
 * 1) getDisplayName : For getting the displayed value of the object that is going to be
 *    shown in single row of the adapter
 * 2)getModelId : For getting the id of the object to be unique.
 *
 * This class also implement SelectorData.OnSelectorDataRequester which has one method :
 * 1) getData : For getting all the data that the user want to be shown as List<T>
 *
 * @param <T> : the object class
 */
public abstract class SelectorDataGetter<T> implements SelectorData.OnSelectorDataRequester<T>{

    public abstract String getDisplayName();
    public abstract Long getModelId();

}
