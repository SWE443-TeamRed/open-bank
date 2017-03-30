Title: Create Transaction class

Description:

  Create an Transaction class for the open-bank. The Transaction class
  is used to store information about transactions that have occured.
  The Transaction class should contain a timestamp, the type of transaction
  (deposit, withdraw, transfer between accounts, or seeding), the amount of
  money being moved, a unique ID, and the accounts involved. There should be
  getters and setters for each attribute.
  
  Note:
  - Each transaction should be related to at most two accounts. I suggest
  giving them the roles "sender" and "reciever".
 
  
Related scenario(s):


  
Time estimate(s):

  

Author(s):

  - Vincent

Assigned persons (currently working on task):

  - Cycielya
  - Fatih
  - Mitchell

Log entries (who worked when and how long on this):
- Fatih: Class created using SDMLib with following attributes
amount
date
time
note

Also changed the datatypes for date and time from String to Java.util.Date(Using SDMLib).
SetAmount method modified. Checks for negative value. if it is less than 0 thwrows IllegalArgumentException

Spend about 3-4 hours.


