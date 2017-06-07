public class Post
{
    private static int nextPostNumber = 1;
    private int postNumber;
    private User author;
    private String blog;
    private String webAddress;

    //create the constructor to be used for each post instance
    public Post (User author, String blog, String webAddress)
    {
        this.postNumber = nextPostNumber;
        nextPostNumber++;
        this.author = author;
        this.blog = blog;
        this.webAddress = webAddress;
    }

    public int getPostNumber()
    {
        return postNumber;
    }

    public String getAuthorUserName()
    {
        User currentAuthor = (User)author;
        String authorUserName = currentAuthor.getUserName();
        return authorUserName;
    }

    public String getBlog()
    {
        return blog;
    }

    public String getWebAddress()
    {
        return webAddress;
    }
}