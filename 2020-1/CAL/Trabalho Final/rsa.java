import java.math.BigInteger;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.io.*;

public class rsa{

    public static int isPrime(BigInteger n, int k, int bits) {
        if (n.compareTo(BigInteger.valueOf(2)) < 0 || n.equals(BigInteger.valueOf(4)))
            return 0;
        if (n.compareTo(BigInteger.valueOf(4)) < 0)
            return 1;
        while (k > 0) {
            Random gerador = new Random();
            BigInteger res = new BigInteger(bits, gerador);
            BigInteger a = BigInteger.TWO.add(res.mod(n.subtract(BigInteger.valueOf(4))));
            if (!(a.modPow(n.subtract(BigInteger.ONE), n)).equals(BigInteger.ONE))
                return 0;
            k--;
        }
        return 1;
    }

    public static BigInteger gdc(BigInteger a, BigInteger b){
        BigInteger remainder;
        do{
            remainder = a.mod(b);
            a = b;
            b = remainder;
        }while(!remainder.equals(BigInteger.ZERO));
        return a;
    }

    public static BigInteger randomBig(int bits, int flag){
        Random gerador = new Random();
        BigInteger res = new BigInteger(bits, gerador);
        if (flag == 1)
            return res;
        else{
            if (res.mod(BigInteger.valueOf(2)) == BigInteger.ZERO)
                res = res.add(BigInteger.ONE);

            while (true) {
                if (isPrime(res, 100, bits) == 1)
                    return res;
                res = res.add(BigInteger.TWO);
            }
        }
    }

    public static BigInteger gcdExtended(BigInteger a, BigInteger b) {
        if (a.compareTo(BigInteger.ZERO) == 0)
            return b;

        return gcdExtended(b.mod(a), a);
    }

    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        String msg = "", msgInt = "";
        String[][] array = new String[2][];
        BigInteger p, q, n, e = BigInteger.ZERO, d, t, codigo, fat1 = BigInteger.ZERO, fat2 = BigInteger.ZERO, x;
        int opcao, i, bits;
        int space;
        long begin, elapsedTime, now;

