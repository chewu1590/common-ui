package cn.woochen.common_ui.navigation.iterator;


import cn.woochen.common_ui.navigation.TabItemView;

/**
 *数组型迭代器
 *@author woochen123
 *@time 2017/11/22 15:01
 *@desc
 */

public class ArrayTabIterator implements ITabIterator {
    private TabItemView[] array;
    int index = 0;

    public ArrayTabIterator(int length) {
        array = new TabItemView[length];
    }

    @Override
    public TabItemView next() {
        return array[index++];
    }

    @Override
    public boolean hasNext() {
        return index < array.length;
    }

    public void addItem(TabItemView item, int dex) {
        array[dex] = item;
    }
}
