import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class Microblog
{
    static Scanner keyboard = new Scanner(System.in);
    //these need to be instance variables because they are used by more than one function
    private User currentUser; //keeps track of the current user name
    private ArrayList allUsers; //holds all bloggers with their User class information
    private ArrayList allPosts; //holds all posts made by all bloggers, includes number, author, blog, and web address
    private Post currentBlog; 

    public void run()
    {  
        allUsers = new ArrayList();
        //variable for number chosen from menu, set to any value other than 9 (the exit condition of loop)
        int menuChoice = 0; 
        //variable for number chosen from list of users to choose to be a current user
        int userChoice = 0;
        allPosts = new ArrayList();

        //Print initial message
        System.out.println("Welcome to Microblog");

        //Continue to print menu after each user's choice, until exit condition, 9, is entered
        while (menuChoice != 9)
        {
            System.out.println();
            displayMenu();

            //call function to read the user's menu choice and check for entry errors.  Set valid entry equal to menuChoice
            menuChoice = readMenuEntry();

            //option 1 is to create a new user
            if (menuChoice == 1)
            {
                currentUser = obtainNewUserInfo();
                //add this new user to the User ArrayList
                allUsers.add(currentUser);
            }
            //if choosing to do anything else, check to make sure there is an existing user.  If not, one will need to be created first.
            else if (currentUser == null && menuChoice != 9)
            {
                System.out.println ("You must create a user before performing this function");
                //call the function to allow to add user information
                currentUser = obtainNewUserInfo();
                //add this new user to the User ArrayList
                allUsers.add(currentUser);
            }
            //option 2 is to become an existing user. Print a list of their names and let the user pick which one to become.
            else if (menuChoice == 2)
            {
                displayListOfUsers();
                //call function to read the user's menu choice and check for entry errors.  Set valid entry equal to userChoice
                userChoice = readUserChoiceEntry();
                //provide a way for user to exit with 9 if doesn't want to continue
                if (userChoice == 9)
                {
                    //set the variable that will cause program to exit
                    menuChoice = userChoice;
                }
                //set the current user to the one chosen
                else
                {

                    //set the current user to the one chosen, but need to subtract 1 since UserNum 1 is in ArrayList location 0
                    currentUser = (User) allUsers.get (userChoice - 1);

                    System.out.println ("The user you chose is " + currentUser.getUserName());
                }
            }
            //option 3 is to create a post as the current user
            else if (menuChoice == 3)
            {
                //first check to see if user has made any posts before.  If the array size is 0, this is their first post.
                if (currentUser.getPostArraySize() == 0)
                {
                    System.out.print("This is your first blog. ");
                }
                else
                {
                    //print the user's latest blog before asking for a new one
                    System.out.println("Your last blog was:");
                    //call function in user class that calls function in post class to return blog 
                    System.out.println(currentUser.getLastUserBlog());
                    System.out.println();
                }

                //function that asks for blog message information and stores in Post instance
                currentBlog = obtainBlog();
                //add to the list of all posts
                allPosts.add(currentBlog);
                //also add to the posts made by that particular user
                currentUser.addUserPost(currentBlog);
            }
            //If the user asks to print all posts, go through the post array and print all the fields of the posts.
            //You can skip the web link if it's empty (null)..let know if there are currently no posts
            //Note:  I decided to print just the user name from the User class that is a field in each post
            else if (menuChoice == 4)
            {
                if (allPosts.size() == 0)
                {
                    System.out.println("There are currently no posts.");
                    System.out.println();
                }
                else
                {
                    printAllPosts();
                }
            }
            //If the user asks to print all users, go through the user array and print all fields of the User.  
            //First make sure there are Users 
            else if (menuChoice == 5)
            {
                if (allUsers.size() == 0)
                {
                    System.out.println ("There are currently no users.");
                    System.out.println();
                }
                else
                {
                    printAllUsers();
                }
            }

            //error condition
            else if (menuChoice != 9)
            {
                System.out.println ("You must choose a valid entry (1,2,3,4,5) or 9 to exit");
            }
        }
//Once 9 (exit condition) is chosen, print "Exit"
        System.out.println("Exit");
    }

    //Here is where Java knows to start the program.  The first thing it does is to instantiate its own class, and hand off control to an instance method. (85.11)
    public static void main (String [] args)
    {
        Microblog me = new Microblog();
        me.run();
    }

    //This function is called when the menu needs to be displayed, nothing is passed to or from it, but last statement asks for user to make an entry
    public static void displayMenu ()
    {
        System.out.println("Main Menu");
        System.out.println ("1) Create a new user");
        System.out.println ("2) Become an existing user");
        System.out.println ("3) Create a post as the current user");
        System.out.println ("4) Print all posts");
        System.out.println ("5) Print all users");
        System.out.println ("9) Exit");
        return;
    }

    //This function reads the menu choice and returns it to the main.  It will eventually be used to check for errors in entry
    //such as invalid choice, trying to become an existing user before any are created (2 before 1), or trying to create a post as the
    //current user when no users have been created yet (3 before 1)

    public int readMenuEntry()
    {
        if (currentUser == null)
        {
            System.out.println ("\nYou are the first user.  What would you like to do?");
        }
        else
        {
            System.out.println ("\nYou are currently user " + currentUser.getUserName() + ".  What would you like to do?");
        }
        System.out.print ("> ");
        int entry = keyboard.nextInt();
        keyboard.skip ("\n");
        System.out.println();
        //error checking for valid entry is done at end of main program's loop
        return entry;
    }

    //function that is called when new user is created to get all the information from keyboard that is needed to store in User class
    public User obtainNewUserInfo()
    {
        System.out.print ("User name: ");
        String name = keyboard.nextLine();

        System.out.print("Link to Avatar: ");
        String linktoavatar = keyboard.nextLine();

        System.out.print("Full Name: ");
        String realname = keyboard.nextLine();

        System.out.print("email address: ");
        String emailaddress = keyboard.nextLine();

        User tempblogger = new User(name, realname, emailaddress, linktoavatar);
        return tempblogger;
    }

    //function that prints out the current list of users by number and user name
    public void displayListOfUsers()
    {
        int sizeUserArray = allUsers.size();
        Iterator iter = allUsers.iterator();
        while (iter.hasNext())
        {
            User u = (User)iter.next();
            System.out.println (u.getUserNum() + ") " + u.getUserName());
        }
    }

    //function that asks for choice of user (opt. 2), checks for valid entry, returns to userChoice
    public int readUserChoiceEntry()
    {
        int whichUser;
        //continues to ask question until a valid number associated with the user name is chosen
        do
        {
            System.out.println ("Choose number corresponding to user you would like to be.");
            System.out.print ("> ");
            whichUser = keyboard.nextInt();
            keyboard.skip ("\n");
            System.out.println();

        }while ((whichUser <= 0) || (whichUser > allUsers.size()) && (whichUser != 9));
        return whichUser;
    }

    //function that creates a post instance based on user entry
    public Post createPost()
    {
        System.out.println("What would you like to post?");
        String bloggermsg = keyboard.nextLine();

        System.out.print("Web address: ");
        String blogwebaddress = keyboard.nextLine();

        //create a Post instance using above data
        Post tempblog = new Post(currentUser, bloggermsg, blogwebaddress);
        return tempblog;

    }

    public Post obtainBlog()
    {
        System.out.println ("What would you like to say?");
        String bloggerMsg = keyboard.nextLine();

        System.out.print("Web address: ");
        String blogWebAddress = keyboard.nextLine();

        //create a Post instance using above data
        Post tempBlog = new Post(currentUser, bloggerMsg, blogWebAddress);
        return tempBlog;

    }

    //function that prints all posts made by all users, skipping web address is left empty
    public void printAllPosts()
    {
        Iterator iter = allPosts.iterator();
        while (iter.hasNext())
        {
            Post p = (Post) iter.next();
            System.out.println("Post " + p.getPostNumber() + ", By: " + p.getAuthorUserName());
            if (!"".equals (p.getWebAddress()))
            {
                System.out.println ("Web address: " + p.getWebAddress());
            }
            System.out.println(p.getBlog());
            System.out.println();
        }
    }

    public void printAllUsers()
    {
        Iterator iter = allUsers.iterator();
        while (iter.hasNext())
        {
            User u = (User)iter.next();
            System.out.println("User Number: " + u.getUserNum() + "     User Name: " + u.getUserName());
            System.out.println ("Name: "  + u.getRealName());
            System.out.println ("Email: " + u.getEmailAddress());
            System.out.println ("Avatar: " + u.getLinkToAvatar());
            System.out.println();
        }

    }
}

