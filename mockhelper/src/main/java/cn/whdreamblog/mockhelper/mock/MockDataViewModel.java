package cn.whdreamblog.mockhelper.mock;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.whdreamblog.mockhelper.data.MockRemote;
import cn.whdreamblog.mockhelper.data.model.MocksResponse;
import cn.whdreamblog.mockhelper.util.MockUtils;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/13 20:15
 * desc :
 */
public class MockDataViewModel extends ViewModel {
    private MutableLiveData<List<MocksResponse>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Set<MocksResponse>> selectDataSet = new MutableLiveData<>();
    private MutableLiveData<Integer> selectItem = new MutableLiveData<>();
    private List<String> searchList = new ArrayList<>();

    public void addSelectData(MocksResponse mocksResponse){
        if (isSelectListNull()) return;
        selectDataSet.getValue().add(mocksResponse);
        MockRemote.getSelectDataSet().add(mocksResponse);
    }
    public void removeSelectData(MocksResponse mocksResponse){
        if (isSelectListNull()) return;
        selectDataSet.getValue().remove(mocksResponse);
        MockRemote.getSelectDataSet().remove(mocksResponse);
    }
    public void setMutableLiveDataList(List<MocksResponse> mocksResponseList){
        mutableLiveData.setValue(mocksResponseList);
    }
    public MutableLiveData<List<MocksResponse>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setSelectDataSet(Set<MocksResponse> dataSet) {
        this.selectDataSet.setValue(dataSet);
    }

    public MutableLiveData<Set<MocksResponse>> getSelectDataSet() {
        return selectDataSet;
    }

    public void addAll() {
        if (isNull()) {
            return;
        }
        setSelectDataSet(new HashSet<>(mutableLiveData.getValue()));
    }

    private boolean isNull() {
        if (isSelectListNull()) return true;
        return mutableLiveData.getValue() == null;
    }

    public void clearAll() {
        if (isSelectListNull()) return;
        selectDataSet.setValue(new HashSet<MocksResponse>());
    }

    private boolean isSelectListNull() {
        return selectDataSet.getValue() == null;
    }

    public List<String> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<String> searchList) {
        if (searchList == null){
            return;
        }
        this.searchList = new ArrayList<>(searchList);
    }

    public MutableLiveData<Integer> getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(Integer selectItem) {
        this.selectItem.setValue(selectItem);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        MockUtils.saveSelectMockList();
    }
}
