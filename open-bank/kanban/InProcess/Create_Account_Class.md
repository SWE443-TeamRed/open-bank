Title: Create Account Class

Description:

  Create an Account class for the open-bank. The Account class should
  hold information related to the owner of the account as well as be able
  to interact with the money stored in the account. The Account class should
  contain an unique ID, account balance, type of account, and be associated 
  with the User class, which contains information about the owner. The 
  Account class should contain getter/setters for each attribute and 
  an appropriate constructor. Finally, the Account class should contain
  methods for  depositing money, withdrawing money, transfering money between
  accounts, and seeding accounts. 
  
  Test any and all methods created to ensure they work as intended.
  
  Note: 
  - We may be encorporating a boolean flag to determine if the 
  account is an admin console.
  - Seeding for accounts is likely going to be a one time action that
  occurs when the account is first created. You may be able to perform
  this action in the constructor rather then creating a method for it.
  - I recommend having each account keep track of transactions that 
  involve the account.
  
Related scenario(s):


  
Time estimate(s):

    - 15 hr

Author(s):

    - Vincent

Assigned persons (currently working on task):

    - Samuel
    - Kimberly
    - Savindi
    - Henry

Log entries (who worked when and how long on this):

    - 03/29 Kimberly 4 hr
    - 3/31 Henry - 2 hrs
            -Refactoreed Account class to not include fields that the User class should store, 
            Account should only store a User object which can then have user info