        while (true){
            System.out.println("----------------------------");
            System.out.println("      Main Menu   ");
            System.out.println("----------------------------");
            System.out.println("1 - Generate Keys");
            System.out.println("2 - Encrypt Message");
            System.out.println("3 - Decrypt Message");
            System.out.println("4 - Brute Force");
            System.out.println("----------------------------");
            System.out.println("      0 to Exit   ");
            opcao = scan.nextInt();

            switch(opcao){
                case 0:
                    scan.close();
                    System.exit(0);
                    break;

                case 1:
                    System.out.println("----------------------------");
                    System.out.println("      Generate Keys   ");
                    System.out.println("----------------------------");
                    System.out.println("Number of Bits?");
                    bits = scan.nextInt();
                    if (bits <= 2 || bits >= 256) {
                        System.out.println("Error! Value not accepted!\nAt least 2 and maximum 255 Bits");
                        break;
                    }
                    ArrayList<String> GK_Space = new ArrayList();
                    ArrayList<String> GK_Time = new ArrayList();
                    space = 0;
                    begin = System.currentTimeMillis();//Time measure
                    p = randomBig(bits, 0);
                    while (true) {
                        space = space + 1;
                        now = System.currentTimeMillis() - begin;
                        GK_Time.add(String.valueOf(now));
                        GK_Space.add(String.valueOf(space));
                        q = randomBig(bits, 0);
                        if (!q.equals(p)) {
                            space = space + 1;
                            now = System.currentTimeMillis() - begin;
                            GK_Time.add(String.valueOf(now));
                            GK_Space.add(String.valueOf(space));
                            break;
                        }
                    }
                    space = space + 1;
                    now = System.currentTimeMillis() - begin;
                    GK_Time.add(String.valueOf(now));
                    GK_Space.add(String.valueOf(space));
                    n = p.multiply(q);
                    t = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
                    while (true) {
                        space = space + 1;
                        now = System.currentTimeMillis() - begin;
                        GK_Time.add(String.valueOf(now));
                        GK_Space.add(String.valueOf(space));
                        x = randomBig(bits / 2, 1);
                        if (gcdExtended(x, t).compareTo(BigInteger.ONE) == 0){
                            e = x;
                            space = space + 1;
                            now = System.currentTimeMillis() - begin;
                            GK_Time.add(String.valueOf(now));
                            GK_Space.add(String.valueOf(space));
                            break;
                        }
                    }
                    d = e.modInverse(t);
                    elapsedTime = System.currentTimeMillis() - begin;
                    System.out.println("Elapsed Time: " + ((double) elapsedTime) + "ms");
                    System.out.println("Public Key " + "n: " + n + " e: " + e);
                    System.out.println("Private Key " +"p: " + p + " q: " + q + " d: " + d);
                    //Graphics Records
                    space = space + 1;
                    now = System.currentTimeMillis() - begin;
                    GK_Time.add(String.valueOf(now));
                    GK_Space.add(String.valueOf(space));
                    FileWriter graphics_GK = new FileWriter("GK_Graphs_Records.csv");
                    PrintWriter writeFile_GK = new PrintWriter(graphics_GK);
                    int size_GK = GK_Space.size();
                    for (i=0; i<size_GK; i++){
                        writeFile_GK.printf("%s,%s\n", GK_Time.get(i), GK_Space.get(i));
                    }
                    writeFile_GK.close();
                    graphics_GK.close();
                    break;

                case 2:
                    System.out.println("----------------------------");
                    System.out.println("      Encrypt Message   ");
                    System.out.println("----------------------------");
                    System.out.println("New Message: ");
                    msg = scan.next();

                    System.out.println("Value of n?");
                    n = scan.nextBigInteger();

                    System.out.println("Value of e?");
                    e = scan.nextBigInteger();

                    FileWriter arq = new FileWriter("Encrypted.txt");
                    PrintWriter gravarArq = new PrintWriter(arq);

                    ArrayList<String> Encrypted_Space = new ArrayList();
                    ArrayList<String> Encrypted_Time = new ArrayList();
                    space = 0;
                    begin = System.currentTimeMillis();

                    space = space + 1;
                    now = System.currentTimeMillis() - begin;
                    Encrypted_Space.add(String.valueOf(now));
                    Encrypted_Time.add(String.valueOf(space));
                    

                    for (i = 0; i < msg.length(); i += 1) {
                        space = space + 1;
                        now = System.currentTimeMillis() - begin;
                        Encrypted_Space.add(String.valueOf(now));
                        Encrypted_Time.add(String.valueOf(space));
                        msgInt = msgInt + BigInteger.valueOf(msg.charAt(i)).modPow(e, n) + "-";
                    }

                    elapsedTime = System.currentTimeMillis() - begin;
                    System.out.println("\nTempo percorrido: " + ((double) elapsedTime) / 1000 + "s");
                    space = space + 1;
                    now = System.currentTimeMillis() - begin;
                    Encrypted_Space.add(String.valueOf(now));
                    Encrypted_Time.add(String.valueOf(space));
                    msgInt = msgInt.substring(0, msgInt.length() - 1) + '\n';
                    gravarArq.printf("%s", msgInt);
                    gravarArq.close();
                    arq.close();
                    FileWriter graphics_Encrypted = new FileWriter("Encrypted_Graphs_Records.csv");
                    PrintWriter writeFile_Encrypted = new PrintWriter(graphics_Encrypted);
                    int size_Encrypted = Encrypted_Space.size();
                    for (i=0; i<size_Encrypted; i++){
                        writeFile_Encrypted.printf("%s,%s\n", Encrypted_Time.get(i), Encrypted_Space.get(i));
                    }
                    writeFile_Encrypted.close();
                    graphics_Encrypted.close();
                    break;

                case 3:
                    System.out.println("----------------------------");
                    System.out.println("      Decrypt Message   ");
                    System.out.println("----------------------------");
                    String file_name;
                    System.out.println("Name of File?");
                    file_name = scan.next();
                    File encrypted = new File(file_name);
                    i = 0;
                    ArrayList<String> Decrypted_Space = new ArrayList();
                    ArrayList<String> Decrypted_Time = new ArrayList();
                    space = 0;
                    begin = System.currentTimeMillis();
                    try {
                        Scanner sub_scan = new Scanner(encrypted);
                        while (sub_scan.hasNext()) {
                            array[i] = (sub_scan.nextLine()).split("-");
                            i++;
                        }
                        sub_scan.close();

                        System.out.println("Value of p?");
                        p = scan.nextBigInteger();

                        System.out.println("Value of q?");
                        q = scan.nextBigInteger();

                        System.out.println("Value of d?");
                        d = scan.nextBigInteger();

                        space = space + 1;
                        now = System.currentTimeMillis() - begin;
                        Decrypted_Space.add(String.valueOf(now));
                        Decrypted_Time.add(String.valueOf(space));

                        n = p.multiply(q);
                        System.out.println("Decrypted Message: ");
                        for (i = 0; i < array[0].length; i += 1) {
                            space = space + 1;
                            now = System.currentTimeMillis() - begin;
                            Decrypted_Space.add(String.valueOf(now));
                            Decrypted_Time.add(String.valueOf(space));
                            try {
                                codigo = new BigInteger(array[0][i]).modPow(d, n);
                                System.out.print(new String(codigo.toByteArray(), "UTF-8"));
                            } catch (UnsupportedEncodingException error) {
                                error.printStackTrace();
                            }
                        }

                        elapsedTime = System.currentTimeMillis() - begin;
                        System.out.println("\nElapsed Time: " + ((double) elapsedTime) + "ms");
                    } catch (IOException error) {
                        System.out.println("Error! No Message Encrypted!\n");
                    }
                    space = space + 1;
                    now = System.currentTimeMillis() - begin;
                    Decrypted_Space.add(String.valueOf(now));
                    Decrypted_Time.add(String.valueOf(space));
                    FileWriter graphics_Decrypted = new FileWriter("Decrypted_Graphs_Records.csv");
                    PrintWriter writeFile_Decrypted = new PrintWriter(graphics_Decrypted);
                    int size_Decrypted = Decrypted_Space.size();
                    for (i=0; i<size_Decrypted; i++){
                        writeFile_Decrypted.printf("%s,%s\n", Decrypted_Time.get(i), Decrypted_Space.get(i));
                    }
                    writeFile_Decrypted.close();
                    graphics_Decrypted.close();
                    break;

                case 4:
                    System.out.println("----------------------------");
                    System.out.println("      Brute Force   ");
                    System.out.println("----------------------------");
                    System.out.println("Value of n?");
                    n = scan.nextBigInteger();

                    System.out.println("Value of e?");
                    e = scan.nextBigInteger();
                    begin = System.currentTimeMillis();
                    x = BigInteger.valueOf(3);
                    try {
                        while (true) {
                            if ((n.mod(x)).compareTo(BigInteger.ZERO) == 0) {
                                fat1 = x;
                                fat2 = n.divide(x);
                                break;
                            }

                            if ((x.add(BigInteger.valueOf(2))).mod(BigInteger.valueOf(3))
                                    .compareTo(BigInteger.ZERO) == 0) {
                                x = x.add(BigInteger.valueOf(4));
                            } else {
                                x = x.add(BigInteger.valueOf(2));
                            }
                        }
                        t = (fat1.subtract(BigInteger.ONE)).multiply(fat2.subtract(BigInteger.ONE));
                        System.out.println("Private Key" + " p: "+ fat1 + " q: " + fat2 + " n: "
                                + fat1.multiply(fat2) + " d: " + e.modInverse(t));
                    } catch (java.lang.ArithmeticException error) {
                        System.out.println("Error! Invalid Key");
                    }
                    elapsedTime = System.currentTimeMillis() - begin;
                    System.out.println("Time Elapsed: " + ((double) elapsedTime) + "ms");
                    break;
            }
        }
    }
}