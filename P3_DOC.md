## Design Pattern: 

**Singleton Pattern**  
We use one database manager (ObjectBox) for the entire app / implementation.  
*Path:* 3354-Team_Too\App\Contacts\app\src\main\java\me\saipathuri\contacts\ContactsApp.java

**Composite Pattern**   
In Validation Testing we use a generic BaseValidator interface and then implement PhoneValidator and EmailValidator by extending the interface.  
*Path:* 3354-Team_Too\App\Contacts\app\src\main\java\me\saipathuri\contacts\utils\validators

<br />


## Testing:


**Contact Creation Testing (Sai)**   
*Purpose:* Verify that a new contact is stored in / can be accessed from ObjectBox.  
*Location:* 3354-Team_Too\App\Contacts\app\src\androidTest\java\me\saipathuri\contacts\
ObjectBoxInstrumentedTest.java  


**Validation Testing (Sai)**  
*Purpose:* Verify that numbers are 10 digit numeric value and emails are formatted correctly are are from acceptable domains.  
*Location:* 3354-Team_Too\App\Contacts\app\src\test\java\me\saipathuri\  contacts\ValidatorTests.java  

**Group Creation Test (Haneya)**  
*Purpose:* Verify that the relationship between Groups and Contacts is defined correctly in  / can be accessed from ObjectBox.  
*Location:* 3354-Team_Too\App\Contacts\app\src\androidTest\java\me\saipathuri\contacts\
GroupTest.java

**Search Test (Haleigh)**  
*Purpose:* Verify that searches are executed properly and that they query by both first and last  name without duplication.  
*Location:* 3354-Team_Too\App\Contacts\app\src\androidTest\java\me\saipathuri\contacts\
SearchTest.java

**Blacklist Test (Trey)**  
*Purpose:* Verify that blacklisting is implemented correctly.  
*Location:* 3354-Team_Too\App\Contacts\app\src\androidTest\java\me\saipathuri\
contacts\BlacklistTest.java

<br />

## How to Build and Run:
1. Clone or download the project from GitHub.
2. Open the Contacts project in Android Studio (version 3.0 or later).
3. Gradle should begin building automatically and the app should run.
4. If the app doesn’t start automatically, it is named ‘Contacts’ and has a green android icon.

<br />

## Implemented Functionalities:

**The app allows users to manage contacts.**  
The app allows users to add a contact with fields for name, number, email(s), and photo.  
The app allows users to view a contact’s information, including name, number(s), email, and photo.  
The app allows users to edit a contact’s information, including their name, number, email(s), and photo.  
The app allows users to delete a contact.  
The app allows users to search for specific contacts by name.  
The app sorts contact by name in the main view.  
The app allows users to blacklist a contact.  

**The app allows users to organize contacts in groups.**  
The app allows users to create groups.  
The app allows  users to add contacts to groups.  
The app allows  users to delete groups.  

**The app allows users to communicate with contacts from the contact view.**  
The app allows users to easily send a message/text to a contact.  
The app allows users to easily make a phone call to a contact.  

**The app allows persistent access to contacts.**








