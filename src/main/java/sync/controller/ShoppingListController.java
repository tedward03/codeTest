package sync.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sync.dto.device.ShoppingListDevice;
import sync.service.ShoppingListService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 *
 */
@RestController
@RequestMapping("/api/shoppingList")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @RequestMapping(value = "/", method = GET)
    public String saySomething(){
        return "shoppingListEndpoint";
    }

    @RequestMapping(value = "/sync/{user}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List<ShoppingListDevice>> deviceSync(@PathVariable String user, @RequestBody List<ShoppingListDevice> deviceLists) {
        return new ResponseEntity<List<ShoppingListDevice>>(shoppingListService.syncDeviceLists(deviceLists, user),HttpStatus.OK);
    }

}
