public class Main {
    public static void main(String[] args) {
        Markov markov = new Markov();
        markov.addFromFile("phrases.txt");
        System.out.println(markov);

        for (int i = 0; i < 10; i++) {
            System.out.println(markov.getSentence());
        }
    }
}
