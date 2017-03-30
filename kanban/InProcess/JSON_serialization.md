Title: Using JSON Serialization

Description:

Setup a JSON Serialization within SDMLib to make persitent data to be used throughout the project. i.e for testing and running our bank.

Related scenario(s):

Time estimate(s):

5H

Author(s):

Victor

Assigned persons (currently working on task):

Victor
Daniel

Log entries (who worked when and how long on this):

Daniel (1 hour)
    -Added basic Json Serialization
    -Converts Account object and associated user object to Json string
    -Converts the same Json string back to an Account/User object
    
Daniel (.25 hours)    
    -Created method toJson(Account)
        -Takes in an account and converts it to a json string
    -Created method fromJson() which returns the previously created json string as an Account object
    -Have test case for demonstration
    
Daniel (1.25 hours)    
    -Changed toJson(Account) so it now writes the resulting json string to a text file
    -Changed fromJson() so it now reads from the json file and returns an Account object
    -Have test case for demonstration
    
