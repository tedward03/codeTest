# Code Test Documentation and design portion
Skip to bottom for code explanation
### Intro 

This is the Readme for the code that I created, but I felt that this was probably the easiest way
for me to answer the design portion of the code test,
and also do some (ALWAYS IMPORTANT) documentation on what I created too.

### Entity Model 

It was posed that this is the Entity model for the design portion 

![previousEM](http://url/to/img.png)

That might not be sufficient for the needs set fourth.
The same information can be saved, but there are modifications to those tables that must be done. 

![moddedEM](http://url/to/img.png)

Here a quick run down of the final Entity model designed

As we can see there is a local database side ( mobile ) and a server side ( server )
Some of the fields do need explaining 

I   **serverCopy**
On both the shopping list and items table.
It's used to tell the local device that its information is up to date with the server
so that record will be definitely up to date with te server, 
this only really come when the record is updated on the server by another device.

II **isLocallyModified** 
On both the shopping list and items table.
this is a flag that the local device will set when it updates/adds a record
This will show that its change should be saved back to the server.

III **isLocallyDeleted/hasBeenLocallyDeleted**
A local device can't actually delete a record by themselves.
If the record is deleted then how will the server know to delete it too. that is where this field comes in.
after all the changed have happened at the end the server will send back the end result records for the local device to have.

**N.B** All these three fields don't need to exist on the server. they only need to exist on a device.
When the interaction between the modification of the server and client happen
the server will give these values back for each client individually. in this form

![serverSync](http://url/to/img.png)

IV **listIdOnDevice/Server**
this is a foreign key that links the items to the shopping list. that is it.

V **User <-> ShoppingList**
There is a many to many relation between users and Shopping lists.
due to there being a need for multiple users needing multiple shopping lists,
and shopping lists having multiple "owners" (this will be more important later for the "shopping list collaboration"). 

Using this entity model why are able to describe better how to solve some of the design challenges


### Design Challenges

#### One user can synchronize his/her list on all his/her devices. User and friend both can add/update/delete items on the same list while being online or offline

Example. Lets say the user modified a list item and the name of the list it is in.
And while they did that there was the same thing happened on the server too
i.e. Another user did the same action and already synced their changes.

Ok so that should be done using the flags from before. Lets add in a quick flow diagram

![flowDiagram1](http://url/to/img.png)

..1. The user would send their changes to the server for the records that were changed locally.
..2. The Server will change its record in correspondence to the changes that were on the local device,
.... in addition to its own changes,
..3. the server will then send the updated data to the local device 
..4. the device would then rewrite their local copies to these changes.

Here would be an an example of the changes that the device would send
```
{
    "listId":"12",
    "listName":"Toiletries",
    "serverCopy":false,
    "locallyModified":true, <-- change
    "isDeleted":false
    "items":[{
        "itemName":"shampoo",
        "checked":"true,
        "quantity":"2" 
        "serverCopy":false,
        "locallyModified":true, <-- change
        "isDeleted":false
    },{
        "itemName":"conditioner",
        "checked":"false,
        "quantity":"2"   
        "serverCopy":true,
        "locallyModified":false,
        "isDeleted":false
    }]
}
```
and say the data on the server would be this
```
{
    "listId":"12",
    "listName":"Groceries",
    "items":[{
        "itemName":"shampoo",
        "checked":"true,
        "quantity":"3" 
    },{
        "itemName":"conditioner",
        "checked":"false,
        "quantity":"2"   
    }{ -- addition on server from another device --
        "itemName":"soap",
        "checked":"false,
        "quantity":"2"   
    }]
}
```
the server would get the local modifications and then update its entries with this.

* It would change the list item first because that would be able to found by the id *(so it would change the name of the list)*
* Then it would change the underlying item to the devices changes *(shampoo's quantity was changed)*


It would then be the servers responsibility to send those updated back,
as well as any other changes that happened on the server since the device1 last sync

So lets say the server responds with this.
```
{
    "listId":"12",
    "listName":"Toiletries",
    "serverCopy":true,
    "locallyModified":false,
    "isDeleted":false
    "items":[{
        "itemName":"shampoo",
        "checked":"true,
        "quantity":"2" 
        "serverCopy":true, <-- all these will be the same i.e. 1,0,0
        "locallyModified":false,
        "isDeleted":false
    },{
        "itemName":"conditioner",
        "checked":"false,
        "quantity":"2"   
        "serverCopy":true,
        "locallyModified":false,
        "isDeleted":false
    }{ -- addition on server from another device --
        "itemName":"soap",
        "checked":"false,
        "quantity":"2"   
        "serverCopy":true,
        "locallyModified":false,
        "isDeleted":false
    }]
}
```
So that is how a user will sync up his devices using a central server.

### Which flow do you recommend for the synchronization?

There are few different ways that i looked into for the proper way of data synchronization.

**SyncML (sync4j)**
If its a standard that people are trying to put forth then this has definite merit.

**Amazon Cognito**
Simply allows amazon to take care of all those needs.

but the one that I looked into that seemed to fit the requirement was some documentation that I found from outSystem
https://success.outsystems.com/Documentation/10/Developing_an_Application/Use_Data/Offline/Offline_Data_Synchronization_Patterns
Modifying this a bit would work, adding in the user, and maybe some addition flow parts for merge conflicts.

#### One user can invite a friend to collaborate on a list / The friend can accept the invitation
**Additionally this challenge**

*Invite a friend works from device to device. E.g.: User-1 invites
User-2 by sending him a link via WhatsApp. The link will open the
app on User-2’s device, or let the user download the app and then
open the app. The link can carry parameters. Expect this
functionality to be existing. The only thing that is missing are the
parameters of the link that should be send to User-2, in order to
accept the invitation and then get access to the according list.*

Ok so for this one is comes down to this part of the database.

![entitymodel1](http://url/to/img.png)

The **many to many** relationship is what we need, so that multiple users can share the same list. 
and users can have multiple list for themselves.


This flow has three major parts. 
1. User1 sends invite to server
2. User1 send whatsapp link to User2.( or the parameters therein)
3. User2 accepts the invitation.

so lets break these down 

##### User1 sends invite to server

1. This is where the user will find the other user and invite them to collaborate, this request will be sent to the server.
2. The server will response by creating a many to many link for the (already existing) list and the (already existing ) User and setting the accepted flag to false.

![flow2](http://url/to/img.png)

##### 2. User1 send whatsapp link to User2.( or the parameters therein)

User1 will have to send **three** parameters, 
two human readable parameters to show user2 what they are accepting, **ListName** and the **User1's Name**.
the third parameter will be the **id of the list** so that when user2 accepts the invitation we have context for the server.

![flow3](http://url/to/img.png)

##### 3. User2 accepts the invitation.

Using the parameters provided by the link. User2 sends an accept request to the server which holds the unaccepted user-list link.
the server then sets the accepted to true and now whenever the user gets their lists again it will bring back that list also.

if the user declines then the decline request is sent. the server will delete the user-list link

![flow4](http://url/to/img.png)

### How (what) does your API look like?

The important question to ask first is how exactly will the the request and response will be sent in terms of _synchronicity_.
Is the client going to a send a request with all the modifications, and then have to wait for the response with all the compiled changes from the server?
This could take an awfully long time for the server to respond.

#### Synchronized Calls
The device would send a request to the server for all of the changes, the server would compile the list of changes
for the device and then send back all of those changes. 

**Drawback**
Leading to the response taking a long time to come back.

##### A-synchronized calls 
* The device sends all modifications made, the server responds with a 200 OK saying I have received those changes.
* The server in turn would start some other process to deal with those changes, and that process would be responsible
 for returning the changes to the device. Probably using a rest template to create the request at the end of its live-cycle
* The device would then also have to be enabled to take rest calls, and then use that returning data to create its records again.

**DrawBack** 
Increased complexity.

#### Endpoints

##### Server 
###### Synchronization

```POST /api/server/shoppingList/sync/```
endpoint for receiving the modifications from a device.
takes in the changes as an embedded json structure.
```
{
    "listId":"12",
    "listName":"Toiletries",
    "serverCopy":false,
    "locallyModified":true,
    "isDeleted":false
    "items":[{
        "itemName":"shampoo",
        "checked":"true,
        "quantity":"2" 
        "serverCopy":false,
        "locallyModified":true,
    },{
        "itemName":"conditioner",
        "checked":"false,
        "quantity":"2"   
        "serverCopy":true,
        "locallyModified":false,
        "isDeleted":false
    }]
}
```
_SYNC_ then this would return the changes made on the server 

_ASYNC_ return a 200 and kick off another process on the server and that will return the changes


###### Collaboration

```PUT /api/shoppingList/{shoppingListId}/invite/{user1}```
This would be used for inviting a user to collaborate on a specific shoppingList
Notice that this is PUT, as it is essentially a modification on an existing shoppingList and not posting a new shoppingList.

```PUT /api/shoppingList/{shoppingListId}/accept```
For the second user accepting the invitation for collaboration on a list
The userId should not be needed for this as the server should have context of who is making the call (The user should be already logged in)

##### Device 
###### Synchronization
With the case of Asynchronized calls each device will also need some rest endpoint to take in the request made by the server.


POST /api/device/shoppingList/syncMod
within the body of the request there will be the new list of entities that the device should have. and the response to this should just be ok
```
{
    "listId":"12",
    "listName":"Toiletries",
    "serverCopy":true,
    "locallyModified":false,
    "isDeleted":false
    "items":[{
        "itemName":"shampoo",
        "checked":"true,
        "quantity":"2" 
        "serverCopy":true, <-- all these will be the same i.e. 1,0,0
        "locallyModified":false,
        "isDeleted":false
    },{
        "itemName":"conditioner",
        "checked":"false,
        "quantity":"2"   
        "serverCopy":true,
        "locallyModified":false,
        "isDeleted":false
    }{ -- addition on server from another device --
        "itemName":"soap",
        "checked":"false,
        "quantity":"2"   
        "serverCopy":true,
        "locallyModified":false,
        "isDeleted":false
    }]
}
```

Response OK

### Which database technology would you choose,
 
 You'll need to use a relational database on both sides to keep the records relating,
 but for the **mobile** side you want something lightweight  
 **Perhaps SQLite**
 
 On the **server** as long as it can replicate the same structure. 
 And preferably something that works underlying with sql otherwise you'll get unhappy developers
 **MySQL,OracleDB, should be fine** 
 
### and how would your database look like?

![entitymodel2](http://url/to/img.png)

this was already discussed above.

Additional Notes

## Discrepancies 
LUID and GUID (Local id vs Global id )
When all the devices and the central server share identities for each of the records , 
this WILL lead to problems with additions of new Items.
Example:  device1 creates a record its id is 15 lets say, device 2 also does the same thing and the same id is assigned 15. 
device 2 sync its changes , device 1 then tries to sync and another device holds that id in globla , how do we get around this?
the second sync must change its Id to something else. 

In some theories of data synchronization there would be different list of id for each item on each device than the global server
Devices would have a list of LUIDs and the server would have GUIDs. the server would hold the mappings between the two.
I decided against this idea due to it being a little to complicated and unnecessary when the only thing that it is protecting against is multiple adds.


## Actual Code Description 
You know that thing that readme.md is used for. 

So for simplicity's sake I created an sync version of the server data sync, that should give a good idea of how it would work. 

Also due to there being no way of logging in the user as it was declared out of scope for this code test, the url for retrieving the sync information has been changed to this.



```POST /api/shoppingList/sync/{userName}```
Headers are just the **Content-Type : application/json**

Open your favourite rest client and give it a go, right now it is hosted here.

http://codetest.eu-central-1.elasticbeanstalk.com/api/shoppingList/sync/

The only two users in place so far are Jim and Joe.

The Request body for getting all the info for a user is and empty Json Array
So this rest call should get you some information

```
POST http://codetest.eu-central-1.elasticbeanstalk.com/api/shoppingList/sync/Jim
Content-Type=application/json
Body 
[]
```
If you have any more questions don't hesitate to email me at the tedward603@gmail.com








 




