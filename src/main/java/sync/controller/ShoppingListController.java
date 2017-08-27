/*
    Copyright of Ed.Co Enterprises
 */
package sync.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sync.dto.device.DeviceList;
import sync.service.ShoppingListService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller for Shopping lists
 *
 * @author tedward603@gmail.com
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

    /**
     *  This is the endpoint that I used for syning up the device information with that of the server.
     *
     * @param user this is user to mock which user is logged in at the point of a request coming in.
     *             In a fully fledged program this would come from their logged in context i.e. a header cookie
     * @param deviceLists the list that the device is sending back in order to change the server lists
     * @return
     */
    @RequestMapping(value = "/sync/{user}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List<DeviceList>> deviceSync(@PathVariable String user, @RequestBody List<DeviceList> deviceLists) {
        return new ResponseEntity<List<DeviceList>>(shoppingListService.syncDeviceLists(deviceLists, user),HttpStatus.OK);
    }

}
