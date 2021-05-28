package com.glqdlt.ex.servicebusexample.topic.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author glqdlt
 */
@RestController
@RequestMapping("/item")
public class ItemRestController {

    @GetMapping("/{itemId:[0-9]+]}")
    public ResponseEntity<ItemResponse> getItemDetail(@PathVariable Integer itemId) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setItemId(itemId);
        itemResponse.setItemName("item-" + itemId);
        itemResponse.setCount(5);
        itemResponse.setItemCreateTime(LocalDateTime.now().minusDays(1));
        itemResponse.setItemUpdateTime(LocalDateTime.now().minusHours(1L));
        itemResponse.setPayment(10000);
        return ResponseEntity.ok(itemResponse);
    }

}
