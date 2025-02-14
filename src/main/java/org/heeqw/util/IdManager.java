package org.heeqw.util;

import java.util.HashSet;
import java.util.Set;

public class IdManager {
    private static IdManager instance;
    private final Set<String> usedIds;



    private IdManager(){
        usedIds = new HashSet<>();
    }

    public static synchronized IdManager getInstance(){
        if (instance == null){
            instance = new IdManager();
        }
        return instance;
    }


    public boolean isIdAvailable(String id){
        return !usedIds.contains(id);
    }

    public void registerId(String id) {
        usedIds.add(id);
    }

    public void removeId(String id) {
        usedIds.remove(id);
    }

    public void clear() {
        usedIds.clear();
    }

    public Set<String> getAllRegisteredIds() {
        return new HashSet<>(usedIds);  // 返回一个新的 HashSet，防止外部修改内部集合
    }
}
