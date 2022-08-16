import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        executorService.submit(() -> {
            int integer = isPalindrome(texts);
            System.out.println("Красивых слов с длиной 3: " + integer + " шт");
            return integer;
        });

        Future<Integer> future1 = executorService.submit(() -> {
            int integer = isIdenticalLetters(texts);
            System.out.println("Красивых слов с длиной 4: " + integer + " шт");
            return integer;
        });

        executorService.submit(() -> {
            int integer = isAscending(texts);
            System.out.println("Красивых слов с длиной 5: " + integer + future1.get() + " шт");
            return integer;
        });

        executorService.shutdown();


        //   Thread beautifulWord3 = new Thread(() -> System.out.println("Красивых слов с длиной 3: " + isPalindrome(texts) + " шт"));
        //  Thread beautifulWord4 = new Thread(() -> System.out.println("Красивых слов с длиной 4: " + isIdenticalLetters(texts) + " шт"));
        // Thread beautifulWord5 = new Thread(() -> System.out.println("Красивых слов с длиной 5: " + isAscending(texts) + " шт"));

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int isPalindrome(String[] array) {
        AtomicInteger integer = new AtomicInteger();
        for (int a = 0; a < array.length; a++) {
            if (array[a].length() == 3 && array[a].equals(
                    new StringBuilder().
                            append(array[a])
                            .reverse()
                            .toString())) {
                integer.addAndGet(1);

            }

        }
        return integer.get();

    }


    public static int isIdenticalLetters(String[] array) {
        AtomicInteger hashCode = new AtomicInteger();
        for (String line : array) {
            if (line.length() == 4 && hashCode(line)) {
                hashCode.addAndGet(1);
            }
        }
        return hashCode.get();
    }

    public static boolean hashCode(String line) {
        AtomicInteger integer = new AtomicInteger(line.charAt(0));
        for (int a = 1; a < line.length(); a++) {
            if (line.charAt(a) != integer.get()) {
                return false;
            }
        }
        return true;
    }

    public static int isAscending(String[] array) {
        AtomicInteger hashCode = new AtomicInteger();
        for (String line : array) {
            if (line.length() == 5 && ascendingHashCode(line)) {
                hashCode.addAndGet(1);
            }
        }
        return hashCode.get();
    }

    public static boolean ascendingHashCode(String line) {
        AtomicInteger integer = new AtomicInteger(line.charAt(0));
        for (int a = 1; a < line.length(); a++) {
            if (line.charAt(a) <= integer.get()) {
                return false;
            }
        }
        return true;
    }
}