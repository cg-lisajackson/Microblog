import java.util.ArrayList;

public class User
{
    private static int nextUserNum = 1;
    private int userNum; 
    private String userName;
    private String realName;
    private String emailAddress;
    private String linkToAvatar;

    private ArrayList userPosts;

    //create User constructor
    public User(String userName, String realName, String emailAddress, String linkToAvatar)
    {
        this.userNum = nextUserNum;
        nextUserNum++;
        this.userName = userName;
        this.realName = realName;
        this.emailAddress = emailAddress;
        this.linkToAvatar = linkToAvatar;
        userPosts = new ArrayList();
    }

    //function to return the User Name
    public String getUserName()
    {
        return userName;
    }

    //function to return the User Number
    public int getUserNum()
    {
        return userNum;
    }

    public String getRealName()
    {
        return realName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public String getLinkToAvatar()
    {
        return linkToAvatar;
    }

    //function is called when a user adds a post (currentUser.add(newblog))
    public void addUserPost (Post newBlog)
    {
        userPosts.add(newBlog);
    }

    public int getPostArraySize()
    {
        return userPosts.size();
    }

    public String getLastUserBlog()
    {
        int userNumOfPosts = this.getPostArraySize();
        //to find position in array,subtract 1 since posts start at 1, but array starts at 0
        int PosOfLastPost = userNumOfPosts - 1;
        Post lastPost = (Post)userPosts.get(PosOfLastPost);
        return lastPost.getBlog();
    }
   }