/*     //If choice is 1, call function obtainInfo to ask user for contact information
if (choice == 1)
{
//set up array list for users
bloggers = new ArrayList();
}

//If chooses 1, 

//declare array of User
User[] bloggers = new User ArrayList();

//set up 3 users by calling function getInfo which returns an instance of User
for (int i = 0; i<3; i++)
{
bloggers[i] =  obtainInfo();
}

//declare array of Post
Post[] blogs = new Post[5];

//create 5 blogs by calling function getBlog which returns an instance of Post
for (int i= 0; i<5; i++)
{
blogs[i] =  obtainBlog();
}

//Print user(bloggers) information
for (int i = 0; i<3; i++)
{
//add one to the blogger integer number since the array starts at 0
System.out.println("\nBlogger " + (i + 1) + ":");
bloggers[i].printUserInfo();
}

//Print post information
for(int i = 0; i<5; i++)
{
System.out.println ("\nPosts:");
blogs[i].printPost();
}
}

//function that asks for information to put into a User instance
public static User obtainInfo()
{
System.out.print ("User name: ");
String name = keyboard.nextLine();

System.out.print("Link to Avatar: ");
String linktoavatar = keyboard.nextLine();

System.out.print("Full Name: ");
String fullname = keyboard.nextLine();

System.out.print("email address: ");
String emailaddress = keyboard.nextLine();

User tempblogger = new User(name, fullname, emailaddress, linktoavatar);
return tempblogger;
}

//function that asks for blog message information and stores in Post instance
public static Post obtainBlog()
{
System.out.print("\nBlogger's name: ");
String bloggername = keyboard.nextLine();

System.out.println("What would you like to post?");
String bloggermsg = keyboard.nextLine();

System.out.print("Web address: ");
String blogwebaddress = keyboard.nextLine();

//create a Post instance using above data
Post tempblog = new Post(bloggername, bloggermsg, blogwebaddress);
return tempblog;*/

