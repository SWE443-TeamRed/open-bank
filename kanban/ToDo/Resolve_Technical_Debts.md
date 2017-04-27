Title: Resolve Technical Debts

Description:

  Resolve the technical debts that have accumulated during the project.
  a) Update User Class:
	X- Datatype of Phone Number: Change it to a String
	X- Add a username String attribute and use username for the functions
		userID was previously used for
		+ userID will be a unique identifier for each user
  b) Update Account Class:
	X- Remove TransactionSets Debit/Credit, consolidate to TransactionSets
		called transactions
	- Balance datatype should be two BigIntegers [java type, one to hold 
		before the decimal place and one for after the decimal place]
	- Edit transaction methods to also create an additional fee transaction
		when necessary
	- Fix receiveFunds and recordTransaction methods for account in model
		- Search recordTransaction in account and you will see two methods with different attributes. The one with code in it wasn't created in model to create it. The one without code was created in model so delete all occurences of it.
  c) Update Transaction Class:
    - Transactions should have a 1 to 1 relationship with itself
		+ Label it as previous and next
		+ (this is a change to the Class Diagram)
    - Amount datatype should be two BigIntegers [java type, one to hold 
		before the decimal place and one for after the decimal place]
    X- Change TransactionTypeEnum fields to [DEPOSIT, WITHDRAW, TRANSFER,
		CREATE, CLOSE, FEE]
  
Related scenario(s):

  
  
Time estimate(s):

 - 9H

Author(s):

  - Vincent

Assigned persons (currently working on task):

  - Cycielya
  - Vincent

Log entries (who worked when and how long on this):

	04/25/2017 22:00 - 22:30 Vincent Liu
	- Renamed TransactionTypeEnums and consolidated credit and debit