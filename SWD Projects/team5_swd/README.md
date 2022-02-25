[Home](Home)

![](https://github.com/johnelwart/Projects/blob/main/SWD%20Projects/team5_swd/team5_swd.png)

### Problem Statement

The goal of this application is to develop an application that will allow individuals to register for events put on by event organizers. This application will have two types of users: individuals and organizations. Organizations are entities that have the ability to organize events and individuals can then register for those events. 

During the creation of an event, the organizing organization has the ability to specify a name, date, time, location, and certain safety-related requirements that individuals must meet in order to attend (being vaccinated against Covid for example). An individual must meet all of the organization-defined safety requirements in order to register for the event. 

### Developer Documentation

This application is driven by a class called Main. Inside of the main class is a main() method and a start(), which in conjunction set up the GUI and launch the application. 

This application is composed of five main JavaFX Controller classes, EventDetailController, OrgHomepageController, userHomePageController, registerPageController, and SignInPageController.

The EventDetailController class is responsible for controlling the event detail page. This page displays the details of an event, including date, time, location, name, and people registered to attend. It also allows the user to modify any information or delete the event altogether.

The OrgHomepageController class is responsible for controlling the organization home page. This page displays any events created by the organization, and allows the organization to create new events.

The userHomePageController class is responsible for controlling the user home page. This page has three tabs, Profile, Edit information, and COVID Info. The Profile tab displays the personal information of the user, as well as Events available to register for and events currently registered for. The Edit information tab allows the user to edit their personal information, and the COVID Info tab allows the user to enter and update their vaccination ID, status, and the date of any recent COVID tests.

The registerPageController class is responsible for controlling the register page. This page allows users to register for accounts, either individual or organization. 

The SignInPageController class is responsible for controlling the sign in page. This page allows users to either enter their username and password and sign into their accout, or register for a new account.

This application also contains four auxiliary classes: Event, Registration, User, and Vaccine. Each of these classes are used to create objects that store type-specific information (example: username for User). They also simplify the transfer of data between GUI scenes and the database.

All of the database interaction in this application is handled in the Team5DatabaseConnection class. This class creates a connection to our database and defines several methods for performing different queries and updates to various tables in the database.

### User Documentation

In order to launch this application, the main() method inside of the Main class needs to be run. Once the GUI loads, the user should see a page with fields for a username and a password, as well as buttons to sign in and register. If the user doesn't already have an account, they should click on the register button. 

Upon clicking the register button, the page should redirect to a different page which has fields for a first name, last name, date of birth, username, and password, as well as a checkbox and two buttons, register and back. Clicking the back button will return the user to the sign in page. If the user wants to register as an individual, they should leave the checkbox unchecked and fill in all text fields. If the user wants to register as an organization, they should check the checkbox and fill in all text fields that aren't grayed out. Once the information is entered, the user should click the register button. If the user failed to fill out all of the required information, they will be prompted with an error message.

If the user decided to register as an individual, they should now see a page with three tabs: Profile, Edit information, and COVID Info. Under the Profile tab, the user can view all of their personal information, view events available to them, and register for events. To register for an event, simply click on the event and click the Register! button. This should move that event into the Current Registrations list. Under the Edit information tab, the user can edit any personal information (except username). To edit information, simply enter the information into the field that you would like to update and click the update button. If the user now navigates back to the Profile tab, they should see their updated information. Under the COVID Info tab, the user can enter and edit information regarding their vaccination status and any recent negative COVID tests they've received. The COVID related information that the user enters on this page will dictate what events are displayed to them on their Profile tab. For example, if the user has not received their booster shot, any events that require a booster shot will not show up in their available events list.

At this point, the individual has the ability to log out of their account by clicking on the log out button in the Profile tab. They can then log back into their account by entering their username and password. 

If, on the other hand, the user decided to register as an organization, upon clicking the register button they should see a screen with an empty list on the left side and an option to create a new event on the right side. To create a new event, the user should enter all of the required information, set the appropriate COVID precautions, and then click the Add event button. The user should now see the new event displayed in the list on the left. 

By double clicking on an event, the user can go to an Event detail page, which allows them to modify event information, view any individuals registered to attend, or delete the event altogether. If the user wants to navigate back to the organization homepage, they should click the Save event button.

The user can now log out by clicking on the logout button on the organization homepage. If they want to sign back in, they just need to enter their username and password.

### Source Code

[Source Code](https://github.com/johnelwart/Projects/tree/main/SWD%20Projects/team5_swd/src)
