package com.glqdlt.ex.servicebusexample.topic.item;

import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository {

    void save(Item i);

    Item findByItem(Integer id);

}
