import java.util.Scanner;

public class MyProgram {

    ApiClient myApiClient;

    //Konstruktor som skapar ett nytt ApiClient-objekt
    public MyProgram() {
        myApiClient = new ApiClient("http://127.0.0.1:8080/api/v1/blog");
    }


    //Där vårt program startar

    public void start() {

        boolean programRun = true;

        while (programRun) {
            System.out.println("Hej, vad vill du göra?");
            System.out.println("1. Skapa blogginlägg");
            System.out.println("2. Lista alla inlägg");
            System.out.println("3. Visa ett specifikt inlägg");
            System.out.println("4. Uppdatera ett specifikt inlägg");
            System.out.println("5. Ta bort ett specifikt inlägg");
            System.out.println("6. Avsluta program");

            int userChoice = getUserInt();

            System.out.println("Du valde " + userChoice);

            switch (userChoice) {
                case 1:
                    addBlogPost();

                    break;
                case 2:
                    listBlogPosts();

                    break;
                case 3:
                    listBlogPost();

                    break;
                case 4:
                    updateBlogPost();

                    break;

                case 5:
                    deleteBlogPost();

                    break;
                case 6:
                    System.out.println("Du har valt att avsluta programmet");
                    programRun = false;
                    break;


            }
        }
    }

    //Metod för att lista alla blogginlägg
    public void listBlogPosts() {
        Blog[] blogPosts = myApiClient.getBlogPosts();

        System.out.println("Blogginlägg:");

        if (blogPosts.length > 0) {
            for (int i = 0; i < blogPosts.length; i++) {
                String title = blogPosts[i].title;
                int id = blogPosts[i].id;

                System.out.println("Titel: " + title + ", ID: " + id);
            }
        } else {
            System.out.println("Det finns inga blogginlägg :(");
        }
    }

    //Metod för att skapa ett blogginlägg
    public void addBlogPost() {
        System.out.println("Title?");
        String title = getUserString();

        System.out.println("Skriv ditt inlägg: ");
        String body = getUserString();

        Blog newBlog = new Blog(title, body);

        boolean success = myApiClient.addBlogPost(newBlog);

        if (success) {
            System.out.println("Ett blogginlägg har skapats");
        } else {
            System.out.println("Det gick inte att skapa ett blogginlägg :(");
        }
    }

    //Metod för att ta bort specifikt inlägg
    public void deleteBlogPost() {
        System.out.println("Skriv id:et på det blogginlägg som du vill ta bort:");
        int blogid = getUserInt();

        if (myApiClient.deleteBlogPost(blogid)) {
            System.out.println("Du har tagit bort blogginlägg: " + blogid);
        } else {
            System.out.println("Det gick inte att ta bort något blogginlägg :(");
        }
    }

    //Metod för att lista specifikt inlägg
    public void listBlogPost() {
        System.out.println("Skriv id:et på det blogginlägg du vill se: ");
        int blogid = getUserInt();
        Blog blog = myApiClient.GetBlogPost(blogid);

        if (blog != null) {
            String title = blog.title;
            String body = blog.body;
            int id = blog.id;

            System.out.println("ID: " + id);
            System.out.println("Titel: " + title);
            System.out.println("Innehåll: " + body);
        } else {
            System.out.println("Det finns inget blogginlägg med detta id");

        }
    }

    //Metod för att uppdatera specifikt inlägg
    public void updateBlogPost() {
        System.out.println("Skriv id:et på inlägget som du vill uppdatera:");
        int id = getUserInt();

        System.out.println("Skriv din nya titel: ");
        String updateTitle = getUserString();

        System.out.println("Skriv innehållet i ditt nya inlägg: ");
        String updateBody = getUserString();

        Blog updateBlog = new Blog(updateTitle, updateBody);

        boolean success = myApiClient.updateBlogPost(id, updateBlog);

        if (success) {
            System.out.println("Du har uppdaterat ditt blogginlägg!");
        } else {
            System.out.println("Det gick inte att uppdatera ditt blogginlägg");
        }

    }

    public String getUserString() {
        Scanner myScan = new Scanner(System.in);

        String myString;

        while (true) {
            try {
                System.out.print("> ");
                myString = myScan.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Felaktig indata");
            }
        }
        return myString;
    }

    public int getUserInt() {
        Scanner myScan = new Scanner(System.in);

        int myInteger;

        while (true) {
            try {
                System.out.print("> ");
                myInteger = Integer.parseInt(myScan.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Felaktig indata");
            }
        }

        return myInteger;
    }

}
