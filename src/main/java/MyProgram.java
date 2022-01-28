import java.util.Scanner;

public class MyProgram {

     ApiClient myApiClient;

    public MyProgram() {
        myApiClient = new ApiClient("http://127.0.0.1:8080/api/v1");
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
            System.out.println("6. Ta bort alla inlägg");
            System.out.println("7. Avsluta program");

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

                    break;
                case 4:

                    break;

                case 5:

                    break;
                case 6:
                    clearBlogPosts();
                    break;
                case 7:


            }
        }
    }

    //Metod lista alla blogginlägg
    public void listBlogPosts() {
        Blog[] blogPosts = myApiClient.getBlogPosts();

        System.out.println("Blogginlägg");

        if (blogPosts.length > 0) {
            for (int i = 0; i < blogPosts.length; i++) {
                String title = blogPosts[i].title;
                int id = blogPosts[i].id;

                System.out.printf("-> %s (%d/10)\n", title, id);
            }
        } else {
            System.out.println("Det finns inga blogginlägg :(");
        }
    }

    //Metod skapa ett blogginlägg
    public void addBlogPost() {
        System.out.println("Title?");
        String title = getUserString();

        System.out.println("Skriv ditt inlägg: ");
        String body = getUserString();

        Blog newBlog = new Blog (title, body);

        boolean success = myApiClient.addBlogPost(newBlog);

        if (success) {
            System.out.println("Ett blogginlägg har skapats");
        }
        else {
            System.out.println("Det gick inte att skapa ett blogginlägg :(");
        }
    }

    //Metod ta bort alla blogginlägg
    public void clearBlogPosts() {
        if (myApiClient.clearBlogPosts()) {
            System.out.println("Alla blogginlägg har tagits bort");
        }
        else {
            System.out.println("Det gick inte att ta bort alla blogginlägg");
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
