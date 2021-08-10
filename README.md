# BugCatcher

BugCatcher is a near complete ticketing webapp build with Eclipse in the Java language. 
I have created this app as a showcase for my portfolio and is not meant to be used as a professional program as is.

For this project I have used the Bootstrapius admin html template which I have customized. 
I have also implemented Datatables for a better organization of the used data, everything else has been custom build by myself.
Languages, tools and frameworks used:

Java, Spring Boot, Spring security, Spring REST, Maven, hibernate, Thymeleaf, HTML5, CSS, JavaScript, Ajax, H2 Database, Eclipse IDE

<h3>To do:</h3>

Implement messaging system
Implement assign ticket to user function 
Change inconsistencies, Clean, refactor and solve some minor bugs and issues.

<h3>Using the app:</h3>

The app comes with random data created when it is first started. This data ( users, tickets, projects) are stored in JPA repositories on the H2 database.
A user can create an account, currently I have allowed the choice of user roles while signing up so the previewer can see the slight differences for each role while navigating the app. If the password was lost, the user can request a password token. ( currently not available as I had used my own gmail account to test this, details which I have removed from the app for security reasons, will add another solution in a future update)

General users will only be able to create tickets and upload images with the ticket, all other users will be able to enter the program with adjusted permissions depending on the role they have.

At the main dashboard, users are welcomed by the different types of tickets currently available. On the left hand navigation, depending on the user role, one can navigate to "Manage users" page where one can view, edit/update and create new users, "Manage project users" where one can assign users to current projects, "Projects" where one can view, edit and create new projects, "Tickets" where one can view all current tickets, edit and create them ( a function to assign them to specific users/employees is a work in progress), and "Profile" where the current user can change their user details. 

In the top navigation, one finds a functional search bar, a message icon ( messaging to be implemented soon) and a functional logout button which sends the user back to the login screen.

